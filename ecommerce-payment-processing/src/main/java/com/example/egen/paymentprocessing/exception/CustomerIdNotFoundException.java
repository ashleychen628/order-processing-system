package com.example.egen.paymentprocessing.exception;

import org.springframework.http.HttpStatus;

public class CustomerIdNotFoundException extends Exception {
    public CustomerIdNotFoundException(HttpStatus notFound, String s) {
        super(s);
    }
}
