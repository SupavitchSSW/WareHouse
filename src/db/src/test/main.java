package test;
import java.util.*;
import javax.persistence.*;
import connectionDB.*;
import product.Product;

public class main {


    public static void main(String[] args) {
        serviceDB s = new serviceDB();
        List<Product> results = s.getAllProduct();
        s.setProductBrand(2,"luis");
        for (Product p : results) {
            System.out.println(p);
        }
        s.closeConnection();

    }
}
