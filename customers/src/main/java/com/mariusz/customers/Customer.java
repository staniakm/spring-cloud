package com.mariusz.customers;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Customer {
    private final Integer id;
    private final String name;
}
