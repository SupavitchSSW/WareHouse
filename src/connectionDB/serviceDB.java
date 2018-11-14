package connectionDB;
//import com.sun.org.apache.xpath.internal.operations.Or;
import product.Product;
import ordermanagement.OrderProduct;
import ordermanagement.Order;
import transaction.Transaction;
import user.User;
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
    public int createOrder(String name, String owner){
        em.getTransaction().begin();
        Order o = new Order(name,owner);
        em.persist(o);
        em.getTransaction().commit();
        return o.getId();
    }
    public List<Order> getAllOrder() {
        TypedQuery<Order> query = em.createQuery("SELECT w FROM Order w", Order.class);
        List<Order> resultOrder = query.getResultList();
        return resultOrder;
    }
    public int getQtbyID(int id){
          String sql = "SELECT c FROM Product c Where c.id =" + id +"";
          TypedQuery<Product> query = em.createQuery(sql, Product.class);
          List<Product> results = query.getResultList();
          return results.get(0).getQuantity();
    }
    public void setOrderStatus(int id , String status){
          String sql = "SELECT c FROM Order c Where c.id =" + id +"";
          TypedQuery<Order> query = em.createQuery(sql, Order.class);
          List<Order> results = query.getResultList();
          em.getTransaction().begin();
          results.get(0).setStatus(status);
          em.getTransaction().commit();

    }
    public void addOrderproduct(int id_order,int productId, String name, String brand, int orderQuantit){
          OrderProduct a = new OrderProduct(productId,name,brand,orderQuantit);
          em.getTransaction().begin();
          em.persist(a);
          em.getTransaction().commit();
          em.getTransaction().begin();
          String sql = "SELECT c FROM Order c Where c.id =" + id_order +"";
          TypedQuery<Order> query = em.createQuery(sql, Order.class);
          List<Order> results = query.getResultList();
          results.get(0).addOrderProduct(a);
          em.getTransaction().commit();
    }
    public Product getProductbyID(int id){
           String sql = "SELECT c FROM Product c Where c.id =" + id +"";
           TypedQuery<Product> query = em.createQuery(sql, Product.class);
           List<Product> results = query.getResultList();
           return results.get(0);
    }
    public void createTransaction(int productId, int changedQuantity, Date date, String type){
           em.getTransaction().begin();
           Transaction p1 = new Transaction(productId,  changedQuantity,  date, type);
           em.persist(p1);
           em.getTransaction().commit();
    }
    public List<Transaction> getAllTransactionInMonth(int month, int year){
           String sql = "SELECT c FROM Transaction c Where c.date.getMonth() =" + month +" AND c.date.getYear() =" + year +"";
           TypedQuery<Transaction> query = em.createQuery(sql, Transaction.class);
           List<Transaction> results = query.getResultList();
           return results;
    }

    public List<Transaction> getMinMonthTransection(){
           String sql = "SELECT c FROM Transaction c ";
           TypedQuery<Transaction> query = em.createQuery(sql, Transaction.class);
           List<Transaction> results = query.getResultList();
           return results;
    }


    public User authen(String username , String password){
           String sql = "SELECT c FROM User c Where c.username LIKE '"+ username +"' AND c.password LIKE '"+ password +"'";
           TypedQuery<User> query = em.createQuery(sql, User.class);
           List<User> results = query.getResultList();
           if (results.isEmpty()){
               return null;
           }
           return results.get(0);
    }
    public void createUser(String username, String password, String role, String firstname, String surname, String phoneNumber){
            em.getTransaction().begin();
            User p1 = new User(username, password, role, firstname, surname, phoneNumber);
            em.persist(p1);
            em.getTransaction().commit();

    }
        public void removeUser(int id){
            String sql = "SELECT c FROM User c Where c.id =" + id +"";
            TypedQuery<User> query = em.createQuery(sql, User.class);
            List<User> results = query.getResultList();
            em.getTransaction().begin();
            em.remove(results.get(0));
            em.getTransaction().commit();
    }
    public List<User> getAllUser() {
        TypedQuery<User> query = em.createQuery("SELECT w FROM User w", User.class);
        List<User> resultUser = query.getResultList();
        return resultUser;
    }
    public User getUserbyID(int id){
        String sql = "SELECT c FROM User c Where c.id =" + id +"";
        TypedQuery<User> query = em.createQuery(sql, User.class);
        List<User> results = query.getResultList();
        return results.get(0);
    }


    public void setUserName(int id,String username){
        String sql = "SELECT c FROM User c Where c.id =" + id +"";
        TypedQuery<User> query = em.createQuery(sql, User.class);
        List<User> results = query.getResultList();
        em.getTransaction().begin();
        results.get(0).setUsername(username);
        em.getTransaction().commit();
    }
    public void setPassword(int id,String password){
        String sql = "SELECT c FROM User c Where c.id =" + id +"";
        TypedQuery<User> query = em.createQuery(sql, User.class);
        List<User> results = query.getResultList();
        em.getTransaction().begin();
        results.get(0).setPassword(password);
        em.getTransaction().commit();
    }
    public void setFirstname(int id,String firstname){
        String sql = "SELECT c FROM User c Where c.id =" + id +"";
        TypedQuery<User> query = em.createQuery(sql, User.class);
        List<User> results = query.getResultList();
        em.getTransaction().begin();
        results.get(0).setFirstname(firstname);
        em.getTransaction().commit();
    }
    public void setSurname(int id,String surname){
        String sql = "SELECT c FROM User c Where c.id =" + id +"";
        TypedQuery<User> query = em.createQuery(sql, User.class);
        List<User> results = query.getResultList();
        em.getTransaction().begin();
        results.get(0).setSurname(surname);
        em.getTransaction().commit();
    }
    public void setPhoneNumber(int id,String number){
        String sql = "SELECT c FROM User c Where c.id =" + id +"";
        TypedQuery<User> query = em.createQuery(sql, User.class);
        List<User> results = query.getResultList();
        em.getTransaction().begin();
        results.get(0).setPhoneNumber(number);
        em.getTransaction().commit();
    }








}
