package product;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.*;

@Entity
public class CatalogueEntry implements Serializable {
    private int lastid=0,warehouseCapacity=0;
    private List<Product> products = new ArrayList<Product>();

    public CatalogueEntry(int warehouseCapacity) {
        this.warehouseCapacity = warehouseCapacity;
    }

    public void addProduct(Product product){
        products.add(product);
    }

    public void addProduct(int productId, int quantity, int price, int amountInPack, int packCapacity, String name, String brand){
        products.add(new Product(productId,quantity,price,amountInPack,packCapacity,name,brand));
    }

    public void removeProduct(int id){
        for (Iterator<Product> iter = products.listIterator(); iter.hasNext(); ) {
            Product a = iter.next();
            if (a.getProductId() == id) {
                iter.remove();
            }
        }
    }

    public void setProductQuantity(int productId,int quantity){
        for(Product p : products){
            if(p.getProductId() == productId){
                p.setQuantity(quantity);
            }
        }
    }

    public void setProductDetail(int productId, int price, int amountInPack, int packCapacity, String name, String brand){
        for(Product p : products){
            if(p.getProductId() == productId){
                p.setPrice(price);
                p.setAmountInPack(amountInPack);
                p.setPackCapacity(packCapacity);
                p.setName(name);
                p.setBrand(brand);
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