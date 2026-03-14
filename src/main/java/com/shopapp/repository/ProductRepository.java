package com.shopapp.repository;

import com.shopapp.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByCategory(String category);
    List<Product> findByFeaturedTrue();
    List<Product> findByNameContainingIgnoreCase(String name);
}