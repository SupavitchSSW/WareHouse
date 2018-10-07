package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.Date;

public class Order {

    private int id,quantity;
    private String name;
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

    }

    public Order(int id, String name) {
        this.name = name;
        this.id = id;
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

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
