package com.trinity.webflux_playground.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class BaseConfiguration {

    private static final Logger logger = LoggerFactory.getLogger(BaseConfiguration.class);
    @Value("${external-service.base-url}")
    private String baseUrl;

    @Bean
    public WebClient webClient() {
        logger.info("baseUrl: {}", baseUrl);
        return WebClient.builder().baseUrl(baseUrl).build();
    }

    @Bean
    public RestClient restClient() {
        logger.info("baseUrl: {}", baseUrl);
        return RestClient.builder().requestFactory(new JdkClientHttpRequestFactory()).baseUrl(baseUrl).build();
    }

    public ObjectMapper mapper() {
        logger.info("Object Mapper configured");
        return new ObjectMapper();
    }


}
