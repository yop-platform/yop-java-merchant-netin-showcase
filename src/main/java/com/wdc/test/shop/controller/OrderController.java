package com.wdc.test.shop.controller;

import com.wdc.test.shop.model.Order;
import com.wdc.test.shop.model.Payment;
import com.wdc.test.shop.model.Product;
import com.wdc.test.shop.model.User;
import com.wdc.test.shop.service.OrderService;
import com.wdc.test.shop.service.PaymentService;
import com.wdc.test.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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
    
    @Autowired
    public OrderController(OrderService orderService, ProductService productService, PaymentService paymentService) {
        this.orderService = orderService;
        this.productService = productService;
        this.paymentService = paymentService;
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
        Payment payment = paymentService.getPaymentByOrderId(order.getId());
        
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
}