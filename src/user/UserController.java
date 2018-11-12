package user;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sample.Controller;
import sample.PageController;
import sample.Product;

public class UserController implements Controller{
    PageController pageController;
    private TableView userTable;
    private ObservableList<Product> users = getOrder();
    private ObservableList<Product> subEntries;

    public ObservableList<Product> getOrder(){
        users = FXCollections.observableArrayList();

        users.add(new Product(1,2,"B","t"));
        users.add(new Product(2,22,"C","h"));
        users.add(new Product(3,1,"D","f"));
        users.add(new Product(4,1,"E","y"));
        users.add(new Product(5,1,"F","i"));
        users.add(new Product(8,1,"G","d"));
        users.add(new Product(9,1,"H","q"));


        return users;
    }

    public UserController(PageController pageController){
        this.pageController = pageController;
    }

    public void initilize(){
        Scene scene = pageController.getScene("userPage");
        Button mainBt = (Button) scene.lookup("#mainButton");
        Button summeryBt = (Button) scene.lookup("#summeryButton");

        Button orderBt = (Button) scene.lookup("#orderButton");
        Button userSearchBt = (Button) scene.lookup("#userSearchButton");
        TextField searchBox = (TextField) scene.lookup("#searchBox");

        searchBox.setPromptText("Search");
        searchBox.textProperty().addListener((observable, oldVal, newVal) -> {
            handleSearchByKey((String) oldVal, (String) newVal);
        });

        userTable = (TableView) scene.lookup("#userList");

        TableColumn <Product,Integer> idCol = new TableColumn("ID");
        idCol.setMinWidth(100);
        idCol.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        TableColumn <Product,String> nameCol = new TableColumn("Name");
        nameCol.setMinWidth(390);
        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("Name"));

        TableColumn <Product,String> brandCol = new TableColumn("Phone Number");
        brandCol.setMinWidth(250);
        brandCol.setCellValueFactory(
                new PropertyValueFactory<>("brand"));

        TableColumn <Product, Double> quanCol = new TableColumn("Role");
        quanCol.setMinWidth(50);
        quanCol.setCellValueFactory(
                new PropertyValueFactory<>("quantity"));

        userTable.getColumns().addAll(idCol,nameCol,brandCol,quanCol);
        mainBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pageController.active("productList");
            }
        });
        summeryBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pageController.active("report");

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
            public void handle(MouseEvent event) {
                pageController.active("user");
            }
        });

    }

    public void onActive() {
        userTable.setItems(users);
    }
    public void handleSearchByKey(String oldValue, String newValue) {
        if ( oldValue != null && (newValue.length() < oldValue.length()) ) {
            userTable.setItems(users);
        }

        String[] parts = newValue.toUpperCase().split(" ");

        subEntries = FXCollections.observableArrayList();
        for ( Object entry: userTable.getItems() ) {
            boolean match = true;
            Product entryP = (Product) entry;
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
        userTable.setItems(subEntries);
    }

}
