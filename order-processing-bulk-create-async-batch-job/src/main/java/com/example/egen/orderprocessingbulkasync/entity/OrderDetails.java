package com.example.egen.orderprocessingbulkasync.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Entity
@Table(name = "order_details")
@TypeDef(
        name = "pgsql_order_enum",
        typeClass = EnumTypePostgreSql.class
)
public class OrderDetails {
    @Id
    @JsonIgnore
    @Column(name = "order_number")
    private String orderNumber;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "order_status_info")
    @Type(type = "pgsql_order_enum")
    private OrderStatus orderStatus;

    @Column(name = "subtotal")
    private double orderSubtotal;

    @Column(name = "tax")
    private double orderTax;

    @Column(name = "total")
    private double orderTotal;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "modified_date")
    private Date modifiedDate;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "shipping_id", referencedColumnName = "shipping_id")
    private Shipping shipping;

    @Column(name = "customer_id")
    private long customerId;

    @OneToMany(targetEntity = OrderItem.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_number", referencedColumnName = "order_number")
    private List<OrderItem> items;

    @OneToMany(targetEntity = OrderPayment.class, cascade = CascadeType.ALL)
    @JoinColumn(name = "order_number", referencedColumnName = "order_number")
    private List<OrderPayment> payments;

    public OrderDetails() {
        this.orderNumber = UUID.randomUUID().toString();
    }

    public OrderDetails(String orderNumber, OrderStatus orderStatus, double orderSubtotal, double orderTax, double orderTotal, Shipping shipping, long customerId, List<OrderItem> items, List<OrderPayment> payments) {
        this.orderNumber = orderNumber;
        this.orderStatus = orderStatus;
        this.orderSubtotal = orderSubtotal;
        this.orderTax = orderTax;
        this.orderTotal = orderTotal;
        this.shipping = shipping;
        this.customerId = customerId;
        this.items = items;
        this.payments = payments;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public double getOrderSubtotal() {
        return orderSubtotal;
    }

    public void setOrderTotal(double orderTotal) {
        this.orderTotal = orderTotal;
    }

    public void setOrderSubtotal(double orderSubtotal) {
        this.orderSubtotal = orderSubtotal;
    }

    public double getOrderTax() {
        return orderTax;
    }

    public void setOrderTax(double orderTax) {
        this.orderTax = orderTax;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        this.shipping = shipping;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        this.items = items;
    }

    public List<OrderPayment> getPayments() {
        return payments;
    }

    public void setPayments(List<OrderPayment> payments) {
        this.payments = payments;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }
}
