package com.mariusz.feign.client;

import com.mariusz.feign.model.CustomerOrders;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "http://localhost:8081/customers", name = "customerOrders")
public interface CustomerOrdersClient {

    @RequestMapping(method = RequestMethod.GET, value = "/{customersId}/orders")
    CustomerOrders getOrders(@PathVariable Integer customersId);
}
