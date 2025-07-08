package com.wdc.test.shop.payment;

import com.wdc.test.shop.model.Payment;

public interface PaymentChannel {
    String getChannelName();
    String initiatePayment(Payment payment);
    boolean processCallback(String transactionId, String status);
    
    /**
     * 退款操作
     * 
     * @param payment 支付记录
     * @param refundAmount 退款金额
     * @param refundReason 退款原因
     * @return 退款结果，成功返回true，失败返回false
     */
    boolean refund(Payment payment, double refundAmount, String refundReason);
}