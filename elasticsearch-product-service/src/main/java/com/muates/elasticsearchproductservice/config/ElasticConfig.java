package com.muates.elasticsearchproductservice.config;

import com.muates.elasticsearchproductservice.config.data.ElasticConfigData;
import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.config.AbstractElasticsearchConfiguration;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Objects;

@Configuration
@EnableElasticsearchRepositories(basePackages = "com.muates.elasticsearchproductservice")
public class ElasticConfig extends AbstractElasticsearchConfiguration {

    private final ElasticConfigData elasticsearchConfigData;

    public ElasticConfig(ElasticConfigData elasticsearchConfigData) {
        this.elasticsearchConfigData = elasticsearchConfigData;
    }

    @Override
    public RestHighLevelClient elasticsearchClient() {
        UriComponents serverUri = UriComponentsBuilder.fromHttpUrl(elasticsearchConfigData.getConnectionUrl()).build();
        return new RestHighLevelClient(
                RestClient.builder(new HttpHost(
                        Objects.requireNonNull(serverUri.getHost()),
                        serverUri.getPort(),
                        serverUri.getScheme()
                )).setRequestConfigCallback(
                        requestConfigBuilder ->
                                requestConfigBuilder
                                        .setConnectTimeout(elasticsearchConfigData.getConnectTimeoutMs())
                                        .setSocketTimeout(elasticsearchConfigData.getSocketTimeoutMs())
                )
        );
    }

    @Bean
    public ElasticsearchOperations elasticsearchTemplate() {
        return new ElasticsearchRestTemplate(elasticsearchClient());
    }
}
