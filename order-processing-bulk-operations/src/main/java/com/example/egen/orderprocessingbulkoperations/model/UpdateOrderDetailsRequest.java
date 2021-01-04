package com.example.egen.orderprocessingbulkoperations.model;

import java.util.List;

public class UpdateOrderDetailsRequest {
    private List<UpdateOrderDetails> updateOrderDetails;

    public List<UpdateOrderDetails> getUpdateOrderDetails() {
        return updateOrderDetails;
    }

    public void setUpdateOrderDetails(List<UpdateOrderDetails> updateOrderDetails) {
        this.updateOrderDetails = updateOrderDetails;
    }
}
