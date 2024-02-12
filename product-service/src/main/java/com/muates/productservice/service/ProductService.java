package com.muates.productservice.service;

import com.muates.productservice.model.dto.request.ProductRequest;
import com.muates.productservice.model.dto.request.ProductUpdateRequest;
import com.muates.productservice.model.entity.ProductEntity;

import java.util.UUID;

public interface ProductService {
    ProductEntity createProduct(ProductRequest request);
    ProductEntity getProduct(UUID id);
    ProductEntity updateProduct(UUID id, ProductUpdateRequest request);
    void deleteProduct(UUID id);
}
