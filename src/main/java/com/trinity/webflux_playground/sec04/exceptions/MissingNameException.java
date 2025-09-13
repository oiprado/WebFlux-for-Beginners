package com.trinity.webflux_playground.sec04.exceptions;

public class MissingNameException extends RuntimeException {
    public MissingNameException() {
        super("Missing Name exception");
    }
}
