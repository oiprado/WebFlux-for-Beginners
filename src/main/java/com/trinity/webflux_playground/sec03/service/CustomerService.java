package com.trinity.webflux_playground.sec03.service;

import com.trinity.webflux_playground.sec03.model.Customer;
import com.trinity.webflux_playground.sec03.dto.CustomerDto;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

    Flux<CustomerDto> getAllCustomers();

    Mono<CustomerDto> getCustomerById(Integer id);

    Mono<CustomerDto> save(Mono<CustomerDto> customerMono);

    Mono<Void> delete(Integer id);
}
