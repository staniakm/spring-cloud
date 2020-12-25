package com.mariusz.customers.controller;

import com.mariusz.customers.model.Customer;
import com.mariusz.customers.model.CustomerOrders;
import com.mariusz.customers.model.Order;
import com.mariusz.customers.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
@Slf4j
public class CustomerController {

    private final WebClient webClient;
    private final CustomerRepository repository;

    @GetMapping("")
    public Flux<Customer> getCustomers() {
        log.info("Customer service: Fetching all customers ");
        return repository.findAll();
    }

    @PostMapping("")
    public Mono<Customer> addCustomer(@RequestBody Customer customer) {
        log.info("Customer service: Adding customer {}", customer.getName());
        return repository.save(customer);
    }

    @GetMapping("/{customerId}")
    public Mono<Customer> getCustomer(@PathVariable Integer customerId) {
        log.info("Customer service: Fetching customer {}", customerId);
        return repository.findById(customerId);
    }

    @GetMapping("/{customersId}/orders")
    public Mono<CustomerOrders> getCustomerOrders(@PathVariable Integer customersId) {
        log.info("Fetching customer orders for {}", customersId);
        return repository.findById(customersId)
                .zipWhen(customer -> this.webClient.get().uri("http://localhost:8082/orders/" + customersId)
                        .retrieve().bodyToFlux(Order.class)
                        .retryWhen(Retry.fixedDelay(3, Duration.ofSeconds(5)))
                        .onErrorResume(e -> Flux.empty())
                        .collect(Collectors.toList()))
                .map(t -> new CustomerOrders(t.getT1(), t.getT2()));
    }


}
