package ordermanagement;

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
import sample.Product;

import java.util.Optional;


public class OrderDetailController implements Controller {
    PageController pageController;
    private Order order;
    private TableView detail_table;
    private TextField orderName_TextField,orderOwner_TextField,orderDate_TextField;
    private ObservableList<OrderProduct> orderProducts;

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
        orderName_TextField = (TextField) scene.lookup("#orderName");
        orderName_TextField.setEditable(false);

        orderOwner_TextField = (TextField) scene.lookup("#orderOwner");
        orderOwner_TextField.setEditable(false);

        orderDate_TextField  = (TextField) scene.lookup("#orderDate");
        orderDate_TextField.setEditable(false);


        //table setup
        detail_table = (TableView) scene.lookup("#detail_table");
        detail_table.setEditable(true);

        TableColumn<Product,Integer> idColumn = new TableColumn<>("Product ID");
        TableColumn<Product,String> nameColumn = new TableColumn<>("Product NAME");
        TableColumn<Product,Integer> brandColumn = new TableColumn<>("BLAND");
        TableColumn<Product,Integer> orderQuantityColumn = new TableColumn<>("ORDER");
        TableColumn<Product,Integer> warehouseQuantityColumn = new TableColumn<>("QUOTA");
        TableColumn<Product,Integer> sendQuantityColumn = new TableColumn<>("SEND");

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        orderQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("orderQuantity"));
        warehouseQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        sendQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("sendQuantity"));

        //set send cell
        sendQuantityColumn.setEditable(true);
        sendQuantityColumn.setCellFactory(TextFieldTableCell.forTableColumn(new IntegerStringConverter()));
        sendQuantityColumn.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent<Product, Integer>>() {
            @Override
            public void handle(TableColumn.CellEditEvent<Product, Integer> event) {
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
                    System.out.println("approve order id : "+order.getId());
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


    private void getOrderProductList(){
        //get order product from order
        orderProducts = order.getOrderProducts();

        //============================================================================
        //           (TODO) set current warehouse quantity to each OrderProduct
        //============================================================================

        for(OrderProduct orderProduct:orderProducts){
            orderProduct.setQuantity(500);
        }

    }

    @Override
    public void onActive() {
        System.out.println("display detail order ID : "+order.getId());
        orderName_TextField.setText(order.getName());
        orderOwner_TextField.setText(order.getOwner());
        orderDate_TextField.setText(order.getDate().toLocaleString());

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
