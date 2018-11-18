package sample;
import java.io.Serializable;
import javax.persistence.*;

@Entity
public class Pallet implements Serializable{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int ShelfLocation,idProduct,quantityOfProduct,size;

    public Pallet(int shelfLocation, int idProduct, int quantityOfProduct, int size) {

        this.ShelfLocation = shelfLocation;
        this.idProduct = idProduct;
        this.quantityOfProduct = quantityOfProduct;
        this.size = size;
    }

    @Override
    public String toString() {
        return "Pallet{" +
                "id=" + id +
                ", ShelfLocation=" + ShelfLocation +
                ", idProduct=" + idProduct +
                ", quantityOfProduct=" + quantityOfProduct +
                ", size=" + size +
                '}';
    }

    public int getId() {
        return id;
    }

    public int getShelfLocation() {
        return ShelfLocation;
    }

    public void setShelfLocation(int shelfLocation) {
        ShelfLocation = shelfLocation;
    }

    public int getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(int idProduct) {
        this.idProduct = idProduct;
    }

    public int getQuantityOfProduct() {
        return quantityOfProduct;
    }

    public void setQuantityOfProduct(int quantityOfProduct) {
        this.quantityOfProduct = quantityOfProduct;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
