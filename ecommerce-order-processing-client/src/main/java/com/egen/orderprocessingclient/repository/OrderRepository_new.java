package com.egen.orderprocessingclient.repository;
import java.util.List;
import com.egen.orderprocessingclient.model.OrderDetails;

public interface OrderRepository {
  OrderDetails findById(Long id);
  List<OrderDetails> findAll();
  void save(OrderDetails order);
}
