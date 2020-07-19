package cn.mhj.server.RequsetHandler;

import cn.mhj.entity.Goods;
import cn.mhj.repository.GoodsList;
import cn.mhj.server.RequestData;
import cn.mhj.server.RequestType;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

public class AddGoodsHandler extends Handler {

  private static Logger logger = Logger.getLogger(AddGoodsHandler.class);

  @Override
  public String handleRequest(RequestData request) {
    JSONObject responseData = new JSONObject();
    if (request.getRequestType().equals(RequestType.AddGoods)) {
      Goods newGoods = JSON.toJavaObject((JSONObject) request.getObj(), Goods.class);
      GoodsList.getInstance().addGoods(newGoods);
      logger.info("add new Goods: " + newGoods.getName());
    } else {
      if (this.getNext() != null) {
        return this.getNext().handleRequest(request);
      } else {
        logger.error("Unknown Request!");
      }
    }
    return responseData.toJSONString();
  }
}
