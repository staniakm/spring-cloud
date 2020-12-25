package com.mariusz.customers.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CustomerOrders {
    private final Customer customer;
    private final List<Order> orders;

}
