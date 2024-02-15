package com.muates.elasticsearchproductservice.service.impl;

import com.muates.elasticsearchproductservice.model.ProductIndex;
import com.muates.elasticsearchproductservice.repository.ProductElasticRepository;
import com.muates.elasticsearchproductservice.service.ProductElasticService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProductElasticServiceImpl implements ProductElasticService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductElasticServiceImpl.class);

    private final ProductElasticRepository productElasticRepository;

    public ProductElasticServiceImpl(ProductElasticRepository productElasticRepository) {
        this.productElasticRepository = productElasticRepository;
    }

    @Override
    public List<UUID> save(List<ProductIndex> documents) {
        List<ProductIndex> response = (List<ProductIndex>) productElasticRepository.saveAll(documents);
        List<UUID> ids = response.stream().map(ProductIndex::getId).collect(Collectors.toList());
        LOGGER.info("Saved {} documents to Elasticsearch with IDs: {}", documents.size(), ids);
        return ids;
    }
}
