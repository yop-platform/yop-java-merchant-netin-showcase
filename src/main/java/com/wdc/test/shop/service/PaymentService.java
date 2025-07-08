package com.wdc.test.shop.service;

import com.wdc.test.shop.model.Payment;
import com.wdc.test.shop.payment.PaymentChannel;
import com.wdc.test.shop.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
public class PaymentService {
    private final PaymentRepository paymentRepository;
    private final OrderService orderService;
    private final Map<String, PaymentChannel> paymentChannels = new HashMap<>();

    private static final String PROJECT_PREFIX = "SIMPSHOP";

    @Autowired
    public PaymentService(PaymentRepository paymentRepository, OrderService orderService, List<PaymentChannel> channels) {
        this.paymentRepository = paymentRepository;
        this.orderService = orderService;
        
        // 注册所有支付通道
        for (PaymentChannel channel : channels) {
            paymentChannels.put(channel.getChannelName(), channel);
        }
    }

    public Payment createPayment(Long systemOrderId, String paymentMethod, double amount) {
        // 生成全局唯一的订单ID，用于支付平台识别
        String orderId = generateGlobalOrderId(paymentMethod);
        
        Payment payment = new Payment(null, systemOrderId, orderId, paymentMethod, amount, "PENDING");
        return paymentRepository.save(payment);
    }
    
    /**
     * 生成全局唯一的订单ID，用于支付平台识别
     * 格式: PROJECT_PREFIX-PAYMENT_METHOD-UUID
     */
    private String generateGlobalOrderId(String paymentMethod) {
        // 生成不包含'-'的简短UUID
        String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        return PROJECT_PREFIX + "_" + paymentMethod + "_" + uuid;
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

    /**
     * 申请退款
     * 
     * @param paymentId 支付ID
     * @param refundAmount 退款金额
     * @param refundReason 退款原因
     * @return 退款结果，成功返回true，失败返回false
     */
    public boolean refund(Long paymentId, double refundAmount, String refundReason) {
        Payment payment = paymentRepository.findById(paymentId);
        if (payment != null) {
            PaymentChannel channel = paymentChannels.get(payment.getPaymentMethod());
            if (channel != null) {
                // 生成唯一退款请求号并存储
                String refundRequestId = generateRefundRequestId(payment);
                payment.setRefundRequestId(refundRequestId);
                paymentRepository.save(payment);
                return channel.refund(payment, refundAmount, refundReason);
            }
        }
        return false;
    }

    /**
     * 生成唯一退款请求号，格式：orderId-REFUND-时间戳
     */
    private String generateRefundRequestId(Payment payment) {
        return payment.getOrderId() + "-REFUND-" + System.currentTimeMillis();
    }

    public Payment getPaymentById(Long id) {
        return paymentRepository.findById(id);
    }

    public Payment getPaymentBySystemOrderId(Long systemOrderId) {
        return paymentRepository.findBySystemOrderId(systemOrderId);
    }

    public void updatePayment(Payment payment) {
        paymentRepository.save(payment);
    }

    public void updateOrderStatus(Long systemOrderId, String status) {
        orderService.updateOrderStatus(systemOrderId, status);
    }
}