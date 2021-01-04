package com.egen.orderprocessingclient.model;

public class CustomerPaymentDetails {
    private long paymentId;
    private String type;
    private Billing billingAddress;

    public long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Billing getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Billing billingAddress) {
        this.billingAddress = billingAddress;
    }
}
