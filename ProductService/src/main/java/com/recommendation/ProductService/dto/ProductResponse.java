package com.recommendation.ProductService.dto;


public class ProductResponse {

    private Long id;
    private String name;
    private String category;
    private double price;
    private int stock;

    public ProductResponse(Long id, String name, String category, double price, int stock) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.stock = stock;
    }

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getCategory() { return category; }
    public double getPrice() { return price; }
    public int getStock() { return stock; }
}