package com.muates.productservice.model.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductRequest {

    @NotBlank
    private String name;

    private String description;

    @NotNull
    private Double price;

    @NotBlank
    private String brand;

    private String model;

    @NotNull
    private int stockQuantity;

    @NotNull
    private int minStockQuantity;

    private String category;

    private String imageUrl;
}
