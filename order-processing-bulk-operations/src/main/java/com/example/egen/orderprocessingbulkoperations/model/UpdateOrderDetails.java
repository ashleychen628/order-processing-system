package com.example.egen.orderprocessingbulkoperations.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateOrderDetails {
    private String orderNumber;
    private OrderStatus orderStatus;
}
