package com.example.egen.paymentprocessing.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.egen.paymentprocessing.model.Customer;

@Repository
public interface CustomerDetailsRepository extends JpaRepository<Customer, Long> {
}
