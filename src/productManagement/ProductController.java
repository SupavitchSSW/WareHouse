package productManagement;

import connectionDB.serviceDB;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import product.CatalogueEntry;
import product.Pallet;
import product.Product;
import product.Shelf;

import java.util.Date;
import java.util.List;

public class ProductController {
    private serviceDB warehouse;
    private CatalogueEntry catalogueEntry;
    private List<Shelf> shelfs;

    public ProductController(serviceDB warehouse) {
        this.warehouse = warehouse;
    }

//    need to change
    public ObservableList<Product> getAllProduct(){
        catalogueEntry = warehouse.getCatalogueEntry();
        List<Product> results = catalogueEntry.getProducts();   //warehouse.getAllProduct();
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
        warehouse.removeProductCatalogueEntry(productId);
//        getAllShelf -> loop all pallet in all shelf -> removeProductPallet
    }
    public void addNewProduct(String name, String brand, int price, int amountInPack, int packCapacity, int quantity) {
        int qt = quantity;
        if (quantity*packCapacity > warehouse.getWarehouseCapacity()){
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Can Not Add Product to Warehouse");
            alertError.setHeaderText(null);
            alertError.setContentText("Warehouse have not enough capacity");
            alertError.showAndWait();
        }
        else {
            int productId = catalogueEntry.getLastid() + 1;
            warehouse.addProductToCatalogueEntry(productId, quantity, price, amountInPack, packCapacity, name, brand);
            warehouse.setLastId(productId);
            warehouse.setWarehouseCapacity(warehouse.getWarehouseCapacity()-(quantity*packCapacity));

            shelfs = warehouse.getAllShelf();
            for (Shelf s : shelfs){
                if (qt==0) break;
                for (Pallet p : s.getPallets()) {
                    int quantityCanAdd = (p.getMaxCapacity()-p.getCapacity()) / packCapacity;
                    if (quantityCanAdd >= quantity){
                        warehouse.addProductToPallet(p.getId(),productId,quantity,price,amountInPack,packCapacity,name,brand);
                        qt = 0;
                        break;
                    }
                    else if (quantityCanAdd != 0) {
                        warehouse.addProductToPallet(p.getId(),productId,quantityCanAdd,price,amountInPack,packCapacity,name,brand);
                        qt -= quantityCanAdd;
                    }
                }
                while (qt!=0 && (s.getMaxPallet()-s.getPallets().size())!=0) {
                    warehouse.addPallet(s.getId(),0,100);
                    Pallet newPallet = s.getPallets().get(s.getPallets().size()-1);
                    int quantityCanAdd = newPallet.getMaxCapacity() / packCapacity;
                    if (quantityCanAdd >= qt){
                        warehouse.addProductToPallet(newPallet.getId(),productId,qt,price,amountInPack,packCapacity,name,brand);
                        qt = 0;
                        break;
                    }
                    else if (quantityCanAdd != 0) {
                        warehouse.addProductToPallet(newPallet.getId(),productId,quantityCanAdd,price,amountInPack,packCapacity,name,brand);
                        qt -= quantityCanAdd;
                    }
                }
            }
        }
    }
    public void createShelf(String name, int maxPallet) {
        warehouse.createShelf(name,maxPallet);
        warehouse.setWarehouseCapacity(100*maxPallet);
    }
}
