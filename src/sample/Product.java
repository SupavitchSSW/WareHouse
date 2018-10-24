package sample;

public class Product {
    private int id,quantity,cost=0,price=0;
    private String name,brand;

    public Product(){
        this.id = -1;
        this.quantity = 0;
        this.name = "null";
        this.brand = "null";
        this.cost = 0;
        this.price = 0;
    }

    public Product(int id, int quantity, String name, String brand) {
        this.id = id;
        this.quantity = quantity;
        this.name = name;
        this.brand = brand;
    }

    public Product(int id, int quantity, String name, String brand, int price) {
        this.id = id;
        this.quantity = quantity;
        this.name = name;
        this.brand = brand;
        this.price = price;
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

    public void setQuantity(String quantity) {
        this.quantity = Integer.parseInt(quantity);
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

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setPrice(String price) {
        this.price = Integer.parseInt(price);
    }
}
