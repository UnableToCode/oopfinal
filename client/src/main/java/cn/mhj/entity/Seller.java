package cn.mhj.entity;

public class Seller extends User {
  private String name;

  public Seller(Integer ID, String username, String password, String name) {
    super(ID, username, password);
    this.name = name;
  }

  public Seller() {
    super();
    this.name = null;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return super.toString() + ',' + this.name;
  }
}
