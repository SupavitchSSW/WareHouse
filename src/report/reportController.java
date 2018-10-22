package report;


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

public class reportController implements Controller{
    PageController pageController;
    private TableView reportTable;
    private ObservableList<Product> reports = getOrder();
    private ObservableList<Product> subEntries;

    public ObservableList<Product> getOrder(){
        reports = FXCollections.observableArrayList();
        reports.add(new Product(1,1,"A","ก"));
        reports.add(new Product(2,2,"B","ข"));
        reports.add(new Product(3,22,"C","ค"));
        reports.add(new Product(5,1,"D","ง"));
        reports.add(new Product(6,1,"E","ฉ"));
        reports.add(new Product(7,1,"F","ฉ"));
        reports.add(new Product(8,1,"G","ช"));
        reports.add(new Product(9,1,"H","ซ"));
        reports.add(new Product(10,150,"I","ฆ"));
        reports.add(new Product(11,1,"G","ณ"));
        reports.add(new Product(12,1,"K","ญ"));
        reports.add(new Product(13,1,"L","ฎ"));
        reports.add(new Product(14,1,"M","ฏ"));
        reports.add(new Product(15,1,"N","ฐ"));
        return reports;
    }

    public reportController(PageController pageController){
        this.pageController = pageController;
    }

    public void initilize(){
        Scene scene = pageController.getScene("report");
        Button mainBt = (Button) scene.lookup("#mainButton");
        Button reportBt = (Button) scene.lookup("#reportButton");
        Button orderBt = (Button) scene.lookup("#orderButton");
        Button userSearchBt = (Button) scene.lookup("#userSearchButton");
        TextField searchBox = (TextField) scene.lookup("#searchBox");

        searchBox.setPromptText("Search");
        searchBox.textProperty().addListener((observable, oldVal, newVal) -> {
            handleSearchByKey((String) oldVal, (String) newVal);
        });

        reportTable = (TableView) scene.lookup("#report");

        TableColumn <Product,Integer> idCol = new TableColumn("ID");
        idCol.setMinWidth(100);
        idCol.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        TableColumn <Product,String> nameCol = new TableColumn("Name");
        nameCol.setMinWidth(270);
        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("Name"));

        TableColumn <Product,String> brandCol = new TableColumn("Brand");
        brandCol.setMinWidth(270);
        brandCol.setCellValueFactory(
                new PropertyValueFactory<>("brand"));

        TableColumn <Product, Double> quanCol = new TableColumn("Quantity");
        quanCol.setMinWidth(150);
        quanCol.setCellValueFactory(
                new PropertyValueFactory<>("quantity"));

        reportTable.getColumns().addAll(idCol,nameCol,brandCol,quanCol);
        mainBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pageController.active("productList");
            }
        });
        reportBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {pageController.active("report");

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

            }
        });

    }

    public void onActive() {
        reportTable.setItems(reports);
    }
    public void handleSearchByKey(String oldValue, String newValue) {
        if ( oldValue != null && (newValue.length() < oldValue.length()) ) {
            reportTable.setItems(reports);
        }

        String[] parts = newValue.toUpperCase().split(" ");

        subEntries = FXCollections.observableArrayList();
        for ( Object entry: reportTable.getItems() ) {
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
        reportTable.setItems(subEntries);
    }

}
