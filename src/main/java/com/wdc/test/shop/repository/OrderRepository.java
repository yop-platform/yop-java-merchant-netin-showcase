package com.wdc.test.shop.repository;

import com.wdc.test.shop.model.Order;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class OrderRepository {
    private final Map<Long, Order> orders = new HashMap<>();
    private long nextId = 1;

    public Order save(Order order) {
        if (order.getId() == null) {
            order.setId(nextId++);
        }
        orders.put(order.getId(), order);
        return order;
    }

    public Order findById(Long id) {
        return orders.get(id);
    }

    public List<Order> findByUserId(Long userId) {
        List<Order> userOrders = new ArrayList<>();
        for (Order order : orders.values()) {
            if (order.getUserId().equals(userId)) {
                userOrders.add(order);
            }
        }
        return userOrders;
    }

    public List<Order> findAll() {
        return new ArrayList<>(orders.values());
    }
}