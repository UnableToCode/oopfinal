package cn.mhj.Controller;

import cn.mhj.entity.Order;
import cn.mhj.entity.OrderForm;
import cn.mhj.entity.UserInfo;
import cn.mhj.socket.Request;
import cn.mhj.socket.RequestData;
import cn.mhj.socket.RequestType;
import cn.mhj.tableProperty.OrderFromProperty;
import cn.mhj.tableProperty.OrderProperty;
import com.alibaba.fastjson.JSONObject;
import java.net.URL;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ManagerController implements Initializable {

  @FXML public Label username;
  @FXML public TableView<OrderProperty> orderTable;
  @FXML public TableView<OrderFromProperty> orderFormTable;
  @FXML public Label weekSum;
  @FXML public Label monthSum;
  @FXML public Button refreshBtn;

  private void refreshOrderForm() {
    orderFormTable.getItems().clear();
    RequestData requestData = new RequestData();
    requestData.setRequestType(RequestType.GetAllOrder);
    String response = Request.getInstance().sendData(requestData);
    List<OrderForm> orderForms = JSONObject.parseArray(response, OrderForm.class);
    for (OrderForm orderForm : orderForms) {
      orderFormTable.getItems().add(new OrderFromProperty(orderForm));
    }
    calculateSum();
  }

  private void showOrder(List<Order> orders) {
    orderTable.getItems().clear();
    for (Order order : orders) {
      orderTable.getItems().add(new OrderProperty(order));
    }
  }

  private void calculateSum() {
    double week = 0;
    double month = 0;

    Date curDate = new Date();
    for (OrderFromProperty orderFromProperty : orderFormTable.getItems()) {
      Date orderDate = orderFromProperty.getOrderForm().getOrderTime();
      Calendar curTime = Calendar.getInstance();
      curTime.setTime(curDate);
      Calendar orderTime = Calendar.getInstance();
      orderTime.setTime(orderDate);

      if (curTime.get(Calendar.YEAR) == orderTime.get(Calendar.YEAR)
          && curTime.get(Calendar.MONTH) == orderTime.get(Calendar.MONTH)) {
        month += orderFromProperty.getOrderForm().getPriceSum();
        if (curTime.get(Calendar.WEEK_OF_MONTH) == orderTime.get(Calendar.WEEK_OF_MONTH)) {
          week += orderFromProperty.getOrderForm().getPriceSum();
        }
      }
    }
    weekSum.setText(String.format("%.2f", week));
    monthSum.setText(String.format("%.2f", month));
  }

  @FXML
  public void refreshBtnClicked() {
    refreshOrderForm();
  }

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    username.setText(UserInfo.getInstance().getUser().getUsername());
    orderFormTable
        .getColumns()
        .get(0)
        .setCellValueFactory(new PropertyValueFactory<>("customerName"));
    orderFormTable
        .getColumns()
        .get(1)
        .setCellValueFactory(new PropertyValueFactory<>("sellerName"));
    orderFormTable.getColumns().get(2).setCellValueFactory(new PropertyValueFactory<>("date"));
    orderFormTable
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (observableValue, oldItem, newItem) ->
                showOrder(newItem.getOrderForm().getOrderList()));

    orderTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
    orderTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("number"));

    refreshOrderForm();
  }
}
