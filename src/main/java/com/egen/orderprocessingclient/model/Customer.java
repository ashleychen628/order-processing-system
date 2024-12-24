package com.egen.orderprocessingclient.model;

public class Customer {
    private long customerId;

    public Customer(){}

    public Customer(long customerId) {
        this.customerId = customerId;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        assert customerId >= 0 : "customerId must be non-negative";
        this.customerId = customerId;
    }

    public static void main(String[] args) {
      // Test Default Constructor
      Customer defaultCustomer = new Customer();
      assert defaultCustomer.getCustomerId() == 0 : "Default constructor should initialize customerId to 0";

      // Test Parameterized Constructor
      Customer parameterizedCustomer = new Customer(12345);
      assert parameterizedCustomer.getCustomerId() == 12345 : "Parameterized constructor did not correctly set customerId";

      // Test getCustomerId
      Customer customer = new Customer(67890);
      assert customer.getCustomerId() == 67890 : "getCustomerId() did not return the correct customerId";

      // Test setCustomerId
      customer.setCustomerId(98765);
      assert customer.getCustomerId() == 98765 : "setCustomerId() did not update customerId correctly";

      // Test Boundary Values
      customer.setCustomerId(0);
      assert customer.getCustomerId() == 0 : "customerId should handle 0";

      customer.setCustomerId(Long.MAX_VALUE);
      assert customer.getCustomerId() == Long.MAX_VALUE : "customerId should handle Long.MAX_VALUE";

      customer.setCustomerId(Long.MIN_VALUE);
      assert customer.getCustomerId() == Long.MIN_VALUE : "customerId should handle Long.MIN_VALUE";
  }
}
