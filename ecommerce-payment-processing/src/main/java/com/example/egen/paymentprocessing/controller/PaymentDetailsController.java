package com.example.egen.paymentprocessing.controller;

import com.example.egen.paymentprocessing.exception.CustomerIdNotFoundException;
import com.example.egen.paymentprocessing.model.CustomerPaymentDetailsList;
import com.example.egen.paymentprocessing.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PaymentDetailsController {

    @Autowired
    private PaymentService paymentService;

    @GetMapping("/getCustomerPayment/{customerId}")
    public CustomerPaymentDetailsList getCustomerPayment(@PathVariable Long customerId) throws CustomerIdNotFoundException {
        return paymentService.getCustomerPayment(customerId);
    }
}
