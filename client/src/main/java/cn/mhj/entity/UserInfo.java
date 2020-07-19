package cn.mhj.entity;

public class UserInfo {

  public enum UserType {
    Customer,
    Seller,
    Manager
  }

  private static final UserInfo instance = new UserInfo();

  private User user;
  private UserType userType;

  private UserInfo() {}

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public UserType getUserType() {
    return userType;
  }

  public void setUserType(UserType userType) {
    this.userType = userType;
  }

  public static UserInfo getInstance() {
    return instance;
  }
}
