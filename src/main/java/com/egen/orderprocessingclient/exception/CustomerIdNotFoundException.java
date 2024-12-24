package com.egen.orderprocessingclient.exception;

import org.springframework.http.HttpStatus;

public class CustomerIdNotFoundException extends Exception {
    public CustomerIdNotFoundException(HttpStatus notFound, String s) {
        super(s);
    }
}
