package com.mariusz.customers.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
@AllArgsConstructor
public class Customer {
    @Id
    private final Integer id;
    private final String name;
}
