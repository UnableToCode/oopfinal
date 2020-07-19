package cn.mhj.repository;

import cn.mhj.entity.Goods;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GoodsList {
  public static final GoodsList instance = new GoodsList();
  private Map<Integer, Goods> goodsList;

  private GoodsList() {
    goodsList = new HashMap<>();
  }

  public void addGoods(Goods goods) {
    Integer newId = goodsList.size();
    goods.setID(newId);
    goodsList.put(newId, goods);
  }

  public List<Goods> getGoodsBySeller(Integer sellerId) {
    List<Goods> temp = new ArrayList<>();
    for (Goods goods : goodsList.values()) {
      if (goods.getSellerID().equals(sellerId)) {
        temp.add(goods);
      }
    }
    return temp;
  }

  public static GoodsList getInstance() {
    return instance;
  }
}
