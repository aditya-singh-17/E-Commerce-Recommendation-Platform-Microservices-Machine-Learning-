package com.recommendation.OrderService.dto;

public class PaymentEvent {

    private Long orderId;
    private String status; // PAID or FAILED

    public PaymentEvent() {}

    public PaymentEvent(Long orderId, String status) {
        this.orderId = orderId;
        this.status = status;
    }

    public Long getOrderId() { return orderId; }
    public String getStatus() { return status; }

    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public void setStatus(String status) { this.status = status; }
}
