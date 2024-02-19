package com.muates.elasticsearchproductservice.repository;

import com.muates.elasticsearchproductservice.model.ProductIndex;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ElasticProductRepository extends ElasticsearchRepository<ProductIndex, UUID> {

    @Query("{" +
            "\"bool\": {" +
            "    \"should\": [" +
            "        {" +
            "            \"multi_match\": {" +
            "                \"query\": \"?0\"," +
            "                \"fields\": [\"name^1\", \"brand^1\", \"model\", \"category\"]" +
            "            }" +
            "        }" +
            "    ]" +
            "}" +
            "}")
    List<ProductIndex> searchProducts(@Param("query") String query);
}