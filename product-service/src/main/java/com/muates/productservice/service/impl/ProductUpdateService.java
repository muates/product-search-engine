package com.muates.productservice.service.impl;

import com.muates.productservice.model.dto.request.ProductUpdateRequest;
import com.muates.productservice.model.entity.ProductEntity;
import com.muates.productservice.service.UpdateService;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.function.Consumer;

@Component
public class ProductUpdateService implements UpdateService<ProductEntity, ProductUpdateRequest> {

    @Override
    public ProductEntity update(ProductEntity value, ProductUpdateRequest target) {
        updateField(target.getName(), value::setName);
        updateField(target.getDescription(), value::setDescription);
        updateField(target.getPrice(), value::setPrice);
        updateField(target.getBrand(), value::setBrand);
        updateField(target.getModel(), value::setModel);
        updateFieldIfPositive(target.getStockQuantity(), value::setStockQuantity);
        updateFieldIfPositive(target.getMinStockQuantity(), value::setMinStockQuantity);
        updateField(target.getCategory(), value::setCategory);
        updateField(target.getImageUrl(), value::setImageUrl);
        return value;
    }

    private <T> void updateField(T target, Consumer<T> updater) {
        Optional.ofNullable(target).ifPresent(updater);
    }

    private void updateFieldIfPositive(Integer target, Consumer<Integer> updater) {
        Optional.ofNullable(target).filter(v -> v >= 0).ifPresent(updater);
    }
}
