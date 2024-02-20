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

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
            String[] queryParts = query.split("\\s+");

            String name = null;
            String brand = null;
            String model = null;
            String category = null;
            int minMatchFields = 0;

            for (String part : queryParts) {
                if (elasticProductRepository.countByCategory(part) > 0) {
                    category = (category != null) ? category + " " + part: part;
                    continue;
                }
                if (elasticProductRepository.countByBrand(part) > 0) {
                    brand = (brand != null) ? brand + " " + part : part;
                    continue;
                }

                if (elasticProductRepository.countByModel(part) > 0) {
                    model = (model != null) ? model + " " + part : part;
                    continue;
                }
                if (elasticProductRepository.countByName(part) > 0) {
                    name = (name != null) ? (name + " " + part) : part;
                }
            }

            BoolQueryBuilder boolQuery = QueryBuilders.boolQuery();

            if (name != null) {
                boolQuery.should(QueryBuilders.matchQuery("name", name).boost(1));
                minMatchFields++;
            }

            if (brand != null) {
                boolQuery.should(QueryBuilders.matchQuery("brand", brand).boost(2));
                minMatchFields++;
            }

            if (model != null) {
                boolQuery.should(QueryBuilders.matchQuery("model", model).boost(2));
                minMatchFields++;
            }

            if (category != null) {
                boolQuery.should(QueryBuilders.matchQuery("category", category).boost(1));
                minMatchFields++;
            }

            boolQuery.minimumShouldMatch(minMatchFields);

            SearchHits<ProductIndex> searchHits = elasticsearchRestTemplate.search(
                    new NativeSearchQueryBuilder().withQuery(boolQuery).build(), ProductIndex.class);

            searchResults = searchHits.stream().map(SearchHit::getContent).collect(Collectors.toList());
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
