package com.example.egen.paymentprocessing.model;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "billing_address")
@EntityListeners(AuditingEntityListener.class)
public class BillingAddress {
    @Id
    @GeneratedValue(generator = "billing_id_generator")
    @SequenceGenerator(
            name = "billing_id_generator",
            sequenceName = "billing_id_sequence",
            initialValue = 10
    )
    @Column(name = "billing_id")
    private long billingId;

    @Column(name = "billing_address1")
    private String addressLine1;

    @Column(name = "billing_address2")
    private String addressLine2;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip")
    private int zip;

    @JsonIgnore
    @OneToOne(mappedBy = "billingAddress")
    private Payment payment;
}
