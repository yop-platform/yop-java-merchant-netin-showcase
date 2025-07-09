package com.wdc.test.shop.controller;

import com.wdc.test.shop.model.Order;
import com.wdc.test.shop.model.Payment;
import com.wdc.test.shop.model.Product;
import com.wdc.test.shop.model.User;
import com.wdc.test.shop.payment.yeepay.YeepayChannel;
import com.wdc.test.shop.payment.yeepay.YeepayConfig;
import com.wdc.test.shop.payment.yeepay.YeepayOrderQueryRequest;
import com.wdc.test.shop.payment.yeepay.YeepayOrderQueryResponse;
import com.wdc.test.shop.payment.yeepay.YeepayRefundQueryRequest;
import com.wdc.test.shop.payment.yeepay.YeepayRefundQueryResponse;
import com.wdc.test.shop.service.OrderService;
import com.wdc.test.shop.service.PaymentService;
import com.wdc.test.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/orders")
public class OrderController {
    
    private final OrderService orderService;
    private final ProductService productService;
    private final PaymentService paymentService;
    private final YeepayChannel yeepayChannel;
    private final YeepayConfig yeepayConfig;
    
    @Autowired
    public OrderController(OrderService orderService, ProductService productService, PaymentService paymentService, YeepayChannel yeepayChannel, YeepayConfig yeepayConfig) {
        this.orderService = orderService;
        this.productService = productService;
        this.paymentService = paymentService;
        this.yeepayChannel = yeepayChannel;
        this.yeepayConfig = yeepayConfig;
    }
    
    @GetMapping
    public String listOrders(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        List<Order> orders = orderService.getOrdersByUserId(user.getId());
        model.addAttribute("orders", orders);
        return "order/list";
    }
    
    @GetMapping("/{id}")
    public String orderDetail(@PathVariable Long id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        Order order = orderService.getOrderById(id);
        if (order == null || !order.getUserId().equals(user.getId())) {
            return "redirect:/orders";
        }
        
        Product product = productService.getProductById(order.getProductId());
        Payment payment = paymentService.getPaymentBySystemOrderId(order.getId());
        
        model.addAttribute("order", order);
        model.addAttribute("product", product);
        model.addAttribute("payment", payment);
        
        return "order/detail";
    }
    
    @PostMapping("/create")
    public String createOrder(@RequestParam Long productId, @RequestParam int quantity, 
                             HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        Order order = orderService.createOrder(user.getId(), productId, quantity);
        if (order == null) {
            model.addAttribute("error", "创建订单失败，可能是库存不足");
            return "redirect:/products/" + productId;
        }
        
        return "redirect:/orders/" + order.getId();
    }

    /**
     * 查单接口，返回JSON
     */
    @ResponseBody
    @GetMapping("/queryOrder")
    public ResponseEntity<?> queryOrder(@RequestParam Long orderId) {
        Payment payment = paymentService.getPaymentBySystemOrderId(orderId);
        if (payment == null || !"YEEPAY".equals(payment.getPaymentMethod())) {
            return ResponseEntity.badRequest().body("未找到易宝支付信息");
        }
        YeepayOrderQueryRequest req = new YeepayOrderQueryRequest();
        req.setParentMerchantNo(yeepayConfig.getStandardMerchantNo());
        req.setMerchantNo(yeepayConfig.getStandardMerchantNo());
        req.setOrderId(payment.getOrderId());
        YeepayOrderQueryResponse resp = yeepayChannel.queryOrder(req);
        if (resp != null && resp.isSuccess() && "SUCCESS".equalsIgnoreCase(resp.getStatus())) {
            payment.setStatus("SUCCESS");
            payment.setTransactionId(resp.getUniqueOrderNo());
            payment.setRawResponse(resp.toString());
            paymentService.updatePayment(payment);
            paymentService.updateOrderStatus(payment.getSystemOrderId(), "PAID");
        }
        return ResponseEntity.ok(resp);
    }

    /**
     * 查退款接口，返回JSON
     */
    @ResponseBody
    @GetMapping("/queryRefund")
    public ResponseEntity<?> queryRefund(@RequestParam Long orderId) {
        Payment payment = paymentService.getPaymentBySystemOrderId(orderId);
        if (payment == null || !"YEEPAY".equals(payment.getPaymentMethod())) {
            return ResponseEntity.badRequest().body("未找到易宝支付信息");
        }
        if (payment.getRefundRequestId() == null || payment.getRefundRequestId().isEmpty()) {
            return ResponseEntity.badRequest().body("未找到退款请求号，请先发起退款");
        }
        YeepayRefundQueryRequest req = new YeepayRefundQueryRequest();
        req.setParentMerchantNo(yeepayConfig.getStandardMerchantNo());
        req.setMerchantNo(yeepayConfig.getStandardMerchantNo());
        req.setOrderId(payment.getOrderId());
        req.setRefundRequestId(payment.getRefundRequestId());
        YeepayRefundQueryResponse resp = yeepayChannel.queryRefund(req);
        // 查到退款成功/失败/关闭等，自动同步本地状态
        if (resp != null && resp.isSuccess()) {
            if ("SUCCESS".equalsIgnoreCase(resp.getStatus())) {
                payment.setStatus("REFUNDED");
                payment.setRawResponse(resp.toString());
                paymentService.updatePayment(payment);
                paymentService.updateOrderStatus(payment.getSystemOrderId(), "REFUNDED");
            } else if ("FAILED".equalsIgnoreCase(resp.getStatus()) || "CANCEL".equalsIgnoreCase(resp.getStatus())) {
                payment.setStatus("REFUND_FAILED");
                payment.setRawResponse(resp.toString());
                paymentService.updatePayment(payment);
            }
        }
        return ResponseEntity.ok(resp);
    }
}