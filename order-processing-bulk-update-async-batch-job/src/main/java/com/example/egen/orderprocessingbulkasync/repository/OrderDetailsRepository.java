package com.example.egen.orderprocessingbulkasync.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.egen.orderprocessingbulkasync.entity.OrderDetails;

@Repository
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, String> {
    Optional<OrderDetails> findByOrderNumber(String orderNumber);
}
