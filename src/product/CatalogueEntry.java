package product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class CatalogueEntry implements Serializable {
    private int lastid=0,warehouseCapacity=0;
    private List<Product> products = new ArrayList<>();

    public CatalogueEntry(int warehouseCapacity) {
        this.warehouseCapacity = warehouseCapacity;
    }

    public void addProduct(Product product){
        products.add(product);
    }

    public void removeProduct(int id){
        for (Iterator<Product> iter = products.listIterator(); iter.hasNext(); ) {
            Product a = iter.next();
            if (a.getProductId() == id) {
                iter.remove();
                break;
            }
        }
    }

    public int getLastid() {
        return lastid;
    }

    public void setLastid(int lastid) {
        this.lastid = lastid;
    }

    public int getWarehouseCapacity() {
        return warehouseCapacity;
    }

    public void setWarehouseCapacity(int warehouseCapacity) {
        this.warehouseCapacity = warehouseCapacity;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}