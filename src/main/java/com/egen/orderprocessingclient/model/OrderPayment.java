package com.egen.orderprocessingclient.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@ToString
@Entity
@Table(name = "order_payment")
@TypeDef(
        name = "pgsql_enum",
        typeClass = EnumTypePostgreSql.class
)
public class OrderPayment {
    @Id
    @Column(name = "payment_id")
    private String paymentId;

    @Column(name = "payment_confirmation_number", unique = true)
    private String paymentConfirmationNumber;

    @Column(name = "payment_amount")
    private double paymentAmount;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "payment_method_info")
    @Type(type = "pgsql_enum")
    private PaymentMethod paymentMethod;

    public OrderPayment() {
        this.paymentId = UUID.randomUUID().toString();
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
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
}
