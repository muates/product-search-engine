package com.muates.elasticproductservice.repository;

import com.muates.elasticproductservice.model.ProductIndex;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ElasticProductRepository extends ElasticsearchRepository<ProductIndex, UUID> {

}