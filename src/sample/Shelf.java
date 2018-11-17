package sample;

import java.util.List;

public class Shelf {
    private int id,size,numberOfPallet;

    public Shelf(int id, int size, int numberOfPallet) {
        this.id = id;
        this.size = size;
        this.numberOfPallet = numberOfPallet;
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
