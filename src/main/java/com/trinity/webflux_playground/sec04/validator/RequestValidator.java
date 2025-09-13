package com.trinity.webflux_playground.sec04.validator;

import com.trinity.webflux_playground.sec04.dto.CustomerDto;
import com.trinity.webflux_playground.sec04.exceptions.ApplicationExceptions;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.function.UnaryOperator;

public class RequestValidator {

    public static UnaryOperator<Mono<CustomerDto>> validate() {
        return mono -> mono.filter(hasName())
                .switchIfEmpty(ApplicationExceptions.missingName())
                .filter(hasValidMail())
                .switchIfEmpty(ApplicationExceptions.missingValidMail())
                ;
    }

    private static Predicate<CustomerDto> hasName() {
        return dto -> Objects.nonNull(dto.getName());
    }

    private static Predicate<CustomerDto> hasValidMail() {
        return dto -> Objects.nonNull(dto.getEmail()) && dto.getEmail().contains("@");
    }

}
