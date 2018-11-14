package report;


import connectionDB.serviceDB;
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
import sample.Transaction;
import user.User;
import javax.persistence.*;

import java.util.Date;
import java.util.List;

public class reportController implements Controller {
    PageController pageController;
    serviceDB database;
    private TableView reportTable;
    private Report report;
    private ObservableList<transaction.Transaction> reports = getAllTransactionInMonth(6);
    private ObservableList<Transaction> subEntries;
    private User currentUser;

    public reportController(PageController pageController, serviceDB database, User currentUser) {
        this.pageController = pageController;
        this.database = database;
        this.currentUser = currentUser;
    }

    public ObservableList<transaction.Transaction> getAllTransactionInMonth(int month){
//        database.createProduct("s","s",23,12);
//        database.createTransaction(1,2,new Date(),"dfd");
//        List<transaction.Transaction> t = database.getAllTransactionInMonth(10);
//        for (transaction.Transaction x:t){
//            System.out.println(x);
//        }
//        List<transaction.Transaction> b = database.getAllTransactionInMonth(11);
        ObservableList<transaction.Transaction> reports = FXCollections.observableArrayList();
        reports.add(new transaction.Transaction(1,1,new Date(),"ee"));

        return reports;
    }

    public void initilize() {
        Scene scene = pageController.getScene("report");
        Button mainBt = (Button) scene.lookup("#mainButton");
        Button reportBt = (Button) scene.lookup("#summaryButton");
        Button orderBt = (Button) scene.lookup("#orderButton");
        Button userSearchBt = (Button) scene.lookup("#userSearchButton");
        Button logoutBt = (Button) scene.lookup("#logoutButton");
        Button userInfoBt = (Button) scene.lookup("#userInfo");
        TextField searchBox = (TextField) scene.lookup("#searchBox");


//        searchBox.setPromptText("Search");
//        searchBox.textProperty().addListener((observable, oldVal, newVal) -> {
//            handleSearchByKey((String) oldVal, (String) newVal);
//        });

        reportTable = (TableView) scene.lookup("#report");
//yah
        TableColumn<Transaction, Integer> idCol = new TableColumn("Product ID");
        idCol.setMinWidth(100);
        idCol.setCellValueFactory(
                new PropertyValueFactory<>("productId"));

        TableColumn<Transaction, Integer> nameCol = new TableColumn("Changed Quantity");
        nameCol.setMinWidth(270);
        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("changedQuantity"));

        TableColumn<Transaction, Date> brandCol = new TableColumn("Date");
        brandCol.setMinWidth(270);
        brandCol.setCellValueFactory(
                new PropertyValueFactory<>("date"));

        TableColumn<Transaction, String> quanCol = new TableColumn("Type");
        quanCol.setMinWidth(150);
        quanCol.setCellValueFactory(
                new PropertyValueFactory<>("type"));

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
        logoutBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) { pageController.active("login"); }
        });
        userInfoBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) { pageController.active("profile"); }
        });
    }

    public void onActive() {
        reportTable.setItems(reports);
    }


//    public void handleSearchByKey(String oldValue, String newValue) {
//        if ( oldValue != null && (newValue.length() < oldValue.length()) ) {
//            reportTable.setItems(reports);
//        }
//
//        String[] parts = newValue.toUpperCase().split(" ");
//
//        subEntries = FXCollections.observableArrayList();
//        for ( Object entry: reportTable.getItems() ) {
//            boolean match = true;
//            Product entryP = (Product) entry;
//            String detailEntryP = entryP.getId()+entryP.getName().toUpperCase()+entryP.getBrand().toUpperCase();
//            for ( String part: parts ) {
//                if ( ! detailEntryP.contains(part) ) {
//                    match = false;
//                    break;
//                }
//            }
//
//            if ( match ) {
//                subEntries.add(entryP);
//            }
//        }
//        reportTable.setItems(subEntries);
//    }
    public Report getReport() {
        return report;
    }


    public void setReport(Report report) {
        this.report = report;
    }
}