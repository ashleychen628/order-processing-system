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

// import org.hibernate.annotations.Type;
// import org.hibernate.annotations.TypeDef;

// import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@Entity
@Table(name = "order_shipping")
// @TypeDef(
//         name = "pgsql_shipping_enum",
//         typeClass = EnumTypePostgreSql.class
// )
public class Shipping {
    @Id
    @Column(name = "shipping_id")
    private String shippingId;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "shipping_method_info")
    // @Type(type = "pgsql_shipping_enum")
    private ShippingMethod shippingMethod;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "shipping_status_info")
    // @Type(type = "pgsql_shipping_enum")
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

    // @JsonIgnore
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
    public Shipping() {
        this.shippingId = UUID.randomUUID().toString();
    }

    public ShippingMethod getShippingMethod() {
        return shippingMethod;
    }

    public void setShippingMethod(ShippingMethod shippingMethod) {
        assert shippingMethod != null : "Shipping method must not be null";
        this.shippingMethod = shippingMethod;
    }

    public ShippingStatus getShippingStatus() {
        return shippingStatus;
    }

    public void setShippingStatus(ShippingStatus shippingStatus) {
        assert shippingStatus != null : "Shipping status must not be null";
        this.shippingStatus = shippingStatus;
    }

    public String getShippingId() {
        return shippingId;
    }

    public void setShippingId(String shippingId) {
        assert shippingId != null && !shippingId.isEmpty() : "Shipping ID must not be null or empty";
        this.shippingId = shippingId;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        assert cost >= 0 : "Shipping cost must be non-negative";
        this.cost = cost;
    }

    public String getAddressLine1() {
        assert addressLine1 != null && !addressLine1.isEmpty() : "Address Line 1 must not be null or empty";
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
        assert city != null && !city.isEmpty() : "City must not be null or empty";
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        assert state != null && !state.isEmpty() : "State must not be null or empty";
        this.state = state;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        assert zip > 0 : "ZIP code must be a positive number";
        this.zip = zip;
    }

    public OrderDetails getOrder() {
        return order;
    }

    public void setOrder(OrderDetails order) {
        assert order != null : "Order details must not be null";
        this.order = order;
    }

    public static void main(String[] args) {
      Shipping shipping = new Shipping();

      // Test setCost
      shipping.setCost(20.50);
      assert shipping.getCost() == 20.50 : "Shipping cost mismatch";

      try {
          shipping.setCost(-5.00);
          assert false : "Negative shipping cost should not be allowed";
      } catch (AssertionError e) {
          System.out.println("Caught expected error: " + e.getMessage());
      }

      // Test setShippingId
      shipping.setShippingId("SHIP123");
      assert shipping.getShippingId().equals("SHIP123") : "Shipping ID mismatch";

      // Test setShippingMethod
      shipping.setShippingMethod(ShippingMethod.STORE_PICKUP);
      assert shipping.getShippingMethod() == ShippingMethod.STORE_PICKUP : "Shipping method mismatch";

      // Test setZip
      shipping.setZip(60616);
      assert shipping.getZip() == 60616 : "ZIP code mismatch";

      try {
          shipping.setZip(-1);
          assert false : "Negative ZIP code should not be allowed";
      } catch (AssertionError e) {
          System.out.println("Caught expected error: " + e.getMessage());
      }
  }
}
