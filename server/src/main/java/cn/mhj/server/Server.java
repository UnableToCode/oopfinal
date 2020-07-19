package cn.mhj.server;

import java.io.FileReader;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;
import org.apache.log4j.Logger;

public class Server {
  private final int PORT;
  private static final Server instance = new Server();
  private static Logger logger = Logger.getLogger(Server.class);
  private ServerSocket serverSocket;

  private Server() {
    Properties config = new Properties();
    try {
      config.load(new FileReader("config.properties"));
    } catch (IOException e) {
      logger.error("load config error", e);
    }
    this.PORT = Integer.parseInt(config.getProperty("port"));
  }

  public void run() {
    try {
      serverSocket = new ServerSocket(PORT);
      logger.info("server is running. listen " + PORT + " port.");
      while (true) {
        Socket socket = serverSocket.accept();
        socket.setKeepAlive(true);
        new Thread(new MySocket(socket)).start();
        logger.info(socket.getInetAddress().getHostAddress() + " connected to server");
      }
    } catch (IOException e) {
      logger.error("build socket error", e);
    } finally {
      try {
        if (serverSocket != null) {
          serverSocket.close();
        }
      } catch (IOException e) {
        logger.error("close socket error", e);
      }
    }
  }

  public static Server getInstance() {
    return instance;
  }
}
