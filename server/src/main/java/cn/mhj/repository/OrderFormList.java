package cn.mhj.repository;

import cn.mhj.entity.OrderForm;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderFormList {
  private static final OrderFormList instance = new OrderFormList();

  private Map<Integer, OrderForm> orderFormList;

  private OrderFormList() {
    this.orderFormList = new HashMap<>();
  }

  public void addOrderForm(OrderForm orderForm) {
    Integer newId = orderFormList.size();
    orderForm.setOrderFormID(newId);
    orderFormList.put(newId, orderForm);
  }

  public List<OrderForm> getOrderFormBySeller(Integer sellerId) {
    List<OrderForm> temp = new ArrayList<>();
    for (OrderForm orderForm : orderFormList.values()) {
      if (orderForm.getSeller().getID().equals(sellerId)) {
        temp.add(orderForm);
      }
    }
    return temp;
  }

  public List<OrderForm> getOrderFormByCustomer(Integer ordererId) {
    List<OrderForm> temp = new ArrayList<>();
    for (OrderForm orderForm : orderFormList.values()) {
      if (orderForm.getCustomer().getID().equals(ordererId)) {
        temp.add(orderForm);
      }
    }
    return temp;
  }

  public List<OrderForm> getAll() {
    return new ArrayList<>(orderFormList.values());
  }

  public static OrderFormList getInstance() {
    return instance;
  }
}
