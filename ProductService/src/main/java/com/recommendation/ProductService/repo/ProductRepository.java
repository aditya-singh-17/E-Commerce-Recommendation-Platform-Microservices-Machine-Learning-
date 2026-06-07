package com.recommendation.ProductService.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.recommendation.ProductService.entity.Product;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory(String category);

    List<Product> findByNameContainingIgnoreCase(String keyword);
}