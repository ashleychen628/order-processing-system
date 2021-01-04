package com.example.egen.orderprocessingbulkasync;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableBatchProcessing
public class OrderProcessingBulkUpdateAsyncApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrderProcessingBulkUpdateAsyncApplication.class, args);
	}
    
}
