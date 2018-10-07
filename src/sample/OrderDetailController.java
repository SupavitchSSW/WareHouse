package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.util.ArrayList;

public class OrderDetailController implements Controller{
    PageController pageController;
    int order_id;
    Order order = new Order(50,"cookie");

    public OrderDetailController(PageController pageController) {
        this.pageController = pageController;
    }

    @Override
    public void initilize() {

        Scene scene = pageController.getScene("orderDetail");


        //text box
        TextField orderOwner_TextFielc = (TextField) scene.lookup("#orderOwner");
        orderOwner_TextFielc.setEditable(false);
        orderOwner_TextFielc.setText("Por-shop");

        TextField orderDate_TextField  = (TextField) scene.lookup("#orderDate");
        orderDate_TextField.setEditable(false);
        orderDate_TextField.setText("12/12/15");


        //table setup
        TableView detail_table = (TableView) scene.lookup("#detail_table");

        TableColumn<Product,Integer> idColumn = new TableColumn<>("Product ID");
        TableColumn<Product,String> nameColumn = new TableColumn<>("Product NAME");
        TableColumn<Product,Integer> brandColumn = new TableColumn<>("BLAND");
        TableColumn<Product,Integer> quantityColumn = new TableColumn<>("QUANTITY");


        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        detail_table.getColumns().addAll(idColumn,nameColumn,brandColumn,quantityColumn);

        ObservableList<Product> products = getProductList(order);
        detail_table.setItems(products);

        //set button
        Button goBack_btn = (Button) scene.lookup("#goBack");
        Button approve_btn = (Button) scene.lookup("#approve_btn");
        Button reject_btn = (Button) scene.lookup("#reject_btn");

        goBack_btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event){
                pageController.active("orderList");
            }
        });




    }

    private ObservableList<Product> getProductList(Order order) {
        //return order.getProducts();
        ObservableList<Product> products = FXCollections.observableArrayList();
        products.add(new Product(51,20,"cookie","m&m"));
        products.add(new Product(12,11,"bomb","aka"));

        return products;
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
