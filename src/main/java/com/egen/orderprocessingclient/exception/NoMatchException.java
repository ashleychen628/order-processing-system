package com.egen.orderprocessingclient.exception;

import org.springframework.http.HttpStatus;

public class NoMatchException extends Exception {
    public NoMatchException(HttpStatus notFound, String s) {
        super(s);
    }
}
