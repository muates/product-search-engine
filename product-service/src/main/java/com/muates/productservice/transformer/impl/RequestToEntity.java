package com.muates.productservice.transformer.impl;

import com.muates.productservice.model.dto.request.ProductRequest;
import com.muates.productservice.model.entity.ProductEntity;
import com.muates.productservice.transformer.ProductTransformer;
import org.springframework.stereotype.Component;

@Component
public class RequestToEntity implements ProductTransformer<ProductRequest, ProductEntity> {

    @Override
    public ProductEntity transform(ProductRequest value) {
        return ProductEntity
                .builder()
                .name(value.getName())
                .description(value.getDescription())
                .price(value.getPrice())
                .brand(value.getBrand())
                .model(value.getModel())
                .stockQuantity(value.getStockQuantity())
                .minStockQuantity(value.getMinStockQuantity())
                .category(value.getCategory())
                .imageUrl(value.getImageUrl())
                .build();
    }
}
