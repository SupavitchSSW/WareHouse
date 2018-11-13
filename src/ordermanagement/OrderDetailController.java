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
import sample.PageController;
import product.Product;
import sample.Transaction;
import user.User;

import java.util.Date;
import java.util.Optional;
import java.util.function.Consumer;


public class OrderDetailController implements Controller {
    PageController pageController;
    private Order order;
    private TableView detail_table;
    private TextField orderName_TextField,orderOwner_TextField,orderDate_TextField,status_TextField;
    private ObservableList<OrderProduct> orderProducts;
    private User currentUser;
    private serviceDB database;

    public OrderDetailController(PageController pageController,serviceDB database, User currentUser) {
        this.pageController = pageController;
        this.currentUser = currentUser;
        this.database = database;
    }

    @Override
    public void initilize() {

        Scene scene = pageController.getScene("orderDetail");

        Button mainBt = (Button) scene.lookup("#mainButton");
        Button summaryBt = (Button) scene.lookup("#summaryButton");
        Button orderBt = (Button) scene.lookup("#orderButton");
        Button logoutBt = (Button) scene.lookup("#logoutButton");
        Button userSearchBt = (Button) scene.lookup("#userSearchButton");
        Button userInfoBt = (Button) scene.lookup("#userInfo");
        //set button
        Button goBack_btn = (Button) scene.lookup("#goBack");
        Button approve_btn = (Button) scene.lookup("#approve_btn");
        Button reject_btn = (Button) scene.lookup("#reject_btn");

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

        TableColumn<OrderProduct,Integer> idColumn = new TableColumn<>("Product ID");
        TableColumn<OrderProduct,String> nameColumn = new TableColumn<>("Product NAME");
        TableColumn<OrderProduct,Integer> brandColumn = new TableColumn<>("BLAND");
        TableColumn<OrderProduct,Integer> orderQuantityColumn = new TableColumn<>("ORDER");
        TableColumn<OrderProduct,Integer> warehouseQuantityColumn = new TableColumn<>("QUOTA");
        TableColumn<OrderProduct,Integer> sendQuantityColumn = new TableColumn<>("SEND");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        orderQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("orderQuantity"));
        warehouseQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        sendQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("sendQuantity"));

        //check order status
        if(order.getStatus().equals("waiting")){
            sendQuantityColumn.setEditable(true);
            approve_btn.setDisable(false);
            reject_btn.setDisable(false);
        }else{
            sendQuantityColumn.setEditable(false);
            approve_btn.setDisable(true);
            reject_btn.setDisable(true);
        }
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
                    //change order status
                    database.setOrderStatus(order.getId(),"approve");

                    //create transaction
                    Date date = new Date();
                    for ( Object entry: detail_table.getItems() ) {
                        OrderProduct orderProduct = (OrderProduct) entry;
                        database.createTransaction(orderProduct.getProductId(),orderProduct.getSendQuantity()*-1,date,"approveOrder");
                    }

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
                    database.setOrderStatus(order.getId(),"reject");


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
            detail_table.setItems(orderProducts);
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


    private void getOrderProductList(){
        //get order product from order
        orderProducts = FXCollections.observableArrayList(order.getOrderProducts());
        for ( OrderProduct entry: orderProducts ) {
            entry.setQuantity(database.getQtbyID(entry.getProductId()));
        }
    }

    @Override
    public void onActive() {
        System.out.println("display detail order ID : "+order.getId());
        orderName_TextField.setText(order.getName());
        orderOwner_TextField.setText(order.getOwner());
        orderDate_TextField.setText(order.getDate().toLocaleString());
        status_TextField.setText(order.getStatus());

        getOrderProductList();
        //get OrderProduct list in order
        detail_table.setItems(orderProducts);
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
