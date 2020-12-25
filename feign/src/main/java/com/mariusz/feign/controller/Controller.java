package com.mariusz.feign.controller;

import com.mariusz.feign.model.CustomerOrders;
import com.mariusz.feign.service.CustomerOrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/customerOrder")
@RequiredArgsConstructor
@Slf4j
public class Controller {

    private final CustomerOrderService customerOrderService;

    @GetMapping("/{customerId}")
    CustomerOrders getOrder(@PathVariable Integer customerId) {
        return customerOrderService.getCustomerOrders(customerId);
    }
}
