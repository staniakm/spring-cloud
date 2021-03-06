package com.mariusz.orders.controller;

import com.mariusz.orders.model.Order;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping("/orders")
@Slf4j
public class OrderController {
    //todo switch on postgres db
    private static final Map<Integer, List<Order>> db = new ConcurrentHashMap<>();

    @PostConstruct
    private void inMemoryDb() {
        Random random = new Random();
        for (int i = 1; i <= 2; i++) {
            List<Order> orders = new ArrayList<>();
            for (int j = 1; j <= (random.nextInt(20)); j++) {
                orders.add(new Order(i + "_" + j, BigDecimal.valueOf(random.nextInt(300)), i));
            }
            db.put(i, orders);
        }
    }

    @GetMapping("/{customerId}")
    public Flux<Order> getOrders(@PathVariable Integer customerId) {
        log.info("Fetching orders for customer " + customerId);
        return Flux.fromIterable(db.getOrDefault(customerId, Collections.emptyList()));
    }
}
