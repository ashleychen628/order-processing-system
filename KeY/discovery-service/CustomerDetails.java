public class CustomerDetails {
    
    /*@ public normal_behavior
      @ requires customerRequest != null;
      @ requires customerRequest.getCustomer() != null;
      @ ensures \result != null;
      @ ensures \fresh(\result);
      @ ensures \result == customerRequest.getCustomer();
      @*/
    public Customer createCustomer(CustomerRequest customerRequest) {
        return customerRequest.getCustomer();
    }
    
    /*@ public normal_behavior
      @ requires customerId > 0;
      @ ensures \result != null ==> \result.getId() == customerId;
      @*/
    public /*@ nullable @*/ Customer getCustomerInfo(long customerId) {
        if (customerId <= 0) return null;
        Customer customer = new Customer();
        customer.setId(customerId);
        return customer;
    }
    
    /*@ public normal_behavior
      @ requires customerId > 0;
      @ ensures \result == (getCustomerInfo(customerId) != null);
      @*/
    public boolean isRegisteredCustomer(long customerId) {
        return getCustomerInfo(customerId) != null;
    }
}