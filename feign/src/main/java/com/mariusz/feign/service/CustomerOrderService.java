package com.mariusz.feign.service;

import com.mariusz.feign.client.CustomerOrdersClient;
import com.mariusz.feign.model.CustomerOrders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerOrderService {

    private final CustomerOrdersClient client;

    public CustomerOrders getCustomerOrders(Integer customerId) {
        log.info("{} - Fetching orders for {}", this.getClass().getName(), customerId);
        return client.getOrders(customerId);
    }

}


