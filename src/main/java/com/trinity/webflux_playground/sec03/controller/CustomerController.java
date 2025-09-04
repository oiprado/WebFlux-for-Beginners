package com.trinity.webflux_playground.sec03.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trinity.webflux_playground.sec03.dto.CustomerDto;
import com.trinity.webflux_playground.sec03.model.Customer;
import com.trinity.webflux_playground.sec03.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/customers")
public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private ObjectMapper mapper;

    @GetMapping
    public Flux<CustomerDto> getAllCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/{id}")
    public Mono<CustomerDto> getCustomerById(@PathVariable Integer id) {
        return customerService.getCustomerById(id);
    }

    @PostMapping
    public Mono<CustomerDto> save(@RequestBody CustomerDto customerMono) {
        return customerService.save(Mono.just(customerMono))
                .map(customer -> mapper.convertValue(customer, CustomerDto.class));
    }

    @PutMapping("{id}")
    public Mono<CustomerDto> update(@PathVariable Integer id,  @RequestBody Mono<CustomerDto> customerMono) {

        return customerService.save(customerMono)
                .map(customer -> mapper.convertValue(customer, CustomerDto.class));
    }

    @DeleteMapping("{id}")
    public Mono<Void> delete(@PathVariable Integer id) {
        return customerService.delete(id);

    }

}
