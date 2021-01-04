package com.example.egen.orderprocessingbulkasync.configuration;

import com.example.egen.orderprocessingbulkasync.entity.OrderDetails;
import com.google.gson.Gson;

import org.springframework.batch.item.ItemProcessor;

import java.util.Date;

public class CreateOrderDetailsProcessor implements ItemProcessor<String, OrderDetails>{

    @Override
    public OrderDetails process(final String input) {
        Gson gson = new Gson();
        OrderDetails orderDetails = gson.fromJson(input, OrderDetails.class);

        OrderDetails processedOrderDetails = new OrderDetails();
        processedOrderDetails.setOrderStatus(orderDetails.getOrderStatus());
        processedOrderDetails.setOrderTax(orderDetails.getOrderTax());
        processedOrderDetails.setOrderSubtotal(orderDetails.getOrderSubtotal());
        processedOrderDetails.setOrderTotal(orderDetails.getOrderTotal());
        processedOrderDetails.setShipping(orderDetails.getShipping());
        processedOrderDetails.setItems(orderDetails.getItems());
        Date currentDate = new Date();
        processedOrderDetails.setModifiedDate(currentDate);
        processedOrderDetails.setCustomerId(orderDetails.getCustomerId());
        processedOrderDetails.setPayments(orderDetails.getPayments());
        return processedOrderDetails;
    }
}
