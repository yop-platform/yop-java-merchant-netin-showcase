package com.wdc.test.shop.payment;

import com.wdc.test.shop.model.Payment;
import com.wdc.test.shop.repository.PaymentRepository;
import com.wdc.test.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class AlipayChannel implements PaymentChannel {
    
    private final PaymentRepository paymentRepository;
    private final OrderService orderService;
    
    @Autowired
    public AlipayChannel(PaymentRepository paymentRepository, OrderService orderService) {
        this.paymentRepository = paymentRepository;
        this.orderService = orderService;
    }
    
    @Override
    public String getChannelName() {
        return "ALIPAY";
    }

    @Override
    public String initiatePayment(Payment payment) {
        // 模拟支付宝支付流程
        String transactionId = "ALI" + UUID.randomUUID().toString().replace("-", "").substring(0, 10);
        payment.setTransactionId(transactionId);
        paymentRepository.save(payment);
        
        // 返回支付链接或表单
        return "https://alipay.example.com/pay?orderId=" + payment.getOrderId() + "&amount=" + payment.getAmount() + "&transactionId=" + transactionId;
    }

    @Override
    public boolean processCallback(String transactionId, String status) {
        try {
            Payment payment = paymentRepository.findAll().stream()
                    .filter(p -> transactionId.equals(p.getTransactionId()))
                    .findFirst()
                    .orElse(null);
                    
            if (payment != null) {
                payment.setStatus(status);
                paymentRepository.save(payment);
                
                // 更新订单状态
                if ("SUCCESS".equals(status)) {
                    orderService.updateOrderStatus(payment.getOrderId(), "PAID");
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            // 记录异常信息
            System.err.println("处理支付宝回调时发生错误: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}