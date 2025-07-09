package com.wdc.test.shop.repository;

import com.wdc.test.shop.model.User;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class UserRepository {
    private final Map<Long, User> users = new HashMap<>();
    private long nextId = 1;

    public UserRepository() {
        // 初始化一个用户
        save(new User(null, "user1", "password", "测试用户", "user1@example.com", "北京市海淀区"));
    }

    public User save(User user) {
        if (user.getId() == null) {
            user.setId(nextId++);
        }
        users.put(user.getId(), user);
        return user;
    }

    public User findById(Long id) {
        return users.get(id);
    }

    public User findByUsername(String username) {
        return users.values().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst()
                .orElse(null);
    }
}