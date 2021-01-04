package com.example.egen.orderprocessingbulkasync.entity;

public class UpdateOrderRequest {
    private String orderNumber;
    private OrderStatus orderStatus;

    public UpdateOrderRequest() {
    }

    public UpdateOrderRequest(String orderNumber, OrderStatus orderStatus) {
        this.orderNumber = orderNumber;
        this.orderStatus = orderStatus;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }
}
