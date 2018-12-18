package ordermanagement;

import java.io.Serializable;
import javax.persistence.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import product.Product;
import java.util.Date;
import java.util.*;

@Entity
public class Order implements Serializable{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name,owner,status="waiting";
    private Date date = new Date(100,9,9,18,10);
    private List<OrderProduct> orderProducts = new ArrayList<OrderProduct>();

    public List<OrderProduct> getOrderProducts() {
        return orderProducts;
    }

    public void setOrderProducts(List<OrderProduct> orderProducts) {
        this.orderProducts = orderProducts;
    }

    public Order(){
        this.name = "";
        this.owner = "undefind";
    }

    public Order( String name,String owner) {
        this.name = name;
        this.owner = owner;
    }
    public Order(Date date ,String name,String owner,String status){
        this.date = date;
        this.name = name;
        this.owner = owner;
        this.status = status;
    }

//    public ObservableList<OrderProduct> getOrderProducts() {
//        return orderProducts;
//    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", status='" + status + '\'' +
                ", date=" + date +
                ", orderProducts=" + orderProducts +
                '}';
    }
}
