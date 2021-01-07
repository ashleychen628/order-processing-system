package com.example.egen.paymentprocessing.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

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
@TypeDef(
        name = "pgsql_shipping_enum",
        typeClass = EnumTypePostgreSql.class
)
public class Payment {
    @Id
    @GeneratedValue(generator = "payment_id_generator")
    @SequenceGenerator(
            name = "payment_id_generator",
            sequenceName = "payment_id_sequence",
            initialValue = 10
    )
    @Column(name = "payment_id")
    private long paymentId;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "payment_method_info")
    @Type(type = "pgsql_shipping_enum")
    private PaymentMethod type;

    @Column(name = "card_number", unique = true)
    private String cardNumber;

    @Column(name = "cvv")
    private int cvv;

    @Column(name = "expiry_month")
    private int expiryMonth;

    @Column(name = "expiry_year")
    private int expiryYear;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "billing_id", referencedColumnName = "billing_id")
    private BillingAddress billingAddress;
}
