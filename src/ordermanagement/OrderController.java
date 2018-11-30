package ordermanagement;

import connectionDB.serviceDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import product.Product;
import sample.OrderReadWrite;
import user.User;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;
import java.util.List;

public class OrderController {
    private Order selectOrder;
    private User currentUser;
    private static serviceDB warehouse;

    public OrderController(serviceDB warehouse,User currentUser) {
        this.warehouse = warehouse;
        this.currentUser = currentUser;
    }

    // ------------------------------------ Order List Controller

    public ObservableList<Order> getAllOrder(){
        ObservableList<Order> orders = FXCollections.observableArrayList(warehouse.getAllOrder());
        return orders;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public Order getSelectOrder() {
        return selectOrder;
    }

    public void setSelectOrder(Order selectOrder) {
        this.selectOrder = selectOrder;
        updateOrder();
    }

    // ------------------------------------- Order Detail Controller

    public void updateOrder(){
        //update product quantity to select order
        for ( OrderProduct entry: selectOrder.getOrderProducts() ) {
            entry.setQuantity(warehouse.getQtbyID(entry.getProductId()));
        }
    }

    public void acceptOrder(){
        //change order status
        warehouse.setOrderStatus(selectOrder.getId(),"approve");


        // (TODO) use method in productController !!
        //create transaction
        Date date = new Date();
        for ( OrderProduct orderProduct: selectOrder.getOrderProducts()){
            warehouse.createTransaction(orderProduct.getProductId(),orderProduct.getSendQuantity()*-1,date,"approveOrder");
        }

        //write respond back to customer via json
        try {
            writeRespondOrder(selectOrder);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // (TODO) use method in productController !!
        //update product quantity
        List<OrderProduct> orderProducts = selectOrder.getOrderProducts();
        for(OrderProduct o : orderProducts){
            warehouse.setProductQuantity(o.getProductId(),o.getQuantity()-o.getSendQuantity());
        }
    }

    public void rejectOrder(){
        warehouse.setOrderStatus(selectOrder.getId(),"reject");
    }

    // =------------------------------------ Order Read Write

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

    public static void writeRespondOrder(Order order) throws IOException{
        List<OrderProduct> orderProducts = order.getOrderProducts();
        JSONObject obj = new JSONObject();
        JSONObject json = new JSONObject();
        JSONArray array = new JSONArray();

        //add product to json
        for(OrderProduct o: orderProducts){
            obj.put("id",o.getProductId());
            obj.put("name",o.getName());
            obj.put("brand",o.getBrand());
            obj.put("amount",o.getOrderQuantity());
            obj.put("send",o.getSendQuantity());
            array.add(obj.clone());
        }
        json.put("name",order.getName());
        json.put("date",new Date().getTime());
        json.put("products",array);

        //write to file
        String fileName = "respond_"+order.getName();
        System.out.println("Write Product list to file : "+fileName);
        System.out.println(json.toString());
        FileWriter out = null;

        try{
            out = new FileWriter("OrderAPI/out/"+fileName+".txt");
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

        //read folder
        File folder = new File("OrderAPI/in");
        File[] listOfFile = folder.listFiles();
        FileReader in = null;

        for(int i = 0;i<listOfFile.length;i++){
            System.out.println(listOfFile[i].getName());
        }

        for(int j =0;j<listOfFile.length;j++){
            String inputJSON ="";
            try {
                in = new FileReader("OrderAPI/in/"+listOfFile[j].getName());
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

            // create Order
            int id = warehouse.createOrder(json.get("name").toString(),json.get("owner").toString());
            // add OrderProduct to order
            array = (JSONArray) json.get("products");
            for(int i =0;i<array.size();i++){
                obj = (JSONObject) array.get(i);
                //order.addOrderProduct(new OrderProduct(Integer.parseInt(obj.get("id").toString()),obj.get("name").toString(),obj.get("brand").toString(),Integer.parseInt(obj.get("amount").toString())));
                warehouse.addOrderproduct(id,Integer.parseInt(obj.get("id").toString()),obj.get("name").toString(),obj.get("brand").toString(),Integer.parseInt(obj.get("amount").toString()));
            }

            System.out.println("Read JSON : "+inputJSON);
            System.out.println(order.toString());
        }
    }

    public static ObservableList<product.Product> getProducts(){
        List<product.Product> results = warehouse.getAllProduct();
        ObservableList<product.Product> products = FXCollections.observableArrayList(results);
        return products;
    }
}
