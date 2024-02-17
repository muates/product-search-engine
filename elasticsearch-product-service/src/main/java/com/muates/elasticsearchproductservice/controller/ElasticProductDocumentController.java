package com.muates.elasticsearchproductservice.controller;

import com.muates.elasticsearchproductservice.model.ProductIndex;
import com.muates.elasticsearchproductservice.service.ElasticProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/documents")
public class ElasticProductDocumentController {

    private final ElasticProductService elasticProductService;

    public ElasticProductDocumentController(ElasticProductService elasticProductService) {
        this.elasticProductService = elasticProductService;
    }

    @GetMapping("/search")
    public ResponseEntity<List<ProductIndex>> searchProducts(@RequestParam(name = "query") String query) {
        List<ProductIndex> response = elasticProductService.searchProducts(query);
        return ResponseEntity.ok(response);
    }
}
