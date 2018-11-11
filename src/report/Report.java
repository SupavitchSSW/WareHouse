package report;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Transaction;

import java.util.Date;

public class Report {

    private int productId;
    private Date date;
    private ObservableList<Transaction> transactions = FXCollections.observableArrayList();

    public Report(){
        this.productId = -1;
    }

    public Report(int id) {
        this.productId = id;
    }

    public ObservableList<Transaction> getTransactions() {
        return transactions;
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
        return "Order{" +
                "productId=" + productId +
                ", date=" + date +
                ", transactions=" + transactions+
                '}';
    }

}
