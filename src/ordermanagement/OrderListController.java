package ordermanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import ordermanagement.Order;
import ordermanagement.OrderDetailController;
import sample.Controller;
import sample.PageController;


public class OrderListController implements Controller {
    PageController pageController;
    OrderDetailController orderDetailController;
    private TableView order_table;

    public OrderListController(PageController pageController, OrderDetailController orderDetailController) {
        this.pageController = pageController;
        this.orderDetailController = orderDetailController;
    }


    public void initilize() {
        Scene scene = pageController.getScene("orderList");
        Button btn = (Button) scene.lookup("#switchScene");


        //table setup
        order_table = (TableView) scene.lookup("#order_table");

        // column name
        TableColumn<Order, Integer> idColumn = new TableColumn<>("ID");
        TableColumn<Order, String> nameColumn = new TableColumn<>("NAME");

        idColumn.setMinWidth(20);
        nameColumn.setMinWidth(130);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        order_table.getColumns().addAll(idColumn, nameColumn);

        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //get select order id

                Order o = (Order) order_table.getSelectionModel().getSelectedItem();
                if (o != null) {
                    //change page
                    orderDetailController.setOrder_id(o.getId());
                    pageController.active("orderDetail");
                }
            }
        });


    }

    @Override
    public void onActive() {
        ObservableList<Order> orders = getOrder();
        order_table.setItems(orders);
    }

    public ObservableList<Order> getOrder() {
        ObservableList<Order> orders = FXCollections.observableArrayList();
        orders.add(new Order(1, "Por_shop1"));
        orders.add(new Order(2, "Por_shop2"));
        orders.add(new Order(3, "Por_shop3"));
        return orders;
    }
}