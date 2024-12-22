package com.egen.orderprocessingclient.repository;

import com.egen.orderprocessingclient.model.OrderDetails;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends CrudRepository<OrderDetails, String> {
    Optional<OrderDetails> findByOrderNumber(String orderNumber);

    Optional<OrderDetails> findByCustomerIdAndOrderNumber(long customerId, String orderNumber);

    List<OrderDetails> findByCustomerId(long customerId);
}
