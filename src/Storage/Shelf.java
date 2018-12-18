package Storage;
import Storage.Pallet;

import java.io.Serializable;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Entity
public class Shelf implements Serializable {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;
    private int maxPallet;
    private List<Pallet> pallets = new ArrayList<>();

    public Shelf(String name, int maxPallet) {
        this.name = name;
        this.maxPallet = maxPallet;
    }

    public void addPallet(int capacity, int maxCapacity){
        pallets.add(new Pallet(capacity,maxCapacity));
    }
    public void addPallet(Pallet pallet){
        pallets.add(pallet);
    }

    public void removePallet(int id){
        for (Iterator<Pallet> iter = pallets.listIterator(); iter.hasNext(); ) {
            Pallet a = iter.next();
            if (a.getId() == id) {
                iter.remove();
            }
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMaxPallet() {
        return maxPallet;
    }

    public void setMaxPallet(int maxPallet) {
        this.maxPallet = maxPallet;
    }

    public List<Pallet> getPallets() {
        return pallets;
    }

    public void setPallets(List<Pallet> pallets) {
        this.pallets = pallets;
    }
}