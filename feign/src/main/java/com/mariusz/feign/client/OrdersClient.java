package com.mariusz.feign.client;

import com.mariusz.feign.model.Order;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(url = "http://localhost:8082/orders", name = "orders")
public interface OrdersClient {

    @RequestMapping(method = RequestMethod.GET, value = "/{customersId}")
    List<Order> getOrders(@PathVariable Integer customersId);
}
