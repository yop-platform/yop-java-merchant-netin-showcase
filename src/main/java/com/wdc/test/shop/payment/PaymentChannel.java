package com.wdc.test.shop.payment;

import com.wdc.test.shop.model.Payment;

public interface PaymentChannel {
    String getChannelName();
    String initiatePayment(Payment payment);
    boolean processCallback(String transactionId, String status);
}