package ordermanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Product;

import java.util.Date;

public class Order {

    private int id;
    private String name,owner;
    private Date date = new Date(1999,9,9);
    ObservableList<Product> products;

    public ObservableList<Product> getProducts() {
        return products;
    }

    public void addProduct(Product product) {
        this.products.add(product);
    }

    public Order(){
        this.name = "";
        this.id = -1;
        this.owner = "undefind";
    }

    public Order(int id, String name,String owner) {
        this.name = name;
        this.id = id;
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}
