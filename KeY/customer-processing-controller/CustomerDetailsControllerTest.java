public class CustomerDetailsControllerTest {
    private CustomerDetailsController controller;
    private MockCustomerDetailsRepository repository;

    /*@ public normal_behavior
      @ ensures controller != null;
      @ ensures repository != null;
      @*/
    public void setUp() {
        repository = new MockCustomerDetailsRepository();
        controller = new CustomerDetailsController(repository);
    }

    /*@ public normal_behavior
      @ requires setUp() has been called;
      @ ensures \result == true;
      @*/
    public boolean testCreateCustomer() {
        CustomerRequest request = new CustomerRequest(new Customer(1L, "John Doe"));
        Customer created = controller.createCustomer(request);
        return created != null && created.getId() == 1L && "John Doe".equals(created.getName());
    }

    /*@ public normal_behavior
      @ requires setUp() has been called;
      @ requires testCreateCustomer() has been called;
      @ ensures \result == true;
      @*/
    public boolean testGetCustomerInfo() throws Exception {
        Customer retrieved = controller.getCustomerInfo(1L);
        return retrieved != null && retrieved.getId() == 1L && "John Doe".equals(retrieved.getName());
    }

    /*@ public normal_behavior
      @ requires setUp() has been called;
      @ requires testCreateCustomer() has been called;
      @ ensures \result == true;
      @*/
    public boolean testIsRegisteredCustomer() {
        return controller.isRegisteredCustomer(1L) && !controller.isRegisteredCustomer(2L);
    }
}

