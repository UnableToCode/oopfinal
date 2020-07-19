package cn.mhj.entity;

public class Goods {

  private Integer ID;
  private Integer sellerID;
  private String name;
  private Double price;
  private String description;

  public Goods(Integer ID, Integer sellerID, String name, Double price, String description) {
    this.ID = ID;
    this.sellerID = sellerID;
    this.name = name;
    this.price = price;
    this.description = description;
  }

  public Goods() {
    this.ID = null;
    this.sellerID = null;
    this.name = null;
    this.price = null;
    this.description = null;
  }

  public Integer getID() {
    return ID;
  }

  public void setID(Integer ID) {
    this.ID = ID;
  }

  public Integer getSellerID() {
    return sellerID;
  }

  public void setSellerID(Integer sellerID) {
    this.sellerID = sellerID;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
