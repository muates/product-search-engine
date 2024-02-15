package com.muates.elasticsearchproductservice.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import org.springframework.data.elasticsearch.annotations.Document;

import java.util.UUID;

@Data
@Builder
@Document(indexName = "#{elasticConfigData.indexName}")
public class ProductIndex {

    @JsonProperty
    private UUID id;

    @JsonProperty
    private String name;

    @JsonProperty
    private String brand;

    @JsonProperty
    private String model;

    @JsonProperty
    private String category;

    @JsonProperty
    private String description;

    @JsonProperty
    private double price;

    @JsonProperty
    private int stockQuantity;

    @JsonProperty
    private int minStockQuantity;

    @JsonProperty
    private String imageUrl;

    @JsonProperty
    private String createdAt;
}
