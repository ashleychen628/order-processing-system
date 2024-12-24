public class OrderService {
    // Make inner classes and enums static
    public static enum OrderStatus {
        PLACED, CANCELLED, DECLINED, DELIVERED, SHIPPED
    }

    public static class Order {
        private String orderNumber;
        private OrderStatus status;
        private double total;
        private double tax;
        private double subtotal;
        private double shippingCost;
        
        public Order(String orderNumber) {
            this.orderNumber = orderNumber;
        }

        // Getters and setters
        public String getOrderNumber() { return orderNumber; }
        public OrderStatus getStatus() { return status; }
        public void setStatus(OrderStatus status) { this.status = status; }
        public double getTotal() { return total; }
        public void setTotal(double total) { this.total = total; }
        public double getTax() { return tax; }
        public void setTax(double tax) { this.tax = tax; }
        public double getSubtotal() { return subtotal; }
        public void setSubtotal(double subtotal) { this.subtotal = subtotal; }
        public double getShippingCost() { return shippingCost; }
        public void setShippingCost(double shippingCost) { this.shippingCost = shippingCost; }
    }

    // Core calculation method
    public double calculateOrderTotal(Order order) {
        double total = order.getSubtotal() + order.getTax() + order.getShippingCost();
        order.setTotal(total);
        return total;
    }

    // Order status update method
    public boolean updateOrderStatus(Order order, OrderStatus newStatus) {
        if (order == null || newStatus == null) {
            return false;
        }

        // Status transition logic
        switch (newStatus) {
            case CANCELLED:
                if (order.getStatus() == OrderStatus.DELIVERED) {
                    return false;
                }
                break;
            case DELIVERED:
                if (order.getStatus() == OrderStatus.CANCELLED) {
                    return false;
                }
                break;
            case SHIPPED:
                if (order.getStatus() == OrderStatus.CANCELLED || 
                    order.getStatus() == OrderStatus.DELIVERED) {
                    return false;
                }
                break;
        }

        order.setStatus(newStatus);
        return true;
    }

    // Address verification method
    public boolean verifyAddress(String submittedAddress, String registeredAddress) {
        if (submittedAddress == null || registeredAddress == null) {
            return false;
        }
        return submittedAddress.equals(registeredAddress);
    }
}