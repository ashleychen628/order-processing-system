package com.example.egen.paymentprocessing.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.egen.paymentprocessing.model.Payment;

@Repository
public interface PaymentDetailsRepository extends JpaRepository<Payment, Long> {
    List<Payment> findByCustomerId(Long customerId);
}
