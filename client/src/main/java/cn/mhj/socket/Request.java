package cn.mhj.socket;

import com.alibaba.fastjson.JSON;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Properties;
import org.apache.log4j.Logger;

public class Request {
  private static final Request instance = new Request();
  private static Logger logger = Logger.getLogger(Request.class);
  private String HOST;
  private int PORT;
  private Socket socket;

  private Request() {
    Properties config = new Properties();
    try {
      config.load(new FileReader("config.properties"));
    } catch (IOException e) {
      logger.error("load config error", e);
    }
    this.HOST = config.getProperty("host");
    this.PORT = Integer.parseInt(config.getProperty("port"));
  }

  public String sendData(RequestData data) {
    StringBuffer responseData = null;
    try {
      socket = new Socket(HOST, PORT);
      String jsonStr = JSON.toJSONString(data);
      OutputStream out = socket.getOutputStream();
      InputStream in = socket.getInputStream();
      out.write(jsonStr.getBytes());
      socket.shutdownOutput();
      responseData = new StringBuffer();
      BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
      String str = null;
      while ((str = reader.readLine()) != null) {
        responseData.append(str);
      }
    } catch (IOException e) {
      logger.error("send package error", e);
    } finally {
      try {
        socket.close();
      } catch (IOException e) {
        logger.error("close socket error", e);
      }
    }
    return responseData.toString();
  }

  public static Request getInstance() {
    return instance;
  }
}
