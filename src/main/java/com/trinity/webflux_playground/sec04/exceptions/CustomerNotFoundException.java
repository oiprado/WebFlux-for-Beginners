package com.trinity.webflux_playground.sec04.exceptions;

public class CustomerNotFoundException extends RuntimeException {
    public CustomerNotFoundException(Integer id) {
        super(String.format("Customer [id=%d] is not found", id));
    }
}
