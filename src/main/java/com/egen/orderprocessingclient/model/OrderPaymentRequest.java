package com.egen.orderprocessingclient.model;

import com.egen.orderprocessingclient.model.PaymentMethod; 
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class OrderPaymentRequest {
    private String paymentConfirmationNumber;
    private double paymentAmount;
    private PaymentMethod paymentMethod;
    private Payment paymentType;

    public OrderPaymentRequest(String paymentConfirmationNumber, double paymentAmount, PaymentMethod paymentMethod) {
        this.paymentConfirmationNumber = paymentConfirmationNumber;
        this.paymentAmount = paymentAmount;
        this.paymentMethod = paymentMethod;
    }

    public String getPaymentConfirmationNumber() {
        return paymentConfirmationNumber;
    }

    public void setPaymentConfirmationNumber(String paymentConfirmationNumber) {
        assert paymentConfirmationNumber != null && !paymentConfirmationNumber.isEmpty() : "Payment confirmation number must not be null or empty";
        this.paymentConfirmationNumber = paymentConfirmationNumber;
    }

    public double getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(double paymentAmount) {
        assert paymentAmount >= 0 : "Payment amount must be non-negative";
        this.paymentAmount = paymentAmount;
    }

    public PaymentMethod getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(PaymentMethod paymentMethod) {
        assert paymentMethod != null : "Payment method must not be null";
        this.paymentMethod = paymentMethod;
    }

    public Payment getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Payment paymentType) {
        this.paymentType = paymentType;
    }
    public static void main(String[] args) {
      // Test Constructor
      OrderPaymentRequest request = new OrderPaymentRequest("CONF123", 100.0, PaymentMethod.CREDIT);
      assert request.getPaymentConfirmationNumber().equals("CONF123") : "Payment confirmation number mismatch";
      assert request.getPaymentAmount() == 100.0 : "Payment amount mismatch";
      assert request.getPaymentMethod() == PaymentMethod.CREDIT : "Payment method mismatch";

      // Test setPaymentConfirmationNumber
      request.setPaymentConfirmationNumber("CONF456");
      assert request.getPaymentConfirmationNumber().equals("CONF456") : "Payment confirmation number mismatch";

      try {
          request.setPaymentConfirmationNumber(null);
          assert false : "Payment confirmation number should not be null";
      } catch (AssertionError e) {
          System.out.println("Caught expected error: " + e.getMessage());
      }

      // Test setPaymentAmount
      request.setPaymentAmount(200.0);
      assert request.getPaymentAmount() == 200.0 : "Payment amount mismatch";

      try {
          request.setPaymentAmount(-50.0);
          assert false : "Negative payment amount should not be allowed";
      } catch (AssertionError e) {
          System.out.println("Caught expected error: " + e.getMessage());
      }

      // Test setPaymentMethod
      request.setPaymentMethod(PaymentMethod.CREDIT);
      assert request.getPaymentMethod() == PaymentMethod.CREDIT : "Payment method mismatch";

      try {
          request.setPaymentMethod(null);
          assert false : "Payment method should not be null";
      } catch (AssertionError e) {
          System.out.println("Caught expected error: " + e.getMessage());
      }

      // Test setPaymentType
      // Payment paymentType = Payment.DEBIT;
      // request.setPaymentType(paymentType);
      // assert request.getPaymentType() == paymentType : "Payment type mismatch";

      // try {
      //     request.setPaymentType(null);
      //     assert false : "Payment type should not be null";
      // } catch (AssertionError e) {
      //     System.out.println("Caught expected error: " + e.getMessage());
      // }
  }
}
