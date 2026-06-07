package com.company.ecommerce.product_service.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.company.ecommerce.product_service.entity.Product;

public interface ProductRepository extends JpaRepository<Product, Long>{
	
    List<Product> findByActiveTrue();

    // 🔥 Pagination happens here (DB level)
    Page<Product> findByActiveTrue(Pageable pageable);

}
