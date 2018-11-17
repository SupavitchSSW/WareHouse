package sample;

import java.util.List;

public class Shelf {
    private int id,size,numberOfPallet;
    private List<Pallet> pallets;

    public Shelf(int id, int size, int numberOfPallet) {
        this.id = id;
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

    public void setId(int id) {
        this.id = id;
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
}
