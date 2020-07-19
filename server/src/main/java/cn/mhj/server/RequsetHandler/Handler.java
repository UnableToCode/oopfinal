package cn.mhj.server.RequsetHandler;

import cn.mhj.server.RequestData;

public abstract class Handler {
  private Handler next = null;

  public void setNext(Handler next) {
    this.next = next;
  }

  public Handler getNext() {
    return next;
  }

  public abstract String handleRequest(RequestData request);
}
