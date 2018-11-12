package report;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.StageStyle;
import sample.Controller;
import sample.PageController;
import sample.Product;
import report.reportMainController;


import java.util.Date;
import java.util.Optional;

public class reportController implements Controller {
    PageController pageController;
    private TableView reportTable;
    private Report report;
    private ObservableList<Product> reports = getOrder();
    private ObservableList<Product> subEntries;

    public ObservableList<Product> getOrder() {
        reports = FXCollections.observableArrayList();
        reports.add(new Product(1, 1, "A", "g"));
        reports.add(new Product(2, 2, "B", "t"));
        reports.add(new Product(3, 22, "C", "h"));
        reports.add(new Product(5, 1, "D", "f"));


        return reports;
    }

    public reportController(PageController pageController) {
        this.pageController = pageController;
    }

    public void initilize() {
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
//yah
        TableColumn<Product, Integer> idCol = new TableColumn("ID");
        idCol.setMinWidth(100);
        idCol.setCellValueFactory(
                new PropertyValueFactory<>("id"));

        TableColumn<Product, String> nameCol = new TableColumn("Name");
        nameCol.setMinWidth(270);
        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("Name"));

        TableColumn<Product, String> brandCol = new TableColumn("Brand");
        brandCol.setMinWidth(270);
        brandCol.setCellValueFactory(
                new PropertyValueFactory<>("brand"));

        TableColumn<Product, Double> quanCol = new TableColumn("Quantity");
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
    public Report getReport() {
        return report;
    }


    public void setReport(Report report) {
        this.report = report;
    }
}