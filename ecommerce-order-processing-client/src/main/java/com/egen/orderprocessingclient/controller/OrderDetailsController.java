package com.egen.orderprocessingclient.controller;

import com.egen.orderprocessingclient.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.egen.orderprocessingclient.model.CreateOrderRequest;
import com.egen.orderprocessingclient.model.CustomerPaymentDetailsList;
import com.egen.orderprocessingclient.model.OrderDetails;
import com.egen.orderprocessingclient.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@Api(description = "Order related endpoints")
public class OrderDetailsController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private RestTemplate restTemplate;

    @ApiOperation(value = "Create the order details based on input provided",
            notes = "Adds order details to the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved order detail"),
            @ApiResponse(code = 201, message = "Created order detail"),
            @ApiResponse(code = 403, message = "Accessing order detail is forbidden"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(method = RequestMethod.POST, value = "/createOrder", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderDetails createOrder(@RequestBody CreateOrderRequest order) throws Exception, CustomerIdNotFoundException {
        long customerId = order.getCustomer().getCustomerId();
        boolean isRegisteredCustomer = restTemplate.getForObject("http://customer-processing-service/isRegisteredCustomer/" +
                customerId, Boolean.class);
        boolean matchBillingAddress = false;
        if (isRegisteredCustomer) {
            CustomerPaymentDetailsList registeredCustomerPayment = restTemplate.getForObject("http://payment-processing-service/getCustomerPayment/" +
                    customerId, CustomerPaymentDetailsList.class);
            matchBillingAddress = orderService.verifyBillingAddress(order, registeredCustomerPayment);
        } else {
            throw new CustomerIdNotFoundException(HttpStatus.NOT_FOUND, customerId + " is not found. Create Customer information.");
        }
        if (matchBillingAddress) {
            return orderService.createOrder(order);
        } else {
            throw new BillingAddressNotMatchException(HttpStatus.NOT_ACCEPTABLE, "Billing Address is not matching with customer's billing address");
        }
    }

    @ApiOperation(value = "Search the order detail by order number",
            notes = "Returns Order Details available in the database")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved order detail"),
            @ApiResponse(code = 401, message = "Not authorized to view order detail"),
            @ApiResponse(code = 403, message = "Accessing order detail is forbidden"),
            @ApiResponse(code = 404, message = "Order number is not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(method = RequestMethod.GET, value = "/getOrder/{orderNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderDetails getOrderById(@PathVariable("orderNumber") String orderNumber) throws OrderIdNotFoundException {
        return orderService.getOrderById(orderNumber);
    }

    @ApiOperation(value = "Cancel the order with an order number ")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved order detail"),
            @ApiResponse(code = 201, message = "updated order status"),
            @ApiResponse(code = 403, message = "Accessing order detail is forbidden"),
            @ApiResponse(code = 404, message = "Order number is not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(method = RequestMethod.PUT, value = "/cancelOrder/{orderNumber}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderDetails cancelOrderById(@PathVariable("orderNumber") String orderNumber) throws OrderIdNotFoundException {
        return orderService.cancelOrderById(orderNumber);
    }

    @ApiOperation(value = "Update the order status with the provided order status")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved order detail"),
            @ApiResponse(code = 201, message = "updated order status"),
            @ApiResponse(code = 403, message = "Accessing order detail is forbidden"),
            @ApiResponse(code = 404, message = "Not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(method = RequestMethod.PUT, value = "/updateOrderStatus", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderDetails updateOrderStatus(@RequestBody OrderDetails orderDetails) throws OrderIdNotFoundException, EnumNotPresentException {
        return orderService.updateOrderStatus(orderDetails);
    }

    @ApiOperation(value = "Get an order detail by customerId and orderNumber",
            notes = "Returns Order Details")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successfully retrieved order detail"),
            @ApiResponse(code = 401, message = "Not authorized to view order detail"),
            @ApiResponse(code = 403, message = "Accessing order detail is forbidden"),
            @ApiResponse(code = 404, message = "Order number is not found"),
            @ApiResponse(code = 500, message = "Internal Server Error")
    })
    @RequestMapping(method = RequestMethod.GET, value = "/getOrderByCustomerIDAndOrderID/{customerId}/{orderNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
    public OrderDetails getOrderByCustomerIDAndOrderID(@PathVariable long customerId, @PathVariable String orderNumber) throws OrderIdNotFoundException, NoMatchException, CustomerIdNotFoundException {
        return orderService.getOrderByCustomerIDAndOrderID(customerId, orderNumber);
    }
}
