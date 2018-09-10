package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;


public class OrderController implements Controller{
    PageController pageController;

    public OrderController(PageController pageController){
        this.pageController = pageController;
        initilize();
    }


    public void initilize(){
        Scene scene = pageController.getScene("orderManagement");
        Button btn = (Button) scene.lookup("#switchScene");

        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event){
                pageController.active("sample");
            }
        });


        TableView order_table = (TableView) scene.lookup("#order_table");
        TableView orderDetail_table = (TableView) scene.lookup("#orderDetail_table");

        TableColumn<Order,Integer> idColumn = new TableColumn<>("ID");
        TableColumn<Order,String> nameColumn = new TableColumn<>("NAME");


        idColumn.setMinWidth(20);
        nameColumn.setMinWidth(130);



        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));


        ObservableList<Order> orders = getOrder();


        order_table.getColumns().addAll(idColumn,nameColumn);
        order_table.setItems(orders);

//        TableColumn<Order,String> itemColumn = new TableColumn<>("Item");
//        TableColumn<Order,String> numColumn = new TableColumn<>("Num");
//        itemColumn.setMinWidth(200);
//        numColumn.setMinWidth(50);
//        itemColumn.setCellValueFactory(new PropertyValueFactory<>("item"));
//        numColumn.setCellValueFactory(new PropertyValueFactory<>("num"));
//        ObservableList<OrderDetail> ordersDetail = getOrderDetail(orders);
//        orderDetail_table.getColumns().addAll(itemColumn,numColumn);
//        order_table.setItems(ordersDetail);
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