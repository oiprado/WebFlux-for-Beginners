package com.trinity.webflux_playground.sec04.exceptions;

import reactor.core.publisher.Mono;

public class ApplicationExceptions {

    public static <T> Mono<T> customerNotFound(Integer id) {
        return Mono.error(new CustomerNotFoundException(id));
    }

    public static <T> Mono<T> missingName() {
        return Mono.error(new MissingNameException());
    }

    public static <T> Mono<T> missingValidMail() {
        return Mono.error(new MissingValidMailException());
    }
}
