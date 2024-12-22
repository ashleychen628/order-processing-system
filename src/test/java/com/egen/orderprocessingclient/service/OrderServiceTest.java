package com.egen.orderprocessingclient.service;

import com.egen.orderprocessingclient.exception.CustomerIdNotFoundException;
import com.egen.orderprocessingclient.exception.NoMatchException;
import com.egen.orderprocessingclient.exception.OrderIdNotFoundException;
import com.egen.orderprocessingclient.model.*;
import com.egen.orderprocessingclient.repository.OrderRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(SpringRunner.class)
public class OrderServiceTest {

    @TestConfiguration
    static class OrderServiceTestConfiguration {
        @Bean
        public OrderServiceImpl getService() {
            return new OrderServiceImpl();
        }
    }

    @Autowired
    private OrderServiceImpl orderService;

    @MockBean
    private OrderRepository mockOrderRepository;

    private OrderDetails orderDetailsTesting;
    private CreateOrderRequest orderDetails;
    private Shipping shipping_address;
    private List<OrderItem> orderItemList;

    @Before
    public void setup() {
        OrderItem orderItem1 = new OrderItem("123", "pant", 12.0, 2);
        OrderItem orderItem2 = new OrderItem("124", "dress", 12.0, 1);

        orderItemList = new ArrayList<>();
        orderItemList.add(orderItem1);
        orderItemList.add(orderItem2);

        OrderPaymentRequest orderPaymentRequest = new OrderPaymentRequest("st234",
                35, PaymentMethod.CREDIT);
        List<OrderPaymentRequest> orderPaymentRequests = new ArrayList<>();
        orderPaymentRequests.add(orderPaymentRequest);

        shipping_address = new Shipping("345", ShippingMethod.STORE_PICKUP, 10,
                "line1", "line2", "KOP", "PA", 12344);

        orderDetails = new CreateOrderRequest(OrderStatus.PLACED, 36.0, 1.00, 47,
                shipping_address, new Customer(1002), orderItemList, orderPaymentRequests);

        orderDetailsTesting = new OrderDetails();

        orderDetailsTesting.setOrderStatus(orderDetails.getOrderStatus());
        orderDetailsTesting.setOrderSubtotal(orderDetails.getOrderSubtotal());
        orderDetailsTesting.setOrderTax(orderDetails.getOrderTax());
        orderDetailsTesting.setOrderTotal(orderDetails.getOrderTotal());

        orderDetailsTesting.setShipping(orderDetails.getShipping());
        orderDetailsTesting.setCustomerId(orderDetails.getCustomer().getCustomerId());
        orderDetailsTesting.setItems(orderDetails.getItems());

        List<OrderPaymentRequest> orderPayments = orderDetails.getPayments();
        List<OrderPayment> orderPaymentList = new ArrayList<>(orderPayments.size());
        int i = 0;
        for (OrderPaymentRequest orderPayment : orderPayments) {
            OrderPayment individualOrderPayment = new OrderPayment();
            individualOrderPayment.setPaymentAmount(orderPayment.getPaymentAmount());
            individualOrderPayment.setPaymentConfirmationNumber(orderPayment.getPaymentConfirmationNumber());
            individualOrderPayment.setPaymentMethod(orderPayment.getPaymentMethod());
            orderPaymentList.add(i, individualOrderPayment);
            i++;
        }

        orderDetailsTesting.setPayments(orderPaymentList);
        Mockito.when(mockOrderRepository.save(orderDetailsTesting)).thenReturn(orderDetailsTesting);
    }

    @After
    public void cleanup() {
        mockOrderRepository.deleteAll();
    }

    @Test
    public void createOrderTest() {
        Assert.assertEquals(orderDetailsTesting.getOrderStatus(),
                orderService.createOrder(orderDetails).getOrderStatus());
    }

    @Test
    public void getOrderByIdTest() throws Exception {
        String orderNumber = "123";
        orderDetailsTesting.setOrderNumber("123");
        Mockito.when(mockOrderRepository.findByOrderNumber(orderDetailsTesting.getOrderNumber()))
                .thenReturn(Optional.of(orderDetailsTesting));

        Assert.assertEquals(orderDetailsTesting, orderService.getOrderById(orderNumber));

        Assert.assertEquals(orderDetailsTesting.getOrderNumber(),
                orderService.getOrderById(orderNumber).getOrderNumber());
    }

    @Test(expected = OrderIdNotFoundException.class)
    public void getOrderNotFoundTest() throws OrderIdNotFoundException {
        String orderNumber = "124";
        Mockito.when(mockOrderRepository.findByOrderNumber(orderDetailsTesting.getOrderNumber()))
                .thenReturn(Optional.of(orderDetailsTesting));
        Assert.assertEquals(orderDetailsTesting.getOrderNumber(), orderService.getOrderById(orderNumber));
    }

    @Test
    public void cancelOrderByIdTest() throws Exception {
        orderDetailsTesting.setOrderNumber("123");
        Mockito.when(mockOrderRepository.findByOrderNumber(orderDetailsTesting.getOrderNumber()))
                .thenReturn(Optional.of(orderDetailsTesting));
        String orderNumber = "123";
        OrderDetails orderDetails1 = orderService.cancelOrderById(orderNumber);
        Assert.assertEquals(orderDetails1.getOrderStatus().name(),
                OrderStatus.CANCELLED.toString());
    }

    @Test(expected = OrderIdNotFoundException.class)
    public void CancelingOrderNumberNotfound() throws Exception {
        orderDetailsTesting.setOrderNumber("123");
        Mockito.when(mockOrderRepository.findByOrderNumber(orderDetailsTesting.getOrderNumber()))
                .thenReturn(Optional.of(orderDetailsTesting));
        String orderNumber = "13";
        OrderDetails orderDetails1 = orderService.cancelOrderById(orderNumber);
        Assert.assertEquals(orderDetails1.getOrderStatus().name(),
                OrderStatus.CANCELLED.toString());
    }

    @Test
    public void getOrderByCustomerIDAndOrderIDTest() throws CustomerIdNotFoundException, NoMatchException {
        orderDetailsTesting.setOrderNumber("13");
        Mockito.when(mockOrderRepository.findByCustomerIdAndOrderNumber(orderDetailsTesting.getCustomerId(),
                orderDetailsTesting.getOrderNumber()))
                .thenReturn(Optional.of(orderDetailsTesting));

        String orderNumber = "13";
        long customerId = 1002;
        OrderDetails orderDetails1 = orderService.getOrderByCustomerIDAndOrderID(customerId, orderNumber);
        Assert.assertEquals(orderDetails1.getOrderNumber(),
                orderDetailsTesting.getOrderNumber());
        Assert.assertEquals(orderDetails1.getCustomerId(),
                orderDetailsTesting.getCustomerId());
    }
}