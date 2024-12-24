public class OrderServiceTest {
    public static void main(String[] args) {
        // Create service instance
        OrderService service = new OrderService();
        
        // Test 1: Calculate Order Total
        OrderService.Order order = new OrderService.Order("TEST001");
        order.setSubtotal(100.0);
        order.setTax(10.0);
        order.setShippingCost(5.0);
        
        double total = service.calculateOrderTotal(order);
        assert total == 115.0 : "Total calculation failed";
        System.out.println("Test 1 passed: Order total calculation is correct");

        // Test 2: Update Order Status
        order.setStatus(OrderService.OrderStatus.PLACED);
        boolean updated = service.updateOrderStatus(
            order, 
            OrderService.OrderStatus.SHIPPED
        );
        assert updated : "Status update failed";
        assert order.getStatus() == OrderService.OrderStatus.SHIPPED : "Wrong status";
        System.out.println("Test 2 passed: Order status update is correct");

        // Test 3: Address Verification
        boolean addressVerified = service.verifyAddress("123 Main St", "123 Main St");
        assert addressVerified : "Address verification failed";
        System.out.println("Test 3 passed: Address verification is correct");
        
        System.out.println("All tests passed!");
    }
}