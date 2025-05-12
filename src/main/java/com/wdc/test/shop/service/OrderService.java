package com.wdc.test.shop.service;

import com.wdc.test.shop.model.Order;
import com.wdc.test.shop.model.Product;
import com.wdc.test.shop.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductService productService;

    @Autowired
    public OrderService(OrderRepository orderRepository, ProductService productService) {
        this.orderRepository = orderRepository;
        this.productService = productService;
    }

    public Order createOrder(Long userId, Long productId, int quantity) {
        Product product = productService.getProductById(productId);
        if (product == null || product.getStock() < quantity) {
            return null;
        }

        double totalAmount = product.getPrice() * quantity;
        Order order = new Order(null, userId, productId, quantity, totalAmount, "PENDING");
        
        // 更新库存
        productService.updateProductStock(productId, quantity);
        
        return orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public void updateOrderStatus(Long orderId, String status) {
        Order order = getOrderById(orderId);
        if (order != null) {
            order.setStatus(status);
            orderRepository.save(order);
        }
    }
}