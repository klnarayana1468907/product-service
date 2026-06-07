package com.company.ecommerce.product_service.service.impl;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.company.ecommerce.product_service.dto.ProductRequest;
import com.company.ecommerce.product_service.dto.ProductResponse;
import com.company.ecommerce.product_service.entity.Product;
import com.company.ecommerce.product_service.exception.ResourceNotFoundException;
import com.company.ecommerce.product_service.repository.ProductRepository;
import com.company.ecommerce.product_service.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{
	
	 private final ProductRepository productRepository;

	    public ProductServiceImpl(ProductRepository productRepository) {
	        this.productRepository = productRepository;
	    }

	    @Override
	    public ProductResponse create(ProductRequest request) {
	        Product p = new Product();
	        p.setName(request.getName());
	        p.setDescription(request.getDescription());
	        p.setPrice(request.getPrice());
	        p.setStock(request.getStock());

	        return mapToResponse(productRepository.save(p));
	    }

	    @Override
	    public ProductResponse getById(Long id) {
	        Product p = productRepository.findById(id)
	            .filter(Product::getActive)
	            .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

	        return mapToResponse(p);
	    }

	    @Override
	    public List<ProductResponse> getAll() {
	        return productRepository.findByActiveTrue()
	                   .stream()
	                   .map(this::mapToResponse)
	                   .toList();
	    }

	    @Override
	    public ProductResponse update(Long id, ProductRequest request) {
	        Product p = productRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Product not found"));

	        p.setName(request.getName());
	        p.setDescription(request.getDescription());
	        p.setPrice(request.getPrice());
	        p.setStock(request.getStock());

	        return mapToResponse(productRepository.save(p));
	    }

	    @Override
	    public void delete(Long id) {
	        Product p = productRepository.findById(id)
	            .orElseThrow(() -> new RuntimeException("Product not found"));

	        p.setActive(false);
	        productRepository.save(p);
	    }

	    private ProductResponse mapToResponse(Product p) {
	        ProductResponse r = new ProductResponse();
	        r.setId(p.getId());
	        r.setName(p.getName());
	        r.setDescription(p.getDescription());
	        r.setPrice(p.getPrice());
	        r.setStock(p.getStock());
	        return r;
	    }
	
	    @Override
	    public Page<ProductResponse> getAllProducts(
	            int page,
	            int size,
	            String sortBy,
	            String direction
	    ) {

	        // 🔥 Sorting logic
	        Sort sort = direction.equalsIgnoreCase("desc")
	                ? Sort.by(sortBy).descending()
	                : Sort.by(sortBy).ascending();

	        // 🔥 Pagination + Sorting combined
	        Pageable pageable = PageRequest.of(page, size, sort);

	        // 🔥 DB call
	        Page<Product> productPage =
	                productRepository.findByActiveTrue(pageable);

	        // 🔥 Convert Entity → DTO
	        return productPage.map(this::mapToResponse);
	    }

	    @Override
	    public void reduceStock(Long productId, int quantity) {

	        Product product = productRepository.findById(productId)
	                .orElseThrow(() -> new RuntimeException("Product not found"));

	        if (product.getStock() < quantity) {
	            throw new RuntimeException("Insufficient stock");
	        }

	        product.setStock(product.getStock() - quantity);
	        productRepository.save(product);
	    }

	    @Override
	    public void restoreStock(
	            Long productId,
	            Integer quantity
	    ) {

	        Product product = productRepository
	                .findById(productId)
	                .orElseThrow(() ->
	                        new RuntimeException(
	                                "Product not found"
	                        )
	                );

	        product.setStock(
	                product.getStock() + quantity
	        );

	        productRepository.save(product);

	        System.out.println(
	                "Stock restored for product: "
	                + productId
	        );
	    }
}
