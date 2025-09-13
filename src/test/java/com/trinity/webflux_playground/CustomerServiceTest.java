package com.trinity.webflux_playground;

import com.trinity.webflux_playground.sec04.dto.CustomerDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.ProblemDetail;
import org.springframework.test.web.reactive.server.WebTestClient;

@AutoConfigureWebTestClient
@SpringBootTest(properties = "section=sec04")
public class CustomerServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(CustomerServiceTest.class);

    @Autowired
    WebTestClient webTestClient;

    @Test
    public void getAllCustomers_test() {
        webTestClient.get()
                .uri("/customers")
                .exchange().expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBodyList(CustomerDto.class)
                .value(customers -> logger.info("{}", customers.size()))
                .hasSize(12);
    }

    @Test
    public void createCustomerTest() {
        webTestClient.post()
                .uri("/customers")
                .bodyValue(new CustomerDto("oiprado@gmail.com", "Oscar Ivan"))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id=11");
    }

    @Test
    public void createCustomerSignalErrorTest() {
        var dto = new CustomerDto();
        webTestClient.post()
                .uri("/customers")
                .bodyValue(dto)
                .exchange()
                .expectStatus().is4xxClientError()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .consumeWith(result -> logger.info("{}", result));
    }


    @Test
    public void updateCustomerTest() {

        webTestClient.post()
                .uri("/customers")
                .bodyValue(new CustomerDto("oiprado@gmail.com", "Oscar Ivan"))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.id=11");

        webTestClient.put()
                .uri("/customers/11")
                .bodyValue(new CustomerDto(11,"Oscar Ivan Prado", "oiprado@gmail.com"))
                .exchange()
                .expectStatus().is2xxSuccessful()
                .expectHeader().contentType(MediaType.APPLICATION_JSON)
                .expectBody()
                .jsonPath("$.name").isEqualTo("Oscar Ivan Prado");
    }

    @Test
    public void getCustomerById_NotFound_Exception_test() {
        webTestClient.get()
                .uri("/customers/110")
                .exchange()
                .expectStatus().is4xxClientError()
                .expectBody(ProblemDetail.class)
                .value(problem -> {
                    logger.info("{}", problem);
                    Assertions.assertEquals("Customer Not Found", problem.getTitle());
                    Assertions.assertEquals(404, problem.getStatus());
                    Assertions.assertEquals( "Customer [id=110] is not found", problem.getDetail());
                });
    }

}
