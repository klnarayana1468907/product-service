package com.company.ecommerce.product_service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.company.ecommerce.common.event.RestoreInventoryEvent;
import com.company.ecommerce.product_service.service.ProductService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ProductKafkaConsumer {
	
    private final ProductService productService;
    
    @KafkaListener(
            topics = "inventory-restore",
            groupId = "product-group"
    )
    public void restoreInventory(RestoreInventoryEvent event) {

        System.out.println(
                "Restoring inventory for product: "
                + event.getProductId()
        );

        productService.restoreStock(
                event.getProductId(),
                event.getQuantity()
        );

        System.out.println(
                "Inventory restored successfully"
        );
    }


}
