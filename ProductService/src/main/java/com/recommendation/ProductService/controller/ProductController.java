package com.recommendation.ProductService.controller;

import org.springframework.web.bind.annotation.*;

import com.recommendation.ProductService.dto.ProductRequest;
import com.recommendation.ProductService.dto.ProductResponse;
import com.recommendation.ProductService.service.ProductService;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @PostMapping
    public ProductResponse addProduct(@RequestBody ProductRequest request) {
        return service.addProduct(request);
    }

    @GetMapping
    public List<ProductResponse> getAllProducts() {
        return service.getAllProducts();
    }

    @GetMapping("/category/{category}")
    public List<ProductResponse> getByCategory(@PathVariable String category) {
        return service.searchByCategory(category);
    }

    @GetMapping("/search")
    public List<ProductResponse> search(@RequestParam String keyword) {
        return service.searchByName(keyword);
    }
    @GetMapping("/{id}")
    public ProductResponse getProductById(@PathVariable Long id) {
        return service.getProductById(id);
    }
}