package cn.mhj.Controller;

import cn.mhj.entity.Customer;
import cn.mhj.entity.Customer.VIP;
import cn.mhj.entity.Seller;
import cn.mhj.entity.User;
import cn.mhj.entity.UserInfo;
import cn.mhj.entity.UserInfo.UserType;
import cn.mhj.socket.Request;
import cn.mhj.socket.RequestData;
import cn.mhj.socket.RequestType;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {

  public static final int WIDTH = 800, HEIGHT = 600;
  @FXML private TextField usernameInput;
  @FXML private PasswordField passwordInput;
  @FXML private Button loginBtn;
  @FXML private Button registerBtn;

  public void init(Stage stage) throws IOException {
    Parent login = FXMLLoader.load(getClass().getResource("../FXML/Login.fxml"));
    Scene scene = new Scene(login, WIDTH, HEIGHT);
    stage.setScene(scene);
    stage.show();
  }

  private void enterCustomerScene() throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXML/Customer.fxml"));
    Parent root = fxmlLoader.load();
    Scene scene = new Scene(root, WIDTH, HEIGHT);
    Stage stage = (Stage) loginBtn.getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }

  private void enterSellerScene() throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXML/Seller.fxml"));
    Parent root = fxmlLoader.load();
    Scene scene = new Scene(root, WIDTH, HEIGHT);
    Stage stage = (Stage) loginBtn.getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }

  private void enterManagerScene() throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXML/Manager.fxml"));
    Parent root = fxmlLoader.load();
    Scene scene = new Scene(root, WIDTH, HEIGHT);
    Stage stage = (Stage) loginBtn.getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }

  private int login() {
    if (usernameInput.getText().isEmpty() || passwordInput.getText().isEmpty()) {
      Alert alert = new Alert(AlertType.WARNING);
      alert.titleProperty().set("错误");
      alert.headerTextProperty().set("请输入密码和用户名");
      alert.showAndWait();
      return 0;
    }
    RequestData requestData = new RequestData();
    requestData.setRequestType(RequestType.Login);
    requestData.setObj(new User(null, usernameInput.getText(), passwordInput.getText()));
    String response = Request.getInstance().sendData(requestData);
    JSONObject responseData = JSONObject.parseObject(response);

    if (responseData.get("type").equals("success")) {
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.titleProperty().set("登入");
      alert.headerTextProperty().set("登陆成功");
      alert.showAndWait();
      if (responseData.get("userType").equals("Customer")) {
        UserInfo.getInstance()
            .setUser(
                new Customer(
                    (Integer) responseData.get("userID"),
                    usernameInput.getText(),
                    null,
                    VIP.valueOf((String) responseData.get("vip"))));
        UserInfo.getInstance().setUserType(UserType.Customer);
        return 1;
      } else if (responseData.get("userType").equals("Seller")) {
        UserInfo.getInstance()
            .setUser(
                new Seller(
                    (Integer) responseData.get("userID"),
                    usernameInput.getText(),
                    null,
                    (String) responseData.get("name")));
        UserInfo.getInstance().setUserType(UserType.Seller);
        return 2;
      } else if (responseData.get("userType").equals("Manager")) {
        UserInfo.getInstance()
            .setUser(new User((Integer) responseData.get("userID"), usernameInput.getText(), null));
        UserInfo.getInstance().setUserType(UserType.Manager);
        return 3;
      }
    } else {
      Alert alert = new Alert(AlertType.WARNING);
      alert.titleProperty().set("登录失败");
      alert.headerTextProperty().set((String) responseData.get("msg"));
      alert.showAndWait();
      return 0;
    }
    return 0;
  }

  @FXML
  public void loginBtnClicked() throws IOException {
    int res = login();
    if (res == 1) {
      enterCustomerScene();
    } else if (res == 2) {
      enterSellerScene();
    } else if (res == 3) {
      enterManagerScene();
    }
  }

  @FXML
  public void registerBtnClicked() throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXML/Register.fxml"));
    Parent root = fxmlLoader.load();
    Scene scene = new Scene(root, WIDTH, HEIGHT);
    Stage stage = (Stage) loginBtn.getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }
}
