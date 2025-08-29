package com.trinity.webflux_playground.sec01;

import com.trinity.webflux_playground.sec01.model.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClient;

import java.util.List;

@RestController
@RequestMapping("traditional")
public class WebTraditionalController {

    private static final Logger logger = LoggerFactory.getLogger(WebTraditionalController.class);

    final RestClient restClient;

    public WebTraditionalController(RestClient restClient) {
        this.restClient = restClient;
    }

    @GetMapping("/products")
    public List<Product> getProduct() {
        var list = restClient.get()
                .uri("/demo01/products")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Product>>() {});

        logger.info("list of products: {}", list);

        return list;
    }

}
