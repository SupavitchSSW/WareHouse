package sample;

public class Product {
    private int id,quantity;
    private String name,brand;

    public Product(){
        this.id = -1;
        this.quantity = 0;
        this.name = "null";
        this.brand = "null";
    }

    public Product(int id, int quantity, String name, String brand) {
        this.id = id;
        this.quantity = quantity;
        this.name = name;
        this.brand = brand;
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
