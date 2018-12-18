package Presentation;

import Logic.OrderController;
import Storage.Order;
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
import Logic.Controller;
import Logic.PageController;
import Storage.Staff;

import java.io.IOException;


public class OrderListUI implements Controller {
    PageController pageController;
    private ObservableList<Order> orders;
    private TableView order_table;
    private Button updateBt,productListBt,userSearchBt,summaryBt;
    private OrderController orderController;

    public OrderListUI(PageController pageController, OrderController orderController) {
        this.pageController = pageController;
        this.orderController = orderController;
    }


    public void initilize() {
        Scene scene = pageController.getScene("orderList");
        Button nextBtn = (Button) scene.lookup("#nextBtn");
        Button mainBt = (Button) scene.lookup("#mainButton");
        summaryBt = (Button) scene.lookup("#summaryButton");
        Button orderBt = (Button) scene.lookup("#orderButton");
        Button logoutBt = (Button) scene.lookup("#logoutButton");
        userSearchBt = (Button) scene.lookup("#userSearchButton");
        Button userInfoBt = (Button) scene.lookup("#userInfo");
        updateBt = (Button) scene.lookup("#updateBtn");
        productListBt = (Button) scene.lookup("#productListBtn");
        TextField search_TextField = (TextField) scene.lookup("#searchBox");

        //search setup
        search_TextField.textProperty().addListener((observable, oldVal, newVal) -> {
            handleSearchByKey((String) oldVal, (String) newVal);
        });

        //table setup
        order_table = (TableView) scene.lookup("#order_table");

        // column name
        TableColumn<Order, Integer> idColumn = new TableColumn<>("ID");
        TableColumn<Order, String> nameColumn = new TableColumn<>("NAME");
        TableColumn<Order, String> ownerColumn = new TableColumn<>("OWNER");
        TableColumn<Order, String> statusColumn = new TableColumn<>("STATUS");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<>("owner"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("status"));
        order_table.getColumns().addAll(idColumn, nameColumn,ownerColumn,statusColumn);

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

        logoutBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) { pageController.active("login"); }
        });

        userSearchBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) { pageController.active("user"); }
        });

        userInfoBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) { pageController.active("profile"); }
        });

        nextBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                //get select order id

                Order o = (Order) order_table.getSelectionModel().getSelectedItem();
                if (o != null) {
                    //change page
                    orderController.setSelectOrder(o);
                    pageController.active("orderDetail");
                }
            }
        });

        updateBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("read order file");
                try {
                    orderController.readOrder();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                pageController.active("orderList");
            }
        });

        productListBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("write product list");
                try {
                    orderController.writeProductList();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

    }

    private void handleSearchByKey(String oldValue, String newValue) {
        if ( oldValue != null && (newValue.length() < oldValue.length()) ) {
            order_table.setItems(orders);
        }

        String[] parts = newValue.toUpperCase().split(" ");

        ObservableList<Order> subEntries = FXCollections.observableArrayList();
        for ( Object entry: order_table.getItems() ) {
            boolean match = true;
            Order entryP = (Order) entry;
            String detailEntryP = entryP.getId()+entryP.getName().toUpperCase()+entryP.getStatus();
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
        order_table.setItems(subEntries);
    }

    @Override
    public void onActive() {
        orders = orderController.getAllOrder();
        order_table.setItems(orders);
        order_table.refresh();
        if(orderController.getCurrentUser() instanceof Staff){
            updateBt.setDisable(true);
            productListBt.setDisable(true);
            summaryBt.setDisable(true);
            userSearchBt.setDisable(true);
        }else{
            updateBt.setDisable(false);
            productListBt.setDisable(false);
            summaryBt.setDisable(false);
            userSearchBt.setDisable(false);
        }
    }
}