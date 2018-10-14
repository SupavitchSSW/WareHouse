package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.simple.*;


public class OrderReadWrite {

    public OrderReadWrite() {

    }

    public static void run(){
        System.out.println("OrderReadWrite online ...");
        writeProductList();
    }

    public static void writeProductList(){
        ObservableList<Product> products = getProducts();
        JSONObject obj = new JSONObject();
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();

        //add product to json
        for(Product p:products){
            obj.put("price",p.getPrice());
            obj.put("brand",p.getBrand());
            obj.put("id",p.getId());
            obj.put("name",p.getName());
            array.add(obj.clone());
        }

        json.put("products",array);
        System.out.println(json.toString());
    }

    public static void readOrder(){}

    public static void writeOrderRespond(){}

    public static ObservableList<Product> getProducts(){
        ObservableList<Product> products = FXCollections.observableArrayList();
        products.add(new Product(1,12,"ยาสีฟัน","ฝนฝน"));
        products.add(new Product(2,2,"dss","Por_shop2"));
        products.add(new Product(3,22,"dsd","Por_shop3"));
        products.add(new Product(4,1,"jud","ng"));
        products.add(new Product(5,6,"gg","pv"));
        return products;
    }

}
