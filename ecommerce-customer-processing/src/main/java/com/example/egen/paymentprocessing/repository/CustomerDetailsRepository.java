package com.example.egen.paymentprocessing.repository;

import com.example.egen.paymentprocessing.model.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerDetailsRepository extends CrudRepository<Customer, Long> {
}
