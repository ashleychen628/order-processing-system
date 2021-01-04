package com.example.egen.paymentprocessing.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.egen.paymentprocessing.model.Customer;
import com.example.egen.paymentprocessing.model.CustomerRequest;
import com.example.egen.paymentprocessing.repository.CustomerDetailsRepository;

@RestController
public class CustomerDetailsController {

    @Autowired
    private CustomerDetailsRepository customerDetailsRepository;

    @PostMapping("/createNewCustomer")
    public Customer createCustomer(@RequestBody CustomerRequest customerRequest) {
        return customerDetailsRepository.save(customerRequest.getCustomer());
    }

    @GetMapping("/getCustomer/{customerId}")
    public Customer getCustomerInfo(@PathVariable Long customerId) {
        return customerDetailsRepository.findById(customerId).get();
    }

    @GetMapping("/isRegisteredCustomer/{customerId}")
    public Boolean isRegisteredCustomer(@PathVariable Long customerId) {
        return customerDetailsRepository.findById(customerId).isPresent();
    }
}
