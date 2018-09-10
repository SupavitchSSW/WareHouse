package sample;

import java.util.Date;

public class Order {

    private int id;
    private String name;
    private String a;


    public Order(){
        this.name = "";
        this.id = -1;
        this.a = "B";
    }

    public Order(int id, String name) {
        this.name = name;
        this.id = id;
        this.a = "A";
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
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
