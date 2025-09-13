package com.trinity.webflux_playground.sec04.service;

import com.trinity.webflux_playground.sec04.dto.CustomerDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerService {

    Flux<CustomerDto> getAllCustomers();

    Mono<CustomerDto> getCustomerById(Integer id);

    Mono<CustomerDto> save(Mono<CustomerDto> customerMono);

    Mono<CustomerDto> update(Integer id, Mono<CustomerDto> customerDtoMono);

    Mono<Boolean> delete(Integer id);

    Mono<Page<CustomerDto>> getCustomersByPage(PageRequest pageRequest);
}
