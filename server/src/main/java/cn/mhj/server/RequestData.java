package cn.mhj.server;

public class RequestData {
  private RequestType requestType;
  private Object obj;

  public RequestData(RequestType requestType, Object obj) {
    this.requestType = requestType;
    this.obj = obj;
  }

  public RequestData() {
    this.requestType = null;
    this.obj = null;
  }

  public RequestType getRequestType() {
    return requestType;
  }

  public void setRequestType(RequestType requestType) {
    this.requestType = requestType;
  }

  public Object getObj() {
    return obj;
  }

  public void setObj(Object obj) {
    this.obj = obj;
  }
}
