package com.trinity.webflux_playground.sec02.controller;

import com.trinity.webflux_playground.sec02.model.Customer;
import com.trinity.webflux_playground.sec02.repository.CustomerRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;


@RestController
@RequestMapping("/orders")
public class OrderController {

    private final CustomerRepository customerRepository;

    public OrderController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping(value = "/customers")
    public Flux<Customer> getCustomer() {
        return customerRepository.findAll();
    }



}
