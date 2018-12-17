package productManagement;

import connectionDB.serviceDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import product.CatalogueEntry;
import product.Product;

import java.util.Date;
import java.util.List;

public class ProductController {
    private serviceDB warehouse;
    private CatalogueEntry catalogueEntry;

    public ProductController(serviceDB warehouse) {
        this.warehouse = warehouse;
    }

//    need to change
    public ObservableList<Product> getAllProduct(){
        List<Product> results = warehouse.getAllProduct();
        ObservableList<Product> products = FXCollections.observableArrayList(results);
        return products;
    }
    public void changeProductDetail(int productId, String name, String brand, int price, int amountInPack, int packCapacity) {
        warehouse.setProductName(productId,name);
        warehouse.setProductBrand(productId,brand);
//        warehouse.setProductDetailPallet();
//        warehouse.setProductDetailCatalogueEntry();
    }
    public void changeProductQuantity(){
//        warehouse.setProductQuantityPallet();
//        warehouse.setProductQuantityCatalogueEntry();
    }
    public void createTransaction (int productId, int changeQuantity, Date date, String type) {
        warehouse.createTransaction(productId, changeQuantity, date, type);
    }
    public void deleteProduct(int productId) {
//        warehouse
    }
    public void addNewProduct(String name, String brand, int price, int amountInPack, int packCapacity, int quantity) {

    }
    public void createShelf(String name, int maxPallet) {
//        warehouse.createShelf(name,maxPallet);
    }
//    public void removeShelf(int id) {
//
//    }
}
