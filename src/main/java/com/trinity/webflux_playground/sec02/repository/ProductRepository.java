package com.trinity.webflux_playground.sec02.repository;

import com.trinity.webflux_playground.sec02.model.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;


public interface ProductRepository extends ReactiveCrudRepository<Product, Integer> {

    @Query("SELECT * from product WHERE price between :lowRange and :highRange")
    Flux<Product> findProductByRange(Integer lowRange, Integer highRange);

    Flux<Product> findByPriceBetween(Integer lowRange, Integer highRange);

    Flux<Product> findBy(Pageable pageable);

}
