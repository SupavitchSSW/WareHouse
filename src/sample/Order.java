package sample;

import java.util.Date;

public class Order {

    private int id,quantity;
    private String name;



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
