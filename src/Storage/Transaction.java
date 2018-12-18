package Storage;
import java.io.Serializable;
import javax.persistence.*;
import java.util.*;
@Entity
public class Transaction implements Serializable{
    private int productId,changedQuantity;
    private Date date;
    private String type;

    public Transaction(int productId, int changedQuantity, Date date, String type) {
        this.productId = productId;
        this.changedQuantity = changedQuantity;
        this.date = date;
        this.type = type;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "productId=" + productId +
                ", changedQuantity=" + changedQuantity +
                ", date=" + date +
                ", type='" + type + '\'' +
                '}';
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
