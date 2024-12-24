import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class MockCustomerDetailsRepository implements CustomerDetailsRepository {
    private Map<Long, Customer> customers = new HashMap<>();

    /*@ public normal_behavior
      @ ensures \result == customers.get(customerId);
      @*/
    public /*@ pure @*/ Optional<Customer> findById(Long customerId) {
        return Optional.ofNullable(customers.get(customerId));
    }

    /*@ public normal_behavior
      @ requires customer != null;
      @ ensures customers.containsKey(customer.getId());
      @ ensures customers.get(customer.getId()).equals(customer);
      @ ensures \result.equals(customer);
      @*/
    public Customer save(Customer customer) {
        customers.put(customer.getId(), customer);
        return customer;
    }
}

