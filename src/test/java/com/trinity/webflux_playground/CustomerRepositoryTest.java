package com.trinity.webflux_playground;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trinity.webflux_playground.sec02.model.Customer;
import com.trinity.webflux_playground.sec02.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Sort;
import reactor.test.StepVerifier;

import static org.slf4j.LoggerFactory.getLogger;


public class CustomerRepositoryTest extends AbstractTest {

    private static final Logger logger = getLogger(CustomerRepositoryTest.class);

    @Autowired
    private CustomerRepository customerRepository;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    @Test
    public void findAllCustomers() {
        customerRepository.findAll()
                .doOnNext(customer -> logger.info("{}", gson.toJson(customer)))
                .as(StepVerifier::create)
                .expectNextCount(10)
                .expectComplete()
                .verify();
    }

    @Test
    public void findCustomerById() {
        customerRepository.findById(1)
                .doOnNext(customer -> logger.info("{}", gson.toJson(customer)))
                .as(StepVerifier::create)
                .assertNext(customer -> Assertions.assertEquals("sam@gmail.com", customer.getEmail()))
                .expectComplete()
                .verify();
    }

    @Test
    public void findCustomerByName() {
        customerRepository.findByName("mike")
                .doOnNext(customer -> logger.info("{}", gson.toJson(customer)))
                .as(StepVerifier::create)
                .assertNext(customer -> Assertions.assertEquals("mike@gmail.com", customer.getEmail()))
                .expectComplete()
                .verify();
    }

    @Test
    public void findCustomerByMailContaining() {
        customerRepository.findByEmailContaining("ke@")
                .doOnNext(customer -> logger.info("{}", gson.toJson(customer)))
                .as(StepVerifier::create).expectNextCount(2)
                .expectComplete()
                .verify();
    }

    @Test
    public void saveAndDelete() {

        var customer = new Customer();
        customer.setName("Oscar Ivan");
        customer.setEmail("oiprado@gmail.com");

        customerRepository.save(customer)
                .doOnNext(customerOnNext -> logger.info("{}", gson.toJson(customer)))
                .as(StepVerifier::create)
                .assertNext(newCustomer -> Assertions.assertNotNull(newCustomer.getId()))
                .expectComplete()
                .verify();

        customerRepository.count()
                .doOnNext(customerOnNext -> logger.info("{}", gson.toJson(customer)))
                .as(StepVerifier::create)
                .expectNext(11L)
                .expectComplete()
                .verify();

        customerRepository.delete(customer)
                .doOnNext(customerOnNext -> logger.info("{}", gson.toJson(customer)))
                .as(StepVerifier::create)
                .expectComplete()
                .verify();
    }

    @Test
    public void update() {

        customerRepository.findByName("sam")
                .doOnNext(customerOnNext -> {
                    logger.info("{}", gson.toJson(customerOnNext));
                    customerOnNext.setName("Oscar Ivan");
                }).flatMap(customer -> customerRepository.save(customer))
                .as(StepVerifier::create)
                .assertNext(newCustomer -> Assertions.assertEquals("Oscar Ivan", newCustomer.getName()))
                .expectComplete()
                .verify();

        customerRepository.findById(1)
                .doOnNext(customer -> logger.info("{}", gson.toJson(customer)))
                .as(StepVerifier::create)
                .assertNext(customer -> Assertions.assertEquals("sam@gmail.com", customer.getEmail()))
                .expectComplete()
                .verify();
    }


}
