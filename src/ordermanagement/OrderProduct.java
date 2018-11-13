package ordermanagement;

import product.Product;

import javax.persistence.Entity;
import java.io.Serializable;

@Entity
public class OrderProduct extends Product implements Serializable {
    private int orderQuantity,sendQuantity = 0,productId;

    public OrderProduct(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public OrderProduct(int productId, String name, String brand, int orderQuantity) {
        super(0, name, brand);
        this.productId = productId;
        this.orderQuantity = orderQuantity;
        if(super.getQuantity() < orderQuantity) {
            sendQuantity = super.getQuantity();
        }else{
            sendQuantity = orderQuantity;
        }
    }

    @Override
    public String toString() {
        return "OrderProduct{" +
                "orderQuantity=" + orderQuantity +
                ", sendQuantity=" + sendQuantity +
                ", productId=" + productId +
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
}
