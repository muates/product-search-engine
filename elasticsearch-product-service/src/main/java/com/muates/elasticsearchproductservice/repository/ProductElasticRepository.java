package com.muates.elasticsearchproductservice.repository;

import com.muates.elasticsearchproductservice.model.ProductIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductElasticRepository extends ElasticsearchRepository<ProductIndex, UUID> {
}
