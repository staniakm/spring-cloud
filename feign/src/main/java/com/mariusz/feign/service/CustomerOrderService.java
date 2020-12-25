package com.mariusz.feign.service;

import com.mariusz.feign.client.CustomerClient;
import com.mariusz.feign.client.OrdersClient;
import com.mariusz.feign.model.Customer;
import com.mariusz.feign.model.CustomerOrders;
import com.mariusz.feign.model.Order;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerOrderService {

    private final CustomerClient customerClient;
    private final OrdersClient ordersClient;

    public CustomerOrders getCustomerOrders(Integer customerId) {
        log.info("{} - Fetching orders for {}", this.getClass().getName(), customerId);
        Customer customer = customerClient.getCustomer(customerId);
        List<Order> orders = customer != null ? ordersClient.getOrders(customerId) : List.of();
        return new CustomerOrders(customer, orders);
    }

}


