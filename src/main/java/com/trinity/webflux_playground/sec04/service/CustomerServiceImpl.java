package com.trinity.webflux_playground.sec04.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trinity.webflux_playground.sec04.dto.CustomerDto;
import com.trinity.webflux_playground.sec04.model.Customer;
import com.trinity.webflux_playground.sec04.repository.CustomerRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;

    private final ObjectMapper mapper;

    public CustomerServiceImpl(CustomerRepository customerRepository, ObjectMapper mapper) {
        this.customerRepository = customerRepository;
        this.mapper = mapper;
    }

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
    public Mono<CustomerDto> update(Integer id, Mono<CustomerDto> customerDtoMono) {
        return customerRepository.findById(id)
                .flatMap(customer -> customerDtoMono)
                .map(customer -> mapper.convertValue(customer, Customer.class))
                .doOnNext(customerDto -> customerDto.setId(id))
                .flatMap(customer -> customerRepository.save(customer))
                .map(customer -> mapper.convertValue(customer, CustomerDto.class));
    }

    @Override
    public Mono<Boolean> delete(Integer id) {
        return customerRepository.deleteCustomerById(id);
    }

    @Override
    public Mono<Page<CustomerDto>> getCustomersByPage(PageRequest pageRequest) {
        pageRequest.withSort(Sort.by("id", "name").ascending());
        return customerRepository.findBy(pageRequest)
                .map(customer -> mapper.convertValue(customer, CustomerDto.class))
                .collectList()
                .zipWith(customerRepository.count())
                .map(t -> new PageImpl<>(t.getT1(), pageRequest, t.getT2()));
    }


}
