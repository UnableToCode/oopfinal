package cn.mhj.Controller;

import cn.mhj.entity.Goods;
import cn.mhj.entity.Order;
import cn.mhj.entity.OrderForm;
import cn.mhj.entity.Seller;
import cn.mhj.entity.UserInfo;
import cn.mhj.socket.Request;
import cn.mhj.socket.RequestData;
import cn.mhj.socket.RequestType;
import cn.mhj.tableProperty.GoodsProperty;
import cn.mhj.tableProperty.OrderFromProperty;
import cn.mhj.tableProperty.OrderProperty;
import com.alibaba.fastjson.JSONObject;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class SellerController implements Initializable {
  @FXML public Label username;
  @FXML public Label name;
  @FXML public TableView<GoodsProperty> goodsTable;
  @FXML public TextField goodsNameInput;
  @FXML public TextField priceInput;
  @FXML public TextField descriptionInput;
  @FXML public Button newGoodsBtn;
  @FXML public TableView<OrderFromProperty> orderFormTable;
  @FXML public TableView<OrderProperty> orderTable;
  @FXML public Button refreshBtn;

  private void refreshGoods() {
    RequestData requestData = new RequestData();
    requestData.setRequestType(RequestType.GetGoodsList);
    requestData.setObj(UserInfo.getInstance().getUser().getID());
    String response = Request.getInstance().sendData(requestData);
    List<Goods> goodsList = JSONObject.parseArray(response, Goods.class);
    goodsTable.getItems().clear();
    for (Goods goods : goodsList) {
      goodsTable.getItems().add(new GoodsProperty(goods));
    }
  }

  private void addGoods() {
    Goods goods =
        new Goods(
            null,
            UserInfo.getInstance().getUser().getID(),
            goodsNameInput.getText(),
            Double.valueOf(priceInput.getText()),
            descriptionInput.getText());

    RequestData requestData = new RequestData();
    requestData.setRequestType(RequestType.AddGoods);
    requestData.setObj(goods);
    Request.getInstance().sendData(requestData);

    goodsNameInput.clear();
    priceInput.clear();
    descriptionInput.clear();
  }

  private void refreshOrderForm() {
    orderFormTable.getItems().clear();
    RequestData requestData = new RequestData();
    requestData.setRequestType(RequestType.SellerCheckOrder);
    requestData.setObj(UserInfo.getInstance().getUser().getID());
    String response = Request.getInstance().sendData(requestData);
    List<OrderForm> orderForms = JSONObject.parseArray(response, OrderForm.class);
    for (OrderForm orderForm : orderForms) {
      orderFormTable.getItems().add(new OrderFromProperty(orderForm));
    }
  }

  private void showOrder(List<Order> orders) {
    orderTable.getItems().clear();
    for (Order order : orders) {
      orderTable.getItems().add(new OrderProperty(order));
    }
  }

  @FXML
  public void newGoodsBtnClicked() {
    addGoods();
    refreshGoods();
  }

  @FXML
  public void refreshBtnClicked() {
    refreshOrderForm();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    Seller seller = (Seller) UserInfo.getInstance().getUser();
    username.setText(seller.getUsername());
    name.setText(seller.getName());

    goodsTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
    goodsTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("price"));
    goodsTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("description"));
    refreshGoods();

    orderFormTable
        .getColumns()
        .get(0)
        .setCellValueFactory(new PropertyValueFactory<>("customerName"));
    orderFormTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("date"));
    orderFormTable
        .getSelectionModel()
        .selectedItemProperty()
        .addListener( // 选中某一行
            (observableValue, oldItem, newItem) ->
                showOrder(newItem.getOrderForm().getOrderList()));
    refreshOrderForm();

    orderTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
    orderTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("number"));
  }
}
