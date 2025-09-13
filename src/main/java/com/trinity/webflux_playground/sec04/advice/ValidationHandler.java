package com.trinity.webflux_playground.sec04.advice;

import com.trinity.webflux_playground.sec04.exceptions.CustomerNotFoundException;
import com.trinity.webflux_playground.sec04.exceptions.MissingNameException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.support.WebExchangeBindException;

import java.net.URI;
import java.util.List;

@ControllerAdvice
public class ValidationHandler {

    private static final Logger logger = LoggerFactory.getLogger(ValidationHandler.class);

    @ExceptionHandler(WebExchangeBindException.class)
    public ResponseEntity<List<ProblemDetail>> handleException(WebExchangeBindException e) {
        var errors = e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(objectError -> {

                    logger.info("Code: {} ", objectError.getCode());

                    var problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, objectError.toString());
                    problemDetail.setType(URI.create("http://example.com/problems/customer-not-found"));
                    problemDetail.setTitle(objectError.getDefaultMessage());
                    return problemDetail;
                }).toList();

        return ResponseEntity.badRequest().body(errors);
    }

    @ExceptionHandler(CustomerNotFoundException.class)
    public ProblemDetail handleException(CustomerNotFoundException ex){
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setType(URI.create("http://example.com/problems/customer-not-found"));
        problem.setTitle("Customer Not Found");
        return problem;
    }


    @ExceptionHandler(MissingNameException.class)
    public ProblemDetail missingNameException(MissingNameException ex){
        var problem = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, ex.getMessage());
        problem.setType(URI.create("http://example.com/problems/customer-not-found"));
        problem.setTitle("Customer Not Found");
        return problem;
    }

}