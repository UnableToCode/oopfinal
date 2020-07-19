package cn.mhj.server;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import org.apache.log4j.Logger;

public class MySocket implements Runnable {
  private Socket socket;
  private static Logger logger = Logger.getLogger(MySocket.class);

  public MySocket(Socket socket) {
    this.socket = socket;
  }

  @Override
  public void run() {
    try {
      BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
      BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

      String requestData = reader.readLine();
      logger.info("receive request from " + socket.getInetAddress().getHostAddress());
      String response = Response.processRequest(requestData);
      if (response == null) {
        response = "";
      }
      writer.write(response);
      writer.flush();
      logger.info("have processed request from " + socket.getInetAddress().getHostAddress());
    } catch (IOException e) {
      logger.error("read or write throw socket failed", e);
    } finally {
      try {
        logger.info("disconnect with " + socket.getInetAddress().getHostAddress());
        socket.close();
      } catch (IOException e) {
        logger.error("close socket failed", e);
      }
    }
  }
}
