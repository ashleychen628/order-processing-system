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
        assert orderStatus != null : "OrderStatus cannot be null";
        this.orderStatus = orderStatus;
    }

    public double getOrderSubtotal() {
        return orderSubtotal;
    }

    public void setOrderSubtotal(double orderSubtotal) {
        assert orderSubtotal >= 0 : "OrderSubtotal must be non-negative";
        this.orderSubtotal = orderSubtotal;
    }

    public double getOrderTax() {
        return orderTax;
    }

    public void setOrderTax(double orderTax) {
        assert orderTax >= 0 : "OrderTax must be non-negative";
        this.orderTax = orderTax;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        assert orderTotal == (orderSubtotal + orderTax) : "OrderTotal must equal OrderSubtotal + OrderTax";
        this.orderTotal = orderTotal;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        assert shipping != null : "Shipping cannot be null";
        this.shipping = shipping;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        assert customer != null : "Customer cannot be null";
        this.customer = customer;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        assert items != null && !items.isEmpty() : "Items list cannot be null or empty";
        this.items = items;
    }

    public List<OrderPaymentRequest> getPayments() {
        return payments;
    }

    public void setPayments(List<OrderPaymentRequest> payments) {
        assert payments != null && !payments.isEmpty() : "Payments list cannot be null or empty";
        this.payments = payments;
    }

    public void validateFinancials() {
      assert orderTotal == (orderSubtotal + orderTax) : "OrderTotal must be equal to OrderSubtotal + OrderTax";
      assert orderTotal >= 0 : "OrderTotal must be non-negative";
    }

    public void validateItems() {
      double calculatedSubtotal = items.stream()
                                       .mapToDouble(item -> item.getItemPrice() * item.getQuantity())
                                       .sum();
      assert orderSubtotal == calculatedSubtotal : "OrderSubtotal must match the sum of item prices";
    }
  
    public static void main(String[] args) {
      // Example usage of CreateOrderRequest
      CreateOrderRequest request = new CreateOrderRequest();

      // Set some example values
      request.setOrderStatus(OrderStatus.PLACED);
      request.setOrderSubtotal(100.0);
      request.setOrderTax(10.0);
      request.setOrderTotal(110.0);

      // Example assertions to verify
      assert request.getOrderStatus() == OrderStatus.PLACED : "OrderStatus mismatch";
      assert request.getOrderSubtotal() == 100.0 : "OrderSubtotal mismatch";
      assert request.getOrderTax() == 10.0 : "OrderTax mismatch";
      assert request.getOrderTotal() == 110.0 : "OrderTotal mismatch";

      System.out.println("All assertions passed.");
  }
}
