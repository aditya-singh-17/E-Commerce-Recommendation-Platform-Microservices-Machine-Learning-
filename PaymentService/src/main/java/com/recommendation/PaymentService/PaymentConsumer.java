package com.recommendation.PaymentService;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.recommendation.PaymentService.dto.OrderEvent;
import com.recommendation.PaymentService.dto.PaymentEvent;

import java.util.Random;

@Service
public class PaymentConsumer {

    private final KafkaProducer producer;

    public PaymentConsumer(KafkaProducer producer) {
        this.producer = producer;
    }

    @KafkaListener(topics = "order-topic", groupId = "payment-group")
    public void consume(OrderEvent event) {

        System.out.println("Received order: " + event.getOrderId());

        // 🔥 simulate payment
        boolean success = new Random().nextBoolean();

        String status = success ? "PAID" : "FAILED";

        System.out.println("Payment status: " + status);

        // 🔥 SEND RESULT BACK
        producer.sendPaymentEvent(
                new PaymentEvent(event.getOrderId(), status)
        );
    }
}