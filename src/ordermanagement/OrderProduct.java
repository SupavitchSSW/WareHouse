package ordermanagement;

import sample.Product;

public class OrderProduct extends Product {
    private int orderQuantity,sendQuantity = 0;

    public OrderProduct(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public OrderProduct(int id, int quantity, String name, String brand, int orderQuantity) {
        super(id, quantity, name, brand);
        this.orderQuantity = orderQuantity;
        if(super.getQuantity() < orderQuantity) {
            sendQuantity = super.getQuantity();
        }else{
            sendQuantity = orderQuantity;
        }
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
