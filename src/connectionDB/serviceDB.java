package connectionDB;
//import com.sun.org.apache.xpath.internal.operations.Or;
import product.Product;
import ordermanagement.OrderProduct;
import ordermanagement.Order;
import user.Staff;
import user.Manager;
import product.Pallet;
import product.Shelf;
import product.CatalogueEntry;
import transaction.Transaction;
import user.User;
import java.util.*;
import javax.persistence.*;
public class serviceDB {

    private EntityManagerFactory emf ;
    private EntityManager em;
    private User currentUser;
    public serviceDB(){
        this.emf = Persistence.createEntityManagerFactory("$objectdb/db/Stock.odb");
        this.em = emf.createEntityManager();
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public List<CatalogueEntry> getCatalogueEntry(){
        TypedQuery<CatalogueEntry> query = em.createQuery("SELECT w FROM CatalogueEntry w", CatalogueEntry.class);
        List<CatalogueEntry> resultOrder = query.getResultList();
        return resultOrder;
    }
    public void createShelf(String name, int maxPallet){
        em.getTransaction().begin();
        Shelf p1 = new Shelf(name,maxPallet);
        em.persist(p1);
        em.getTransaction().commit();
    }
    public void createCatalogueEntry(int warehouseCapacity){
        em.getTransaction().begin();
        CatalogueEntry p1 = new CatalogueEntry(warehouseCapacity);
        em.persist(p1);
        em.getTransaction().commit();
    }
    public void addPallet(int shelfId ,int capacity, int maxCapacity){

        em.getTransaction().begin();
        Pallet p1 = new Pallet(capacity,maxCapacity);
        em.persist(p1);
        em.getTransaction().commit();

        em.getTransaction().begin();
        String sql = "SELECT c FROM Shelf c Where c.id =" + shelfId +"";
        TypedQuery<Shelf> query = em.createQuery(sql, Shelf.class);
        List<Shelf> results = query.getResultList();
        results.get(0).addPallet(p1);
        em.getTransaction().commit();

    }
    public void addProductToPallet(int palletId , int productId, int quantity, int price, int amountInPack, int packCapacity, String name, String brand){
        em.getTransaction().begin();
        Product p1 = new Product(productId, quantity, price,amountInPack,packCapacity, name, brand);
        em.persist(p1);
        em.getTransaction().commit();

        em.getTransaction().begin();
        String sql = "SELECT c FROM Pallet c Where c.id =" + palletId +"";
        TypedQuery<Pallet> query = em.createQuery(sql, Pallet.class);
        List<Pallet> results = query.getResultList();
        results.get(0).addProduct(p1);
        em.getTransaction().commit();

    }
    public void addProductToCatalogueEntry(int productId, int quantity, int price, int amountInPack, int packCapacity, String name, String brand){

        em.getTransaction().begin();
        Product p1 = new Product(productId, quantity, price,amountInPack,packCapacity, name, brand);
        em.persist(p1);
        em.getTransaction().commit();

        em.getTransaction().begin();
        String sql = "SELECT c FROM CatalogueEntry c Where c.id =" + productId +"";
        TypedQuery<CatalogueEntry> query = em.createQuery(sql, CatalogueEntry.class);
        List<CatalogueEntry> results = query.getResultList();
        results.get(0).addProduct(p1);
        em.getTransaction().commit();
    }

    public void removeProductPallet(int palletId , int productId){
        String sql = "SELECT c FROM Pallet c Where c.id =" + palletId +"";
        TypedQuery<Pallet> query = em.createQuery(sql, Pallet.class);
        List<Pallet> results = query.getResultList();
        em.getTransaction().begin();
        results.get(0).removeProduct(productId);
        em.getTransaction().commit();
    }

    public void removeProductCatalogueEntry(int catalogueEntryId , int productId ){
        String sql = "SELECT c FROM CatalogueEntry c Where c.id =" + catalogueEntryId +"";
        TypedQuery<CatalogueEntry> query = em.createQuery(sql, CatalogueEntry.class);
        List<CatalogueEntry> results = query.getResultList();
        em.getTransaction().begin();
        results.get(0).removeProduct(productId);
        em.getTransaction().commit();
    }
    public void removePallet(int shelfId , int palletId){
        String sql = "SELECT c FROM Shelf c Where c.id =" + shelfId +"";
        TypedQuery<Shelf> query = em.createQuery(sql, Shelf.class);
        List<Shelf> results = query.getResultList();
        em.getTransaction().begin();
        results.get(0).removePallet(palletId);
        em.getTransaction().commit();

        String sql2 = "SELECT c FROM Pallet c Where c.id =" + palletId +"";
        TypedQuery<Pallet> query2 = em.createQuery(sql2, Pallet.class);
        List<Pallet> results2 = query2.getResultList();
        em.getTransaction().begin();
        em.remove(results2.get(0));
        em.getTransaction().commit();

    }
    public void setProductQtPallet(int palletId , int productId , int qt){
        String sql = "SELECT c FROM Pallet c Where c.id =" + palletId +"";
        TypedQuery<Pallet> query = em.createQuery(sql, Pallet.class);
        List<Pallet> results = query.getResultList();
        em.getTransaction().begin();
        results.get(0).setProductQuantity(productId,qt);
        em.getTransaction().commit();
    }
    public void setProductQtCatalogue(int productId, int qt){
        TypedQuery<CatalogueEntry> query = em.createQuery("SELECT w FROM CatalogueEntry w", CatalogueEntry.class);
        List<CatalogueEntry> resultOrder = query.getResultList();
        em.getTransaction().begin();
        resultOrder.get(0).setProductQuantity(productId , qt);
        em.getTransaction().commit();
    }
    public void setProductDetailPallet(int palletId ,int productId, int price, int amountInPack, int packCapacity, String name, String brand){
        String sql = "SELECT c FROM Pallet c Where c.id =" + palletId +"";
        TypedQuery<Pallet> query = em.createQuery(sql, Pallet.class);
        List<Pallet> results = query.getResultList();
        em.getTransaction().begin();
        results.get(0).setProductDetail(productId,price,amountInPack,packCapacity,name,brand);
        em.getTransaction().commit();
    }
    public void setProductDetailCatalogue(int productId, int price, int amountInPack, int packCapacity, String name, String brand){
        String sql = "SELECT c FROM CatalogueEntry c ";
        TypedQuery<CatalogueEntry> query = em.createQuery(sql, CatalogueEntry.class);
        List<CatalogueEntry> results = query.getResultList();
        em.getTransaction().begin();
        results.get(0).setProductDetail(productId,price,amountInPack,packCapacity,name,brand);
        em.getTransaction().commit();
    }
    public void createOrder(Date date ,String name,String owner,String status){
        em.getTransaction().begin();
        Order o = new Order(date,name,owner,status);
        em.persist(o);
        em.getTransaction().commit();
    }
    public void addOrderProduct(int orderId ,int productId, String name, String brand, int orderQuantity){
        OrderProduct orderProduct = new OrderProduct(productId,name,brand,orderQuantity);
        String sql = "SELECT c FROM Order c Where c.id =" + orderId +"";
        TypedQuery<Order> query = em.createQuery(sql, Order.class);
        List<Order> results = query.getResultList();
        em.getTransaction().begin();
        results.get(0).addOrderProduct(orderProduct);
        em.getTransaction().commit();
    }
    public List<Order> getAllOrder(){
        TypedQuery<Order> query = em.createQuery("SELECT w FROM Order w", Order.class);
        List<Order> resultOrder = query.getResultList();
        return resultOrder;
    }
    public void setOrderStatus(int id , String status){
        String sql = "SELECT c FROM Order c Where c.id =" + id +"";
        TypedQuery<Order> query = em.createQuery(sql, Order.class);
        List<Order> results = query.getResultList();
        em.getTransaction().begin();
        results.get(0).setStatus(status);
        em.getTransaction().commit();
    }
    public void createTransaction(int productId, int changedQuantity, Date date, String type){
        em.getTransaction().begin();
        Transaction p1 = new Transaction(productId,  changedQuantity,  date, type);
        em.persist(p1);
        em.getTransaction().commit();
    }
    public List<Transaction> getAllTransaction(){
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
    public void createUserStaff(String username, String password,String firstname, String surname, String phoneNumber){
        em.getTransaction().begin();
        Staff p1 = new Staff(username,password,firstname, surname, phoneNumber);
        em.persist(p1);
        em.getTransaction().commit();
    }
    public void createUserManager(String username, String password, String firstname, String surname, String phoneNumber){
        em.getTransaction().begin();
        Manager p1 = new Manager(username,password,firstname, surname, phoneNumber);
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
    public void setPassword(int id,String password){
        String sql = "SELECT c FROM User c Where c.id =" + id +"";
        TypedQuery<User> query = em.createQuery(sql, User.class);
        List<User> results = query.getResultList();
        em.getTransaction().begin();
        results.get(0).setPassword(password);
        em.getTransaction().commit();
    }
    public void changeUserInfo(int userId , String firstname, String surname, String phoneNumber){
        String sql = "SELECT c FROM User c Where c.id =" + userId +"";
        TypedQuery<User> query = em.createQuery(sql, User.class);
        List<User> results = query.getResultList();
        em.getTransaction().begin();
        results.get(0).setFirstname(firstname);
        results.get(0).setSurname(surname);
        results.get(0).setPhoneNumber(phoneNumber);
        em.getTransaction().commit();
    }
    public void setWarehouseCapacity(int capacity){
        String sql = "SELECT c FROM CatalogueEntry c ";
        TypedQuery<CatalogueEntry> query = em.createQuery(sql, CatalogueEntry.class);
        List<CatalogueEntry> results = query.getResultList();
        em.getTransaction().begin();
        results.get(0).setWarehouseCapacity(capacity);
        em.getTransaction().commit();
    }
    public int getWarehouseCapacity(int capacity){
        String sql = "SELECT c FROM CatalogueEntry c ";
        TypedQuery<CatalogueEntry> query = em.createQuery(sql, CatalogueEntry.class);
        List<CatalogueEntry> results = query.getResultList();
        return results.get(0).getWarehouseCapacity();
    }
    public void setLastId(int lastId){
        String sql = "SELECT c FROM CatalogueEntry c ";
        TypedQuery<CatalogueEntry> query = em.createQuery(sql, CatalogueEntry.class);
        List<CatalogueEntry> results = query.getResultList();
        em.getTransaction().begin();
        results.get(0).setLastid(lastId);
        em.getTransaction().commit();
    }
    public List<Shelf> getAllShelf(){
        String sql = "SELECT c FROM Shelf c ";
        TypedQuery<Shelf> query = em.createQuery(sql, Shelf.class);
        List<Shelf> results = query.getResultList();
        return results;
    }

    
    ///////////////////////////////////////////////////////////////////





    public void createProduct(String name, String brand , int price , int quantity){
        em.getTransaction().begin();
//        int productId, int quantity, int price, int amountInPack, int packCapacity, String name, String brand
        Product p1 = new Product(0,quantity,price,0, 0, name, brand);
        em.persist(p1);
        em.getTransaction().commit();
    }
    public List<Product> getAllProduct(){
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

    public int getQtbyID(int id){
          String sql = "SELECT c FROM Product c Where c.id =" + id +"";
          TypedQuery<Product> query = em.createQuery(sql, Product.class);
          List<Product> results = query.getResultList();
          return results.get(0).getQuantity();
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


    public List<Transaction> getAllTransactionInMonth(int month,int year){
           String sql = "SELECT c FROM Transaction c WHERE c.date.getMonth() =" + month +" AND c.date.getYear() = "+year;
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



    public void createUser(String username, String password, String role, String firstname, String surname, String phoneNumber){
            em.getTransaction().begin();
            User p1 = new User(username, password, role, firstname, surname, phoneNumber);
            em.persist(p1);
            em.getTransaction().commit();

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

//    public void createShelf(int size, int numberOfPallet){
//        em.getTransaction().begin();
//        Shelf o = new Shelf(size,numberOfPallet);
//        em.persist(o);
//        em.getTransaction().commit();
//    }
//    public void addPallet(int id_shelf , int shelfLocation, int idProduct, int quantityOfProduct, int size){
//        Pallet a = new Pallet(shelfLocation,idProduct,quantityOfProduct,size);
//        em.getTransaction().begin();
//        em.persist(a);
//        em.getTransaction().commit();
//
//        em.getTransaction().begin();
//        String sql = "SELECT c FROM Shelf c Where c.id =" + id_shelf +"";
//        TypedQuery<Shelf> query = em.createQuery(sql, Shelf.class);
//        List<Shelf> results = query.getResultList();
//        results.get(0).addPallet(a);
//        em.getTransaction().commit();
//
//        em.getTransaction().begin();
//        String sqlP = "SELECT c FROM Product c Where c.id =" + idProduct +"";
//        TypedQuery<Product> queryP = em.createQuery(sqlP, Product.class);
//        List<Product> resultsP = queryP.getResultList();
////        resultsP.get(0).addPallet(a);
//        em.getTransaction().commit();
//    }
//    public void removeShelf(int id )
}
