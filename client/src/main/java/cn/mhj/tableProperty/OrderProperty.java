package cn.mhj.tableProperty;

import cn.mhj.entity.Order;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class OrderProperty {
  Order order;
  SimpleStringProperty name = new SimpleStringProperty();
  SimpleDoubleProperty price = new SimpleDoubleProperty();
  SimpleIntegerProperty number = new SimpleIntegerProperty();

  public OrderProperty(Order order) {
    this.order = order;
    this.name.setValue(order.getGoods().getName());
    this.price.setValue(order.getGoods().getPrice());
    this.number.setValue(order.getNum());
  }

  public Order getOrder() {
    return order;
  }

  public void setOrder(Order order) {
    this.order = order;
  }

  public String getName() {
    return name.get();
  }

  public SimpleStringProperty nameProperty() {
    return name;
  }

  public void setName(String name) {
    this.name.set(name);
  }

  public double getPrice() {
    return price.get();
  }

  public SimpleDoubleProperty priceProperty() {
    return price;
  }

  public void setPrice(double price) {
    this.price.set(price);
  }

  public int getNumber() {
    return number.get();
  }

  public SimpleIntegerProperty numberProperty() {
    return number;
  }

  public void setNumber(int number) {
    this.order.setNum(number);
    this.number.set(number);
  }
}
