package com.example.egen.orderprocessingbulkasync.configuration;

import java.util.List;
import java.util.Optional;
import java.util.Properties;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.kafka.KafkaItemReader;
import org.springframework.batch.item.kafka.builder.KafkaItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.egen.orderprocessingbulkasync.entity.OrderDetails;
import com.example.egen.orderprocessingbulkasync.entity.UpdateOrderRequest;
import com.example.egen.orderprocessingbulkasync.repository.OrderDetailsRepository;
import com.google.gson.Gson;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private KafkaProperties properties;

    @Autowired
    private OrderDetailsRepository respository;

    @Bean
    KafkaItemReader<Long, OrderDetails> kafkaItemReader() {
        Properties props = new Properties();
        props.putAll(this.properties.buildConsumerProperties());

        return new KafkaItemReaderBuilder<Long, OrderDetails>()
                .partitions(0)
                .consumerProperties(props)
                .name("orders-reader")
                .saveState(true)
                .topic("update-bulk-orders")
                .build();
    }

    @Bean
    Step start() {
        ItemWriter writer = new ItemWriter<String>() {
            @Override
            public void write(List<? extends String> orders)
                    throws Exception {
                for (String order : orders) {
                    Gson gson = new Gson();
                    UpdateOrderRequest updateOrderRequest = gson.fromJson(order, UpdateOrderRequest.class);
                    Optional<OrderDetails> orderDetails = 
                            respository.findByOrderNumber(updateOrderRequest.getOrderNumber());
                    orderDetails.ifPresent(details -> details.setOrderStatus(updateOrderRequest.getOrderStatus()));
                    respository.save(orderDetails.get());
                }
            }
        };
        return stepBuilderFactory.get("job")
                .chunk(0)
                .reader(kafkaItemReader())
                .writer(writer)
                .build();
    }

    @Bean
    Job job(NotificationListener listener, Step step1) {
        return jobBuilderFactory.get("BulkUpdateJob")
                .incrementer(new RunIdIncrementer())
                .start(start())
                .build();
    }
}
