package sample;

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


public class OrderListController implements Controller{
    PageController pageController;
    OrderDetailController orderDetailController;

    public OrderListController(PageController pageController,OrderDetailController orderDetailController){
        this.pageController = pageController;
        this.orderDetailController = orderDetailController;
    }


    public void initilize(){
        Scene scene = pageController.getScene("orderList");
        Button btn = (Button) scene.lookup("#switchScene");


        //table setup
        TableView order_table = (TableView) scene.lookup("#order_table");

        TableColumn<Order,Integer> idColumn = new TableColumn<>("ID");
        TableColumn<Order,String> nameColumn = new TableColumn<>("NAME");

        idColumn.setMinWidth(20);
        nameColumn.setMinWidth(130);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        order_table.getColumns().addAll(idColumn,nameColumn);

        ObservableList<Order> orders = getOrder();
        order_table.setItems(orders);

        btn.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                //get select order id

                Order o = (Order) order_table.getSelectionModel().getSelectedItem();
                if(o != null){
                    //change page
                    orderDetailController.setOrder_id(o.getId());
                    pageController.active("orderDetail");
                }
            }
        });



    }

    @Override
    public void onActive() {

    }

    public ObservableList<Order> getOrder(){
        ObservableList<Order> orders = FXCollections.observableArrayList();
        orders.add(new Order(1,"Por_shop1"));
        orders.add(new Order(2,"Por_shop2"));
        orders.add(new Order(3,"Por_shop3"));
        return orders;
    }

//    public ObservableList<OrderDetail> getOrderDetail(ObservableList<Order> orders){
//        ObservableList<OrderDetail> ordersDetail = FXCollections.observableArrayList();
//        orders.forEach(order->{
//
//            ordersDetail.add(new OrderDetail("A",45));System.out.print(order.getId());
//        });
//        return ordersDetail;
//    }
}
//
//class OrderDetail extends Order{
//    String item;
//    int num;
//
//    public OrderDetail(String item, int num) {
//        this.item = item;
//        this.num = num;
//    }
//
//    public String getItem() {
//        return item;
//    }
//
//    public void setItem(String item) {
//        this.item = item;
//    }
//
//    public int getNum() {
//        return num;
//    }
//
//    public void setNum(int num) {
//        this.num = num;
//    }
//}