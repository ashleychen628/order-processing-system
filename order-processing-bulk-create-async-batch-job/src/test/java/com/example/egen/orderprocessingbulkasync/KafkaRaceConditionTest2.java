package com.example.egen.orderprocessingbulkasync;

import static org.mockito.Mockito.*;
import org.mockito.MockedStatic;
import org.junit.jupiter.api.Test;

public class KafkaRaceConditionTest2 {

    @Test
    public void testKafkaSimulationWithMock() {
        // Mock the static method KafkaSimulation.produce
        try (MockedStatic<KafkaSimulation> mockedKafkaSimulation = mockStatic(KafkaSimulation.class)) {
            mockedKafkaSimulation.when(() -> KafkaSimulation.produce(anyString(), anyString()))
                                 .thenAnswer(invocation -> {
                                     String apiUrl = invocation.getArgument(0);
                                     String payload = invocation.getArgument(1);
                                     System.out.println("Mocked produce called with URL: " + apiUrl + ", Payload: " + payload);
                                     return null;
                                 });

            // Simulate concurrent producers
            String jsonPayload = "{}"; // Replace with actual JSON if needed
            Thread producer1 = new Thread(() -> {
                for (int i = 0; i < 5; i++) {
                    KafkaSimulation.produce("http://localhost:9081/bulk-order-create", jsonPayload);
                }
            });

            Thread producer2 = new Thread(() -> {
                for (int i = 5; i < 10; i++) {
                    KafkaSimulation.produce("http://localhost:9081/bulk-order-create", jsonPayload);
                }
            });

            // Start threads
            producer1.start();
            producer2.start();

            // Wait for threads to complete
            try {
                producer1.join();
                producer2.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            // Verify the mocked method was called the expected number of times
            mockedKafkaSimulation.verify(() -> KafkaSimulation.produce(anyString(), anyString()), times(10));
        }
    }
}

