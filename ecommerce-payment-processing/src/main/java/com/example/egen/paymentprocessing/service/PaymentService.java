package com.example.egen.paymentprocessing.service;

import com.example.egen.paymentprocessing.model.CustomerPaymentDetails;
import com.example.egen.paymentprocessing.model.CustomerPaymentDetailsList;
import com.example.egen.paymentprocessing.model.Payment;
import com.example.egen.paymentprocessing.repository.PaymentDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Service
public class PaymentService {

    @Autowired
    private PaymentDetailsRepository paymentDetailsRepository;

    public CustomerPaymentDetailsList getCustomerPayment(@PathVariable Long customerId) {
        List<Payment> payments = paymentDetailsRepository.findByCustomerId(customerId);

        List<CustomerPaymentDetails> customerPaymentDetailsList = new ArrayList<>(payments.size());

        payments.stream().forEach(payment -> {
            CustomerPaymentDetails customerPaymentDetails = new CustomerPaymentDetails();
            customerPaymentDetails.setBillingAddress(payment.getBillingAddress());
            customerPaymentDetails.setPaymentId(payment.getPaymentId());
            customerPaymentDetails.setType(payment.getType());
            customerPaymentDetailsList.add(customerPaymentDetails);
        });

        CustomerPaymentDetailsList customerPaymentDetailsObject = new CustomerPaymentDetailsList();
        customerPaymentDetailsObject.setCustomerPaymentDetails(customerPaymentDetailsList);
        return customerPaymentDetailsObject;
    }
}
