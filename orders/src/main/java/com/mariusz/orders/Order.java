package com.mariusz.orders;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
public class Order {
    private final String id;
    private final BigDecimal totalAmount;
    private final Integer customerId;
}
