package com.trinity.webflux_playground.sec03.service;

import com.trinity.webflux_playground.sec03.model.Customer;
import com.trinity.webflux_playground.sec03.dto.CustomerDto;
import com.trinity.webflux_playground.sec03.repository.CustomerRepository;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ObjectMapper mapper;

    @Override
    public Flux<CustomerDto> getAllCustomers() {
        return customerRepository.findAll()
                .map(customer -> mapper.convertValue(customer, CustomerDto.class));
    }

    @Override
    public Mono<CustomerDto> getCustomerById(Integer id) {
        return customerRepository.findById(id)
                .map(customer -> mapper.convertValue(customer, CustomerDto.class));
    }

    @Override
    public Mono<CustomerDto> save(Mono<CustomerDto> customerMono) {
        return customerMono.map(customerDto -> mapper.convertValue(customerDto, Customer.class))
                .flatMap(customerRepository::save)
                .map(customer -> mapper.convertValue(customer, CustomerDto.class));
    }

    @Override
    public Mono<Void> delete(Integer id) {
        return customerRepository.deleteById(id);
    }
}
