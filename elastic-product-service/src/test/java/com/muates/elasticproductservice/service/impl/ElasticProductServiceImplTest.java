package com.muates.elasticproductservice.service.impl;

import com.muates.elasticproductservice.model.ProductIndex;
import com.muates.elasticproductservice.repository.ElasticProductRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

public class ElasticProductServiceImplTest {

    @InjectMocks
    private ElasticProductServiceImpl elasticProductService;

    @Mock
    private ElasticProductRepository elasticProductRepository;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testSaveDocument() {
        List<ProductIndex> productIndexList = this.getProducts();

        when(elasticProductRepository.saveAll(anyList())).thenReturn(productIndexList);

        List<UUID> response = elasticProductService.save(productIndexList);

        assertNotNull(response);
        assertTrue(response.contains(UUID.fromString("7a5a1973-0eb5-4c2d-97c4-ef02dd77a63b")));
        assertTrue(response.contains(UUID.fromString("2124d29a-7f3d-4e70-89bd-33b7a05e8d23")));
        assertTrue(response.contains(UUID.fromString("baed3d23-9fd7-49fd-bbe6-2281f186ddef")));
        assertTrue(response.contains(UUID.fromString("c41a09fb-e1a9-4d65-8a8f-7d1b2a894c3f")));

        verify(elasticProductRepository, times(1)).saveAll(anyList());
    }

    private List<ProductIndex> getProducts() {
        List<ProductIndex> products = new ArrayList<>();

        products.add(ProductIndex.builder()
                .id(UUID.fromString("7a5a1973-0eb5-4c2d-97c4-ef02dd77a63b"))
                .name("iPhone 13 Pro")
                .description("Yeni nesil çift kamera sistem, 120Hz ProMotion ekran")
                .price(1299.99)
                .brand("Apple")
                .model("13 Pro")
                .stockQuantity(40)
                .minStockQuantity(5)
                .category("Smart Phone")
                .imageUrl("https://example.com/images/iphone13pro.jpg")
                .build());

        products.add(ProductIndex.builder()
                .id(UUID.fromString("2124d29a-7f3d-4e70-89bd-33b7a05e8d23"))
                .name("iPhone 12 Pro")
                .description("Gelişmiş gece modu, A14 Bionic çip")
                .price(1199.99)
                .brand("Apple")
                .model("12 Pro")
                .stockQuantity(45)
                .minStockQuantity(5)
                .category("Smart Phone")
                .imageUrl("https://example.com/images/iphone12pro.jpg")
                .build());

        products.add(ProductIndex.builder()
                .id(UUID.fromString("baed3d23-9fd7-49fd-bbe6-2281f186ddef"))
                .name("Samsung Galaxy Note 20")
                .description("Kalem desteğine sahip birinci sınıf Android akıllı telefon")
                .price(899.99)
                .brand("Samsung")
                .model("Galaxy Note 20")
                .stockQuantity(40)
                .minStockQuantity(8)
                .category("Smart Phone")
                .imageUrl("https://example.com/galaxy-note-20.jpg")
                .build());

        products.add(ProductIndex.builder()
                .id(UUID.fromString("c41a09fb-e1a9-4d65-8a8f-7d1b2a894c3f"))
                .name("Google Pixel 6")
                .description("Amiral gemisi Google Android akıllı telefon")
                .price(799.99)
                .brand("Google")
                .model("Pixel 6")
                .stockQuantity(35)
                .minStockQuantity(7)
                .category("Smart Phone")
                .imageUrl("https://example.com/pixel-6.jpg")
                .build());

        return products;
    }
}