package com.recommendation.OrderService.dto;

import com.recommendation.OrderService.entity.*;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.recommendation.OrderService.repo.OrderRepository;

@Service
public class PaymentEventConsumer {

    private final OrderRepository repository;

    public PaymentEventConsumer(OrderRepository repository) {
        this.repository = repository;
    }

    @KafkaListener(topics = "payment-topic", groupId = "order-group")
    public void consume(PaymentEvent event) {

        Order order = repository.findById(event.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setStatus(event.getStatus());

        repository.save(order);

        System.out.println("Order updated to: " + event.getStatus());
    }
}