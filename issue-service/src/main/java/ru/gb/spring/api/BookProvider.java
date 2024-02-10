package ru.gb.spring.api;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import lombok.Data;
import org.springframework.cloud.client.loadbalancer.reactive.ReactorLoadBalancerExchangeFilterFunction;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.*;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class BookProvider {

    private final WebClient webClient;
//    private final EurekaClient eurekaClient;

    public BookProvider(EurekaClient eurekaClient,
                        ReactorLoadBalancerExchangeFilterFunction loadBalancerExchangeFilterFunction) {
        this.webClient = WebClient.builder()
                .filter(loadBalancerExchangeFilterFunction)
                .build();
//        this.eurekaClient = eurekaClient;
    }

    public UUID getRandomBookId() {
        Book randomBook = webClient.get()
                .uri("http://book-service/api/book/random")
                .retrieve()
                .bodyToMono(Book.class)
                .block();

        return randomBook.getId();
    }

    public Book getRandomBook() {
        return webClient.get()
                .uri("http://book-service/api/book/random")
                .retrieve()
                .bodyToMono(Book.class)
                .block();
    }

//    private String getBookServiceIp() {
//        Application application = eurekaClient.getApplication("BOOK-SERVICE");
//        List<InstanceInfo> instances = application.getInstances();
//
//        int randomIndex = ThreadLocalRandom.current().nextInt(instances.size());
//        InstanceInfo randomInstance = instances.get(randomIndex);
//        return "http://" + randomInstance.getIPAddr() + ":" + randomInstance.getPort();
//    }
}
