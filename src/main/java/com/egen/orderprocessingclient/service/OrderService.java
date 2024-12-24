package com.egen.orderprocessingclient.service;

import com.egen.orderprocessingclient.exception.EnumNotPresentException;
import com.egen.orderprocessingclient.exception.NoMatchException;
import com.egen.orderprocessingclient.exception.OrderIdNotFoundException;
import com.egen.orderprocessingclient.model.CreateOrderRequest;
import com.egen.orderprocessingclient.model.CustomerPaymentDetailsList;
import com.egen.orderprocessingclient.model.OrderDetails;

public interface OrderService {
    public OrderDetails createOrder(CreateOrderRequest order);

    public OrderDetails getOrderById(String orderNumber) throws OrderIdNotFoundException;

    public OrderDetails cancelOrderById(String orderNumber) throws OrderIdNotFoundException;

    public OrderDetails updateOrderStatus(OrderDetails requestedOrderDetails) throws OrderIdNotFoundException,
            EnumNotPresentException;

    public OrderDetails getOrderByCustomerIDAndOrderID(long customerId, String orderNumber)
            throws NoMatchException;

    public boolean verifyBillingAddress(CreateOrderRequest order, CustomerPaymentDetailsList registeredCustomerPayment);
}
