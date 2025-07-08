package com.wdc.test.shop.repository;

import com.wdc.test.shop.model.Product;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ProductRepository {
    private final Map<Long, Product> products = new HashMap<>();
    private long nextId = 1;

    public ProductRepository() {
        // 初始化两个商品
        save(new Product(null, "iPhone 13", "Apple iPhone 13 128GB", 0.01, 100));
        save(new Product(null, "MacBook Pro", "Apple MacBook Pro 14-inch", 12999.00, 50));
    }

    public Product save(Product product) {
        if (product.getId() == null) {
            product.setId(nextId++);
        }
        products.put(product.getId(), product);
        return product;
    }

    public Product findById(Long id) {
        return products.get(id);
    }

    public List<Product> findAll() {
        return new ArrayList<>(products.values());
    }

    public void updateStock(Long productId, int quantity) {
        Product product = findById(productId);
        if (product != null) {
            product.setStock(product.getStock() - quantity);
            save(product);
        }
    }
}