package com.mariusz.customers;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.*;
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
@Slf4j
class CustomerController {

    private final WebClient webClient;
    private final CustomerRepository repository;

    @GetMapping("")
    public Flux<Customer> getCustomers() {
        return repository.findAll();
    }

    @PostMapping("")
    public Mono<Customer> addCustomer(@RequestBody Customer customer){
        return repository.save(customer);
    }

    @GetMapping("/{customersId}/orders")
    public Mono<CustomerOrders> getCustomerOrders(@PathVariable Integer customersId) {
        log.info("Fetching customer orders for {}", customersId);
        return repository.findById(customersId)
                .zipWhen(customer ->  this.webClient.get().uri("http://localhost:8082/orders/" + customersId)
                        .retrieve().bodyToFlux(Order.class)
                        .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(5)))
                        .onErrorResume(e -> Flux.empty())
                        .collect(Collectors.toList()))
                .map(t -> new CustomerOrders(t.getT1(), t.getT2()));
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
