package com.muates.productweb.controller;

import com.muates.productweb.model.response.ProductWebResponse;
import com.muates.productweb.service.ProductWebService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class ProductWebController {

    private final ProductWebService productWebService;

    public ProductWebController(ProductWebService productWebService) {
        this.productWebService = productWebService;
    }

    @GetMapping("/")
    public String home() {
        return "index";
    }

    @GetMapping("/search")
    public String searchProducts(@RequestParam String query, Model model) {
        List<ProductWebResponse> searchResults = productWebService.searchProducts(query);
        model.addAttribute("searchResults", searchResults);
        return "index";
    }
}
