package com.egen.orderprocessingclient.service;

import com.egen.orderprocessingclient.exception.EnumNotPresentException;
import com.egen.orderprocessingclient.exception.NoMatchException;
import com.egen.orderprocessingclient.exception.OrderIdNotFoundException;
import com.egen.orderprocessingclient.model.*;
import com.egen.orderprocessingclient.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private OrderRepository orderRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(OrderServiceImpl.class);

    @Transactional
    public OrderDetails createOrder(CreateOrderRequest order) {
        OrderDetails orderDetails = new OrderDetails();
        orderDetails.setOrderStatus(OrderStatus.PLACED);

        double itemPrice = 0;

        for (OrderItem item : order.getItems()) {
            itemPrice += item.getItemPrice() * item.getQuantity();
        }

        order.setOrderSubtotal(itemPrice);
        double tax = order.getOrderTax();
        double subTotal = order.getOrderSubtotal();

        if (order.getShipping().getShippingMethod().name().equals("STORE_PICKUP") || order.getShipping().getShippingMethod().name().equals("CURBSIDE")) {
            order.getShipping().setCost(0);
        }

        double shippingTotal = order.getShipping().getCost();
        double total = tax + subTotal + shippingTotal;
        order.setOrderTotal(total);

        orderDetails.setOrderSubtotal(order.getOrderSubtotal());
        orderDetails.setOrderTax(order.getOrderTax());
        orderDetails.setOrderTotal(order.getOrderTotal());

        orderDetails.setShipping(order.getShipping());
        orderDetails.getShipping().setShippingStatus(ShippingStatus.PICKUP);
        orderDetails.setCustomerId(order.getCustomer().getCustomerId());
        orderDetails.setItems(order.getItems());

        Date currentDate = new Date();
        orderDetails.setCreatedDate(currentDate);
        orderDetails.setModifiedDate(currentDate);

        List<OrderPaymentRequest> orderPayments = order.getPayments();
        List<OrderPayment> orderPaymentList = new ArrayList<>(orderPayments.size());

        orderPayments.stream().forEach(orderPayment -> {
            OrderPayment individualOrderPayment = new OrderPayment();
            individualOrderPayment.setPaymentAmount(orderPayment.getPaymentAmount());
            individualOrderPayment.setPaymentConfirmationNumber(UUID.randomUUID().toString().replace("-", ""));
            individualOrderPayment.setPaymentMethod(orderPayment.getPaymentMethod());
            orderPaymentList.add(individualOrderPayment);
        });
        orderDetails.setPayments(orderPaymentList);

        orderRepository.save(orderDetails);
        return orderDetails;
    }

    @Transactional(readOnly = true)
    public OrderDetails getOrderById(String orderNumber) throws OrderIdNotFoundException {
        Optional<OrderDetails> orderDetails = orderRepository.findByOrderNumber(orderNumber);
        if (orderDetails.isPresent()) {
            LOGGER.info("Order number " + orderNumber + " is existed in the database");
            return orderDetails.get();
        } else {
            LOGGER.error("Order number " + orderNumber + " is not found");
            throw new OrderIdNotFoundException(HttpStatus.NOT_FOUND, "Order Number not found in the Database. Provide Existing order Number");
        }
    }

    @Transactional
    public OrderDetails cancelOrderById(String orderNumber) throws OrderIdNotFoundException {
        Optional<OrderDetails> orderDetails = orderRepository.findByOrderNumber(orderNumber);
        if (orderDetails.isPresent()) {
            LOGGER.info("Order number " + orderNumber + " is existed in the database");
            OrderStatus orderStatus = OrderStatus.CANCELLED;
            orderDetails.get().setOrderStatus(orderStatus);

            Date currentDate = new Date();
            orderDetails.get().setModifiedDate(currentDate);

            LOGGER.info("Shipping status is " + ShippingStatus.CANCELLED);
            orderDetails.get().getShipping().setShippingStatus(ShippingStatus.CANCELLED);
            return orderDetails.get();
        } else {
            LOGGER.error("Order number " + orderNumber + " is not found");
            throw new OrderIdNotFoundException(HttpStatus.NOT_FOUND, "Order number " +
                    orderNumber + " is not found. Provide Existing Order Number");
        }
    }

    @Transactional
    public OrderDetails updateOrderStatus(OrderDetails requestedOrderDetails) throws OrderIdNotFoundException,
            EnumNotPresentException {
        String updatingOrderNumber = requestedOrderDetails.getOrderNumber();
        OrderStatus requestedOrderStatus;

        try {
            requestedOrderStatus = requestedOrderDetails.getOrderStatus();
        } catch (EnumConstantNotPresentException ex) {
            throw new EnumNotPresentException(HttpStatus.BAD_REQUEST,
                    requestedOrderDetails.getOrderStatus() + " is not found. Provide correct order status.");
        }

        Optional<OrderDetails> orderDetails = orderRepository.findByOrderNumber(updatingOrderNumber);

        if (orderDetails.isPresent()) {
            LOGGER.info("Order number " + updatingOrderNumber + " is existed in the database");
            orderDetails.get().setOrderStatus(requestedOrderStatus);

            Date currentDate = new Date();
            orderDetails.get().setModifiedDate(currentDate);

            if (requestedOrderStatus.name().equals("DECLINED")) {
                LOGGER.warn("Order number " + updatingOrderNumber + " is "
                        + requestedOrderStatus.name());
                orderDetails.get().getShipping().setShippingStatus(ShippingStatus.CANCELLED);
            }
            if (requestedOrderStatus.name().equals("DELIVERED")) {
                LOGGER.info("Order number " + updatingOrderNumber + " is "
                        + requestedOrderStatus.name());
                orderDetails.get().getShipping().setShippingStatus(ShippingStatus.DELIVERED);
            }
            if (requestedOrderStatus.name().equals("SHIPPED")) {
                LOGGER.info("Order number " + updatingOrderNumber + " is "
                        + requestedOrderStatus.name());
                orderDetails.get().getShipping().setShippingStatus(ShippingStatus.SHIPPED);
                LOGGER.info("Order number " + updatingOrderNumber + " is "
                        + requestedOrderStatus.name() + " and " + "Shipping status is " +
                        "" + ShippingStatus.SHIPPED);
            }

            return orderDetails.get();

        } else {
            LOGGER.error("Order number " + updatingOrderNumber + " is not found");
            throw new OrderIdNotFoundException(HttpStatus.NOT_FOUND, "Order number: " + updatingOrderNumber + " is not found. Provide Correct order number");
        }
    }

    @Transactional
    public OrderDetails getOrderByCustomerIDAndOrderID(long customerId, String orderNumber)
            throws NoMatchException {
        LOGGER.info("Searching order by customerId: " + customerId + " and orderNumber: " + orderNumber);

        Optional<OrderDetails> existingOrder =
                orderRepository.findByCustomerIdAndOrderNumber(customerId, orderNumber);
        if (existingOrder.isPresent()) {
            LOGGER.info("The Order submitted by " + customerId + " is "
                    + existingOrder.get().getOrderStatus() + "");
            return existingOrder.get();
        } else {
            LOGGER.error("customer Id and order id not found");
            throw new NoMatchException(HttpStatus.NOT_FOUND, customerId +
                    " and " + orderNumber + " not exist");
        }
    }

    public boolean verifyBillingAddress(CreateOrderRequest order, CustomerPaymentDetailsList registeredCustomerPayment) {
        boolean matchedAddress = false;
        for (OrderPaymentRequest orderPaymentRequest : order.getPayments()) {
            for (CustomerPaymentDetails customerPaymentDetails : registeredCustomerPayment.getCustomerPaymentDetails()) {
                if (orderPaymentRequest.getPaymentType().getBilling().getAddressLine1().
                        equalsIgnoreCase(customerPaymentDetails.getBillingAddress().getAddressLine1()) &&
                        orderPaymentRequest.getPaymentType().getBilling().getAddressLine2().
                                equalsIgnoreCase(customerPaymentDetails.getBillingAddress().getAddressLine2()) &&
                        orderPaymentRequest.getPaymentType().getBilling().getCity().
                                equalsIgnoreCase(customerPaymentDetails.getBillingAddress().getCity()) &&
                        orderPaymentRequest.getPaymentType().getBilling().getState().
                                equalsIgnoreCase(customerPaymentDetails.getBillingAddress().getState()) &&
                        orderPaymentRequest.getPaymentType().getBilling().getZip() ==
                                customerPaymentDetails.getBillingAddress().getZip()) {
                    matchedAddress = true;
                    break;
                }
            }
        }
        return matchedAddress;
    }
}
