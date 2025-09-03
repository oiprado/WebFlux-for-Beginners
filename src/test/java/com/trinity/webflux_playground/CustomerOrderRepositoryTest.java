package com.trinity.webflux_playground;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.trinity.webflux_playground.sec02.model.Customer;
import com.trinity.webflux_playground.sec02.model.CustomerOrder;
import com.trinity.webflux_playground.sec02.model.OrderDetails;
import com.trinity.webflux_playground.sec02.repository.CustomerOrderRepository;
import com.trinity.webflux_playground.sec02.repository.CustomerRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import reactor.test.StepVerifier;

import static org.slf4j.LoggerFactory.getLogger;


public class CustomerOrderRepositoryTest extends AbstractTest {

    private static final Logger logger = getLogger(CustomerOrderRepositoryTest.class);

    @Autowired
    private CustomerOrderRepository customerRepository;
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();


    @Test
    public void getProductsOrderByCustomer() {

        customerRepository.getProductsOrderByCustomer("sam")
                .doOnNext(customer -> logger.info("{}", gson.toJson(customer)))
                .as(StepVerifier::create)
                .expectNextCount(2)
                .expectComplete()
                .verify();
    }

    @Test
    public void orderDetailsByProduct() {

        customerRepository.getOrderDetailsByProduct("iphone 20")
                .doOnNext(orderDetails ->  logger.info("{}", orderDetails))
                .as(StepVerifier::create)
                .assertNext(orderDetails -> Assertions.assertEquals(975, orderDetails.amount()))
                .assertNext(orderDetails -> Assertions.assertEquals(950, orderDetails.amount()))
                .expectComplete()
                .verify();
    }




}
