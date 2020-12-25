package com.mariusz.feign.client;

import com.mariusz.feign.model.Customer;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url = "http://localhost:8081/customers", name = "customers")
public interface CustomerClient {

    @RequestMapping(method = RequestMethod.GET, value = "/{customerId}")
    Customer getCustomer(@PathVariable Integer customerId);
}
