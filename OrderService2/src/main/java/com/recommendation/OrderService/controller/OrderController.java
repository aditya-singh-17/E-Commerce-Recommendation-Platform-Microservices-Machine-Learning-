package com.recommendation.OrderService.controller;


import org.springframework.web.bind.annotation.*;

import com.recommendation.OrderService.dto.OrderRequest;
import com.recommendation.OrderService.entity.Order;
import com.recommendation.OrderService.service.OrderService;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping
    public Order createOrder(@RequestBody OrderRequest request) {
        return service.createOrder(request);
    }

    @GetMapping("/user/{userId}")
    public List<Order> getUserOrders(@PathVariable Long userId) {
        return service.getOrdersByUser(userId);
    }
}
