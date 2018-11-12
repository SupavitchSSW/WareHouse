package report;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Transaction;

import java.util.Date;

public class Report {

    private int productId;
    private String dateMonth;
    private Date date;
    private ObservableList<Transaction> transactions = FXCollections.observableArrayList();

    public Report(Date date,int productId) {
        this.date = date;
        this.productId = productId;
    }

    public Report(String dateMonth) {
        this.dateMonth = dateMonth;

    }

    public ObservableList<Transaction> getTransactions() {
        return transactions;
    }

    public String getDateMonth() {
        return dateMonth;
    }

    public void setDate(String dateMonth) {
        this.dateMonth = dateMonth;
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
