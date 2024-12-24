import java.util.*;

// CrudRepository接口定义
interface CrudRepository<T, ID> {
    <S extends T> S save(S entity);
    Optional<T> findById(ID id);
    boolean existsById(ID id);
    Iterable<T> findAll();
    void deleteById(ID id);
    void delete(T entity);
    long count();
}

// CustomerDetailsController类
class CustomerDetailsController {
    private final CustomerDetailsRepository customerDetailsRepository;

    public CustomerDetailsController(CustomerDetailsRepository repository) {
        if (repository == null) {
            throw new IllegalArgumentException("Repository cannot be null");
        }
        this.customerDetailsRepository = repository;
    }

    public Customer createCustomer(CustomerRequest customerRequest) {
        if (customerRequest == null || customerRequest.getCustomer() == null) {
            throw new IllegalArgumentException("Customer request cannot be null");
        }
        return customerDetailsRepository.save(customerRequest.getCustomer());
    }

    public Customer getCustomerInfo(Long customerId) {
        if (customerId == null) {
            throw new IllegalArgumentException("Customer ID cannot be null");
        }
        return customerDetailsRepository.findById(customerId)
            .orElseThrow(() -> new IllegalStateException("Customer not found with ID: " + customerId));
    }

    public Boolean isRegisteredCustomer(Long customerId) {
        if (customerId == null) {
            throw new IllegalArgumentException("Customer ID cannot be null");
        }
        return customerDetailsRepository.existsById(customerId);
    }
}

// CustomerDetailsRepository实现
class CustomerDetailsRepository implements CrudRepository<Customer, Long> {
    private final Map<Long, Customer> customers = new HashMap<>();
    private final Set<String> emailIndex = new HashSet<>();

    @Override
    public <S extends Customer> S save(S customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        
        validateCustomer(customer);
        
        if (customer.getEmail() != null) {
            Customer existing = findByEmail(customer.getEmail());
            if (existing != null && existing.getCustomerId() != customer.getCustomerId()) {
                throw new IllegalStateException("Email already exists: " + customer.getEmail());
            }
        }
        
        customers.put(customer.getCustomerId(), customer);
        if (customer.getEmail() != null) {
            emailIndex.add(customer.getEmail());
        }
        return customer;
    }

    @Override
    public Optional<Customer> findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return Optional.ofNullable(customers.get(id));
    }

    public Customer findByEmail(String email) {
        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }
        return customers.values().stream()
            .filter(c -> email.equals(c.getEmail()))
            .findFirst()
            .orElse(null);
    }

    @Override
    public boolean existsById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        return customers.containsKey(id);
    }

    @Override
    public Iterable<Customer> findAll() {
        return new ArrayList<>(customers.values());
    }

    @Override
    public void deleteById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("ID cannot be null");
        }
        Customer customer = customers.remove(id);
        if (customer != null && customer.getEmail() != null) {
            emailIndex.remove(customer.getEmail());
        }
    }

    @Override
    public void delete(Customer customer) {
        if (customer != null) {
            deleteById(customer.getCustomerId());
        }
    }

    @Override
    public long count() {
        return customers.size();
    }

    private void validateCustomer(Customer customer) {
        if (customer.getFirstName() == null || customer.getFirstName().trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        if (customer.getLastName() == null || customer.getLastName().trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        if (customer.getEmail() == null || !customer.getEmail().contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
}

// Customer类
class Customer {
    private long customerId;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private List<Payment> payments;
    
    private static long idCounter = 1000;
    
    public Customer() {
        this.customerId = generateId();
        this.payments = new ArrayList<>();
    }
    
    public Customer(String firstName, String lastName, String email, String phone) {
        this();
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.phone = phone;
    }
    
    private synchronized static long generateId() {
        return idCounter++;
    }
    
    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        if (email == null || !email.contains("@")) {
            throw new IllegalArgumentException("Invalid email format");
        }
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        if (phone == null || phone.trim().isEmpty()) {
            throw new IllegalArgumentException("Phone cannot be null or empty");
        }
        this.phone = phone;
    }

    public List<Payment> getPayments() {
        return payments;
    }

    public void setPayments(List<Payment> payments) {
        this.payments = payments != null ? payments : new ArrayList<>();
    }

    public void addPayment(Payment payment) {
        if (payment == null) {
            throw new IllegalArgumentException("Payment cannot be null");
        }
        if (this.payments == null) {
            this.payments = new ArrayList<>();
        }
        this.payments.add(payment);
    }
}

// Payment类
class Payment {
    private long paymentId;
    private double amount;
    private String type;
    
    public Payment(long paymentId, double amount, String type) {
        this.paymentId = paymentId;
        this.amount = amount;
        if (type == null || type.trim().isEmpty()) {
            throw new IllegalArgumentException("Payment type cannot be null or empty");
        }
        this.type = type;
    }
    
    public long getPaymentId() { return paymentId; }
    public double getAmount() { return amount; }
    public String getType() { return type; }
}

// CustomerRequest类
class CustomerRequest {
    private Customer customer;

    public CustomerRequest(Customer customer) {
        if (customer == null) {
            throw new IllegalArgumentException("Customer cannot be null");
        }
        this.customer = customer;
    }

    public Customer getCustomer() { return customer; }
}

// Main Test Class
public class CustomerDetailsTest {
    public static void main(String[] args) {
        try {
            // Initialize the test environment
            CustomerDetailsRepository repository = new CustomerDetailsRepository();
            CustomerDetailsController controller = new CustomerDetailsController(repository);
            
            // Test Case 1: Creating Effective Customers
            Customer customer = new Customer("John", "Doe", "john@example.com", "1234567890");
            CustomerRequest request = new CustomerRequest(customer);
            Customer savedCustomer = controller.createCustomer(request);
            assert savedCustomer.getCustomerId() >= 1000 : "Invalid customer ID";
            assert savedCustomer.getFirstName().equals("John") : "First name mismatch";
            
            // Test Case 2: Verify Mailbox Uniqueness
            Customer duplicateCustomer = new Customer("Jane", "Doe", "john@example.com", "0987654321");
            try {
                controller.createCustomer(new CustomerRequest(duplicateCustomer));
                assert false : "Should not allow duplicate email";
            } catch (IllegalStateException e) {
                // Expected exception
            }
            
            // Test Case 3: Add Payment Record
            Payment payment = new Payment(1L, 100.0, "CREDIT");
            savedCustomer.addPayment(payment);
            repository.save(savedCustomer);
            
            Customer retrievedCustomer = controller.getCustomerInfo(savedCustomer.getCustomerId());
            assert retrievedCustomer.getPayments().size() == 1 : "Payment not saved";
            assert retrievedCustomer.getPayments().get(0).getAmount() == 100.0 : "Payment amount mismatch";
            
            // Test Case 4: Verify Customer Registration Status
            assert controller.isRegisteredCustomer(savedCustomer.getCustomerId()) : "Customer should be registered";
            assert !controller.isRegisteredCustomer(999L) : "Non-existent customer should not be registered";
            
            // Test Case 5: Validating Field Constraints
            try {
                customer.setEmail("invalid-email");
                assert false : "Should not allow invalid email";
            } catch (IllegalArgumentException e) {
                // Expected exception
            }
            
            // Test Case 6: Delete Customer
            repository.delete(savedCustomer);
            assert !repository.existsById(savedCustomer.getCustomerId()) : "Customer not deleted";
            assert repository.count() == 0 : "Repository should be empty";
            
            // Test Case 7: Null Check
            try {
                controller.createCustomer(null);
                assert false : "Should not allow null customer request";
            } catch (IllegalArgumentException e) {
                // Expected exception
            }

            System.out.println("All tests passed successfully");
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            throw e;
        }
    }
}