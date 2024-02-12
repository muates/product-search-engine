package com.muates.productservice.transformer.impl;

import com.muates.productservice.model.dto.response.ProductResponse;
import com.muates.productservice.model.entity.ProductEntity;
import com.muates.productservice.transformer.ProductTransformer;
import org.springframework.stereotype.Component;

@Component
public class EntityToResponse implements ProductTransformer<ProductEntity, ProductResponse> {

    @Override
    public ProductResponse transform(ProductEntity value) {
        return ProductResponse
                .builder()
                .id(value.getId())
                .name(value.getName())
                .description(value.getDescription())
                .price(value.getPrice())
                .brand(value.getBrand())
                .model(value.getModel())
                .stockQuantity(value.getStockQuantity())
                .category(value.getCategory())
                .imageUrl(value.getImageUrl())
                .build();
    }
}
