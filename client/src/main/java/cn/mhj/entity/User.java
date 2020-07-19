package cn.mhj.entity;

public class User {
  private Integer ID;
  private String username;
  private String password;

  public User(Integer ID, String username, String password) {
    this.ID = ID;
    this.username = username;
    this.password = password;
  }

  public User() {
    this.ID = null;
    this.username = null;
    this.password = null;
  }

  public Integer getID() {
    return ID;
  }

  public void setID(Integer ID) {
    this.ID = ID;
  }

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  @Override
  public String toString() {
    return ID + "," + username + "," + password;
  }
}
