package Storage;

import java.io.Serializable;
import javax.persistence.*;

@Entity
public class Product implements Serializable{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int productId,quantity,price=0,amountInPack=0,packCapacity=0;
    private String name,brand;

    public Product(int productId, int quantity, int price, int amountInPack, int packCapacity, String name, String brand) {
        this.productId = productId;
        this.quantity = quantity;
        this.price = price;
        this.amountInPack = amountInPack;
        this.packCapacity = packCapacity;
        this.name = name;
        this.brand = brand;
    }

    public int getId(){
        return 0;
    }
    public void setId(){}

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getAmountInPack() {
        return amountInPack;
    }

    public void setAmountInPack(int amountInPack) {
        this.amountInPack = amountInPack;
    }

    public int getPackCapacity() {
        return packCapacity;
    }

    public void setPackCapacity(int packCapacity) {
        this.packCapacity = packCapacity;
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