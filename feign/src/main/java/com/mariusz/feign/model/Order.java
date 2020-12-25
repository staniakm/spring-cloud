package com.mariusz.feign.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

//fixme CPP
@AllArgsConstructor
@Getter
public class Order {
    private final String id;
    private final BigDecimal totalAmount;
    private final Integer customerId;
}
