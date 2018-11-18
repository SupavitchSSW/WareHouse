package sample;
import java.io.Serializable;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Shelf implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int size,numberOfPallet;
    private List<Pallet> pallets = new ArrayList<Pallet>();

    public Shelf(int size, int numberOfPallet) {

        this.size = size;
        this.numberOfPallet = numberOfPallet;
    }

    // review pallets
    // (TODO)
    // Think !!! about store a Pallet in Shelf with obj ref. but store with id in product
    // can we use a same way ?

    public void addPallet(Pallet pallet){
        this.pallets.add(pallet);
    }

    public List<Pallet> getPallets() {
        return pallets;
    }

    public int getId() {
        return id;
    }


    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getNumberOfPallet() {
        return numberOfPallet;
    }

    public void setNumberOfPallet(int numberOfPallet) {
        this.numberOfPallet = numberOfPallet;
    }

    @Override
    public String toString() {
        return "Shelf{" +
                "id=" + id +
                ", size=" + size +
                ", numberOfPallet=" + numberOfPallet +
                ", pallets=" + pallets +
                '}';
    }
}
