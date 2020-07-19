package cn.mhj.tableProperty;

import cn.mhj.entity.OrderForm;
import java.text.SimpleDateFormat;
import javafx.beans.property.SimpleStringProperty;

public class OrderFromProperty {
  OrderForm orderForm;
  SimpleStringProperty sellerName = new SimpleStringProperty();
  SimpleStringProperty customerName = new SimpleStringProperty();
  SimpleStringProperty date = new SimpleStringProperty();

  public OrderFromProperty(OrderForm orderForm) {
    this.orderForm = orderForm;
    sellerName.setValue(orderForm.getSeller().getName());
    customerName.setValue(orderForm.getCustomer().getUsername());
    date.setValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss ").format(orderForm.getOrderTime()));
  }

  public OrderForm getOrderForm() {
    return orderForm;
  }

  public void setOrderForm(OrderForm orderForm) {
    this.orderForm = orderForm;
  }

  public String getSellerName() {
    return sellerName.get();
  }

  public SimpleStringProperty sellerNameProperty() {
    return sellerName;
  }

  public void setSellerName(String sellerName) {
    this.sellerName.set(sellerName);
  }

  public String getCustomerName() {
    return customerName.get();
  }

  public SimpleStringProperty customerNameProperty() {
    return customerName;
  }

  public void setCustomerName(String customerName) {
    this.customerName.set(customerName);
  }

  public String getDate() {
    return date.get();
  }

  public SimpleStringProperty dateProperty() {
    return date;
  }

  public void setDate(String date) {
    this.date.set(date);
  }
}
