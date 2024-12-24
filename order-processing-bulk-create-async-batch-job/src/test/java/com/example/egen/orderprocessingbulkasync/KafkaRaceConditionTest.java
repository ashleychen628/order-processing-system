package com.example.egen.orderprocessingbulkasync;

public class KafkaRaceConditionTest {
  
  public static void main(String[] args) {
    // KafkaSimulation.MOCK_MODE = true;
    String jsonFilePath = "/Users/chendanyao/Documents/Columbia/CSEE 6863 Formal Verification/order-processing-system/order-processing-bulk-create-async-batch-job/src/test/java/com/example/egen/orderprocessingbulkasync/orderDetails.json"; 
    String jsonPayload = KafkaSimulation.loadJsonFromFile(jsonFilePath);
    // String jsonPayload = "nothing";
    Thread producer1 = new Thread(() -> {
        for (int i = 0; i < 5; i++) {
            KafkaSimulation.produce(
                "http://localhost:9081/bulk-order-create", jsonPayload
            );
        }
    });

    Thread producer2 = new Thread(() -> {
        for (int i = 5; i < 10; i++) {
            KafkaSimulation.produce(
                "http://localhost:9081/bulk-order-create", jsonPayload
            );
        }
    });

    // Simulate concurrent consumers triggering the batch job
    Thread consumer1 = new Thread(() -> {
        for (int i = 0; i < 5; i++) {
            KafkaSimulation.consumeBatchJob();
        }
    });

    Thread consumer2 = new Thread(() -> {
        for (int i = 0; i < 5; i++) {
            KafkaSimulation.consumeBatchJob();
        }
    });

    // Start the threads
    producer1.start();
    producer2.start();
    consumer1.start();
    consumer2.start();
  }
}
