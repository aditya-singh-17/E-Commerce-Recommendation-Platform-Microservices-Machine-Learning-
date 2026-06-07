package com.recommendation.OrderService.service;

import org.springframework.stereotype.Service;

import com.recommendation.OrderService.dto.OrderEvent;
import com.recommendation.OrderService.dto.OrderItemRequest;
import com.recommendation.OrderService.dto.OrderRequest;
import com.recommendation.OrderService.dto.ProductResponse;
import com.recommendation.OrderService.entity.Order;
import com.recommendation.OrderService.entity.OrderItem;
import com.recommendation.OrderService.repo.OrderRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.reactive.function.client.WebClient;
@Service
public class OrderService {
	private final KafkaProducer kafkaProducer;

    private final OrderRepository repository;
    private final WebClient webClient;

    public OrderService(OrderRepository repository, WebClient webClient,KafkaProducer kafkaProducer) {
        this.repository = repository;
        this.webClient = webClient;
        this.kafkaProducer=kafkaProducer;
        
    }

    public Order createOrder(OrderRequest request) {

        Order order = new Order();
        order.setUserId(request.getUserId());
        order.setStatus("CREATED");

        List<OrderItem> items = new ArrayList<>();
        double total = 0;

        for (OrderItemRequest itemReq : request.getItems()) {

            //  CALL PRODUCT SERVICE
            ProductResponse product = webClient.get()
                    .uri("http://localhost:8081/api/products/" + itemReq.getProductId())
                    .retrieve()
                    .bodyToMono(ProductResponse.class)
                    .block();

            if (product == null) {
                throw new RuntimeException("Product not found");
            }

            if (product.getStock() < itemReq.getQuantity()) {
                throw new RuntimeException("Insufficient stock");
            }

            OrderItem item = new OrderItem();
            item.setProductId(product.getId());
            item.setQuantity(itemReq.getQuantity());
            item.setPrice(product.getPrice()); // 🔥 REAL PRICE

            total += product.getPrice() * itemReq.getQuantity();

            item.setOrder(order);
            items.add(item);
        }
      
        order.setItems(items);
        order.setTotalAmount(total);

        Order savedOrder = repository.save(order);

        kafkaProducer.sendOrderEvent(
            new OrderEvent(savedOrder.getId(), "CREATED")
        );

        return savedOrder;
        
    }
    public List<Order> getOrdersByUser(Long userId) {
        return repository.findByUserId(userId);
    }
    
}