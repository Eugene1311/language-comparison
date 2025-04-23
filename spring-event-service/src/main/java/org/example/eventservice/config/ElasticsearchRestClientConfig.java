package org.example.eventservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.elasticsearch.client.ClientConfiguration;
import org.springframework.data.elasticsearch.client.elc.ReactiveElasticsearchConfiguration;
import org.springframework.lang.NonNull;

@Configuration
public class ElasticsearchRestClientConfig extends ReactiveElasticsearchConfiguration {
    @Value("${app.elasticsearch.address}")
    String address;

    @Override
    @Bean
    public @NonNull ClientConfiguration clientConfiguration() {
        return ClientConfiguration.builder()
                .connectedTo(address)
                .withBasicAuth("elastic", "password")
                .build();
    }
}
