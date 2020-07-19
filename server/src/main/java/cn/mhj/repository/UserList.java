package cn.mhj.repository;

import cn.mhj.entity.Customer;
import cn.mhj.entity.Customer.VIP;
import cn.mhj.entity.Seller;
import cn.mhj.entity.User;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserList {
  private static final UserList instance = new UserList();

  private HashMap<Integer, Seller> sellerList;
  private HashMap<Integer, Customer> customerList;
  private HashMap<Integer, User> managerList;

  private UserList() {
    sellerList = new HashMap<>();
    customerList = new HashMap<>();
    managerList = new HashMap<>();
  }

  public void addSeller(Seller seller) {
    Integer newId = sellerList.size();
    seller.setID(newId);
    sellerList.put(newId, seller);
  }

  public Seller getSeller(Integer sellerId) {
    return sellerList.get(sellerId);
  }

  public Map<String, Object> getSellerList() {
    Map<String, Object> sellerList = new HashMap<>();
    for (Seller seller : this.sellerList.values()) {
      sellerList.put(seller.getID().toString(), seller.getName());
    }
    return sellerList;
  }

  public void addCustomer(Customer customer) {
    Integer newId = customerList.size();
    customer.setID(newId);
    customerList.put(newId, customer);
  }

  public Customer getCustomer(Integer customerId) {
    return customerList.get(customerId);
  }

  public void addManager(User manager) {
    Integer newId = managerList.size();
    manager.setID(newId);
    managerList.put(newId, manager);
  }

  public void setVIP(Integer customerId, VIP vip) {
    Customer customer = customerList.get(customerId);
    if (customer != null) {
      customer.setVipLevel(vip);
    }
  }

  public User getUserInfoByUsername(String username) {

    List<User> allUsers = new ArrayList<>();
    allUsers.addAll(sellerList.values());
    allUsers.addAll(customerList.values());
    allUsers.addAll(managerList.values());

    for (User user : allUsers) {
      if (user.getUsername().equals(username)) {
        return user;
      }
    }

    return null;
  }

  public boolean checkUsername(String username) {
    List<User> allUsers = new ArrayList<>();
    allUsers.addAll(sellerList.values());
    allUsers.addAll(customerList.values());
    allUsers.addAll(managerList.values());

    for (User user : allUsers) {
      if (user.getUsername().equals(username)) {
        return true;
      }
    }
    return false;
  }

  public static UserList getInstance() {
    return instance;
  }
}
