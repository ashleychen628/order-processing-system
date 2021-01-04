package com.egen.orderprocessingclient.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class OrderPaymentRequest {
    private String paymentConfirmationNumber;
    private double paymentAmount;
    private PaymentMethod paymentMethod;
    private Payment paymentType;

    public OrderPaymentRequest(String paymentConfirmationNumber, double paymentAmount, PaymentMethod paymentMethod) {
        this.paymentConfirmationNumber = paymentConfirmationNumber;
        this.paymentAmount = paymentAmount;
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentConfirmationNumber() {
        return paymentConfirmationNumber;
    }

    public void setPaymentConfirmationNumber(String paymentConfirmationNumber) {
        this.paymentConfirmationNumber = paymentConfirmationNumber;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Payment getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Payment paymentType) {
        this.paymentType = paymentType;
    }
}
