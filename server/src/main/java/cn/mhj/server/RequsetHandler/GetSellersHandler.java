package cn.mhj.server.RequsetHandler;

import cn.mhj.repository.UserList;
import cn.mhj.server.RequestData;
import cn.mhj.server.RequestType;
import com.alibaba.fastjson.JSONObject;
import java.util.Map;
import org.apache.log4j.Logger;

public class GetSellersHandler extends Handler {

  private static Logger logger = Logger.getLogger(GetSellersHandler.class);

  @Override
  public String handleRequest(RequestData request) {
    JSONObject responseData = new JSONObject();
    if (request.getRequestType().equals(RequestType.GetSellerList)) {
      Map<String, Object> sellerList = UserList.getInstance().getSellerList();
      logger.info("got seller list");
      responseData = new JSONObject(sellerList);
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
