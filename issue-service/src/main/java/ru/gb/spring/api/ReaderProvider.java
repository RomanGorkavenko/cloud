package ru.gb.spring.api;

import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service
public class ReaderProvider {

    private final WebClient client;

    public ReaderProvider(ReactorLoadBalancerExchangeFilterFunction loadBalancerExchangeFilterFunction) {
        this.client = WebClient.builder()
                .filter(loadBalancerExchangeFilterFunction)
                .build();
    }

    public Reader getRandomReader() {
        return client.get()
                .uri("http://reader-service/api/reader/random")
                .retrieve()
                .bodyToMono(Reader.class)
                .block();
    }
}
