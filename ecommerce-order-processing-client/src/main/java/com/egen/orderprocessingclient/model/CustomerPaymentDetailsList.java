package com.egen.orderprocessingclient.model;

import java.util.List;

public class CustomerPaymentDetailsList {
    private List<CustomerPaymentDetails> customerPaymentDetails;

    public List<CustomerPaymentDetails> getCustomerPaymentDetails() {
        return customerPaymentDetails;
    }

    public void setCustomerPaymentDetails(List<CustomerPaymentDetails> customerPaymentDetails) {
        this.customerPaymentDetails = customerPaymentDetails;
    }
}
