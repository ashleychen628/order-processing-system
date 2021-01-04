package com.egen.orderprocessingclient.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@Entity
@Table(name = "shipping_address")
@TypeDef(
        name = "pgsql_shipping_enum",
        typeClass = EnumTypePostgreSql.class
)
public class Shipping {
    @Id
//    @GeneratedValue(generator = "shipping_id_generator")
//    @SequenceGenerator(
//            name = "shipping_id_generator",
//            sequenceName = "shipping_id_sequence",
//            initialValue = 10
//    )
    @Column(name = "shipping_id")
    private String shippingId;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "shipping_method_info")
    @Type(type = "pgsql_shipping_enum")
    private ShippingMethod shippingMethod;

    public Shipping() {
        this.shippingId = UUID.randomUUID().toString();
    }

    public ShippingMethod getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(ShippingMethod shippingMethod) {
        this.shippingMethod = shippingMethod;
    }

    public ShippingStatus getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(ShippingStatus shippingStatus) {
        this.shippingStatus = shippingStatus;
    }

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "shipping_status_info")
    @Type(type = "pgsql_shipping_enum")
    private ShippingStatus shippingStatus;

    @Column(name = "shipping_charge")
    private double cost;

    @Column(name = "shipping_address1")
    private String addressLine1;

    @Column(name = "shipping_address2")
    private String addressLine2;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zip")
    private int zip;

    @JsonIgnore
    @OneToOne(mappedBy = "shipping")
    private OrderDetails order;

    public Shipping(String shippingId, ShippingMethod shippingMethod, double cost, String addressLine1, String addressLine2, String city, String state, int zip) {
        this.shippingId = shippingId;
        this.shippingMethod = shippingMethod;
        this.cost = cost;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.state = state;
        this.zip = zip;
    }

    public String getShippingId() {
        return shippingId;
    }

    public void setShippingId(String shippingId) {
        this.shippingId = shippingId;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        this.zip = zip;
    }

    public OrderDetails getOrder() {
        return order;
    }

    public void setOrder(OrderDetails order) {
        this.order = order;
    }
}
