package com.egen.orderprocessingclient.exception;

import org.springframework.http.HttpStatus;

public class BillingAddressNotMatchException extends Exception {
    public BillingAddressNotMatchException(HttpStatus notAcceptable, String s) {
        super(s);
    }
}
