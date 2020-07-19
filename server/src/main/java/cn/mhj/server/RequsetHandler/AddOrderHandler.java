package cn.mhj.server.RequsetHandler;

import cn.mhj.entity.OrderForm;
import cn.mhj.repository.OrderFormList;
import cn.mhj.server.RequestData;
import cn.mhj.server.RequestType;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

public class AddOrderHandler extends Handler {

  private static Logger logger = Logger.getLogger(AddOrderHandler.class);

  @Override
  public String handleRequest(RequestData request) {
    JSONObject responseData = new JSONObject();
    if (request.getRequestType().equals(RequestType.AddOrder)) {
      OrderForm newOrder = JSON.toJavaObject((JSONObject) request.getObj(), OrderForm.class);
      OrderFormList.getInstance().addOrderForm(newOrder);
      logger.info("add new order: " + newOrder.getOrderFormID());
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
