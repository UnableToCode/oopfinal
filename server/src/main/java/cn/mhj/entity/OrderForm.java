package cn.mhj.entity;

import java.util.Date;
import java.util.List;

public class OrderForm {

  Integer orderFormID;
  Seller seller;
  Customer customer;
  Date orderTime;
  List<Order> orderList;
  Double priceSum;

  public OrderForm(
      Integer orderFormID,
      Seller seller,
      Customer customer,
      Date orderTime,
      List<Order> orderList,
      Double priceSum) {
    this.orderFormID = orderFormID;
    this.seller = seller;
    this.customer = customer;
    this.orderTime = orderTime;
    this.orderList = orderList;
    this.priceSum = priceSum;
  }

  public OrderForm() {
    this.orderFormID = null;
    this.seller = null;
    this.customer = null;
    this.orderTime = null;
    this.orderList = null;
    this.priceSum = null;
  }

  public Integer getOrderFormID() {
    return orderFormID;
  }

  public void setOrderFormID(Integer orderFormID) {
    this.orderFormID = orderFormID;
  }

  public Seller getSeller() {
    return seller;
  }

  public void setSeller(Seller seller) {
    this.seller = seller;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public Date getOrderTime() {
    return orderTime;
  }

  public void setOrderTime(Date orderTime) {
    this.orderTime = orderTime;
  }

  public List<Order> getOrderList() {
    return orderList;
  }

  public void setOrderList(List<Order> orderList) {
    this.orderList = orderList;
  }

  public Double getPriceSum() {
    return priceSum;
  }

  public void setPriceSum(Double priceSum) {
    this.priceSum = priceSum;
  }
}
