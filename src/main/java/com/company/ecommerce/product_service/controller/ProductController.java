package com.company.ecommerce.product_service.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.company.ecommerce.product_service.dto.ProductRequest;
import com.company.ecommerce.product_service.dto.ProductResponse;
import com.company.ecommerce.product_service.service.ProductService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/products")
public class ProductController {

	private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> create(
            @Valid @RequestBody ProductRequest request) {
		return ResponseEntity.ok(productService.create(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> get(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getById(id));
    }

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(
            @PathVariable Long id,
            @Valid @RequestBody ProductRequest request) {
        return ResponseEntity.ok(productService.update(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
    	productService.delete(id);
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/api/products")
    public Page<ProductResponse> getAllProducts(

            // 🔥 Pagination inputs
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,

            // 🔥 Sorting inputs
            @RequestParam(defaultValue = "id") String sortBy,
            @RequestParam(defaultValue = "asc") String direction
    ) {
        return productService.getAllProducts(page, size, sortBy, direction);
    }

    @PutMapping("/{id}/reduce-stock")
    public void reduceStock(@PathVariable Long id,
                            @RequestParam int quantity) {
        productService.reduceStock(id, quantity);
    }

}