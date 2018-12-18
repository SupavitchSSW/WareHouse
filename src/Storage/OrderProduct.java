package Storage;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class OrderProduct implements Serializable {
    private int quantity=0,orderQuantity,sendQuantity = 0,productId;
    private String name,brand;

    public OrderProduct(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public OrderProduct(int productId, String name, String brand, int orderQuantity) {
        this.productId = productId;
        this.orderQuantity = orderQuantity;
        this.name = name;
        this.brand = brand;
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
                "orderQuantity=" + orderQuantity +
                ", sendQuantity=" + sendQuantity +
                ", productId=" + productId +
                ", name='" + name + '\'' +
                ", brand='" + brand + '\'' +
                '}';
    }

    public int getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public int getSendQuantity() {
        return sendQuantity;
    }

    public void setSendQuantity(int sendQuantity) {
        this.sendQuantity = sendQuantity;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
