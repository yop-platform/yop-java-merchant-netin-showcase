package com.wdc.test.shop.controller;

import com.wdc.test.shop.model.Order;
import com.wdc.test.shop.model.Payment;
import com.wdc.test.shop.model.User;
import com.wdc.test.shop.payment.yeepay.YeepayChannel;
import com.wdc.test.shop.payment.yeepay.YeepayNotifyHandler;
import com.wdc.test.shop.payment.yeepay.YeepayRefundNotification;
import com.wdc.test.shop.payment.yeepay.YeepaySettleNotification;
import com.wdc.test.shop.service.OrderService;
import com.wdc.test.shop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

@Controller
@RequestMapping("/payments")
public class PaymentController {
    
    private static final Logger logger = LoggerFactory.getLogger(PaymentController.class);
    
    private final PaymentService paymentService;
    private final OrderService orderService;
    private final YeepayChannel yeepayChannel;
    private final YeepayNotifyHandler notifyHandler;
    
    @Autowired
    public PaymentController(PaymentService paymentService, OrderService orderService, 
                             YeepayChannel yeepayChannel, YeepayNotifyHandler notifyHandler) {
        this.paymentService = paymentService;
        this.orderService = orderService;
        this.yeepayChannel = yeepayChannel;
        this.notifyHandler = notifyHandler;
    }
    
    @PostMapping("/create")
    public String createPayment(@RequestParam Long orderId, @RequestParam String paymentMethod, 
                               HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        Order order = orderService.getOrderById(orderId);
        if (order == null || !order.getUserId().equals(user.getId())) {
            return "redirect:/orders";
        }
        
        Payment payment = paymentService.createPayment(orderId, paymentMethod, order.getTotalAmount());
        return "redirect:/payments/" + payment.getId() + "/pay";
    }
    
    @GetMapping("/{id}/pay")
    public String paymentPage(@PathVariable Long id, HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        Payment payment = paymentService.getPaymentById(id);
        if (payment == null) {
            return "redirect:/orders";
        }
        
        Order order = orderService.getOrderById(payment.getSystemOrderId());
        if (order == null || !order.getUserId().equals(user.getId())) {
            return "redirect:/orders";
        }
        
        String paymentUrl = paymentService.initiatePayment(payment.getId());
        model.addAttribute("payment", payment);
        model.addAttribute("order", order);
        model.addAttribute("paymentUrl", paymentUrl);
        
        return "payment/pay";
    }
    
    @PostMapping("/callback/{method}")
    @ResponseBody
    public String paymentCallback(@PathVariable String method, 
                                 @RequestParam String transactionId, 
                                 @RequestParam String status) {
        logger.info("收到支付回调通知：method={}，transactionId={}，status={}", method, transactionId, status);
        boolean success = paymentService.processPaymentCallback(method, transactionId, status);
        return success ? "success" : "failure";
    }
    
    /**
     * 处理易宝支付结果通知
     * 接收并处理易宝支付服务器发送的支付结果通知
     */
    @PostMapping("/yeepay/notify")
    @ResponseBody
    public String yeepayNotify(HttpServletRequest request) {
        logger.info("收到易宝支付结果通知");
        
        try {
            // 处理易宝支付通知
            boolean success = yeepayChannel.processNotification(request);
            
            // 根据处理结果返回响应
            String response = notifyHandler.buildResponse(success);
            logger.info("响应易宝支付通知：{}", response);
            return response;
        } catch (Exception e) {
            logger.error("处理易宝支付通知时发生异常", e);
            return notifyHandler.buildResponse(false);
        }
    }
    
    @PostMapping("/refund")
    public String refundPayment(@RequestParam Long paymentId, 
                              @RequestParam(defaultValue = "0") double refundAmount,
                              @RequestParam(defaultValue = "用户申请退款") String refundReason,
                              HttpSession session, Model model) {
        logger.info("收到退款请求：paymentId={}，refundAmount={}，refundReason={}", paymentId, refundAmount, refundReason);
        
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        
        Payment payment = paymentService.getPaymentById(paymentId);
        if (payment == null) {
            model.addAttribute("error", "未找到支付记录");
            return "redirect:/orders";
        }
        
        Order order = orderService.getOrderById(payment.getSystemOrderId());
        if (order == null || !order.getUserId().equals(user.getId())) {
            model.addAttribute("error", "无权操作该订单");
            return "redirect:/orders";
        }
        
        // 若退款金额为0，则全额退款
        if (refundAmount <= 0) {
            refundAmount = payment.getAmount();
        }
        
        boolean success = paymentService.refund(paymentId, refundAmount, refundReason);
        
        if (success) {
            model.addAttribute("message", "退款申请已提交，请等待处理");
        } else {
            model.addAttribute("error", "退款申请失败，请联系客服");
        }
        
        return "redirect:/orders/" + order.getId();
    }
    
    @PostMapping("/refund-callback/YEEPAY")
    @ResponseBody
    public String yeepayRefundNotify(HttpServletRequest request) {
        logger.info("收到易宝退款结果通知");
        try {
            // 解密并验签退款通知
            YeepayRefundNotification notification = notifyHandler.handleRefundNotify(request);
            if (notification == null) {
                logger.error("退款通知验签或解析失败");
                return notifyHandler.buildResponse(false);
            }
            logger.info("退款通知内容: refundRequestId={}, status={}, uniqueRefundNo={}",
                    notification.getRefundRequestId(), notification.getStatus(), notification.getUniqueRefundNo());
            // TODO: 根据refundRequestId、status等更新本地退款单状态，可参考清算/支付回调的处理逻辑
            // 这里只做日志和幂等返回
            return notifyHandler.buildResponse(true);
        } catch (Exception e) {
            logger.error("处理易宝退款通知时发生异常", e);
            return notifyHandler.buildResponse(false);
        }
    }
    
    /**
     * 处理易宝清算结果通知
     * 接收并处理易宝支付服务器发送的清算结果通知
     */
    @PostMapping("/yeepay/settle-notify")
    @ResponseBody
    public String yeepaySettleNotify(HttpServletRequest request) {
        logger.info("收到易宝清算结果通知");
        try {
            YeepaySettleNotification notification = notifyHandler.handleSettleNotify(request);
            if (notification == null) {
                logger.error("清算通知验签或解析失败");
                return notifyHandler.buildResponse(false);
            }
            logger.info("清算通知内容: orderId={}, status={}, uniqueOrderNo={}, csSuccessDate={}",
                    notification.getOrderId(), notification.getStatus(), notification.getUniqueOrderNo(), notification.getCsSuccessDate());
            // TODO: 根据orderId、status等更新本地清算状态，可自定义业务逻辑
            return notifyHandler.buildResponse(true);
        } catch (Exception e) {
            logger.error("处理易宝清算通知时发生异常", e);
            return notifyHandler.buildResponse(false);
        }
    }
}