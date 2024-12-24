import java.util.*;

// Mock service instance class
class ServiceInstance {
    private String serviceId;
    private String host;
    private int port;
    private boolean isAlive;

    public ServiceInstance(String serviceId, String host, int port) {
        if (serviceId == null || serviceId.trim().isEmpty()) {
            throw new IllegalArgumentException("Service ID cannot be null or empty");
        }
        if (host == null || host.trim().isEmpty()) {
            throw new IllegalArgumentException("Host cannot be null or empty");
        }
        if (port <= 0 || port > 65535) {
            throw new IllegalArgumentException("Invalid port number");
        }
        
        this.serviceId = serviceId;
        this.host = host;
        this.port = port;
        this.isAlive = true;
    }

    // Getters and setters
    public String getServiceId() { return serviceId; }
    public String getHost() { return host; }
    public int getPort() { return port; }
    public boolean isAlive() { return isAlive; }
    public void setAlive(boolean alive) { isAlive = alive; }
}

// Mock service registry to maintain service instances
class ServiceRegistry {
    private Map<String, List<ServiceInstance>> services;

    public ServiceRegistry() {
        this.services = new HashMap<>();
    }

    // Register a new service instance
    public void register(ServiceInstance instance) {
        if (instance == null) {
            throw new IllegalArgumentException("Service instance cannot be null");
        }
        
        services.computeIfAbsent(instance.getServiceId(), k -> new ArrayList<>())
                .add(instance);
    }

    // Deregister an existing service instance
    public void deregister(ServiceInstance instance) {
        if (instance == null) {
            throw new IllegalArgumentException("Service instance cannot be null");
        }
        
        List<ServiceInstance> instances = services.get(instance.getServiceId());
        if (instances != null) {
            instances.remove(instance);
            if (instances.isEmpty()) {
                services.remove(instance.getServiceId());
            }
        }
    }

    // Get all instances of a specific service
    public List<ServiceInstance> getInstances(String serviceId) {
        if (serviceId == null || serviceId.trim().isEmpty()) {
            throw new IllegalArgumentException("Service ID cannot be null or empty");
        }
        return services.getOrDefault(serviceId, new ArrayList<>());
    }

    // Get all registered service IDs
    public Set<String> getServices() {
        return new HashSet<>(services.keySet());
    }
}

// Simplified Discovery Service implementation
class DiscoveryService {
    private final ServiceRegistry registry;
    private boolean isRunning;

    public DiscoveryService() {
        this.registry = new ServiceRegistry();
        this.isRunning = false;
    }

    // Start the discovery service
    public void start() {
        if (!isRunning) {
            isRunning = true;
        }
    }

    // Stop the discovery service
    public void stop() {
        if (isRunning) {
            isRunning = false;
        }
    }

    // Check if the service is running
    public boolean isRunning() {
        return isRunning;
    }

    // Register a new service instance
    public void registerService(ServiceInstance instance) {
        if (!isRunning) {
            throw new IllegalStateException("Discovery service is not running");
        }
        registry.register(instance);
    }

    // Deregister an existing service instance
    public void deregisterService(ServiceInstance instance) {
        if (!isRunning) {
            throw new IllegalStateException("Discovery service is not running");
        }
        registry.deregister(instance);
    }

    // Get all instances of a specific service
    public List<ServiceInstance> getServiceInstances(String serviceId) {
        if (!isRunning) {
            throw new IllegalStateException("Discovery service is not running");
        }
        return registry.getInstances(serviceId);
    }

    // Get all registered services
    public Set<String> getRegisteredServices() {
        if (!isRunning) {
            throw new IllegalStateException("Discovery service is not running");
        }
        return registry.getServices();
    }
}

// Main test class for JBMC verification
public class DiscoveryServiceTest {
    public static void main(String[] args) {
        try {
            // Initialize the service
            DiscoveryService discoveryService = new DiscoveryService();
            
            // Test Case 1: Service startup
            discoveryService.start();
            assert discoveryService.isRunning() : "Service should be running after start";
            
            // Test Case 2: Service instance registration
            ServiceInstance instance1 = new ServiceInstance("service1", "localhost", 8080);
            ServiceInstance instance2 = new ServiceInstance("service1", "localhost", 8081);
            ServiceInstance instance3 = new ServiceInstance("service2", "localhost", 8082);
            
            discoveryService.registerService(instance1);
            discoveryService.registerService(instance2);
            discoveryService.registerService(instance3);
            
            // Test Case 3: Service instance retrieval
            List<ServiceInstance> service1Instances = discoveryService.getServiceInstances("service1");
            assert service1Instances.size() == 2 : "Should have 2 instances of service1";
            
            List<ServiceInstance> service2Instances = discoveryService.getServiceInstances("service2");
            assert service2Instances.size() == 1 : "Should have 1 instance of service2";
            
            // Test Case 4: Registered services verification
            Set<String> services = discoveryService.getRegisteredServices();
            assert services.size() == 2 : "Should have 2 unique services";
            assert services.contains("service1") : "service1 should be registered";
            assert services.contains("service2") : "service2 should be registered";
            
            // Test Case 5: Service instance deregistration
            discoveryService.deregisterService(instance1);
            service1Instances = discoveryService.getServiceInstances("service1");
            assert service1Instances.size() == 1 : "Should have 1 instance of service1 after deregistration";
            
            // Test Case 6: Service shutdown
            discoveryService.stop();
            assert !discoveryService.isRunning() : "Service should not be running after stop";
            
            // Test Case 7: Error handling verification
            try {
                discoveryService.registerService(instance1);
                assert false : "Should not allow registration when service is stopped";
            } catch (IllegalStateException e) {
                // Expected exception
            }

            System.out.println("All tests passed successfully");
        } catch (Exception e) {
            System.err.println("Test failed: " + e.getMessage());
            throw e;
        }
    }
}