package cn.mhj.entity;

public class Order {
  private Goods goods;
  private Integer num;

  public Order(Goods goods, Integer num) {
    this.goods = goods;
    this.num = num;
  }

  public Order() {
    this.goods = null;
    this.num = null;
  }

  public Goods getGoods() {
    return goods;
  }

  public void setGoods(Goods goods) {
    this.goods = goods;
  }

  public Integer getNum() {
    return num;
  }

  public void setNum(Integer num) {
    this.num = num;
  }
}
