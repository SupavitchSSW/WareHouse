package ordermanagement;

import sample.Product;

public class OrderProduct extends Product {
    private int orderQuantity,sendQuantity = 0,productId;

    public OrderProduct(int orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public OrderProduct(int id, String name, String brand, int orderQuantity) {
        super(id, 0, name, brand);
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
