package com.example.egen.paymentprocessing.controller;

import com.example.egen.paymentprocessing.model.Customer;
import com.example.egen.paymentprocessing.model.CustomerRequest;
import com.example.egen.paymentprocessing.repository.CustomerDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
