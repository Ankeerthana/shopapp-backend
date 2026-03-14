package com.shopapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.shopapp")
public class ShopappBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(ShopappBackendApplication.class, args);
    }
}