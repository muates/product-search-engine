package com.muates.productservice.service.delegate;

import com.muates.productservice.model.entity.ProductEntity;

import java.util.List;
import java.util.UUID;

public interface ElasticProductDelegateService {
    List<UUID> saveProducts(List<ProductEntity> product);
    void updateProduct(ProductEntity product);
    void deleteProduct(UUID id);
}
