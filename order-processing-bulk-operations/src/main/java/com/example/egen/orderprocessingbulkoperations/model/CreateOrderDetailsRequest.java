package com.example.egen.orderprocessingbulkoperations.model;

import java.util.List;

public class CreateOrderDetailsRequest {
    private List<OrderDetails> orderDetails;

    public List<OrderDetails> getOrderDetails() {
        return orderDetails;
    }

    public void setOrderDetails(List<OrderDetails> orderDetails) {
        this.orderDetails = orderDetails;
    }
}
