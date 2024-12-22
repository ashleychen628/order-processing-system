package com.egen.orderprocessingclient.exception;

import org.springframework.http.HttpStatus;

public class EnumNotPresentException extends Exception {
    public EnumNotPresentException(HttpStatus notFound, String s) {
        super(s);
    }
}