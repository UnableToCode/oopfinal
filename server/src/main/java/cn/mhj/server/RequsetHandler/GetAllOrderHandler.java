package cn.mhj.server.RequsetHandler;

import cn.mhj.entity.OrderForm;
import cn.mhj.repository.OrderFormList;
import cn.mhj.server.RequestData;
import cn.mhj.server.RequestType;
import com.alibaba.fastjson.JSON;
import java.util.List;
import org.apache.log4j.Logger;

public class GetAllOrderHandler extends Handler {

  private static Logger logger = Logger.getLogger(SellerCheckHandler.class);

  @Override
  public String handleRequest(RequestData request) {
    if (request.getRequestType().equals(RequestType.GetAllOrder)) {
      List<OrderForm> orderList = OrderFormList.getInstance().getAll();
      logger.info("manager got all order list");
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
