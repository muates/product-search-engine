package com.muates.productweb.service.impl;

import com.muates.productweb.model.response.ProductWebResponse;
import com.muates.productweb.service.ProductWebService;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public class ProductWebServiceImpl implements ProductWebService {

    private final WebClient webClient;

    public ProductWebServiceImpl(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("http://localhost:8091").build();
    }

    @Override
    public List<ProductWebResponse> searchProducts(String query) {
        return webClient.get()
                .uri("/elastic-product-service/documents/search?query={query}", query)
                .retrieve()
                .bodyToFlux(ProductWebResponse.class)
                .collectList()
                .block();
    }
}
