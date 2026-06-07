package com.recommendation.OrderService.dto;



public class OrderItemRequest {

    private Long productId;
    private int quantity;
    private double price;

    public OrderItemRequest() {}

    public Long getProductId() { return productId; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }

    public void setProductId(Long productId) { this.productId = productId; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPrice(double price) { this.price = price; }
}