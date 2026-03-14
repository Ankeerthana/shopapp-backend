package com.shopapp.entity;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity @Table(name = "products")
@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class Product {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;
    private String description;

    @Column(nullable = false)
    private BigDecimal price;
    private BigDecimal discountPrice;
    private Integer stockQuantity;
    private String category;
    private String brand;
    private String imageUrl;
    private String sizes;
    private String colors;
    private boolean featured = false;
    private boolean active = true;
}
