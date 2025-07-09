package com.wdc.test.shop.repository;

import com.wdc.test.shop.model.Payment;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class PaymentRepository {
    private final Map<Long, Payment> payments = new HashMap<>();
    private long nextId = 1;

    public Payment save(Payment payment) {
        if (payment.getId() == null) {
            payment.setId(nextId++);
        }
        payments.put(payment.getId(), payment);
        return payment;
    }

    public Payment findById(Long id) {
        return payments.get(id);
    }

    public Payment findBySystemOrderId(Long systemOrderId) {
        return payments.values().stream()
                .filter(payment -> systemOrderId.equals(payment.getSystemOrderId()))
                .findFirst()
                .orElse(null);
    }
    
    public Payment findByOrderId(String orderId) {
        return payments.values().stream()
                .filter(payment -> orderId.equals(payment.getOrderId()))
                .findFirst()
                .orElse(null);
    }

    public List<Payment> findAll() {
        return new ArrayList<>(payments.values());
    }
}