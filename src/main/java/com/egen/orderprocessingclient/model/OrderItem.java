package com.egen.orderprocessingclient.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Entity
@Table(name = "order_item")
public class OrderItem {
    @Id
    @Column(name = "item_id")
    private String itemId;

    @Column(name = "item_name")
    private String itemName;

    @Column(name = "item_price")
    private double itemPrice;

    @Column(name = "quantity")
    private int quantity;

    public OrderItem() {
        this.itemId = UUID.randomUUID().toString();
    }

    public OrderItem(String itemId, String itemName, double itemPrice, int quantity) {
        assert itemId != null && !itemId.isEmpty() : "Item ID cannot be null or empty";
        assert itemName != null && !itemName.isEmpty() : "Item name cannot be null or empty";
        assert itemPrice >= 0 : "Item price must be non-negative";
        assert quantity > 0 : "Quantity must be greater than zero";
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.quantity = quantity;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        assert itemId != null && !itemId.isEmpty() : "Item ID cannot be null or empty";
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        assert itemName != null && !itemName.isEmpty() : "Item name cannot be null or empty";
        this.itemName = itemName;
    }

    public double getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(double itemPrice) {
        assert itemPrice >= 0 : "Item price must be non-negative";
        this.itemPrice = itemPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        assert quantity > 0 : "Quantity must be greater than zero";
        this.quantity = quantity;
    }

    public static void main(String[] args) {
      System.out.println("Verifying OrderItem assertions...");
      
      OrderItem item = new OrderItem("1", "Sample Item", 20.0, 5);

      assert item.getItemId().equals("1") : "Item ID mismatch";
      assert item.getItemName().equals("Sample Item") : "Item name mismatch";
      assert item.getItemPrice() == 20.0 : "Item price mismatch";
      assert item.getQuantity() == 5 : "Item quantity mismatch";

      item.setItemPrice(15.0);
      assert item.getItemPrice() == 15.0 : "Updated item price mismatch";
  
      item.setQuantity(10);
      assert item.getQuantity() == 10 : "Updated quantity mismatch";
  
      item.setItemName("Updated Item");
      assert item.getItemName().equals("Updated Item") : "Updated item name mismatch";
  
      System.out.println("All OrderItem assertions passed.");
  }
  
}
