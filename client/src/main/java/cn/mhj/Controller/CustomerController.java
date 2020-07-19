package cn.mhj.Controller;

import cn.mhj.entity.Customer;
import cn.mhj.entity.Customer.VIP;
import cn.mhj.entity.Goods;
import cn.mhj.entity.Order;
import cn.mhj.entity.OrderForm;
import cn.mhj.entity.Seller;
import cn.mhj.entity.UserInfo;
import cn.mhj.socket.Request;
import cn.mhj.socket.RequestData;
import cn.mhj.socket.RequestType;
import cn.mhj.tableProperty.GoodsProperty;
import cn.mhj.tableProperty.OrderProperty;
import com.alibaba.fastjson.JSONObject;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory.IntegerSpinnerValueFactory;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class CustomerController implements Initializable {

  Customer customer;

  @FXML public Label username;
  @FXML public Label vip;
  @FXML public ChoiceBox<String> VIPChoice;
  @FXML public Button changeVIPBtn;
  @FXML public Button okBtn;
  @FXML public ListView<Seller> sellerList;
  @FXML public Spinner<Integer> numberSpinner;
  @FXML public Button addBtn;
  @FXML public Button delBtn;
  @FXML public Button payBtn;
  @FXML public Button checkOrderBtn;
  @FXML public TableView<GoodsProperty> goodsTable;
  @FXML public TableView<OrderProperty> shopcartTable;

  private void getSellerList() {
    RequestData requestData = new RequestData();
    requestData.setRequestType(RequestType.GetSellerList);
    String response = Request.getInstance().sendData(requestData);
    JSONObject responseData = JSONObject.parseObject(response);
    for (String id : responseData.keySet()) {
      sellerList
          .getItems()
          .add(new Seller(Integer.parseInt(id), null, null, (String) responseData.get(id)));
    }
  }

  private void getGoodsList(Integer sellerId) {
    RequestData requestData = new RequestData();
    requestData.setRequestType(RequestType.GetGoodsList);
    requestData.setObj(sellerId);
    String response = Request.getInstance().sendData(requestData);
    List<Goods> goodsList = JSONObject.parseArray(response, Goods.class);
    goodsTable.getItems().clear();
    for (Goods goods : goodsList) {
      goodsTable.getItems().add(new GoodsProperty(goods));
    }
  }

  private void changeVIP() {
    RequestData requestData = new RequestData();
    requestData.setRequestType(RequestType.VIP);
    JSONObject data = new JSONObject();
    data.put("userID", UserInfo.getInstance().getUser().getID());
    data.put("vip", VIP.values()[VIPChoice.getSelectionModel().getSelectedIndex()]);
    requestData.setObj(data);
    Request.getInstance().sendData(requestData);
    customer.setVipLevel(VIP.values()[VIPChoice.getSelectionModel().getSelectedIndex()]);
    vip.setText(customer.getVipLevel().toString());
  }

  private void addGoodsToShopcart() {
    Goods newGoods = goodsTable.getSelectionModel().getSelectedItem().getGoods();
    Integer number = numberSpinner.getValue();
    for (OrderProperty item : shopcartTable.getItems()) {
      if (item.getOrder().getGoods().equals(newGoods)) {
        item.setNumber(item.getNumber() + number);
        return;
      }
    }
    shopcartTable.getItems().add(new OrderProperty(new Order(newGoods, number)));
  }

  private Double calculatePrice() {
    double sum = 0;
    for (OrderProperty item : shopcartTable.getItems()) {
      sum += item.getNumber() * item.getPrice();
    }
    if (customer.getVipLevel() == VIP.VIP1) {
      sum -= 1;
    } else if (customer.getVipLevel() == VIP.VIP2) {
      sum *= 0.95;
    }
    return sum;
  }

  private void payOrder(Double sumPrice) {
    List<Order> orderList = new ArrayList<>();
    for (OrderProperty item : shopcartTable.getItems()) {
      orderList.add(item.getOrder());
    }
    OrderForm orderForm =
        new OrderForm(
            null,
            sellerList.getSelectionModel().getSelectedItem(),
            customer,
            new Date(),
            orderList,
            sumPrice);
    RequestData requestData = new RequestData();
    requestData.setRequestType(RequestType.AddOrder);
    requestData.setObj(orderForm);
    Request.getInstance().sendData(requestData);
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("提交订单");
    alert.setHeaderText("已成功提交订单");
    alert.showAndWait();
  }

  @FXML
  public void ChangeVIPBtnClicked() {
    vip.setVisible(false);
    changeVIPBtn.setVisible(false);
    VIPChoice.setVisible(true);
    okBtn.setVisible(true);
  }

  @FXML
  public void okBtnClicked() {
    VIPChoice.setVisible(false);
    okBtn.setVisible(false);
    changeVIP();
    vip.setVisible(true);
    changeVIPBtn.setVisible(true);
  }

  @FXML
  public void addBtnClicked() {
    addGoodsToShopcart();
    numberSpinner.getValueFactory().setValue(1);
  }

  @FXML
  public void checkOrderBtnClicked() {
    Stage orderFormStage = new Stage();
    orderFormStage.setTitle("OrderForm");
    orderFormStage.setWidth(LoginController.WIDTH);
    orderFormStage.setHeight(LoginController.HEIGHT + 26);
    orderFormStage.setResizable(false);
    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("../FXML/OrderForm.fxml"));
    Parent root = null;
    try {
      root = fxmlLoader.load();
      Scene orderFormScene = new Scene(root, LoginController.WIDTH, LoginController.HEIGHT);
      orderFormStage.setScene(orderFormScene);
      orderFormStage.showAndWait();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @FXML
  public void payBtnClicked() {
    if (shopcartTable.getItems().isEmpty()) {
      Alert alert = new Alert(AlertType.INFORMATION);
      alert.setTitle("购物车为空");
      alert.setHeaderText("请先添加商品至购物车");
      alert.showAndWait();
      return;
    }
    Double sumPrice = calculatePrice();
    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
    alert.setTitle("确认下单");
    alert.setHeaderText("请确认是否下单");
    alert.setContentText(String.format("需要支付%.2f元", sumPrice));

    Optional result = alert.showAndWait();
    if (result.get() == ButtonType.OK) {
      payOrder(sumPrice);
      shopcartTable.getItems().clear();
    }
  }

  @FXML
  public void delBtnClicked() {
    int selectIdx = shopcartTable.getSelectionModel().getSelectedIndex();
    if (selectIdx >= 0) {
      shopcartTable.getItems().remove(selectIdx);
    }
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    customer = (Customer) UserInfo.getInstance().getUser();
    username.setText(customer.getUsername());
    vip.setText(customer.getVipLevel().toString());
    VIPChoice.getItems().addAll("不成为VIP", "VIP1", "VIP2");
    VIPChoice.getSelectionModel().select(0);
    VIPChoice.setVisible(false);
    okBtn.setVisible(false);

    sellerList.setCellFactory(
        param ->
            new ListCell<Seller>() {
              @Override
              protected void updateItem(Seller item, boolean empty) {
                super.updateItem(item, empty);
                if (item == null) {
                  setText(null);
                } else {
                  setText(item.getName());
                }
              }
            });
    sellerList
        .getSelectionModel()
        .selectedIndexProperty()
        .addListener(
            (observable, oldValue, newValue) -> {
              getGoodsList(sellerList.getItems().get(newValue.intValue()).getID());
              shopcartTable.getItems().clear();
            });

    getSellerList();

    goodsTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
    goodsTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("price"));
    goodsTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("description"));

    numberSpinner.setValueFactory(new IntegerSpinnerValueFactory(1, 99, 1));

    shopcartTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
    shopcartTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("price"));
    shopcartTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("number"));
  }
}
