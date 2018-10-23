package sample;

import java.util.Date;

public class transaction {
    private int productId,changedQuantity;
    private Date date;
    private String type;

    public transaction(int productId, int changedQuantity, Date date, String type) {
        this.productId = productId;
        this.changedQuantity = changedQuantity;
        this.date = date;
        this.type = type;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getChangedQuantity() {
        return changedQuantity;
    }

    public void setChangedQuantity(int changedQuantity) {
        this.changedQuantity = changedQuantity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
