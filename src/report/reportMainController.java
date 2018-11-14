package report;

import java.text.SimpleDateFormat;
import connectionDB.serviceDB;
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
import transaction.Transaction;
import user.User;
import connectionDB.*;


import javax.persistence.criteria.CriteriaBuilder;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;



public class reportMainController implements Controller {
    serviceDB database;
    PageController pageController;
    private TableView reportTable;
    reportController reportController;
    private ObservableList<Report> reports;
    private ObservableList<Report> subEntries;
    private User currentUser;
    private ObservableList<MonthYear> monthYears;

    private Product selectedProduct;
    private int index;

    public reportMainController(PageController pageController, reportController reportController, User currentUser, serviceDB database) {
        this.pageController = pageController;
        this.reportController = reportController;
        this.currentUser = currentUser;
        this.database = database;
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


        reportTable = (TableView) scene.lookup("#reportMain");
        TableColumn<MonthYear, Integer> yearCol = new TableColumn("Year");
        TableColumn<MonthYear, Integer> monthCol = new TableColumn("Month");
        yearCol.setCellValueFactory( new PropertyValueFactory<>("year"));
        monthCol.setCellValueFactory( new PropertyValueFactory<>("month"));
        reportTable.getColumns().addAll(yearCol,monthCol);



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
        ObservableList<MonthYear> my = getMY();
        reportTable.setItems(my);

    }

    public ObservableList<MonthYear> getMY(){
        ObservableList<MonthYear> my = FXCollections.observableArrayList();
//        List<Transaction> results  = database.getMinMonthTransection();
//        System.out.println(results.get(0));


        my.add(new MonthYear(1,2000));
        my.add(new MonthYear(2,2020));
        my.add(new MonthYear(3,2030));
        return my;
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
//            String detailEntryP = entryP.getDate();
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

}
