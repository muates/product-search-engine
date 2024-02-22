package com.muates.elasticsearchproductservice.service;

import com.muates.elasticsearchproductservice.model.ProductIndex;

import java.util.List;
import java.util.UUID;

public interface ElasticProductService {
    List<UUID> save(List<ProductIndex> documents);
    List<ProductIndex> searchProducts(String query);
    ProductIndex getProductById(UUID id);
    void updateProduct(ProductIndex document);
    void deleteProduct(UUID id);
}
