package com.example.egen.orderprocessingbulkasync.configuration;

import java.util.*;

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

import com.example.egen.orderprocessingbulkasync.entity.CreateOrderRequest;
import com.example.egen.orderprocessingbulkasync.entity.OrderDetails;
import com.example.egen.orderprocessingbulkasync.entity.OrderItem;
import com.example.egen.orderprocessingbulkasync.entity.OrderPayment;
import com.example.egen.orderprocessingbulkasync.entity.OrderPaymentRequest;
import com.example.egen.orderprocessingbulkasync.entity.OrderStatus;
import com.example.egen.orderprocessingbulkasync.entity.ShippingStatus;
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
                .topic("create-bulk-orders")
                .build();
    }

    @Bean
    Step start() {
        ItemWriter writer = new ItemWriter<String>() {
            @Override
            public void write(List<? extends String> orders)
                    throws Exception {

                orders.stream().forEach(order -> {
                    Gson gson = new Gson();
                    CreateOrderRequest orderDetails = gson.fromJson(order, CreateOrderRequest.class);
                    OrderDetails processedOrderDetails = new OrderDetails();
                    processedOrderDetails.setOrderStatus(OrderStatus.PLACED);
                    processedOrderDetails.setOrderTax(orderDetails.getOrderTax());
                    processedOrderDetails.setOrderSubtotal(orderDetails.getOrderSubtotal());
                    processedOrderDetails.setOrderTotal(orderDetails.getOrderTotal());
                    processedOrderDetails.setShipping(orderDetails.getShipping());
                    processedOrderDetails.getShipping().setShippingStatus(ShippingStatus.PICKUP);
                    processedOrderDetails.setItems(orderDetails.getItems());
                    processedOrderDetails.setCustomerId(orderDetails.getCustomer().getCustomerId());

                    double itemPrice = 0;
                    for (OrderItem item : orderDetails.getItems()) {
                        itemPrice += item.getItemPrice() * item.getQuantity();
                    }
                    processedOrderDetails.setOrderSubtotal(itemPrice);
                    double tax = orderDetails.getOrderTax();
                    double subTotal = orderDetails.getOrderSubtotal();
                    if (orderDetails.getShipping().getShippingMethod().name().equals("STORE_PICKUP") || orderDetails.getShipping().getShippingMethod().name().equals("CURBSIDE")) {
                        orderDetails.getShipping().setCost(0);
                    }
                    double shippingTotal = orderDetails.getShipping().getCost();
                    double total = tax + subTotal + shippingTotal;
                    processedOrderDetails.setOrderTotal(total);

                    Date currentDate = new Date();
                    processedOrderDetails.setCreatedDate(currentDate);
                    processedOrderDetails.setModifiedDate(currentDate);

                    List<OrderPaymentRequest> orderPayments = orderDetails.getPayments();
                    List<OrderPayment> orderPaymentList = new ArrayList<>(orderPayments.size());
                    int i = 0;
                    for (OrderPaymentRequest orderPayment : orderPayments) {
                        OrderPayment individualOrderPayment = new OrderPayment();
                        individualOrderPayment.setPaymentAmount(orderPayment.getPaymentAmount());
                        individualOrderPayment.setPaymentConfirmationNumber(UUID.randomUUID().toString().replace("-", ""));
                        individualOrderPayment.setPaymentMethod(orderPayment.getPaymentMethod());
                        orderPaymentList.add(i, individualOrderPayment);
                        i++;
                    }

                    processedOrderDetails.setPayments(orderPaymentList);
                    respository.save(processedOrderDetails);
                });
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
        return jobBuilderFactory.get("BulkCreateJob")
                .incrementer(new RunIdIncrementer())
                .start(start())
                .build();
    }
}
