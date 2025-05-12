package com.wdc.test.shop.service;

import com.wdc.test.shop.model.Payment;
import com.wdc.test.shop.payment.PaymentChannel;
import com.wdc.test.shop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderService orderService;
    private final Map<String, PaymentChannel> paymentChannels = new HashMap<>();

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, OrderService orderService, List<PaymentChannel> channels) {
        this.paymentRepository = paymentRepository;
        this.orderService = orderService;
        
        // 注册所有支付通道
        for (PaymentChannel channel : channels) {
            paymentChannels.put(channel.getChannelName(), channel);
        }
    }

    public Payment createPayment(Long orderId, String paymentMethod, double amount) {
        Payment payment = new Payment(null, orderId, paymentMethod, amount, "PENDING");
        return paymentRepository.save(payment);
    }

    public String initiatePayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId);
        if (payment != null) {
            PaymentChannel channel = paymentChannels.get(payment.getPaymentMethod());
            if (channel != null) {
                return channel.initiatePayment(payment);
            }
        }
        return null;
    }

    public boolean processPaymentCallback(String paymentMethod, String transactionId, String status) {
        PaymentChannel channel = paymentChannels.get(paymentMethod);
        if (channel != null) {
            return channel.processCallback(transactionId, status);
        }
        return false;
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public Payment getPaymentByOrderId(Long orderId) {
        return paymentRepository.findByOrderId(orderId);
    }

    public void updatePayment(Payment payment) {
        paymentRepository.save(payment);
    }

    public void updateOrderStatus(Long orderId, String status) {
        orderService.updateOrderStatus(orderId, status);
    }
}