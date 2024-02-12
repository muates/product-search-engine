package com.muates.productservice.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductUpdateRequest {
    private String name;
    private String description;
    private Double price;
    private String brand;
    private String model;
    private int stockQuantity;
    private int minStockQuantity;
    private String category;
    private String imageUrl;
}
