package report;


import connectionDB.serviceDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.input.MouseEvent;
import sample.Controller;
import sample.PageController;
import transaction.Transaction;
import user.User;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

public class ReportUI implements Controller {
    PageController pageController;
    private TableView reportTable;
    private ObservableList<transaction.Transaction> reports = FXCollections.observableArrayList();
    private ObservableList<Transaction> subEntries;
    private ReportController reportController;

    public ReportUI(ReportController reportController ,PageController pageController) {
        this.pageController = pageController;
        this.reportController = reportController;

    }

    public void initilize() {
        Scene scene = pageController.getScene("report");
        Button mainBt = (Button) scene.lookup("#mainButton");
        Button reportBt = (Button) scene.lookup("#summaryButton");
        Button orderBt = (Button) scene.lookup("#orderButton");
        Button userSearchBt = (Button) scene.lookup("#userSearchButton");
        Button logoutBt = (Button) scene.lookup("#logoutButton");
        Button userInfoBt = (Button) scene.lookup("#userInfo");
        //TextField searchBox = (TextField) scene.lookup("#searchBox");
        Spinner monthBt = (Spinner) scene.lookup("#monthPicker");
        Spinner yearBt = (Spinner) scene.lookup("#yearPicker");


//        searchBox.setPromptText("Search");
//        searchBox.textProperty().addListener((observable, oldVal, newVal) -> {
//            handleSearchByKey((String) oldVal, (String) newVal);
//        });

        reportTable = (TableView) scene.lookup("#report");

        ObservableList<String> months = FXCollections.observableArrayList(//
                "December", "November", "October", "September", //
                "August", "July", "June", "May", //
                "April", "March", "February", "January");


        // Value factory.
        SpinnerValueFactory<String> valueFactoryM = new SpinnerValueFactory.ListSpinnerValueFactory<String>(months);

        Calendar calendar = new GregorianCalendar();
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);
//        System.out.println(months.get(11-month));

        ObservableList<String> years = FXCollections.observableArrayList(Integer.toString(year-4),Integer.toString(year-3),
                Integer.toString(year-2),Integer.toString(year-1),Integer.toString(year));

        // Value factory.
        SpinnerValueFactory<String> valueFactoryY = new SpinnerValueFactory.ListSpinnerValueFactory<String>(years);

        valueFactoryM.setValue(months.get(11-month));
        monthBt.setValueFactory(valueFactoryM);

        valueFactoryY.setValue(Integer.toString(year));
        yearBt.setValueFactory(valueFactoryY);


//yah
        TableColumn<Transaction, Integer> idCol = new TableColumn("Product ID");
        idCol.setMinWidth(160);
        idCol.setCellValueFactory(
                new PropertyValueFactory<>("productId"));

        TableColumn<Transaction, Integer> nameCol = new TableColumn("Changed Quantity");
        nameCol.setMinWidth(200);
        nameCol.setCellValueFactory(
                new PropertyValueFactory<>("changedQuantity"));

        TableColumn<Transaction, Date> brandCol = new TableColumn("Date");
        brandCol.setMinWidth(280);
        brandCol.setCellValueFactory(
                new PropertyValueFactory<>("date"));

        TableColumn<Transaction, String> quanCol = new TableColumn("Type");
        quanCol.setMinWidth(170);
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
        //System.out.println(selectDate.toLocaleString());
        //System.out.println(selectDate.getMonth()+"_"+selectDate.getYear());
        List<Transaction> result = reportController.getAllTransaction();
//        for(Transaction t : transactions){
//            System.out.println(t.toString());
//            reports.add(new Transaction(t.getProductId(),t.getChangedQuantity(),t.getDate(),t.getType()));
//        }
        reports = FXCollections.observableArrayList(result);
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
            Transaction entryP = (Transaction) entry;
            String detailEntryP = entryP.getProductId()+entryP.getChangedQuantity()+""+entryP.getDate()+""+entryP.getType().toUpperCase();
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