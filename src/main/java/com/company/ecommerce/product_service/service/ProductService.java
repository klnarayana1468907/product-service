package com.company.ecommerce.product_service.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.company.ecommerce.product_service.dto.ProductRequest;
import com.company.ecommerce.product_service.dto.ProductResponse;

public interface ProductService {
	
	ProductResponse create(ProductRequest request);

    ProductResponse getById(Long id);

    List<ProductResponse> getAll();

    ProductResponse update(Long id, ProductRequest request);

    void delete(Long id);
    
    Page<ProductResponse> getAllProducts(int page,int size,String sortBy, String direction);
    
    void reduceStock(Long productId, int quantity);
    
    void restoreStock(Long productId, Integer quantity);


}
