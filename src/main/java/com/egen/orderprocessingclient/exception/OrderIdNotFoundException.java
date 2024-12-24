package com.egen.orderprocessingclient.exception;

import org.springframework.http.HttpStatus;

public class OrderIdNotFoundException extends Exception {
    public OrderIdNotFoundException(HttpStatus notFound, String s) {
        super(s);
    }
}
