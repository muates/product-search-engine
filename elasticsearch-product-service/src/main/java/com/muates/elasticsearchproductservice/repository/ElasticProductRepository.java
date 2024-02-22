package com.muates.elasticsearchproductservice.repository;

import com.muates.elasticsearchproductservice.model.ProductIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ElasticProductRepository extends ElasticsearchRepository<ProductIndex, UUID> {

}