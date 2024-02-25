package com.muates.productservice.service.delegate.impl;

import com.muates.elasticproductservice.model.ProductIndex;
import com.muates.elasticproductservice.service.ElasticProductService;
import com.muates.productservice.model.entity.ProductEntity;
import com.muates.productservice.service.delegate.ElasticProductDelegateService;
import com.muates.productservice.transformer.ProductTransformer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ElasticProductDelegateServiceImpl implements ElasticProductDelegateService {

    private final ElasticProductService elasticProductService;
    private final ProductTransformer<ProductEntity, ProductIndex> entityToIndex;

    public ElasticProductDelegateServiceImpl(ElasticProductService elasticProductService,
                                             ProductTransformer<ProductEntity, ProductIndex> entityToIndex) {
        this.elasticProductService = elasticProductService;
        this.entityToIndex = entityToIndex;
    }

    @Override
    public List<UUID> saveProducts(List<ProductEntity> products) {
        List<ProductIndex> productIndexList = products.stream()
                .map(entityToIndex::transform)
                .collect(Collectors.toList());
        return elasticProductService.save(productIndexList);
    }

    @Override
    public void updateProduct(ProductEntity product) {
        elasticProductService.updateProduct(entityToIndex.transform(product));
    }

    @Override
    public void deleteProduct(UUID id) {
        elasticProductService.deleteProduct(id);
    }
}
