package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ordermanagement.Order;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;


public class OrderReadWrite {

    public OrderReadWrite() {

    }

    public static void run(){
        System.out.println("OrderReadWrite online ...");
        //writeProductList();
        readOrder();
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

    public static void readOrder(){
        Order order = new Order();
        JSONParser parser = new JSONParser();
        JSONObject json = new JSONObject();
        JSONObject obj = new JSONObject();
        JSONArray array = new JSONArray();
        String s = "{\"owner\":\"Por\",\"products\":[{\"name\":\"ยาสีฟัน\",\"id\":1,\"brand\":\"ฝนฝน\",\"price\":0},{\"name\":\"dss\",\"id\":2,\"brand\":\"Por_shop2\",\"price\":0},{\"name\":\"dsd\",\"id\":3,\"brand\":\"Por_shop3\",\"price\":0},{\"name\":\"jud\",\"id\":4,\"brand\":\"ng\",\"price\":0},{\"name\":\"gg\",\"id\":5,\"brand\":\"pv\",\"price\":0}]}";

        try {
            json = (JSONObject) parser.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        array = (JSONArray) json.get("products");

        for(int i =0;i<array.size();i++){
            obj = (JSONObject) array.get(i);
            System.out.println(obj.toString());
        }


    }

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

    //{"owner":"Por","products":[{"name":"ยาสีฟัน","id":1,"brand":"ฝนฝน","price":0},{"name":"dss","id":2,"brand":"Por_shop2","price":0},{"name":"dsd","id":3,"brand":"Por_shop3","price":0},{"name":"jud","id":4,"brand":"ng","price":0},{"name":"gg","id":5,"brand":"pv","price":0}]}

}
