package com.mariusz.customers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@SpringBootApplication
public class CustomersApplication {

    public static void main(String[] args) {
        SpringApplication.run(CustomersApplication.class, args);
    }

}

@RestController
@RequestMapping("/customers")
class CustomerController {

    @GetMapping("/")
    public Flux<Customer> getCustomers() {
        return Flux.just(new Customer(1, "John"), new Customer(2, "Mary"));
    }
}
