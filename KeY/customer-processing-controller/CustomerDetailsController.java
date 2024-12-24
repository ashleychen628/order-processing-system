package com.example.egen.paymentprocessing.controller;

import com.example.egen.paymentprocessing.model.Customer;
import com.example.egen.paymentprocessing.model.CustomerRequest;
import com.example.egen.paymentprocessing.repository.CustomerDetailsRepository;

public class CustomerDetailsController {

    private /*@ spec_public @*/ CustomerDetailsRepository customerDetailsRepository;

    //@ public invariant customerDetailsRepository != null;

    /*@ public normal_behavior
      @ requires customerDetailsRepository != null;
      @ ensures this.customerDetailsRepository == customerDetailsRepository;
      @*/
    public CustomerDetailsController(CustomerDetailsRepository customerDetailsRepository) {
        this.customerDetailsRepository = customerDetailsRepository;
    }

    /*@ public normal_behavior
      @ requires customerRequest != null;
      @ requires customerRequest.getCustomer() != null;
      @ ensures \result != null;
      @ ensures \result.equals(customerDetailsRepository.save(customerRequest.getCustomer()));
      @*/
    public /*@ pure @*/ Customer createCustomer(CustomerRequest customerRequest) {
        return customerDetailsRepository.save(customerRequest.getCustomer());
    }

    /*@ public normal_behavior
      @ requires customerId != null;
      @ requires customerId > 0;
      @ ensures \result != null;
      @ ensures \result.equals(customerDetailsRepository.findById(customerId).orElse(null));
      @ signals_only (Exception e) customerDetailsRepository.findById(customerId).isEmpty();
      @*/
    public /*@ pure @*/ Customer getCustomerInfo(Long customerId) throws Exception {
        return customerDetailsRepository.findById(customerId).orElseThrow(() -> new Exception("Customer not found"));
    }

    /*@ public normal_behavior
      @ requires customerId != null;
      @ requires customerId > 0;
      @ ensures \result == customerDetailsRepository.findById(customerId).isPresent();
      @*/
    public /*@ pure @*/ boolean isRegisteredCustomer(Long customerId) {
        return customerDetailsRepository.findById(customerId).isPresent();
    }
}

