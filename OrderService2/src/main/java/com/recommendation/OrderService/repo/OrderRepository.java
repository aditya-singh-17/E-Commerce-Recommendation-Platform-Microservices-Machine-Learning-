package com.recommendation.OrderService.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recommendation.OrderService.entity.Order;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long userId);
}