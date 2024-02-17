package com.muates.productservice.transformer.impl;

import com.muates.elasticsearchproductservice.model.ProductIndex;
import com.muates.productservice.model.entity.ProductEntity;
import com.muates.productservice.transformer.ProductTransformer;
import org.springframework.stereotype.Component;

@Component
public class EntityToIndex implements ProductTransformer<ProductEntity, ProductIndex> {

    @Override
    public ProductIndex transform(ProductEntity value) {
        return ProductIndex
                .builder()
                .id(value.getId())
                .name(value.getName())
                .brand(value.getBrand())
                .model(value.getModel())
                .category(value.getCategory())
                .description(value.getDescription())
                .price(value.getPrice())
                .stockQuantity(value.getStockQuantity())
                .minStockQuantity(value.getMinStockQuantity())
                .imageUrl(value.getImageUrl())
                .createdAt(value.getCreatedAt())
                .build();
    }
}