package sample;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ordermanagement.Order;
import ordermanagement.OrderProduct;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;


public class OrderReadWrite {

    public OrderReadWrite() {

    }

    public static void run(){
        System.out.println("OrderReadWrite online ...");

        try {
            writeProductList();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            readOrder();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeProductList() throws IOException {
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
        json.put("name","PorShop_0011");
        json.put("date",new Date(2000,1,2).getTime());
        json.put("products",array);

        //write to file
        System.out.println("Write Product list to file : ProductList.txt");
        System.out.println(json.toString());
        FileWriter out = null;

        try{
            out = new FileWriter("OrderAPI/out/ProductList.txt");
            out.write(json.toString());
        }finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public static Order readOrder() throws IOException{
        Order order = new Order();
        JSONParser parser = new JSONParser();
        JSONObject json = new JSONObject();
        JSONObject obj = new JSONObject();
        JSONArray array = new JSONArray();

        //read file
        //String s = "{\"date\":60907654800000,\"name\":\"PorShop_0011\",\"owner\":\"Por\",\"products\":[{\"name\":\"ยาสีฟัน\",\"id\":1,\"brand\":\"ฝนฝน\",\"amount\":20},{\"name\":\"dss\",\"id\":2,\"brand\":\"Por_shop2\",\"amount\":12},{\"name\":\"dsd\",\"id\":3,\"brand\":\"Por_shop3\",\"amount\":33},{\"name\":\"jud\",\"id\":4,\"brand\":\"ng\",\"amount\":100},{\"name\":\"gg\",\"id\":5,\"brand\":\"pv\",\"amount\":5}]}";
        String inputJSON ="";
        FileReader in = null;

        try {
            in = new FileReader("OrderAPI/in/order1.txt");
            int c;
            while ((c = in.read()) != -1) {
                inputJSON = inputJSON+(char) c;
            }
        }finally {
            if (in != null) {
                in.close();
            }
        }

        //convert String to json object
        try {
            json = (JSONObject) parser.parse(inputJSON);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        order.setOwner(json.get("owner").toString());
        order.setName(json.get("name").toString());
        order.setDate(new Date((Long) json.get("date")));

        // add product to order
        array = (JSONArray) json.get("products");
        for(int i =0;i<array.size();i++){
            obj = (JSONObject) array.get(i);
            order.addOrderProduct(new OrderProduct(Integer.parseInt(obj.get("id").toString()),obj.get("name").toString(),obj.get("brand").toString(),Integer.parseInt(obj.get("amount").toString())));
        }

        System.out.println("Read JSON : "+inputJSON);
        System.out.println(order.toString());
        return order;
    }

    public static void writeOrderRespond(){
        JSONObject json = new JSONObject();
    }

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
