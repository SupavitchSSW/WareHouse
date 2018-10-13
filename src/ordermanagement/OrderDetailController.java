package ordermanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import ordermanagement.Order;
import sample.Controller;
import sample.PageController;
import sample.Product;

import java.util.Optional;


public class OrderDetailController implements Controller {
    PageController pageController;
    int order_id;
    Order order = new Order(50,"12356479","por");
    TableView detail_table;
    ObservableList<OrderProduct> orderProducts = getOrderProductList();

    public OrderDetailController(PageController pageController) {
        this.pageController = pageController;
    }

    @Override
    public void initilize() {

        Scene scene = pageController.getScene("orderDetail");

        //search setup
        TextField search_TextField = (TextField) scene.lookup("#searchBox");
        search_TextField.textProperty().addListener((observable, oldVal, newVal) -> {
            handleSearchByKey((String) oldVal, (String) newVal);
        });


        //text box
        TextField orderName_TextField = (TextField) scene.lookup("#orderName");
        orderName_TextField.setEditable(false);
        orderName_TextField.setText(order.getName());

        TextField orderOwner_TextField = (TextField) scene.lookup("#orderOwner");
        orderOwner_TextField.setEditable(false);
        orderOwner_TextField.setText(order.getOwner());

        TextField orderDate_TextField  = (TextField) scene.lookup("#orderDate");
        orderDate_TextField.setEditable(false);
        orderDate_TextField.setText(order.getDate().toLocaleString());


        //table setup
        detail_table = (TableView) scene.lookup("#detail_table");

        TableColumn<Product,Integer> idColumn = new TableColumn<>("Product ID");
        TableColumn<Product,String> nameColumn = new TableColumn<>("Product NAME");
        TableColumn<Product,Integer> brandColumn = new TableColumn<>("BLAND");
        TableColumn<Product,Integer> orderQuantityColumn = new TableColumn<>("ORDER QUANTITY");
        TableColumn<Product,Integer> warehouseQuantityColumn = new TableColumn<>("WAREHOUSE QUANTITY");


        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        orderQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("orderQuantity"));
        warehouseQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        detail_table.getColumns().addAll(idColumn,nameColumn,brandColumn,orderQuantityColumn,warehouseQuantityColumn);


        //get OrderProduct list in order
        ObservableList<OrderProduct> orderProducts = getOrderProductList();
        detail_table.setItems(orderProducts);


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

        approve_btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Approve this order ?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    // ... user chose OK
                    System.out.println("approve order id : "+getOrder_id());
                    pageController.active("orderList");
                } else {
                    // ... user chose CANCEL or closed the dialog
                }
            }
        });

        reject_btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation Dialog");
                alert.setHeaderText(null);
                alert.setContentText("Reject this order ?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){
                    // ... user chose OK
                    System.out.println("reject order id : "+getOrder_id());
                    pageController.active("orderList");
                } else {
                    // ... user chose CANCEL or closed the dialog
                }
            }
        });
    }

    private void handleSearchByKey(String oldValue, String newValue) {
        if ( oldValue != null && (newValue.length() < oldValue.length()) ) {
            detail_table.setItems(orderProducts);
        }

        String[] parts = newValue.toUpperCase().split(" ");

        ObservableList<OrderProduct> subEntries = FXCollections.observableArrayList();
        for ( Object entry: detail_table.getItems() ) {
            boolean match = true;
            OrderProduct entryP = (OrderProduct) entry;
            String detailEntryP = entryP.getId()+entryP.getName().toUpperCase()+entryP.getBrand().toUpperCase();
            for ( String part: parts ) {
                if ( ! detailEntryP.contains(part) ) {
                    match = false;
                    break;
                }
            }

            if ( match ) {
                subEntries.add(entryP);
            }
        }
        detail_table.setItems(subEntries);
    }


    private ObservableList<OrderProduct> getOrderProductList(){
        ObservableList<OrderProduct> orderProducts = FXCollections.observableArrayList();
        orderProducts.add(new OrderProduct(11,1000,"beer","leo",30));
        orderProducts.add(new OrderProduct(10,5000,"coockie","m&m",20));
        return orderProducts;
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
