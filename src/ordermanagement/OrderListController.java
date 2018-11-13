package ordermanagement;

import connectionDB.serviceDB;
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
import ordermanagement.Order;
import ordermanagement.OrderDetailController;
import sample.Controller;
import sample.PageController;
import user.User;


public class OrderListController implements Controller {
    PageController pageController;
    OrderDetailController orderDetailController;
    private ObservableList<Order> orders;
    private TableView order_table;
    private User currentUser;
    private serviceDB database;

    public OrderListController(PageController pageController,serviceDB database, OrderDetailController orderDetailController,User currentUser) {
        this.currentUser = currentUser;
        this.pageController = pageController;
        this.orderDetailController = orderDetailController;
        this.database = database;
    }


    public void initilize() {
        Scene scene = pageController.getScene("orderList");
        Button nextBtn = (Button) scene.lookup("#nextBtn");
        Button mainBt = (Button) scene.lookup("#mainButton");
        Button summaryBt = (Button) scene.lookup("#summaryButton");
        Button orderBt = (Button) scene.lookup("#orderButton");
        Button logoutBt = (Button) scene.lookup("#logoutButton");
        Button userSearchBt = (Button) scene.lookup("#userSearchButton");
        Button userInfoBt = (Button) scene.lookup("#userInfo");
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

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        ownerColumn.setCellValueFactory(new PropertyValueFactory<>("owner"));
        order_table.getColumns().addAll(idColumn, nameColumn,ownerColumn);

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
                    orderDetailController.setOrder(o);
                    pageController.active("orderDetail");
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
            String detailEntryP = entryP.getId()+entryP.getName().toUpperCase()+entryP.getDate()+entryP.getStatus();
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
        orders = getOrder();
        order_table.setItems(orders);
    }

    public ObservableList<Order> getOrder() {
        ObservableList<Order> orders = FXCollections.observableArrayList(database.getAllOrder());
        return orders;
    }
}