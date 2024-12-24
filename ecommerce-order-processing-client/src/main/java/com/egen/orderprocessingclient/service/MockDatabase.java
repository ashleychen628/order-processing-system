package com.egen.orderprocessingclient.service;
import java.util.HashMap;
import java.util.Map;

public class MockDatabase {
    // Mock tables
    private Map<Long, String> ecommerceCustomerDb = new HashMap<>(); // Key: ID, Value: Payment Method
    private Map<Long, Map<String, String>> ecommerceDb = new HashMap<>(); // Key: Order ID, Value: Order Details
    
    public enum PaymentMethodInfo {
      DEBIT, CREDIT, GIFTCARD
    }
    
    public enum ShippingStatusInfo {
      PICKUP, SHIPPED, CANCELLED, DELIVERED
    }
    
    public enum OrderStatusInfo {
      PLACED, SHIPPED, DECLINED, CANCELLED, DELIVERED
    }
    
    public enum ShippingMethodInfo {
      STORE_PICKUP, CURBSIDE, DELIVERY
    }

    public MockDatabase() {
        // Initialize mock data for ecommerce_customer_db
        ecommerceCustomerDb.put(1L, PaymentMethodInfo.DEBIT.name());
        ecommerceCustomerDb.put(2L, PaymentMethodInfo.CREDIT.name());

        // Initialize mock data for ecommerce_db
        Map<String, String> order1 = new HashMap<>();
        order1.put("shipping_status", ShippingStatusInfo.SHIPPED.name());
        order1.put("order_status", OrderStatusInfo.PLACED.name());
        order1.put("shipping_method", ShippingMethodInfo.DELIVERY.name());
        ecommerceDb.put(101L, order1);

        Map<String, String> order2 = new HashMap<>();
        order2.put("shipping_status", ShippingStatusInfo.CANCELLED.name());
        order2.put("order_status", OrderStatusInfo.CANCELLED.name());
        order2.put("shipping_method", ShippingMethodInfo.STORE_PICKUP.name());
        ecommerceDb.put(102L, order2);
    }

    // Mock method to retrieve data
    public String getPaymentMethod(Long customerId) {
        return ecommerceCustomerDb.get(customerId);
    }

    public Map<String, String> getOrderDetails(Long orderId) {
        return ecommerceDb.get(orderId);
    }
}
