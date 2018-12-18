package productManagement;

import connectionDB.serviceDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import product.CatalogueEntry;
import product.Pallet;
import product.Product;
import product.Shelf;

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
//        getAllShelf -> loop all pallet in all shelf -> removeProductPallet
//        removeProductCatalogueEntry
    }
    public void addNewProduct(String name, String brand, int price, int amountInPack, int packCapacity, int quantity) {
//        ++catalogueEntry.getLastid()
//        check wh capacity -> getAllShelf -> empty pallet or addPallet
//        for (Shelf s: shelfs) {}
//        for (Pallet p: s.pallets) {
//            quantityCanAdd = (p.getMaxCapacity()-p.getCapacity()) / packCapacity;
//            if (quantityCanAdd >= quantity) {
//                addProductToPallet(quantity)
//                quantity=0;
//            }
//            else {
//                addProductToPallet(quantityCanAdd)
//            }
//            quantity -= quantityCanAdd;
//        }
//        while (quantity!=0 && (s.getMaxPallet()-s.pallet.size())!=0 ) {
//            addPallet(s.getId(),0,100);
//            quantityCanAdd = s.pallets[-1].getMaxCapacity() / packCapacity;
//            if (quantityCanAdd >= quantity) {
//                addProductToPallet(quantity)
//                quantity=0;
//            }
//            else {
//                addProductToPallet(quantityCanAdd)
//            }
//            quantity -= quantityCanAdd;
//        }
    }
    public void createShelf(String name, int maxPallet) {
        warehouse.createShelf(name,maxPallet);
    }
}
