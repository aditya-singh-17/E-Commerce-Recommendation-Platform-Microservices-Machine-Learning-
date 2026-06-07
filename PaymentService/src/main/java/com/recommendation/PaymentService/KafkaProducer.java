package com.recommendation.PaymentService;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.recommendation.PaymentService.dto.PaymentEvent;

@Service
public class KafkaProducer {

    private final KafkaTemplate<String, PaymentEvent> kafkaTemplate;

    public KafkaProducer(KafkaTemplate<String, PaymentEvent> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendPaymentEvent(PaymentEvent event) {
        kafkaTemplate.send("payment-topic", event);
    }
}
