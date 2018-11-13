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
import report.reportController;
import user.User;


import java.util.Optional;

public class reportMainController implements Controller {
    PageController pageController;
    private TableView reportTable;
    reportController reportController;
    private ObservableList<Product> reports = getOrder();
    private ObservableList<Product> subEntries;
    private User currentUser;

    private Product selectedProduct;
    private int index;

    public reportMainController(PageController pageController, reportController reportController,User currentUser) {
        this.pageController = pageController;
        this.reportController = reportController;
        this.currentUser = currentUser;
    }


    public ObservableList<Product> getOrder() {
        reports = FXCollections.observableArrayList();
        reports.add(new Product("A"));
        reports.add(new Product("B"));
        reports.add(new Product("V"));
        reports.add(new Product("D"));
        reports.add(new Product("G"));


        return reports;
    }


    public void initilize() {
        Scene scene = pageController.getScene("reportMain");
        Button mainBt = (Button) scene.lookup("#mainButton");
        Button reportBt = (Button) scene.lookup("#summaryButton");
        Button orderBt = (Button) scene.lookup("#orderButton");
        Button userSearchBt = (Button) scene.lookup("#userSearchButton");
        Button logoutBt = (Button) scene.lookup("#logoutButton");
        Button userInfoBt = (Button) scene.lookup("#userInfo");
        TextField searchBox = (TextField) scene.lookup("#searchBox");


        searchBox.setPromptText("Search");
        searchBox.textProperty().addListener((observable, oldVal, newVal) -> {
            handleSearchByKey((String) oldVal, (String) newVal);
        });

        reportTable = (TableView) scene.lookup("#reportMain");
        TableColumn<Product, String> dateCol = new TableColumn("Date");
        dateCol.setMinWidth(270);
        dateCol.setCellValueFactory( new PropertyValueFactory<>("date"));


        reportTable.getColumns().addAll(dateCol);



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
        logoutBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) { pageController.active("login"); }
        });
        userInfoBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) { pageController.active("profile"); }
        });
        reportTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    index = reportTable.getSelectionModel().getSelectedIndex();
                    pageController.active("report");
                }
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
            String detailEntryP = entryP.getDate();
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
