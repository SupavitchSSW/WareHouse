package sample;

import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

public class OrderDetailController implements Controller{
    PageController pageController;
    int order_id;

    public OrderDetailController(PageController pageController) {
        this.pageController = pageController;
    }

    @Override
    public void initilize() {

            Scene scene = pageController.getScene("orderDetail");
            Button btn = (Button) scene.lookup("#switchBtn");

            //table setup
//            TableView detail_table = (TableView) scene.lookup("#detail_table");
//
//            TableColumn<Order,Integer> idColumn = new TableColumn<>("ID");
//            TableColumn<Order,String> nameColumn = new TableColumn<>("NAME");
//
//            idColumn.setMinWidth(20);
//            nameColumn.setMinWidth(130);
//
//            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//            nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
//            order_table.getColumns().addAll(idColumn,nameColumn);
//
//            ObservableList<Order> orders = getOrder();
//            order_table.setItems(orders);

            //set listenner
            btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
                @Override
                public void handle(MouseEvent event){
                    pageController.active("orderList");
                }
            });

    }

    @Override
    public void onActive() {
        System.out.println("display detail order ID : "+getOrder_id());

    }

    public int getOrder_id() {
        return order_id;
    }

    public void setOrder_id(int order_id) {
        this.order_id = order_id;
    }
}
