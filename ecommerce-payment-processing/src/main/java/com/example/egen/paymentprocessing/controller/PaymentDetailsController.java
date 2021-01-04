package com.example.egen.paymentprocessing.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.egen.paymentprocessing.model.CustomerPaymentDetails;
import com.example.egen.paymentprocessing.model.CustomerPaymentDetailsList;
import com.example.egen.paymentprocessing.model.Payment;
import com.example.egen.paymentprocessing.repository.PaymentDetailsRepository;
import com.google.gson.Gson;

@RestController
public class PaymentDetailsController {

    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;

    @GetMapping("getCustomerPayment/{customerId}")
    public CustomerPaymentDetailsList getCustomerPayment(@PathVariable Long customerId) {
        List<Payment> payments = paymentDetailsRepository.findByCustomerId(customerId);
        
        List<CustomerPaymentDetails> customerPaymentDetailsList = new ArrayList<>(payments.size());
        int i = 0;
        for (Payment payment : payments) {
            CustomerPaymentDetails customerPaymentDetails = new CustomerPaymentDetails();
            customerPaymentDetails.setBillingAddress(payment.getBillingAddress());
            customerPaymentDetails.setPaymentId(payment.getPaymentId());
            customerPaymentDetails.setType(payment.getType());
            customerPaymentDetailsList.add(i, customerPaymentDetails);
        }
        CustomerPaymentDetailsList customerPaymentDetailsObject = new CustomerPaymentDetailsList();
        customerPaymentDetailsObject.setCustomerPaymentDetails(customerPaymentDetailsList);
        return customerPaymentDetailsObject;
    }
}
