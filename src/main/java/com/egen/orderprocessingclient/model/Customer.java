package com.egen.orderprocessingclient.model;

public class Customer {
    private long customerId;

    public Customer(){}

    public Customer(long customerId) {
        this.customerId = customerId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
}
