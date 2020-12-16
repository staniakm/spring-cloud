package com.mariusz.customers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootApplication
public class CustomersApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomersApplication.class, args);
    }

    @Bean
    WebClient httpClient(WebClient.Builder client) {
        return client.build();
    }

}

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
class CustomerController {

    private final WebClient webClient;

    @GetMapping("/")
    public Flux<Customer> getCustomers() {
        return Flux.just(new Customer(1, "John"), new Customer(2, "Mary"));
    }

    @GetMapping("/{customersId}/orders")
    public Mono<CustomerOrders> getCustomerOrders(@PathVariable Integer customersId) {
        return this.webClient.get().uri("http://localhost:8082/orders/" + customersId)
                .retrieve().bodyToFlux(Order.class)
                .retryWhen(Retry.backoff(10, Duration.ofSeconds(2)))
                .onErrorResume(e -> Flux.empty())
                .collect(Collectors.toList())
                .zipWith(Mono.just(new Customer(customersId, "John")))
                .map(t -> new CustomerOrders(t.getT2(), t.getT1()));
    }


}

@AllArgsConstructor
@Getter
class CustomerOrders {
    private final Customer customer;
    private final List<Order> orders;

}

//fixme CPP
@AllArgsConstructor
@Getter
class Order {
    private final String id;
    private final BigDecimal totalAmount;
    private final Integer customerId;
}
