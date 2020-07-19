package cn.mhj.server.RequsetHandler;

import cn.mhj.entity.Goods;
import cn.mhj.repository.GoodsList;
import cn.mhj.server.RequestData;
import cn.mhj.server.RequestType;
import com.alibaba.fastjson.JSON;
import java.util.List;
import org.apache.log4j.Logger;

public class GetGoodsListHandler extends Handler {

  private static Logger logger = Logger.getLogger(GetGoodsListHandler.class);

  @Override
  public String handleRequest(RequestData request) {

    if (request.getRequestType().equals(RequestType.GetGoodsList)) {
      Integer sellerId = (Integer) request.getObj();
      List<Goods> goodsList = GoodsList.getInstance().getGoodsBySeller(sellerId);
      logger.info("got goods list");
      return JSON.toJSON(goodsList).toString();
    } else {
      if (this.getNext() != null) {
        return this.getNext().handleRequest(request);
      } else {
        logger.error("Unknown Request!");
      }
    }
    return null;
  }
}
