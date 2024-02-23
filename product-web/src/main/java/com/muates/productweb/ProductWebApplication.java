package com.muates.productweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.muates"})
@SpringBootApplication
public class ProductWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(ProductWebApplication.class, args);
    }
}
