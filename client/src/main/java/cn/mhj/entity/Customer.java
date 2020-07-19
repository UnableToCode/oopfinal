package cn.mhj.entity;

public class Customer extends User {

  public enum VIP {
    NOT,
    VIP1,
    VIP2,
  }

  private VIP vipLevel;

  public Customer(Integer ID, String username, String password, VIP vipLevel) {
    super(ID, username, password);
    this.vipLevel = vipLevel;
  }

  public Customer() {
    super();
    this.vipLevel = VIP.NOT;
  }

  public VIP getVipLevel() {
    return vipLevel;
  }

  public void setVipLevel(VIP vipLevel) {
    this.vipLevel = vipLevel;
  }
}
