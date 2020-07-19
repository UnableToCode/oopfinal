package cn.mhj.server.RequsetHandler;

import cn.mhj.entity.Seller;
import cn.mhj.repository.UserList;
import cn.mhj.server.RequestData;
import cn.mhj.server.RequestType;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

public class SellerRegisterHandle extends Handler {

  private static Logger logger = Logger.getLogger(SellerRegisterHandle.class);

  @Override
  public String handleRequest(RequestData request) {
    JSONObject responseData = new JSONObject();
    if (request.getRequestType().equals(RequestType.SellerRegister)) {
      Seller newUser = JSON.toJavaObject((JSONObject) request.getObj(), Seller.class);
      if (UserList.getInstance().checkUsername(newUser.getUsername())) {
        responseData.put("type", "error");
        responseData.put("msg", "用户名已存在！");
        logger.info("register failed, username have existed.");
      } else {
        UserList.getInstance().addSeller(newUser);
        responseData.put("type", "success");
        responseData.put("msg", "注册成功！");
        logger.info("register success, new seller:" + newUser.getUsername());
      }
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
