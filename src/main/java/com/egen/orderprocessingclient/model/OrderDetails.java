package com.egen.orderprocessingclient.model;

import java.util.Arrays;
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

// import org.hibernate.annotations.Type;
// import org.hibernate.annotations.TypeDef;

import lombok.Data;

@Data
@Entity
@Table(name = "order_details")
// @TypeDef(
//         name = "pgsql_order_enum",
//         typeClass = EnumTypePostgreSql.class
// )
public class OrderDetails {
    @Id
    @Column(name = "order_number")
    private String orderNumber;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "order_status_info")
    // @Type(type = "pgsql_order_enum")
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
      assert orderNumber != null : "orderNumber must not be null";  
      this.orderNumber = orderNumber;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        assert orderStatus != null : "orderStatus must not be null";
        this.orderStatus = orderStatus;
    }

    public double getOrderSubtotal() {
        return orderSubtotal;
    }

    public void setOrderSubtotal(double orderSubtotal) {
        assert orderSubtotal >= 0 : "orderSubtotal must be non-negative";
        this.orderSubtotal = orderSubtotal;
    }

    public double getOrderTax() {
        return orderTax;
    }

    public void setOrderTax(double orderTax) {
        assert orderTax >= 0 : "orderTax must not be null";
        this.orderTax = orderTax;
    }

    public double getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(double orderTotal) {
        assert orderTotal >= 0 : "orderTotal must be non-negative";
        this.orderTotal = orderTotal;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        assert createdDate != null : "createdDate must not be null";
        this.createdDate = createdDate;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        assert modifiedDate != null : "modifiedDate must not be null";
        this.modifiedDate = modifiedDate;
    }

    public Shipping getShipping() {
        return shipping;
    }

    public void setShipping(Shipping shipping) {
        assert shipping != null : "shipping must not be null";
        this.shipping = shipping;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        assert customerId >= 0 : "customerId must be non-negative";
        this.customerId = customerId;
    }

    public List<OrderItem> getItems() {
        return items;
    }

    public void setItems(List<OrderItem> items) {
        assert items != null : "items must not be null";
        this.items = items;
    }

    public List<OrderPayment> getPayments() {
        return payments;
    }

    public void setPayments(List<OrderPayment> payments) {
        assert payments != null : "payments must not be null";
        this.payments = payments;
    }

    public static void main(String[] args) {
      OrderDetails order = new OrderDetails();

      // Test Default Constructor
      assert order.getOrderNumber() != null : "Default constructor should initialize orderNumber";

      // Test setOrderSubtotal
      order.setOrderSubtotal(100.0);
      assert order.getOrderSubtotal() == 100.0 : "orderSubtotal mismatch";

      try {
          order.setOrderSubtotal(-1.0);
          assert false : "Negative orderSubtotal should not be allowed";
      } catch (AssertionError e) {
          System.out.println("Caught expected error: " + e.getMessage());
      }

      // Test setCustomerId
      order.setCustomerId(1L);
      assert order.getCustomerId() == 1L : "customerId mismatch";

      try {
          order.setCustomerId(-1L);
          assert false : "Negative customerId should not be allowed";
      } catch (AssertionError e) {
          System.out.println("Caught expected error: " + e.getMessage());
      }

      // Test setItems
      order.setItems(Arrays.asList(new OrderItem()));
      assert order.getItems() != null : "items should not be null";
      assert !order.getItems().isEmpty() : "items should not be empty";

      try {
          order.setItems(null);
          assert false : "items should not accept null";
      } catch (AssertionError e) {
          System.out.println("Caught expected error: " + e.getMessage());
      }
  }
}
