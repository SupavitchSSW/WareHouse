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

    public ObservableList<Product> getAllProduct(){
        catalogueEntry = warehouse.getCatalogueEntry();
        List<Product> results = catalogueEntry.getProducts();
        ObservableList<Product> products = FXCollections.observableArrayList(results);
        return products;
    }
    public void changeProductDetail(int productId, String name, String brand, int price, int amountInPack, int packCapacity) {
        warehouse.setProductDetailCatalogue(productId,price,amountInPack,packCapacity,name,brand);
        shelfs = warehouse.getAllShelf();
        for (Shelf s : shelfs) {
            for (Pallet p : s.getPallets()) {
                warehouse.setProductDetailPallet(p.getId(),productId,price,amountInPack,packCapacity,name,brand);
            }
        }
    }
    public void changeProductQuantity(int productId, int qt){
//        warehouse.setProductQtPallet(palletId,productId,qt);
//        warehouse.setProductQtCatalogue(productId,qt);
//        warehouse.setWarehouseCapacity(warehouse.getWarehouseCapacity());
    }
    public void createTransaction (int productId, int changeQuantity, Date date, String type) {
        warehouse.createTransaction(productId, changeQuantity, date, type);
    }

    public void deleteProduct(int productId, int quantity, int packCapacity) {
        warehouse.removeProductCatalogueEntry(productId);
        warehouse.setWarehouseCapacity(warehouse.getWarehouseCapacity()+(quantity*packCapacity));

        shelfs = warehouse.getAllShelf();
        for (Shelf s : shelfs) {
            for (Pallet p : s.getPallets()) {
                warehouse.removeProductPallet(p.getId(),productId);
            }
        }
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

            int qtCheck = quantity;
            shelfs = warehouse.getAllShelf();
            for (Shelf s : shelfs){
                if (qtCheck==0) break;
                for (Pallet p : s.getPallets()) {
                    int q = (p.getMaxCapacity()-p.getCapacity()) / packCapacity;
                    if (q >= qtCheck){
                        qtCheck = 0;
                        break;
                    }
                    else if (q != 0) {
                        qtCheck -= q;
                    }
                }
                int pIns = s.getPallets().size();
                while (qtCheck!=0 && pIns != s.getMaxPallet()) {
                    int q = 100 / packCapacity;
                    if (q >= qt){
                        qtCheck = 0;
                        break;
                    }
                    else if (q != 0) {
                        qtCheck -= q;
                    }
                    pIns+=1;
                }
            }

            if (qtCheck != 0){
                Alert alertError = new Alert(Alert.AlertType.ERROR);
                alertError.setTitle("Can Not Add Product to Warehouse");
                alertError.setHeaderText(null);
                alertError.setContentText("Can't Put Product to Pallet in Warehouse");
                alertError.showAndWait();
            }
            else {
                warehouse.addProductToCatalogueEntry(productId, quantity, price, amountInPack, packCapacity, name, brand);
                warehouse.setLastId(productId);
                warehouse.setWarehouseCapacity(warehouse.getWarehouseCapacity() - (quantity * packCapacity));

                for (Shelf s : shelfs) {
                    if (qt == 0) break;
                    for (Pallet p : s.getPallets()) {
                        int quantityCanAdd = (p.getMaxCapacity() - p.getCapacity()) / packCapacity;
                        if (quantityCanAdd >= quantity) {
                            warehouse.addProductToPallet(p.getId(), productId, quantity, price, amountInPack, packCapacity, name, brand);
                            qt = 0;
                            break;
                        } else if (quantityCanAdd != 0) {
                            warehouse.addProductToPallet(p.getId(), productId, quantityCanAdd, price, amountInPack, packCapacity, name, brand);
                            qt -= quantityCanAdd;
                        }
                    }
                    while (qt != 0 && (s.getMaxPallet() - s.getPallets().size()) != 0) {
                        warehouse.addPallet(s.getId(), 0, 100);
                        Pallet newPallet = s.getPallets().get(s.getPallets().size() - 1);
                        int qtCanAdd = newPallet.getMaxCapacity() / packCapacity;
                        if (qtCanAdd >= qt) {
                            warehouse.addProductToPallet(newPallet.getId(), productId, qt, price, amountInPack, packCapacity, name, brand);
                            qt = 0;
                            break;
                        } else if (qtCanAdd != 0) {
                            warehouse.addProductToPallet(newPallet.getId(), productId, qtCanAdd, price, amountInPack, packCapacity, name, brand);
                            qt -= qtCanAdd;
                        }
                    }
                }
            }
        }
    }

    public void createShelf(String name, int maxPallet) {
        warehouse.createShelf(name,maxPallet);
        warehouse.setWarehouseCapacity(warehouse.getWarehouseCapacity()+100*maxPallet);
    }
}
