package cn.mhj.server.RequsetHandler;

import cn.mhj.entity.Customer.VIP;
import cn.mhj.repository.UserList;
import cn.mhj.server.RequestData;
import cn.mhj.server.RequestType;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

public class VIPHandler extends Handler {

  private static Logger logger = Logger.getLogger(VIPHandler.class);

  @Override
  public String handleRequest(RequestData request) {
    JSONObject responseData = new JSONObject();
    if (request.getRequestType().equals(RequestType.VIP)) {
      JSONObject data = (JSONObject) request.getObj();
      Integer userID = (Integer) data.get("userID");
      VIP vip = VIP.valueOf((String) data.get("vip"));
      UserList.getInstance().setVIP(userID, vip);
      logger.info("user: " + userID + " vip level become " + vip);
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
