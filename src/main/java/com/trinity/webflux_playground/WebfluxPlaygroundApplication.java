package com.trinity.webflux_playground;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.trinity.webflux_playground.${section}"})
public class WebfluxPlaygroundApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebfluxPlaygroundApplication.class, args);
	}

}
