package com.trinity.webflux_playground.sec04.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trinity.webflux_playground.sec04.dto.CustomerDto;
import com.trinity.webflux_playground.sec04.exceptions.ApplicationExceptions;
import com.trinity.webflux_playground.sec04.service.CustomerService;
import com.trinity.webflux_playground.sec04.validator.RequestValidator;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("paginated")
    public Mono<Page<CustomerDto>> getAllCustomersPaginated(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "3") Integer size) {

        return customerService.getCustomersByPage(PageRequest.of(page, size));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<CustomerDto>> getCustomerById(@PathVariable Integer id) {
        return customerService.getCustomerById(id)
                .map(ResponseEntity::ok)
                .switchIfEmpty(ApplicationExceptions.customerNotFound(id));
    }

    @PostMapping
    public Mono<CustomerDto> save(@Valid @RequestBody CustomerDto customerMono) {
        return customerService.save(Mono.just(customerMono))
                .map(customer -> mapper.convertValue(customer, CustomerDto.class));
    }

    @PostMapping("/mono")
    public Mono<CustomerDto> saveMono(@RequestBody Mono<CustomerDto> customerMono) {

        return customerMono
                .transform(RequestValidator.validate())
                .as(customerService::save);
    }

    @PutMapping("{id}")
    public Mono<ResponseEntity<CustomerDto>> update(@PathVariable Integer id, @RequestBody Mono<CustomerDto> customerMono) {

        return customerService.update(id, customerMono)
                .map(customer -> mapper.convertValue(customer, CustomerDto.class))
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable Integer id) {
        return customerService.delete(id)
                .filter(value -> value)
                .map(value -> ResponseEntity.ok().<Void>build())
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

}
