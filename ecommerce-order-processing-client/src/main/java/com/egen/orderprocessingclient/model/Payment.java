package com.egen.orderprocessingclient.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Payment {
    private Billing billingAddress;

    public Billing getBilling() {
        return billingAddress;
    }

    public void setBilling(Billing billing) {
      assert billing != null : "billing must not be null or empty";
        this.billingAddress = billing;
    }

    public static void main(String[] args) {
      // Test Constructor
      Billing billing = new Billing();
    
      billing.setAddressLine1("123 Main St");
      billing.setCity("Springfield");
      billing.setState("IL");
      billing.setZip(62704);
      Payment payment = new Payment();
      payment.setBilling(billing);
      assert payment.getBilling() != null : "Billing address should not be null";
      assert payment.getBilling().getAddressLine1().equals("123 Main St") : "Billing address line mismatch";
      assert payment.getBilling().getCity().equals("Springfield") : "Billing city mismatch";
      assert payment.getBilling().getState().equals("IL") : "Billing state mismatch";
      assert payment.getBilling().getZip() == 62704 : "Billing ZIP code mismatch";

      // Test setBilling
      Billing newBilling = new Billing();
      billing.setAddressLine1("456 Elm St");
      billing.setCity("Chicago");
      billing.setState("IL");
      billing.setZip(60616);
      payment.setBilling(newBilling);
      assert payment.getBilling() == newBilling : "Billing address not set correctly";
      assert payment.getBilling().getAddressLine1().equals("456 Elm St") : "Updated billing address line mismatch";
      assert payment.getBilling().getCity().equals("Chicago") : "Updated billing city mismatch";
      assert payment.getBilling().getState().equals("IL") : "Updated billing state mismatch";
      assert payment.getBilling().getZip() == 60616 : "Updated billing ZIP code mismatch";

      // Test Default Constructor
      Payment defaultPayment = new Payment();
      assert defaultPayment.getBilling() == null : "Default billing address should be null";
  }
}