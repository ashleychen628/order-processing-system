package com.egen.orderprocessingclient.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;
// import org.hibernate.annotations.Type;
// import org.hibernate.annotations.TypeDef;

import javax.persistence.*;
import java.util.UUID;

@Data
@AllArgsConstructor
@ToString
@Entity
@Table(name = "order_payment")
// @TypeDef(
//         name = "pgsql_enum",
//         typeClass = EnumTypePostgreSql.class
// )
public class OrderPayment {
    @Id
    @Column(name = "payment_id")
    private String paymentId;

    @Column(name = "payment_confirmation_number", unique = true)
    private String paymentConfirmationNumber;

    @Column(name = "payment_amount")
    private double paymentAmount;

    @Enumerated(EnumType.STRING)
    @Column(columnDefinition = "payment_method_info")
    // @Type(type = "pgsql_enum")
    private PaymentMethod paymentMethod;

    public OrderPayment() {
        this.paymentId = UUID.randomUUID().toString();
    }

    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        assert paymentId != null && !paymentId.isEmpty() : "Payment ID must not be null or empty";
        this.paymentId = paymentId;
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

    public static void main(String[] args) {
      // Test Default Constructor
      OrderPayment payment = new OrderPayment();
      assert payment.getPaymentId() != null : "Payment ID should not be null";
      assert payment.getPaymentId().length() == 36 : "Payment ID should be a valid UUID";

      // Test setPaymentId
      payment.setPaymentId("12345-uuid");
      assert payment.getPaymentId().equals("12345-uuid") : "Payment ID mismatch";

      try {
          payment.setPaymentId(null);
          assert false : "Payment ID should not be null";
      } catch (AssertionError e) {
          System.out.println("Caught expected error: " + e.getMessage());
      }

      // Test setPaymentConfirmationNumber
      payment.setPaymentConfirmationNumber("CONFIRM123");
      assert payment.getPaymentConfirmationNumber().equals("CONFIRM123") : "Payment confirmation number mismatch";

      try {
          payment.setPaymentConfirmationNumber("");
          assert false : "Payment confirmation number should not be empty";
      } catch (AssertionError e) {
          System.out.println("Caught expected error: " + e.getMessage());
      }

      // Test setPaymentAmount
      payment.setPaymentAmount(500.0);
      assert payment.getPaymentAmount() == 500.0 : "Payment amount mismatch";

      try {
          payment.setPaymentAmount(-100.0);
          assert false : "Payment amount should not be negative";
      } catch (AssertionError e) {
          System.out.println("Caught expected error: " + e.getMessage());
      }
  }
}
