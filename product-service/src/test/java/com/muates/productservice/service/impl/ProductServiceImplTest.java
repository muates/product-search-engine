package com.muates.productservice.service.impl;

import com.muates.productservice.model.dto.request.ProductRequest;
import com.muates.productservice.model.dto.request.ProductUpdateRequest;
import com.muates.productservice.model.entity.ProductEntity;
import com.muates.productservice.repository.ProductRepository;
import com.muates.productservice.service.UpdateService;
import com.muates.productservice.service.delegate.ElasticProductDelegateService;
import com.muates.productservice.transformer.ProductTransformer;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

public class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductTransformer<ProductRequest, ProductEntity> requestToEntity;

    @Mock
    private UpdateService<ProductEntity, ProductUpdateRequest> updateService;

    @Mock
    private ElasticProductDelegateService elasticProductDelegateService;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testCreateProduct() {
        ProductRequest request = this.getProductRequest(
                "Samsung Galaxy S21 Pro", "Samsung", "Galaxy S21 Pro", "Mobile Phone", 889);
        ProductEntity product = this.getProductData(
                "Samsung Galaxy S21 Pro", "Samsung", "Galaxy S21 Pro", "Mobile Phone", 889);

        when(productRepository.save(any(ProductEntity.class))).thenReturn(product);
        when(requestToEntity.transform(any(ProductRequest.class))).thenReturn(product);
        when(elasticProductDelegateService.saveProducts(List.of(product)))
                .thenReturn(List.of(UUID.fromString("baed3d23-9fd7-49fd-bbe6-2281f186ddef")));

        ProductEntity response = productService.createProduct(request);

        assertNotNull(response);
        assertEquals(UUID.fromString("baed3d23-9fd7-49fd-bbe6-2281f186ddef"), response.getId());
        assertEquals("Samsung", response.getBrand());
        assertEquals("Galaxy S21 Pro", response.getModel());
        assertEquals("Mobile Phone", response.getCategory());
        assertEquals(product, response);

        verify(productRepository, times(1)).save(any(ProductEntity.class));
        verify(requestToEntity, times(1)).transform(any(ProductRequest.class));
        verify(elasticProductDelegateService, times(1)).saveProducts(anyList());
    }

    @Test
    public void testGetProduct() {
        ProductEntity product = this.getProductData(
                "Samsung Galaxy S21 Pro", "Samsung", "Galaxy S21 Pro", "Mobile Phone", 889);

        when(productRepository.findById(any(UUID.class))).thenReturn(Optional.of(product));

        ProductEntity response = productService.getProduct(UUID.fromString("baed3d23-9fd7-49fd-bbe6-2281f186ddef"));

        assertNotNull(response);
        assertEquals(UUID.fromString("baed3d23-9fd7-49fd-bbe6-2281f186ddef"), response.getId());

        verify(productRepository, times(1)).findById(any(UUID.class));
    }

    @Test
    public void updateProduct() {
        ProductEntity existProduct = this.getProductData(
                "Samsung Galaxy S21 Pro", "Samsung", "Galaxy S21 Pro", "Mobile Phone", 889);
        ProductUpdateRequest updateRequest = this.getProductUpdateRequest("Samsung Galaxy S21 128GB");

        when(productRepository.findById(any(UUID.class))).thenReturn(Optional.of(existProduct));
        when(updateService.update(any(ProductEntity.class), any(ProductUpdateRequest.class)))
                .thenAnswer(invocation -> {
                    existProduct.setName(updateRequest.getName());
                    return existProduct;
                });
        when(productRepository.save(any(ProductEntity.class))).thenReturn(existProduct);
        doNothing().when(elasticProductDelegateService).updateProduct(any(ProductEntity.class));
        ProductEntity response = productService.updateProduct(
                UUID.fromString("baed3d23-9fd7-49fd-bbe6-2281f186ddef"), updateRequest);

        assertNotNull(response);
        assertEquals("Samsung Galaxy S21 128GB", response.getName());
        assertEquals(existProduct, response);

        verify(productRepository, times(1)).findById(any(UUID.class));
        verify(updateService, times(1)).update(any(ProductEntity.class), any(ProductUpdateRequest.class));
        verify(productRepository, times(1)).save(any(ProductEntity.class));
        verify(elasticProductDelegateService, times(1)).updateProduct(any(ProductEntity.class));
    }

    @Test
    public void deleteProduct() {
        doNothing().when(productRepository).deleteById(any(UUID.class));
        doNothing().when(elasticProductDelegateService).deleteProduct(any(UUID.class));

        productService.deleteProduct(UUID.fromString("baed3d23-9fd7-49fd-bbe6-2281f186ddef"));

        verify(productRepository, times(1)).deleteById(any(UUID.class));
        verify(elasticProductDelegateService, times(1)).deleteProduct(any(UUID.class));
    }

    private ProductEntity getProductData(String name, String brand, String model, String category, double price) {
        return ProductEntity.builder()
                .id(UUID.fromString("baed3d23-9fd7-49fd-bbe6-2281f186ddef"))
                .name(name)
                .brand(brand)
                .model(model)
                .category(category)
                .price(price)
                .description("example description")
                .stockQuantity(10)
                .minStockQuantity(5)
                .imageUrl("example_img")
                .createdAt(new Date())
                .build();
    }

    private ProductRequest getProductRequest(String name, String brand, String model, String category, double price) {
        return ProductRequest.builder()
                .name(name)
                .brand(brand)
                .model(model)
                .category(category)
                .price(price)
                .description("example description")
                .stockQuantity(10)
                .minStockQuantity(5)
                .imageUrl("example_img")
                .build();
    }

    private ProductUpdateRequest getProductUpdateRequest(String name) {
        return ProductUpdateRequest.builder()
                .name(name)
                .build();
    }

}