import java.util.ArrayList;
import java.util.List;

public class PaymentServiceTest {
    // Payment class representing payment information
    static class Payment {
        private String paymentId;
        private String billingAddress;
        private String type;
        private Long customerId;

        public Payment(String paymentId, String billingAddress, String type, Long customerId) {
            this.paymentId = paymentId;
            this.billingAddress = billingAddress;
            this.type = type;
            this.customerId = customerId;
        }

        public String getPaymentId() { return paymentId; }
        public String getBillingAddress() { return billingAddress; }
        public String getType() { return type; }
        public Long getCustomerId() { return customerId; }
    }

    // Class to hold customer payment details
    static class CustomerPaymentDetails {
        private String paymentId;
        private String billingAddress;
        private String type;

        public void setPaymentId(String paymentId) { this.paymentId = paymentId; }
        public void setBillingAddress(String address) { this.billingAddress = address; }
        public void setType(String type) { this.type = type; }
        
        public String getPaymentId() { return paymentId; }
        public String getBillingAddress() { return billingAddress; }
        public String getType() { return type; }
    }

    // Class to hold list of customer payment details
    static class CustomerPaymentDetailsList {
        private List<CustomerPaymentDetails> customerPaymentDetails;

        public void setCustomerPaymentDetails(List<CustomerPaymentDetails> details) {
            this.customerPaymentDetails = details;
        }

        public List<CustomerPaymentDetails> getCustomerPaymentDetails() {
            return customerPaymentDetails;
        }
    }

    // Service class to handle payment operations
    static class PaymentService {
        public CustomerPaymentDetailsList getCustomerPayment(Long customerId, List<Payment> payments) {
            if (customerId == null) {
                throw new IllegalArgumentException("Customer ID cannot be null");
            }
            
            if (payments == null) {
                throw new IllegalArgumentException("Payments list cannot be null");
            }

            List<CustomerPaymentDetails> customerPaymentDetailsList = new ArrayList<CustomerPaymentDetails>();

            for (Payment payment : payments) {
                if (payment.getCustomerId().equals(customerId)) {
                    CustomerPaymentDetails customerPaymentDetails = new CustomerPaymentDetails();
                    customerPaymentDetails.setBillingAddress(payment.getBillingAddress());
                    customerPaymentDetails.setPaymentId(payment.getPaymentId());
                    customerPaymentDetails.setType(payment.getType());
                    customerPaymentDetailsList.add(customerPaymentDetails);
                }
            }

            CustomerPaymentDetailsList result = new CustomerPaymentDetailsList();
            result.setCustomerPaymentDetails(customerPaymentDetailsList);
            return result;
        }
    }

    // Main method for JBMC testing
    public static void main(String[] args) {
        PaymentService service = new PaymentService();
        
        // Test Case 1: Normal case with multiple payments
        List<Payment> payments = new ArrayList<Payment>();
        payments.add(new Payment("P1", "Address 1", "CREDIT", 1L));
        payments.add(new Payment("P2", "Address 2", "DEBIT", 1L));
        
        CustomerPaymentDetailsList result = service.getCustomerPayment(1L, payments);
        assert result != null : "Result should not be null";
        assert result.getCustomerPaymentDetails().size() == 2 : "Should have 2 payment details";
        
        // Test Case 2: Empty payments list
        List<Payment> emptyPayments = new ArrayList<Payment>();
        CustomerPaymentDetailsList emptyResult = service.getCustomerPayment(1L, emptyPayments);
        assert emptyResult != null : "Result should not be null for empty payments";
        assert emptyResult.getCustomerPaymentDetails().size() == 0 : "Should have no payment details";
        
        // Test Case 3: Verify payment details mapping
        CustomerPaymentDetails firstPayment = result.getCustomerPaymentDetails().get(0);
        assert firstPayment.getPaymentId().equals("P1") : "Payment ID mismatch";
        assert firstPayment.getBillingAddress().equals("Address 1") : "Billing address mismatch";
        assert firstPayment.getType().equals("CREDIT") : "Payment type mismatch";
        
        System.out.println("All tests passed successfully");
    }
}