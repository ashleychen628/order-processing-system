package com.example.egen.orderprocessingbulkoperations.controller;

import com.example.egen.orderprocessingbulkoperations.model.CreateOrderDetailsRequest;
import com.example.egen.orderprocessingbulkoperations.model.OrderDetails;
import com.example.egen.orderprocessingbulkoperations.model.UpdateOrderDetails;
import com.example.egen.orderprocessingbulkoperations.model.UpdateOrderDetailsRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderDetailsController {
    @Autowired
    KafkaTemplate<String, OrderDetails> KafkaCreateOrderJsontemplate;

    @Autowired
    KafkaTemplate<String, UpdateOrderDetails> KafkaUpdateOrderJsontemplate;

    String CREATE_ORDER_TOPIC_NAME = "create-bulk-orders";
    String UPDATE_ORDER_TOPIC_NAME = "update-bulk-orders";

    @RequestMapping(method = RequestMethod.POST, value = "/bulk-order-create",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String publishCreateOrderMessage(@RequestBody CreateOrderDetailsRequest createOrderDetailsRequest) {
        for (OrderDetails orderDetails : createOrderDetailsRequest.getOrderDetails()) {
            KafkaCreateOrderJsontemplate.send(CREATE_ORDER_TOPIC_NAME, orderDetails);
        }
        return "Create Order Request Messages published successfully";
    }

    @RequestMapping(method = RequestMethod.POST, value = "/bulk-order-update",
            consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String publishUpdateOrderMessage
            (@RequestBody UpdateOrderDetailsRequest updateOrderDetailsRequest) {
        for (UpdateOrderDetails updateOrderDetails : updateOrderDetailsRequest.getUpdateOrderDetails()) {
            KafkaUpdateOrderJsontemplate.send(UPDATE_ORDER_TOPIC_NAME, updateOrderDetails);
        }
        return "Update Order Request Messages published successfully";
    }
}
