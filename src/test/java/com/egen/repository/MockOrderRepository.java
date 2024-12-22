package com.egen.repository;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import com.egen.orderprocessingclient.model.OrderDetails;
import com.egen.orderprocessingclient.repository.OrderRepository;

import java.util.*;
import java.util.stream.Collectors;

public class MockOrderRepository implements OrderRepository {
    private final Map<String, OrderDetails> mockDatabase = new HashMap<>();

    @Override
    public Optional<OrderDetails> findByOrderNumber(String orderNumber) {
        return Optional.ofNullable(mockDatabase.get(orderNumber));
    }

    @Override
    public Optional<OrderDetails> findByCustomerIdAndOrderNumber(long customerId, String orderNumber) {
        return mockDatabase.values().stream()
                .filter(order -> order.getCustomerId() == customerId && order.getOrderNumber().equals(orderNumber))
                .findFirst();
    }

    @Override
    public List<OrderDetails> findByCustomerId(long customerId) {
        return mockDatabase.values().stream()
                .filter(order -> order.getCustomerId() == customerId)
                .collect(Collectors.toList());
    }

    @Override
    public <S extends OrderDetails> S save(S order) {
        mockDatabase.put(order.getOrderNumber(), order);
        return order;
    }

    @Override
    public <S extends OrderDetails> Iterable<S> saveAll(Iterable<S> entities) {
        List<S> savedEntities = new ArrayList<>();
        entities.forEach(entity -> {
            mockDatabase.put(entity.getOrderNumber(), entity);
            savedEntities.add(entity);
        });
        return savedEntities;
    }


    @Override
    public Optional<OrderDetails> findById(String id) {
        return Optional.ofNullable(mockDatabase.get(id));
    }

    @Override
    public boolean existsById(String id) {
        return mockDatabase.containsKey(id);
    }

    @Override
    public Iterable<OrderDetails> findAll() {
        return mockDatabase.values();
    }

    @Override
    public Iterable<OrderDetails> findAllById(Iterable<String> ids) {
        List<OrderDetails> result = new ArrayList<>();
        ids.forEach(id -> {
            if (mockDatabase.containsKey(id)) {
                result.add(mockDatabase.get(id));
            }
        });
        return result;
    }

    @Override
    public long count() {
        return mockDatabase.size();
    }

    @Override
    public void deleteById(String id) {
        mockDatabase.remove(id);
    }

    @Override
    public void delete(OrderDetails entity) {
        mockDatabase.remove(entity.getOrderNumber());
    }

    @Override
    public void deleteAll(Iterable<? extends OrderDetails> entities) {
        entities.forEach(entity -> mockDatabase.remove(entity.getOrderNumber()));
    }

    @Override
    public void deleteAll() {
        mockDatabase.clear();
    }
}
