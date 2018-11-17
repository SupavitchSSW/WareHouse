package sample;

public class Pallet {
    private int id,ShelfLocation,idProduct,quantityOfProduct,size;

    public Pallet(int id, int shelfLocation, int idProduct, int quantityOfProduct, int size) {
        this.id = id;
        ShelfLocation = shelfLocation;
        this.idProduct = idProduct;
        this.quantityOfProduct = quantityOfProduct;
        this.size = size;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
