package cn.mhj.tableProperty;

import cn.mhj.entity.Goods;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

public class GoodsProperty {
  Goods goods;

  SimpleStringProperty name = new SimpleStringProperty();
  SimpleDoubleProperty price = new SimpleDoubleProperty();
  SimpleStringProperty description = new SimpleStringProperty();

  public GoodsProperty(Goods goods) {
    this.goods = goods;
    this.name.setValue(goods.getName());
    this.price.setValue(goods.getPrice());
    this.description.setValue(goods.getDescription());
  }

  public Goods getGoods() {
    return goods;
  }

  public void setGoods(Goods goods) {
    this.goods = goods;
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

  public String getDescription() {
    return description.get();
  }

  public SimpleStringProperty descriptionProperty() {
    return description;
  }

  public void setDescription(String description) {
    this.description.set(description);
  }
}
