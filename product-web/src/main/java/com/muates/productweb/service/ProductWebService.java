package com.muates.productweb.service;

import com.muates.productweb.model.response.ProductWebResponse;

import java.util.List;

public interface ProductWebService {
    List<ProductWebResponse> searchProducts(String query);
}
