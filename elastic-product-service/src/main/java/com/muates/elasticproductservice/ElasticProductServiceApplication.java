package com.muates.elasticproductservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {"com.muates"})
@SpringBootApplication
public class ElasticProductServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(ElasticProductServiceApplication.class, args);
    }
}
