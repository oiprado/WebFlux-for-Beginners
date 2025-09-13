package com.trinity.webflux_playground.sec04.repository;

import com.trinity.webflux_playground.sec04.model.Customer;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Modifying;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Repository
public interface CustomerRepository extends ReactiveCrudRepository<Customer, Integer> {

    Flux<Customer> findByName(String name);

    Flux<Customer> findByEmailContaining(String email);

    @Modifying
    @Query("delete from customer where id= :id")
    Mono<Boolean> deleteCustomerById(Integer id);

    Flux<Customer> findBy(Pageable pageable);

}
