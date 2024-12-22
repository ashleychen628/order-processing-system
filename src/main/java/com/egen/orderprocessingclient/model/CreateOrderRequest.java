package com.egen.orderprocessingclient.model;

import java.util.List;

public class CreateOrderRequest {
    private OrderStatus orderStatus;
    private double orderSubtotal;
    private double orderTax;
    private double orderTotal;
    private Shipping shipping;
    private Customer customer;
    private List<OrderItem> items;
    private List<OrderPaymentRequest> payments;

    public CreateOrderRequest() {
    }

    public CreateOrderRequest(OrderStatus orderStatus, double orderSubtotal, double orderTax, double orderTotal, Shipping shipping, Customer customer, List<OrderItem> items, List<OrderPaymentRequest> payments) {
        this.orderStatus = orderStatus;
        this.orderSubtotal = orderSubtotal;
        this.orderTax = orderTax;
        this.orderTotal = orderTotal;
        this.shipping = shipping;
        this.customer = customer;
        this.items = items;
        this.payments = payments;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getOrderSubtotal() {
        return orderSubtotal;
    }

    public void setOrderSubtotal(double orderSubtotal) {
        this.orderSubtotal = orderSubtotal;
    }

    public double getOrderTax() {
        return orderTax;
    }

    public void setOrderTax(double orderTax) {
        this.orderTax = orderTax;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public List<OrderPaymentRequest> getPayments() {
        return payments;
    }

    public void setPayments(List<OrderPaymentRequest> payments) {
        this.payments = payments;
    }
}
