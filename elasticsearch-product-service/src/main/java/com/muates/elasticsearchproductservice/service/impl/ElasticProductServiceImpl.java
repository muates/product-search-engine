package com.muates.elasticsearchproductservice.service.impl;

import com.muates.elasticsearchproductservice.model.ProductIndex;
import com.muates.elasticsearchproductservice.repository.ElasticProductRepository;
import com.muates.elasticsearchproductservice.service.ElasticProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ElasticProductServiceImpl implements ElasticProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ElasticProductServiceImpl.class);

    private final ElasticProductRepository elasticProductRepository;

    public ElasticProductServiceImpl(ElasticProductRepository elasticProductRepository) {
        this.elasticProductRepository = elasticProductRepository;
    }

    @Override
    public List<UUID> save(List<ProductIndex> documents) {
        List<ProductIndex> response = (List<ProductIndex>) elasticProductRepository.saveAll(documents);
        List<UUID> ids = response.stream().map(ProductIndex::getId).collect(Collectors.toList());
        LOGGER.info("Saved {} documents to Elasticsearch with IDs: {}", documents.size(), ids);
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
            ProductIndex product = elasticProductRepository.findById(id).orElse(null);
            searchResults = (product != null) ? Collections.singletonList(product) : Collections.emptyList();
            logProductSearchResult(query, searchResults);
        } else {
            searchResults = elasticProductRepository.searchProducts(query);
            logProductSearchResult(query, searchResults);
        }

        return searchResults;
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
