package com.bwongo.orderservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * @Author bkaaron
 * @Project microservices-new
 * @Date 1/16/23
 */
@Configuration
public class WebClientConfig {

    @Bean
    @LoadBalanced//used when we have multiple instances of the microservice
    public WebClient.Builder webClient() {
        return WebClient.builder();
    }
}
