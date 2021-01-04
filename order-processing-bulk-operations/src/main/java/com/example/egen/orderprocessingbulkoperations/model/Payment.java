package com.example.egen.orderprocessingbulkoperations.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private Billing billingAddress;

    public Billing getBilling() {
        return billingAddress;
    }

    public void setBilling(Billing billing) {
        this.billingAddress = billing;
    }
}