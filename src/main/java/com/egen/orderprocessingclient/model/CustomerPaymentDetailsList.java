package com.egen.orderprocessingclient.model;

import java.util.ArrayList;
import java.util.List;

public class CustomerPaymentDetailsList {
    private List<CustomerPaymentDetails> customerPaymentDetails;

    public List<CustomerPaymentDetails> getCustomerPaymentDetails() {
        return customerPaymentDetails;
    }

    public void setCustomerPaymentDetails(List<CustomerPaymentDetails> customerPaymentDetails) {
      assert customerPaymentDetails != null : "CustomerPaymentDetails list must not be null";  
      this.customerPaymentDetails = customerPaymentDetails;
    }

    public static void main(String[] args) {
        CustomerPaymentDetailsList list = new CustomerPaymentDetailsList();

        // Test setCustomerPaymentDetails with valid input
        List<CustomerPaymentDetails> paymentDetails = new ArrayList<>();
        list.setCustomerPaymentDetails(paymentDetails);
        assert list.getCustomerPaymentDetails() == paymentDetails : "setCustomerPaymentDetails() did not update the list correctly";

        // Test setCustomerPaymentDetails with null
        try {
            list.setCustomerPaymentDetails(null);
            assert false : "setCustomerPaymentDetails() should not accept null";
        } catch (AssertionError e) {
            System.out.println("Caught expected error: " + e.getMessage());
        }

        // Test getCustomerPaymentDetails for null safety
        assert list.getCustomerPaymentDetails() != null : "getCustomerPaymentDetails() should not return null";
        assert list.getCustomerPaymentDetails().isEmpty() : "CustomerPaymentDetails list should be empty";
    }
}
