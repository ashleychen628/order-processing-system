package com.egen.orderprocessingclient.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Billing {
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private int zip;

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        assert addressLine1 != null && !addressLine1.isEmpty() : "Address Line 1 cannot be null or empty";
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
        assert city != null && !city.isEmpty() : "City cannot be null or empty";
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        assert state != null && !state.isEmpty() : "State cannot be null or empty";
        this.state = state;
    }

    public int getZip() {
        return zip;
    }

    public void setZip(int zip) {
        assert zip > 0 : "ZIP code must be a positive number";
        this.zip = zip;
    }

    public static void main(String[] args) {
      Billing billing = new Billing();
    
      billing.setAddressLine1("123 Main St");
      billing.setCity("Springfield");
      billing.setState("IL");
      billing.setZip(62704);
    
      assert billing.getAddressLine1().equals("123 Main St");
      assert billing.getCity().equals("Springfield");
      assert billing.getState().equals("IL");
      assert billing.getZip() == 62704;
    }
}
