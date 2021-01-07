package com.example.egen.paymentprocessing.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "payment")
public class Payment {
    @Id
    @GeneratedValue(generator = "payment_id_generator")
    @SequenceGenerator(
            name = "payment_id_generator",
            sequenceName = "payment_id_sequence",
            initialValue = 10
    )
    private long paymentId;

    @Column(name = "type")
    private String type;

    @Column(name = "card_number", unique = true)
    private String cardNumber;

    @Column(name = "cvv")
    private String cvv;

    @Column(name = "expiry_month")
    private int expiryMonth;

    @Column(name = "expiry_year")
    private int expiryYear;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "billing_id", referencedColumnName = "billing_id")
    private BillingAddress billingAddress;
    
    @Column(name = "customer_id")
    private long customerId;

    public long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(long paymentId) {
        this.paymentId = paymentId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public int getExpiryMonth() {
        return expiryMonth;
    }

    public void setExpiryMonth(int expiryMonth) {
        this.expiryMonth = expiryMonth;
    }

    public int getExpiryYear() {
        return expiryYear;
    }

    public void setExpiryYear(int expiryYear) {
        this.expiryYear = expiryYear;
    }

    public BillingAddress getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(BillingAddress billingAddress) {
        this.billingAddress = billingAddress;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }
}
