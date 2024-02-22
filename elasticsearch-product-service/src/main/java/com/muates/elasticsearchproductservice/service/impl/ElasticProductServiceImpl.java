package com.muates.elasticsearchproductservice.service.impl;

import com.muates.elasticsearchproductservice.model.ProductIndex;
import com.muates.elasticsearchproductservice.repository.ElasticProductRepository;
import com.muates.elasticsearchproductservice.service.ElasticProductService;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ElasticProductServiceImpl implements ElasticProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticProductServiceImpl.class);

    private final ElasticProductRepository elasticProductRepository;
    private final ElasticsearchRestTemplate elasticsearchRestTemplate;

    public ElasticProductServiceImpl(ElasticProductRepository elasticProductRepository,
                                     ElasticsearchRestTemplate elasticsearchRestTemplate) {
        this.elasticProductRepository = elasticProductRepository;
        this.elasticsearchRestTemplate = elasticsearchRestTemplate;
    }

    @Override
    public List<UUID> save(List<ProductIndex> documents) {
        List<ProductIndex> response = (List<ProductIndex>) elasticProductRepository.saveAll(documents);
        List<UUID> ids = response.stream().map(ProductIndex::getId).collect(Collectors.toList());
        LOGGER.info("Saved {} products to Elasticsearch with IDs: {}", documents.size(), ids);
        return ids;
    }

    @Override
    public List<ProductIndex> searchProducts(String query) {
        if (query == null || query.isEmpty()) {
            return Collections.emptyList();
        }

        List<ProductIndex> searchResults;

        if (isUUID(query)) {
            UUID id = UUID.fromString(query);
            ProductIndex product = this.getProductById(id);
            searchResults = (product != null) ? Collections.singletonList(product) : Collections.emptyList();
            logProductSearchResult(query, searchResults);
        } else {
            String[] queryParts = query.split("\\s+");

            Map<String, Integer> boosts = Map.of(
                    "name", 1,
                    "brand", 2,
                    "model", 2,
                    "category", 1
            );

            BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();
            int minMatchFields = (int) Arrays.stream(queryParts)
                    .filter(part -> Stream.of("category", "brand", "model", "name")
                            .anyMatch(field -> countByField(field, part) > 0))
                    .peek(part -> {
                        Stream.of("category", "brand", "model", "name")
                                .filter(field -> countByField(field, part) > 0)
                                .findFirst()
                                .ifPresent(field -> boolQuery.should(QueryBuilders.matchQuery(field, part).boost(boosts.get(field))));
                    })
                    .count();

            boolQuery.minimumShouldMatch(minMatchFields);

            SearchHits<ProductIndex> searchHits = elasticsearchRestTemplate.search(
                    new NativeSearchQueryBuilder().withQuery(boolQuery).build(), ProductIndex.class);

            searchResults = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
            logProductSearchResult(query, searchResults);
        }

        return searchResults;
    }

    @Override
    public ProductIndex getProductById(UUID id) {
        return elasticProductRepository.findById(id).orElse(null);
    }

    @Override
    public void updateProduct(ProductIndex document) {
        elasticProductRepository.save(document);
        LOGGER.info("Product updated successfully: ProductID={}", document.getId());
    }

    @Override
    public void deleteProduct(UUID id) {
        elasticProductRepository.deleteById(id);
        LOGGER.info("Product deletion completed successfully. Product ID: {}", id);
    }

    private long countByField(String field, String value) {
        return elasticsearchRestTemplate.search(new NativeSearchQueryBuilder()
                .withQuery(QueryBuilders.matchQuery(field, value))
                .build(), ProductIndex.class)
                .getTotalHits();
    }

    private void logProductSearchResult(String query, List<ProductIndex> searchResults) {
        String logMessage = (searchResults.isEmpty())
                ? "No product found for query: '{}'"
                : "Found {} products matching the query: '{}'";

        LOGGER.info(logMessage, searchResults.size(), query);
    }

    private boolean isUUID(String str) {
        try {
            UUID.fromString(str);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
