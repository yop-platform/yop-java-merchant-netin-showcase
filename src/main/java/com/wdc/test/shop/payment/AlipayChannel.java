package com.wdc.test.shop.payment;

import com.wdc.test.shop.model.Payment;
import com.wdc.test.shop.repository.PaymentRepository;
import com.wdc.test.shop.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Component
public class AlipayChannel implements PaymentChannel {
    
    private static final Logger logger = LoggerFactory.getLogger(AlipayChannel.class);
    
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
        logger.info("==== 开始支付宝支付下单流程 ====");
        logger.info("支付ID：{}，系统订单ID：{}，全局订单ID：{}", 
                payment.getId(), payment.getSystemOrderId(), payment.getOrderId());
        
        try {
            // 模拟支付宝支付流程
            String transactionId = "ALI" + UUID.randomUUID().toString().replace("-", "").substring(0, 10);
            payment.setTransactionId(transactionId);
            paymentRepository.save(payment);
            
            logger.info("生成支付宝交易ID：{}", transactionId);
            
            // 返回支付链接或表单
            return "https://alipay.example.com/pay?orderId=" + payment.getOrderId() + 
                    "&amount=" + payment.getAmount() + "&transactionId=" + transactionId;
        } catch (Exception e) {
            logger.error("支付宝支付下单异常", e);
            return null;
        } finally {
            logger.info("==== 结束支付宝支付下单流程 ====");
        }
    }

    @Override
    public boolean processCallback(String transactionId, String status) {
        try {
            logger.info("==== 开始处理支付宝回调 ====");
            logger.info("交易ID：{}，状态：{}", transactionId, status);
            
            Payment payment = paymentRepository.findAll().stream()
                    .filter(p -> transactionId.equals(p.getTransactionId()))
                    .findFirst()
                    .orElse(null);
                    
            if (payment != null) {
                logger.info("找到对应支付记录，支付ID：{}，原状态：{}", payment.getId(), payment.getStatus());
                
                payment.setStatus(status);
                paymentRepository.save(payment);
                logger.info("更新支付状态为：{}", status);
                
                // 更新订单状态
                if ("SUCCESS".equals(status)) {
                    logger.info("支付成功，更新订单状态为已支付，系统订单ID：{}", payment.getSystemOrderId());
                    orderService.updateOrderStatus(payment.getSystemOrderId(), "PAID");
                }
                return true;
            }
            
            logger.warn("未找到交易ID对应的支付记录：{}", transactionId);
            return false;
        } catch (Exception e) {
            // 记录异常信息
            logger.error("处理支付宝回调时发生错误", e);
            return false;
        } finally {
            logger.info("==== 结束处理支付宝回调 ====");
        }
    }
    
    @Override
    public boolean refund(Payment payment, double refundAmount, String refundReason) {
        logger.info("==== 开始支付宝退款流程（模拟实现）====");
        logger.info("支付ID：{}，系统订单ID：{}，全局订单ID：{}，退款金额：{}，退款原因：{}", 
                payment.getId(), payment.getSystemOrderId(), payment.getOrderId(), refundAmount, refundReason);
        
        try {
            // 校验支付状态
            if (!"SUCCESS".equals(payment.getStatus())) {
                logger.error("支付状态不是成功状态，无法退款，当前状态：{}", payment.getStatus());
                return false;
            }
            
            // 校验退款金额
            if (refundAmount > payment.getAmount()) {
                logger.error("退款金额（{}）大于支付金额（{}），无法退款", refundAmount, payment.getAmount());
                return false;
            }
            
            // 模拟支付宝退款处理
            logger.info("模拟支付宝退款API调用成功，订单ID：{}", payment.getOrderId());
            
            // 更新支付状态为已退款
            payment.setStatus("REFUNDED");
            paymentRepository.save(payment);
            
            // 更新订单状态为已退款
            orderService.updateOrderStatus(payment.getSystemOrderId(), "REFUNDED");
            
            logger.info("支付宝退款成功处理完成");
            return true;
        } catch (Exception e) {
            logger.error("处理支付宝退款时发生错误", e);
            return false;
        } finally {
            logger.info("==== 结束支付宝退款流程（模拟实现）====");
        }
    }
}