package ordermanagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import sample.Product;

import java.util.Date;

public class Order {

    private int id;
    private String name,owner;
    private Date date = new Date(100,9,9,18,10);
    private ObservableList<OrderProduct> orderProducts = FXCollections.observableArrayList();



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

    public ObservableList<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void addOrderProduct(OrderProduct orderProduct) {
        orderProducts.add(orderProduct);

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

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", date=" + date +
                ", orderProducts=" + orderProducts +
                '}';
    }
}
