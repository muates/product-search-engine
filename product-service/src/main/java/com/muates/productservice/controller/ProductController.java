package com.muates.productservice.controller;

import com.muates.productservice.model.dto.request.ProductRequest;
import com.muates.productservice.model.dto.request.ProductUpdateRequest;
import com.muates.productservice.model.dto.response.ProductResponse;
import com.muates.productservice.model.entity.ProductEntity;
import com.muates.productservice.service.ProductService;
import com.muates.productservice.transformer.ProductTransformer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

    private final ProductService productService;
    private final ProductTransformer<ProductEntity, ProductResponse> entityToRepose;

    public ProductController(ProductService productService,
                             ProductTransformer<ProductEntity, ProductResponse> entityToRepose) {
        this.productService = productService;
        this.entityToRepose = entityToRepose;
    }

    @PostMapping(value = {"", "/"})
    public ResponseEntity<ProductResponse> createProduct(@RequestBody @Valid ProductRequest request) {
        return ResponseEntity.ok(entityToRepose.transform(productService.createProduct(request)));
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable UUID id) {
        return ResponseEntity.ok(entityToRepose.transform(productService.getProduct(id)));
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<ProductResponse> updateProduct(@PathVariable UUID id,
                                                         @RequestBody ProductUpdateRequest request) {
        return ResponseEntity.ok(entityToRepose.transform(productService.updateProduct(id, request)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProductById(@PathVariable UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product is deleted");
    }
}
