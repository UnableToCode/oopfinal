package cn.mhj;

import cn.mhj.Controller.LoginController;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage primaryStage) throws Exception {
    primaryStage.setTitle("LaoBaOrder");
    primaryStage.setWidth(LoginController.WIDTH);
    primaryStage.setHeight(LoginController.HEIGHT + 26);
    primaryStage.setResizable(false);
    LoginController loginController = new LoginController();
    loginController.init(primaryStage);
  }
}
