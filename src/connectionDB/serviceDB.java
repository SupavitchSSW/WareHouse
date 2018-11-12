package connectionDB;
import product.Product;
import java.util.*;
import javax.persistence.*;
public class serviceDB {

    private EntityManagerFactory emf ;
    private EntityManager em;

    public serviceDB(){
        this.emf = Persistence.createEntityManagerFactory("$objectdb/db/Stock.odb");
        this.em = emf.createEntityManager();
    }
    public void createProduct(String name, String brand , int price , int quantity){
        em.getTransaction().begin();
        Product p1 = new Product(name,brand,price,quantity);
        em.persist(p1);
        em.getTransaction().commit();
    }
    public List<Product> getAllProduct() {
        TypedQuery<Product> query = em.createQuery("SELECT p FROM Product p", Product.class);
        List<Product> results = query.getResultList();
        return results;
    }
    public void closeConnection() {
        this.em.close();
        this.emf.close();
    }
    public void removeProduct(int id){
        String sql = "SELECT c FROM Product c Where c.id =" + id +"";
        TypedQuery<Product> query = em.createQuery(sql, Product.class);
        List<Product> results = query.getResultList();
        em.getTransaction().begin();
        em.remove(results.get(0));
        em.getTransaction().commit();
    }
    public void setProductQuantity(int id,int value){                  //edit product's quantity
        String sql = "SELECT c FROM Product c Where c.id =" + id +"";
        TypedQuery<Product> query = em.createQuery(sql, Product.class);
        List<Product> results = query.getResultList();
        em.getTransaction().begin();
        results.get(0).setQuantity(value);
        em.getTransaction().commit();
    }
    public void setProductPrice(int id,int value){                   //edit product's price
        String sql = "SELECT c FROM Product c Where c.id =" + id +"";
        TypedQuery<Product> query = em.createQuery(sql, Product.class);
        List<Product> results = query.getResultList();
        em.getTransaction().begin();
        results.get(0).setPrice(value);
        em.getTransaction().commit();
    }
    public void setProductName(int id,String value){              //edit product's name
        String sql = "SELECT c FROM Product c Where c.id =" + id +"";
        TypedQuery<Product> query = em.createQuery(sql, Product.class);
        List<Product> results = query.getResultList();
        em.getTransaction().begin();
        results.get(0).setName(value);
        em.getTransaction().commit();
    }
    public void setProductBrand(int id,String value){        //edit product's brand
        String sql = "SELECT c FROM Product c Where c.id =" + id +"";
        TypedQuery<Product> query = em.createQuery(sql, Product.class);
        List<Product> results = query.getResultList();
        em.getTransaction().begin();
        results.get(0).setBrand(value);
        em.getTransaction().commit();
    }






}
