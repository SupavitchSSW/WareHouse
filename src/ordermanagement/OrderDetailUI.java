package ordermanagement;

import connectionDB.serviceDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.util.converter.IntegerStringConverter;
import ordermanagement.Order;
import sample.Controller;
import sample.OrderReadWrite;
import sample.PageController;
import product.Product;
import sample.Transaction;
import user.Staff;
import user.User;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;


public class OrderDetailUI implements Controller {
    PageController pageController;
    private Order order;
    private TableView detail_table;
    private TextField orderName_TextField,orderOwner_TextField,orderDate_TextField,status_TextField;
    private Button goBack_btn,approve_btn,reject_btn,userSearchBt,summaryBt;
    private TableColumn<OrderProduct,Integer> sendQuantityColumn;
    private OrderController orderController;

    public OrderDetailUI(PageController pageController,OrderController orderController) {
        this.pageController = pageController;
        this.orderController = orderController;
    }

    @Override
    public void initilize() {

        Scene scene = pageController.getScene("orderDetail");

        Button mainBt = (Button) scene.lookup("#mainButton");
        summaryBt = (Button) scene.lookup("#summaryButton");
        Button orderBt = (Button) scene.lookup("#orderButton");
        Button logoutBt = (Button) scene.lookup("#logoutButton");
        userSearchBt = (Button) scene.lookup("#userSearchButton");
        Button userInfoBt = (Button) scene.lookup("#userInfo");
        //set button
        goBack_btn = (Button) scene.lookup("#goBack");
        approve_btn = (Button) scene.lookup("#approve_btn");
        reject_btn = (Button) scene.lookup("#reject_btn");

        //search setup
        TextField search_TextField = (TextField) scene.lookup("#searchBox");
        search_TextField.textProperty().addListener((observable, oldVal, newVal) -> {
            handleSearchByKey((String) oldVal, (String) newVal);
        });


        //text box
        orderName_TextField = (TextField) scene.lookup("#orderName");
        orderName_TextField.setEditable(false);

        orderOwner_TextField = (TextField) scene.lookup("#orderOwner");
        orderOwner_TextField.setEditable(false);

        orderDate_TextField  = (TextField) scene.lookup("#orderDate");
        orderDate_TextField.setEditable(false);

        status_TextField  = (TextField) scene.lookup("#status");
        status_TextField.setEditable(false);


        //table setup
        detail_table = (TableView) scene.lookup("#detail_table");
        detail_table.setEditable(true);

        TableColumn<OrderProduct,Integer> idColumn = new TableColumn<>("ID");
        TableColumn<OrderProduct,String> nameColumn = new TableColumn<>("NAME");
        TableColumn<OrderProduct,Integer> brandColumn = new TableColumn<>("BRAND");
        TableColumn<OrderProduct,Integer> orderQuantityColumn = new TableColumn<>("ORDER");
        TableColumn<OrderProduct,Integer> warehouseQuantityColumn = new TableColumn<>("QUANTITY");
        sendQuantityColumn = new TableColumn<>("SEND");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        orderQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("orderQuantity"));
        warehouseQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        sendQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("sendQuantity"));

        //set send cell
        sendQuantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        sendQuantityColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<OrderProduct, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<OrderProduct, Integer> event) {
                OrderProduct o = (OrderProduct) event.getTableView().getItems().get(event.getTablePosition().getRow());
                if(event.getNewValue() > o.getQuantity() || event.getNewValue() < 0){
                    System.out.println("Error input");
                    detail_table.refresh();
                }else{
                    System.out.println(o.getName() +" : "+event.getNewValue());
                    o.setSendQuantity(event.getNewValue());
                }
            }
        });


        detail_table.getColumns().addAll(idColumn,nameColumn,brandColumn,orderQuantityColumn,warehouseQuantityColumn,sendQuantityColumn);


        goBack_btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event){
                pageController.active("orderList");
            }
        });
        mainBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pageController.active("productList");
            }
        });
        summaryBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {pageController.active("reportMain");

            }
        });
        orderBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pageController.active("orderList");
            }
        });
        userSearchBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) { pageController.active("user"); }
        });
        logoutBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) { pageController.active("login"); }
        });
        userInfoBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) { pageController.active("profile"); }
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
                    orderController.acceptOrder();
                    pageController.active("orderList");
                } else {
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
                    //change order status
                    orderController.rejectOrder();
                    System.out.println("reject order id : "+order.getId());
                    pageController.active("orderList");
                } else {
                    // ... user chose CANCEL or closed the dialog
                }
            }
        });
    }

    private void handleSearchByKey(String oldValue, String newValue) {
        if ( oldValue != null && (newValue.length() < oldValue.length()) ) {
            detail_table.setItems(FXCollections.observableArrayList(order.getOrderProducts()));
        }

        String[] parts = newValue.toUpperCase().split(" ");

        ObservableList<OrderProduct> subEntries = FXCollections.observableArrayList();
        for ( Object entry: detail_table.getItems() ) {
            boolean match = true;
            OrderProduct entryP = (OrderProduct) entry;
            String detailEntryP = entryP.getProductId()+entryP.getName().toUpperCase()+entryP.getBrand().toUpperCase();
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

    @Override
    public void onActive() {
        //set order
        order = orderController.getSelectOrder();
        System.out.println("display detail order ID : "+order.getId());
        //set text field
        orderName_TextField.setText(order.getName());
        orderOwner_TextField.setText(order.getOwner());
        orderDate_TextField.setText(order.getDate().toLocaleString());
        status_TextField.setText(order.getStatus());

        //check order status
        if(orderController.getCurrentUser() instanceof Staff || !order.getStatus().equals("waiting")){
            System.out.println("status = "+order.getStatus());
            sendQuantityColumn.setEditable(false);
            approve_btn.setDisable(true);
            reject_btn.setDisable(true);
            summaryBt.setDisable(true);
            userSearchBt.setDisable(true);
        }else{
            System.out.println("status = "+order.getStatus());
            sendQuantityColumn.setEditable(true);
            approve_btn.setDisable(false);
            reject_btn.setDisable(false);
            summaryBt.setDisable(false);
            userSearchBt.setDisable(false);
        }

        //get OrderProduct list in order
        detail_table.setItems(FXCollections.observableArrayList(order.getOrderProducts()));

        //check order status for disable button
        if(order.getStatus().equals("waiting")){
            System.out.println("status = waiting");
            sendQuantityColumn.setEditable(true);
            approve_btn.setDisable(false);
            reject_btn.setDisable(false);
        }else{
            System.out.println("status = "+order.getStatus());
            sendQuantityColumn.setEditable(false);
            approve_btn.setDisable(true);
            reject_btn.setDisable(true);
        }
    }

//    public Order getOrder() {
//        return order;
//    }
//
//    public void setOrder(Order order) {
//        this.order = order;
//    }
}
