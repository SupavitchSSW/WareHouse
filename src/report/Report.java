package report;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import transaction.Transaction;

import java.util.Date;

public class Report {

    private int productId, month,year;
    private Date date;
    private ObservableList<Transaction> transactions = FXCollections.observableArrayList();

    public Report(Date date,int productId) {
        this.date = date;
        this.productId = productId;
    }

    public Report(int month,int year) {
        this.month = month;
        this.year = year;
    }



    public ObservableList<Transaction> getTransactions() {
        return transactions;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }


    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }


    @Override
    public String toString() {
        return "Report{" +
                ", date=" + date +
                "productId=" + productId +
                ", transactions=" + transactions+
                '}';
    }

}
