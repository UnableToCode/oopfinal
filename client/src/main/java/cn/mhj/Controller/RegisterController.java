package cn.mhj.Controller;

import cn.mhj.entity.Customer;
import cn.mhj.entity.Customer.VIP;
import cn.mhj.entity.Seller;
import cn.mhj.entity.User;
import cn.mhj.socket.Request;
import cn.mhj.socket.RequestData;
import cn.mhj.socket.RequestType;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class RegisterController implements Initializable {

  @FXML public TextField usernameInput;
  @FXML public PasswordField passwordInput;
  @FXML public PasswordField pwdcheckInput;
  @FXML public ChoiceBox<String> userTypeChoice;
  @FXML public ChoiceBox<String> VIPChoice;
  @FXML public TextField sellerNameInput;
  @FXML public Button okBtn;
  @FXML public Button cancelBtn;

  private void returnToLogin() throws IOException {
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXML/Login.fxml"));
    Parent root = fxmlLoader.load();
    Scene scene = new Scene(root, LoginController.WIDTH, LoginController.HEIGHT);
    Stage stage = (Stage) cancelBtn.getScene().getWindow();
    stage.setScene(scene);
    stage.show();
  }

  private boolean register() {

    if (usernameInput.getText().isEmpty()
        || passwordInput.getText().isEmpty()
        || pwdcheckInput.getText().isEmpty()
        || (userTypeChoice.getSelectionModel().getSelectedIndex() == 1
            && sellerNameInput.getText().isEmpty())) {
      Alert alert = new Alert(AlertType.WARNING);
      alert.titleProperty().set("错误");
      alert.headerTextProperty().set("所有输入均不能为空");
      alert.showAndWait();
      return false;
    }

    if (!passwordInput.getText().equals(pwdcheckInput.getText())) {
      Alert alert = new Alert(AlertType.WARNING);
      alert.titleProperty().set("错误");
      alert.headerTextProperty().set("两次密码输入不一致");
      alert.showAndWait();
      return false;
    }

    RequestData requestData = new RequestData();
    if (userTypeChoice.getSelectionModel().getSelectedIndex() == 0) {
      Customer customer = new Customer();
      customer.setUsername(usernameInput.getText());
      customer.setPassword(passwordInput.getText());
      customer.setVipLevel(VIP.values()[VIPChoice.getSelectionModel().getSelectedIndex()]);
      requestData.setRequestType(RequestType.CustomerRegister);
      requestData.setObj(customer);

    } else if (userTypeChoice.getSelectionModel().getSelectedIndex() == 1) {
      Seller seller = new Seller();
      seller.setUsername(usernameInput.getText());
      seller.setPassword(passwordInput.getText());
      seller.setName(sellerNameInput.getText());
      requestData.setRequestType(RequestType.SellerRegister);
      requestData.setObj(seller);
    } else {
      User user = new User();
      user.setUsername(usernameInput.getText());
      user.setPassword(passwordInput.getText());
      requestData.setRequestType(RequestType.ManagerRegister);
      requestData.setObj(user);
    }
    String response = Request.getInstance().sendData(requestData);
    JSONObject responseData = JSONObject.parseObject(response);
    if (responseData.get("type").equals("success")) {
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.titleProperty().set("恭喜");
      alert.headerTextProperty().set((String) responseData.get("msg"));
      alert.showAndWait();
      return true;
    } else {
      Alert alert = new Alert(AlertType.WARNING);
      alert.titleProperty().set("注册失败");
      alert.headerTextProperty().set((String) responseData.get("msg"));
      alert.showAndWait();
      return false;
    }
  }

  @FXML
  public void okClicked() throws IOException {
    if (register()) {
      returnToLogin();
    }
  }

  @FXML
  public void cancelClicked() throws IOException {
    returnToLogin();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    userTypeChoice.getItems().addAll("客户", "商家", "管理员");
    VIPChoice.getItems().addAll("不成为VIP", "VIP1", "VIP2");
    userTypeChoice.getSelectionModel().select(0);
    VIPChoice.getSelectionModel().select(0);
    VIPChoice.setVisible(true);
    sellerNameInput.setVisible(false);

    userTypeChoice
        .getSelectionModel()
        .selectedIndexProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              if (newValue.intValue() == 0) {
                VIPChoice.setVisible(true);
                sellerNameInput.setVisible(false);
              } else if (newValue.intValue() == 1) {
                VIPChoice.setVisible(false);
                sellerNameInput.setVisible(true);
              } else {
                VIPChoice.setVisible(false);
                sellerNameInput.setVisible(false);
              }
            });
  }
}
