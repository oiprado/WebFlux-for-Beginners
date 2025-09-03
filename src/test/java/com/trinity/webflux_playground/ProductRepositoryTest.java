package com.trinity.webflux_playground;

import com.trinity.webflux_playground.sec02.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import reactor.test.StepVerifier;

public class ProductRepositoryTest extends AbstractTest {

    private static final Logger logger = LoggerFactory.getLogger(ProductRepositoryTest.class);

    @Autowired
    private ProductRepository productRepository;

    @Test
    void getProductByRange() {
        productRepository.findProductByRange(400, 1000)
                .doOnNext(product -> logger.info("{}", product.toString()))
                .as(StepVerifier::create)
                .expectNextCount(4)
                .expectComplete()
                .verify();
    }

    @Test
    void getProductByPriceRange() {
        productRepository.findByPriceBetween(400, 1000)
                .doOnNext(product -> logger.info("{}", product.toString()))
                .as(StepVerifier::create)
                .expectNextCount(4)
                .expectComplete()
                .verify();
    }

    @Test
    void getProductByRangePageable() {
        productRepository.findBy(
                    PageRequest.of(1,4)
                            .withSort(
                                Sort.by("price").descending()
                            )
                )
                .doOnNext(product -> logger.info("{}", product.toString()))
                .as(StepVerifier::create)
                .expectNextCount(4)
                .expectComplete()
                .verify();
    }

}
