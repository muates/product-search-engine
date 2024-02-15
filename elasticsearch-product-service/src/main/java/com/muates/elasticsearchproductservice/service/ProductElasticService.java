package com.muates.elasticsearchproductservice.service;

import com.muates.elasticsearchproductservice.model.ProductIndex;

import java.util.List;
import java.util.UUID;

public interface ProductElasticService {
    List<UUID> save(List<ProductIndex> documents);
}
