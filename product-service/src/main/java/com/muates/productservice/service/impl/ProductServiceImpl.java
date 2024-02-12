package com.muates.productservice.service.impl;

import com.muates.productservice.exception.ProductDeletionException;
import com.muates.productservice.exception.ProductNotFoundException;
import com.muates.productservice.model.dto.request.ProductRequest;
import com.muates.productservice.model.dto.request.ProductUpdateRequest;
import com.muates.productservice.model.entity.ProductEntity;
import com.muates.productservice.repository.ProductRepository;
import com.muates.productservice.service.ProductService;
import com.muates.productservice.service.UpdateService;
import com.muates.productservice.transformer.ProductTransformer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class ProductServiceImpl implements ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final ProductRepository productRepository;
    private final ProductTransformer<ProductRequest, ProductEntity> requestToEntity;
    private final UpdateService<ProductEntity, ProductUpdateRequest> updateService;

    public ProductServiceImpl(ProductRepository productRepository,
                              ProductTransformer<ProductRequest, ProductEntity> requestToEntity,
                              UpdateService<ProductEntity, ProductUpdateRequest> updateService) {
        this.productRepository = productRepository;
        this.requestToEntity = requestToEntity;
        this.updateService = updateService;
    }

    @Override
    public ProductEntity createProduct(ProductRequest request) {
        LOGGER.info("Starting product creation process for product with name: {}", request.getName());
        ProductEntity productEntity = productRepository.save(requestToEntity.transform(request));
        LOGGER.info("Product creation process completed. Created product: {}", productEntity);
        return productEntity;
    }

    @Override
    public ProductEntity getProduct(UUID id) {
        LOGGER.info("Attempting to retrieve product with ID: {}", id);
        Optional<ProductEntity> optProductEntity = productRepository.findById(id);

        if (optProductEntity.isPresent()) {
            LOGGER.info("Product found. Retrieved product: {}", optProductEntity.get());
            return optProductEntity.get();
        }
        LOGGER.warn("Product not found with ID: {}", id);
        throw new ProductNotFoundException("Product not found with ID: " + id);
    }

    @Override
    public ProductEntity updateProduct(UUID id, ProductUpdateRequest request) {
        LOGGER.info("Attempting to update product with ID: {}", id);

        ProductEntity existingProductEntity = this.getProduct(id);

        updateService.update(existingProductEntity, request);

        productRepository.save(existingProductEntity);

        LOGGER.info("Product updated successfully. Updated product: {}", existingProductEntity);

        return existingProductEntity;
    }

    @Override
    public void deleteProduct(UUID id) {
        try {
            productRepository.deleteById(id);
            LOGGER.info("Product deletion completed successfully. Product ID: {}", id);
        } catch (Exception e) {
            LOGGER.error("An error occurred while deleting the product. Product ID: {}", id, e);
            throw new ProductDeletionException("Error deleting the product with ID: " + id, e);
        }
    }
}
