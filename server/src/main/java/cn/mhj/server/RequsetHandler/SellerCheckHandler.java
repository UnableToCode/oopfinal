package cn.mhj.server.RequsetHandler;

import cn.mhj.entity.OrderForm;
import cn.mhj.repository.OrderFormList;
import cn.mhj.server.RequestData;
import cn.mhj.server.RequestType;
import com.alibaba.fastjson.JSON;
import java.util.List;
import org.apache.log4j.Logger;

public class SellerCheckHandler extends Handler {

  private static Logger logger = Logger.getLogger(SellerCheckHandler.class);

  @Override
  public String handleRequest(RequestData request) {
    if (request.getRequestType().equals(RequestType.SellerCheckOrder)) {
      Integer sellerId = (Integer) request.getObj();
      List<OrderForm> orderList = OrderFormList.getInstance().getOrderFormBySeller(sellerId);
      logger.info("seller  " + sellerId + " got order list");
      return JSON.toJSON(orderList).toString();
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
