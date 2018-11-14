package sample;
import connectionDB.serviceDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import ordermanagement.Order;
import ordermanagement.OrderProduct;
import org.json.simple.*;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import product.Product;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;


public class OrderReadWrite {
    static serviceDB database;

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
        ObservableList<product.Product> products = getProducts();
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
        json.put("date",new Date().getTime());
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

    public static void readOrder() throws IOException{
        Order order = new Order();
        JSONParser parser = new JSONParser();
        JSONObject json = new JSONObject();
        JSONObject obj = new JSONObject();
        JSONArray array = new JSONArray();

        //read file
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

        // create Order
        int id = database.createOrder(json.get("name").toString(),json.get("owner").toString());
        // add OrderProduct to order
        array = (JSONArray) json.get("products");
        for(int i =0;i<array.size();i++){
            obj = (JSONObject) array.get(i);
            //order.addOrderProduct(new OrderProduct(Integer.parseInt(obj.get("id").toString()),obj.get("name").toString(),obj.get("brand").toString(),Integer.parseInt(obj.get("amount").toString())));
            database.addOrderproduct(id,Integer.parseInt(obj.get("id").toString()),obj.get("name").toString(),obj.get("brand").toString(),Integer.parseInt(obj.get("amount").toString()));
        }

        System.out.println("Read JSON : "+inputJSON);
        System.out.println(order.toString());
    }

    public static void writeOrderRespond(){
        JSONObject json = new JSONObject();
    }

    public static ObservableList<product.Product> getProducts(){
        List<product.Product> results = database.getAllProduct();
        ObservableList<product.Product> products = FXCollections.observableArrayList(results);
        return products;
    }

    public static serviceDB getDatabase() {
        return database;
    }

    public static void setDatabase(serviceDB database) {
        OrderReadWrite.database = database;
    }
}
