package com.egen.orderprocessingclient.model;

public class CustomerPaymentDetails {
    private long paymentId;
    private String type;
    private Billing billingAddress;

    public long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(long paymentId) {
        assert paymentId >= 0 : "paymentId must be non-negative";
        this.paymentId = paymentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        assert type != null : "type must not be null";
        assert !type.isEmpty() : "type must not be empty";
        this.type = type;
    }

    public Billing getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(Billing billingAddress) {
        assert billingAddress != null : "billingAddress must not be null";
        this.billingAddress = billingAddress;
    }

    public static void main(String[] args) {
        CustomerPaymentDetails details = new CustomerPaymentDetails();

        // Test setPaymentId
        details.setPaymentId(12345);
        assert details.getPaymentId() == 12345 : "paymentId mismatch";

        try {
            details.setPaymentId(-1);
            assert false : "Negative paymentId should not be allowed";
        } catch (AssertionError e) {
            System.out.println("Caught expected error: " + e.getMessage());
        }

        // Test setType
        details.setType("CreditCard");
        assert details.getType().equals("CreditCard") : "type mismatch";

        try {
            details.setType(null);
            assert false : "type should not accept null";
        } catch (AssertionError e) {
            System.out.println("Caught expected error: " + e.getMessage());
        }

        try {
            details.setType("");
            assert false : "type should not accept empty string";
        } catch (AssertionError e) {
            System.out.println("Caught expected error: " + e.getMessage());
        }

        Billing billing = new Billing();
    
        billing.setAddressLine1("123 Main St");
        billing.setCity("Springfield");
        billing.setState("IL");
        billing.setZip(62704);
        details.setBillingAddress(billing);
        assert details.getBillingAddress() == billing : "billingAddress mismatch";

        try {
            details.setBillingAddress(null);
            assert false : "billingAddress should not accept null";
        } catch (AssertionError e) {
            System.out.println("Caught expected error: " + e.getMessage());
        }
    }
}
