package productManagement;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import sample.Controller;
import sample.PageController;


public class mainController implements Controller {
    PageController pageController;
    private TableView warehouse;

    public mainController(PageController pageController){
        this.pageController = pageController;
    }

    public void initilize(){
        Scene scene = pageController.getScene("mainPage");
//        Button btn = (Button) scene.lookup("#switchScene");


        //table setup
        warehouse = (TableView) scene.lookup("#order_table");

        // column name
//        TableColumn<Order,Integer> idColumn = new TableColumn<>("ID");
//        TableColumn<Order,String> nameColumn = new TableColumn<>("NAME");
//
//        idColumn.setMinWidth(20);
//        nameColumn.setMinWidth(130);
//
//        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
//        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
//        warehouse.getColumns().addAll(idColumn,nameColumn);

//        btn.setOnMouseClicked(new EventHandler<MouseEvent>(){
//            @Override
//            public void handle(MouseEvent event){
//                //get select order id
//
//                Order o = (Order) order_table.getSelectionModel().getSelectedItem();
//                if(o != null){
//                    //change page
//                    orderDetailController.setOrder_id(o.getId());
//                    pageController.active("orderDetail");
//                }
//            }
//        });



    }

    @Override
    public void onActive() {
        ObservableList<Order> orders = getOrder();
        warehouse.setItems(orders);
    }

//    public ObservableList<Order> getOrder(){
//        ObservableList<Order> orders = FXCollections.observableArrayList();
//        orders.add(new Order(1,"Por_shop1"));
//        orders.add(new Order(2,"Por_shop2"));
//        orders.add(new Order(3,"Por_shop3"));
//        return orders;
//    }

//    public ObservableList<OrderDetail> getOrderDetail(ObservableList<Order> orders){
//        ObservableList<OrderDetail> ordersDetail = FXCollections.observableArrayList();
//        orders.forEach(order->{
//
//            ordersDetail.add(new OrderDetail("A",45));System.out.print(order.getId());
//        });
//        return ordersDetail;
//    }
}