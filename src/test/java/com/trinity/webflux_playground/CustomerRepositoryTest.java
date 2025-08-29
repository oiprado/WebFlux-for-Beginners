package com.trinity.webflux_playground;


import com.trinity.webflux_playground.sec02.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.test.StepVerifier;


public class CustomerRepositoryTest extends AbstractTest {

    private static final Logger logger = LoggerFactory.getLogger(CustomerRepositoryTest.class);

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void findAllCustomers() {

        customerRepository.findAll()
                .doOnNext(customer -> logger.info("{}", customer))
                .as(StepVerifier::create)
                .expectNextCount(10)
                .expectComplete()
                .verify();
    }

    @Test
    public void findCustomerById() {

        customerRepository.findById(1)
                .doOnNext(customer -> logger.info("{}", customer))
                .as(StepVerifier::create)
                .assertNext(cust -> {
                    Assertions.assertEquals("sam@gmail.com", cust.getEmail());
                })
                .expectComplete()
                .verify();
    }

}
