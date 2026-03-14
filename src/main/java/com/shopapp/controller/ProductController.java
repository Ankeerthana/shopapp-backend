package com.shopapp.controller;

import com.shopapp.entity.Product;
import com.shopapp.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {
    private final ProductRepository productRepo;

    @GetMapping
    public List<Product> getAll() { return productRepo.findAll(); }

    @GetMapping("/featured")
    public List<Product> getFeatured() { return productRepo.findByFeaturedTrue(); }

    @GetMapping("/category/{cat}")
    public List<Product> getByCategory(@PathVariable String cat) {
        return productRepo.findByCategory(cat);
    }

    @GetMapping("/search")
    public List<Product> search(@RequestParam String q) {
        return productRepo.findByNameContainingIgnoreCase(q);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> getById(@PathVariable Long id) {
        return productRepo.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public Product create(@RequestBody Product p) { return productRepo.save(p); }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Product> update(@PathVariable Long id, @RequestBody Product p) {
        return productRepo.findById(id).map(ex -> {
            ex.setName(p.getName());
            ex.setPrice(p.getPrice());
            ex.setStockQuantity(p.getStockQuantity());
            return ResponseEntity.ok(productRepo.save(ex));
        }).orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        productRepo.deleteById(id);
        return ResponseEntity.ok("Deleted");
    }
}