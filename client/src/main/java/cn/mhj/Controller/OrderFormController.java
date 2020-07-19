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
import java.util.List;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class OrderFormController implements Initializable {
  @FXML public TableView<OrderFromProperty> orderFormTable;
  @FXML public TableView<OrderProperty> orderTable;

  private void showOrderForm() {
    orderFormTable.getItems().clear();
    RequestData requestData = new RequestData();
    requestData.setRequestType(RequestType.CustomerCheckOrder);
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

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    orderFormTable
        .getColumns()
        .get(0)
        .setCellValueFactory(new PropertyValueFactory<>("sellerName"));
    orderFormTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("date"));
    orderFormTable
        .getSelectionModel()
        .selectedItemProperty()
        .addListener(
            (observableValue, oldItem, newItem) ->
                showOrder(newItem.getOrderForm().getOrderList()));

    orderTable.getColumns().get(0).setCellValueFactory(new PropertyValueFactory<>("name"));
    orderTable.getColumns().get(1).setCellValueFactory(new PropertyValueFactory<>("number"));

    showOrderForm();
  }
}
