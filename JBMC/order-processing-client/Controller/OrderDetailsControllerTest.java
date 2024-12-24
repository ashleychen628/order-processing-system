import java.util.HashMap;
import java.util.Map;

class OrderDetails {
    private String orderNumber;
    private long customerId;
    private String status;

    public OrderDetails(String orderNumber, long customerId, String status) {
        this.orderNumber = orderNumber;
        this.customerId = customerId;
        this.status = status;
    }

    public String getOrderNumber() { return orderNumber; }
    public long getCustomerId() { return customerId; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}

class CustomerIdNotFoundException extends Exception {
    public CustomerIdNotFoundException(String message) {
        super(message);
    }
}

class OrderIdNotFoundException extends Exception {
    public OrderIdNotFoundException(String message) {
        super(message);
    }
}

class BillingAddressNotMatchException extends Exception {
    public BillingAddressNotMatchException(String message) {
        super(message);
    }
}

class NoMatchException extends Exception {
    public NoMatchException(String message) {
        super(message);
    }
}

class EnumNotPresentException extends Exception {
    public EnumNotPresentException(String message) {
        super(message);
    }
}

class OrderDetailsController {
    private Map<String, OrderDetails> orders = new HashMap<>();
    private Map<Long, Boolean> registeredCustomers = new HashMap<>();

    public OrderDetails createOrder(long customerId, String billingAddress) throws CustomerIdNotFoundException, BillingAddressNotMatchException {
        if (!isRegisteredCustomer(customerId)) {
            throw new CustomerIdNotFoundException(customerId + " is not found. Create Customer information.");
        }

        if (!verifyBillingAddress(customerId, billingAddress)) {
            throw new BillingAddressNotMatchException("Billing Address is not matching with customer's billing address");
        }

        String orderNumber = "ORD-" + System.currentTimeMillis();
        OrderDetails order = new OrderDetails(orderNumber, customerId, "CREATED");
        orders.put(orderNumber, order);
        return order;
    }

    public OrderDetails getOrderById(String orderNumber) throws OrderIdNotFoundException {
        OrderDetails order = orders.get(orderNumber);
        if (order == null) {
            throw new OrderIdNotFoundException("Order not found: " + orderNumber);
        }
        return order;
    }

    public OrderDetails cancelOrderById(String orderNumber) throws OrderIdNotFoundException {
        OrderDetails order = getOrderById(orderNumber);
        order.setStatus("CANCELLED");
        return order;
    }

    public OrderDetails updateOrderStatus(String orderNumber, String newStatus) throws OrderIdNotFoundException, EnumNotPresentException {
        OrderDetails order = getOrderById(orderNumber);
        if (!isValidStatus(newStatus)) {
            throw new EnumNotPresentException("Invalid order status: " + newStatus);
        }
        order.setStatus(newStatus);
        return order;
    }

    public OrderDetails getOrderByCustomerIDAndOrderID(long customerId, String orderNumber)
            throws OrderIdNotFoundException, NoMatchException, CustomerIdNotFoundException {
        OrderDetails order = getOrderById(orderNumber);
        if (order.getCustomerId() != customerId) {
            throw new NoMatchException("Order does not belong to the specified customer");
        }
        return order;
    }

    private boolean isRegisteredCustomer(long customerId) {
        return registeredCustomers.getOrDefault(customerId, false);
    }

    private boolean verifyBillingAddress(long customerId, String billingAddress) {
        // Simplified billing address verification
        return billingAddress != null && !billingAddress.isEmpty();
    }

    private boolean isValidStatus(String status) {
        // Simplified status validation
        return status != null && (status.equals("CREATED") || status.equals("PROCESSING") || 
               status.equals("SHIPPED") || status.equals("DELIVERED") || status.equals("CANCELLED"));
    }

    // Helper method for testing
    public void registerCustomer(long customerId) {
        registeredCustomers.put(customerId, true);
    }
}

public class OrderDetailsControllerTest {
    public static void main(String[] args) {
        OrderDetailsController controller = new OrderDetailsController();
        
        // Register a customer
        controller.registerCustomer(1L);

        try {
            // Test creating an order
            OrderDetails order = controller.createOrder(1L, "123 Test St");
            System.out.println("Order created: " + order.getOrderNumber());

            // Test getting an order
            OrderDetails retrievedOrder = controller.getOrderById(order.getOrderNumber());
            System.out.println("Retrieved order status: " + retrievedOrder.getStatus());

            // Test cancelling an order
            OrderDetails cancelledOrder = controller.cancelOrderById(order.getOrderNumber());
            System.out.println("Cancelled order status: " + cancelledOrder.getStatus());

            // Test updating order status
            OrderDetails updatedOrder = controller.updateOrderStatus(order.getOrderNumber(), "SHIPPED");
            System.out.println("Updated order status: " + updatedOrder.getStatus());

            // Test getting order by customer ID and order ID
            OrderDetails customerOrder = controller.getOrderByCustomerIDAndOrderID(1L, order.getOrderNumber());
            System.out.println("Customer order retrieved: " + customerOrder.getOrderNumber());

            // Test error cases
            try {
                controller.createOrder(2L, "456 Test Ave");
            } catch (CustomerIdNotFoundException e) {
                System.out.println("Expected error: " + e.getMessage());
            }

            try {
                controller.getOrderById("non-existent-order");
            } catch (OrderIdNotFoundException e) {
                System.out.println("Expected error: " + e.getMessage());
            }

            try {
                controller.updateOrderStatus(order.getOrderNumber(), "INVALID_STATUS");
            } catch (EnumNotPresentException e) {
                System.out.println("Expected error: " + e.getMessage());
            }

            try {
                controller.getOrderByCustomerIDAndOrderID(2L, order.getOrderNumber());
            } catch (NoMatchException e) {
                System.out.println("Expected error: " + e.getMessage());
            }

        } catch (Exception e) {
            System.err.println("Unexpected error: " + e.getMessage());
        }
    }
}