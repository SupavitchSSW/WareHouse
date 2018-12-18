package Storage;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.persistence.*;

@Entity
public class Pallet implements Serializable{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int capacity,maxCapacity;
    private List<Product> products = new ArrayList<Product>();

    public Pallet(int capacity, int maxCapacity) {
        this.capacity = capacity;
        this.maxCapacity = maxCapacity;
    }

    public void setProductQuantity(int productId,int quantity){
        for (Product p : products) {
            if (p.getProductId() == productId) {
                int changeQt = p.getQuantity()-quantity;
                p.setQuantity(quantity);
                this.capacity -= changeQt*p.getPackCapacity();
                break;
            }
        }
    }

    public void clearEmptyProduct() {
        for (Iterator<Product> iter = products.listIterator(); iter.hasNext(); ) {
            Product a = iter.next();
            if (a.getQuantity() == 0)
                iter.remove();
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

    public void addProduct(int productId, int quantity, int price, int amountInPack, int packCapacity, String name, String brand){
        products.add(new Product(productId,quantity,price,amountInPack,packCapacity,name,brand));
    }
    public void addProduct(Product product){
        this.capacity += product.getPackCapacity()*product.getQuantity();
        products.add(product);
    }

    public void removeProduct(int productId){
        for (Product p : products) {
            if (p.getProductId() == productId) {
                this.capacity -= (p.getPackCapacity()*p.getQuantity());
                products.remove(p);
            }
        }
    }

    public int getId() {
        return id;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(int maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}