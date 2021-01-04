package com.egen.orderprocessingclient.repository;

import com.egen.orderprocessingclient.model.OrderDetails;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface OrderRepository extends CrudRepository<OrderDetails, String> {
    //@Query("SELECT o.orderStatus FROM Order o where o.orderNumber = :orderNumber")
    Optional<OrderDetails> findByOrderNumber(String orderNumber);

    Optional<OrderDetails> findByCustomerIdAndOrderNumber(long customerId, String orderNumber);

    Optional<OrderDetails> findByCustomerId(long customerId);
}
