package cn.mhj.server.RequsetHandler;

import cn.mhj.entity.Customer;
import cn.mhj.entity.Seller;
import cn.mhj.entity.User;
import cn.mhj.repository.UserList;
import cn.mhj.server.RequestData;
import cn.mhj.server.RequestType;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.log4j.Logger;

public class LoginHandler extends Handler {

  private static Logger logger = Logger.getLogger(LoginHandler.class);

  @Override
  public String handleRequest(RequestData request) {
    JSONObject responseData = new JSONObject();
    if (request.getRequestType().equals(RequestType.Login)) {
      User loginInfo = JSON.toJavaObject((JSONObject) request.getObj(), User.class);
      User result = UserList.getInstance().getUserInfoByUsername(loginInfo.getUsername());
      if (result != null) {
        if (result.getPassword().equals(loginInfo.getPassword())) {
          responseData.put("type", "success");
          responseData.put("userID", result.getID());
          if (result instanceof Customer) {
            responseData.put("userType", "Customer");
            responseData.put("vip", ((Customer) result).getVipLevel());
          } else if (result instanceof Seller) {
            responseData.put("userType", "Seller");
            responseData.put("name", ((Seller) result).getName());
          } else {
            responseData.put("userType", "Manager");
          }
          logger.info(result.getUsername() + " login success");
        } else {
          responseData.put("type", "error");
          responseData.put("msg", "密码错误");
          logger.info(result.getUsername() + "login failed, wrong password");
        }
      } else {
        responseData.put("type", "error");
        responseData.put("msg", "用户名不存在！");
        logger.info("login failed, username not exist");
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
