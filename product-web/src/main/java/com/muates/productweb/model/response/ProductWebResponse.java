package com.muates.productweb.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductWebResponse {
    private UUID id;
    private String name;
    private String brand;
    private String model;
    private String category;
    private String description;
    private double price;
    private int stockQuantity;
    private int minStockQuantity;
    private String imageUrl;
    private Date createdAt;
}
