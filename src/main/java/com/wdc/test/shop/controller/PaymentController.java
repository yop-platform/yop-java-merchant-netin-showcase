package com.wdc.test.shop.controller;

import com.wdc.test.shop.model.Order;
import com.wdc.test.shop.model.Payment;
import com.wdc.test.shop.model.User;
import com.wdc.test.shop.service.OrderService;
import com.wdc.test.shop.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/payments")
public class PaymentController {
    
    private final PaymentService paymentService;
    private final OrderService orderService;
    
    @Autowired
    public PaymentController(PaymentService paymentService, OrderService orderService) {
        this.paymentService = paymentService;
        this.orderService = orderService;
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
        
        Order order = orderService.getOrderById(payment.getOrderId());
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
        boolean success = paymentService.processPaymentCallback(method, transactionId, status);
        return success ? "success" : "failure";
    }
}