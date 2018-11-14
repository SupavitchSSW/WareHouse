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

class monthYear{
    private int month,year;
    public monthYear(int month, int year) {
        this.month = month;
        this.year = year;
    }
}

public class reportMainController implements Controller {
    serviceDB database;
    PageController pageController;
    private TableView reportTable;
    reportController reportController;
    private ObservableList<Report> reports;
    private ObservableList<monthYear> MY;
    private ObservableList<Report> subEntries;
    private User currentUser;

    private Product selectedProduct;
    private int index;

    public reportMainController(PageController pageController, reportController reportController, User currentUser, serviceDB database) {
        this.pageController = pageController;
        this.reportController = reportController;
        this.currentUser = currentUser;
    }


    public void setMY(){
        //static
        System.out.println("wtf");
        MY.add(new monthYear(1,2000));
        MY.add(new monthYear(2,2000));
        MY.add(new monthYear(3,2000));
        MY.add(new monthYear(4,2000));
        MY.add(new monthYear(5,2000));
        MY.add(new monthYear(6,2000));
    }
    public ObservableList<Report> getOrder() {
        reports = FXCollections.observableArrayList();
        reports.add(new Report(new Date(),1));
        reports.add(new Report(new Date(),2));
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


//        searchBox.setPromptText("Search");
//        searchBox.textProperty().addListener((observable, oldVal, newVal) -> {
//            handleSearchByKey((String) oldVal, (String) newVal);
//        });

        reportTable = (TableView) scene.lookup("#reportMain");
        TableColumn<monthYear, Integer> yearCol = new TableColumn("Year");
        TableColumn<monthYear, Integer> monthCol = new TableColumn("Month");
        //dateCol.setMinWidth(270);
        yearCol.setCellValueFactory( new PropertyValueFactory<monthYear,Integer>("year"));
        monthCol.setCellValueFactory( new PropertyValueFactory<monthYear,Integer>("month"));
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
        reportTable.setItems(reports);
        setMY();
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
