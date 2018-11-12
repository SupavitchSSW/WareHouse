package ordermanagement;

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


public class OrderListController implements Controller {
    PageController pageController;
    OrderDetailController orderDetailController;
    private ObservableList<Order> orders = getOrder();
    private TableView order_table;

    public OrderListController(PageController pageController, OrderDetailController orderDetailController) {
        this.pageController = pageController;
        this.orderDetailController = orderDetailController;
    }


    public void initilize() {
        Scene scene = pageController.getScene("orderList");
        Button nextBtn = (Button) scene.lookup("#nextBtn");
        Button backBtn = (Button) scene.lookup("#backBtn");
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

        backBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pageController.active("productList");
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
            String detailEntryP = entryP.getId()+entryP.getName().toUpperCase()+entryP.getDate();
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
        ObservableList<Order> orders = getOrder();
        order_table.setItems(orders);
    }

    public ObservableList<Order> getOrder() {
        ObservableList<Order> orders = FXCollections.observableArrayList();

        Order o = new Order(1, "Por_shop1","por");
        o.addOrderProduct(new OrderProduct(10,"beer","leo",50));
        o.addOrderProduct(new OrderProduct(11,"vodka","tom",20));
        orders.add(o);

        o = new Order(3, "Por_shop2","por");
        o.addOrderProduct(new OrderProduct(102,"cookie","m&m",5));
        o.addOrderProduct(new OrderProduct(1,"water","KMITL",200));
        orders.add(o);

        o = new Order(5, "Por_shop3","por");
        o.addOrderProduct(new OrderProduct(102,"cookie","m&m",5));
        o.addOrderProduct(new OrderProduct(1,"water","KMITL",200));
        o.addOrderProduct(new OrderProduct(10,"beer","leo",50));
        o.addOrderProduct(new OrderProduct(11,"vodka","tom",20));
        orders.add(o);
        return orders;
    }
}