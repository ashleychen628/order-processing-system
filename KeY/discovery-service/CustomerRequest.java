public class CustomerRequest {
    private Customer customer;
    
    /*@ public normal_behavior
      @ ensures \result == customer;
      @*/
    public /*@ pure @*/ Customer getCustomer() {
        return customer;
    }
    
    /*@ public normal_behavior
      @ ensures customer == newCustomer;
      @ assignable this.customer;
      @*/
    public void setCustomer(Customer newCustomer) {
        this.customer = newCustomer;
    }
}