package cn.mhj.server.RequsetHandler;

import cn.mhj.entity.OrderForm;
import cn.mhj.repository.OrderFormList;
import cn.mhj.server.RequestData;
import cn.mhj.server.RequestType;
import com.alibaba.fastjson.JSON;
import java.util.List;
import org.apache.log4j.Logger;

public class CustomerCheckHandler extends Handler {

  private static Logger logger = Logger.getLogger(CustomerCheckHandler.class);

  @Override
  public String handleRequest(RequestData request) {

    if (request.getRequestType().equals(RequestType.CustomerCheckOrder)) {
      Integer customerId = (Integer) request.getObj();
      List<OrderForm> orderList = OrderFormList.getInstance().getOrderFormByCustomer(customerId);
      logger.info("customer " + customerId + " got order list");
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
