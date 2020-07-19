package cn.mhj.server;

import cn.mhj.server.RequsetHandler.AddGoodsHandler;
import cn.mhj.server.RequsetHandler.AddOrderHandler;
import cn.mhj.server.RequsetHandler.CustomerCheckHandler;
import cn.mhj.server.RequsetHandler.CustomerRegisterHandler;
import cn.mhj.server.RequsetHandler.GetAllOrderHandler;
import cn.mhj.server.RequsetHandler.GetGoodsListHandler;
import cn.mhj.server.RequsetHandler.GetSellersHandler;
import cn.mhj.server.RequsetHandler.Handler;
import cn.mhj.server.RequsetHandler.LoginHandler;
import cn.mhj.server.RequsetHandler.ManagerRegisterHandler;
import cn.mhj.server.RequsetHandler.SellerCheckHandler;
import cn.mhj.server.RequsetHandler.SellerRegisterHandle;
import cn.mhj.server.RequsetHandler.VIPHandler;
import com.alibaba.fastjson.JSON;

public class Response {
  static Handler customerRegisterHandler = new CustomerRegisterHandler();
  static Handler sellerRegisterHandler = new SellerRegisterHandle();
  static Handler loginHandler = new LoginHandler();
  static Handler vipHandler = new VIPHandler();
  static Handler getSellerListHandler = new GetSellersHandler();
  static Handler getGoodsListHandler = new GetGoodsListHandler();
  static Handler addGoodsHandler = new AddGoodsHandler();
  static Handler addOrderHandler = new AddOrderHandler();
  static Handler customerCheckHandler = new CustomerCheckHandler();
  static Handler sellerCheckHandler = new SellerCheckHandler();
  static Handler managerRegisterHandler = new ManagerRegisterHandler();
  static Handler getAllOrderHandler = new GetAllOrderHandler();

  static {
    customerRegisterHandler.setNext(sellerRegisterHandler);
    sellerRegisterHandler.setNext(loginHandler);
    loginHandler.setNext(vipHandler);
    vipHandler.setNext(getSellerListHandler);
    getSellerListHandler.setNext(getGoodsListHandler);
    getGoodsListHandler.setNext(addGoodsHandler);
    addGoodsHandler.setNext(addOrderHandler);
    addOrderHandler.setNext(customerCheckHandler);
    customerCheckHandler.setNext(sellerCheckHandler);
    sellerCheckHandler.setNext(managerRegisterHandler);
    managerRegisterHandler.setNext(getAllOrderHandler);
  }

  public static String processRequest(String json) {
    RequestData data = JSON.parseObject(json, RequestData.class);
    return customerRegisterHandler.handleRequest(data);
  }
}
