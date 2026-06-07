package com.recommendation.ProductService.service;



import org.springframework.stereotype.Service;

import com.recommendation.ProductService.dto.ProductRequest;
import com.recommendation.ProductService.dto.ProductResponse;
import com.recommendation.ProductService.entity.Product;
import com.recommendation.ProductService.repo.ProductRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductService {

    private final ProductRepository repository;

    public ProductService(ProductRepository repository) {
        this.repository = repository;
    }

    public ProductResponse addProduct(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setCategory(request.getCategory());
        product.setPrice(request.getPrice());
        product.setStock(request.getStock());

        Product saved = repository.save(product);

        return mapToResponse(saved);
    }

    public List<ProductResponse> getAllProducts() {
        return repository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> searchByCategory(String category) {
        return repository.findByCategory(category)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    public List<ProductResponse> searchByName(String keyword) {
        return repository.findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    private ProductResponse mapToResponse(Product product) {
        return new ProductResponse(
                product.getId(),
                product.getName(),
                product.getCategory(),
                product.getPrice(),
                product.getStock()
        );
    }
    public ProductResponse getProductById(Long id) {
        Product product = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        return mapToResponse(product);
    }
}