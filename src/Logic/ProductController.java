package Logic;

import Storage.WarehouseSystem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import Storage.CatalogueEntry;
import Storage.Pallet;
import Storage.Product;
import Storage.Shelf;
import Storage.User;

import java.util.Date;
import java.util.List;

public class ProductController {
    private WarehouseSystem warehouse;
    private CatalogueEntry catalogueEntry;
    private List<Shelf> shelfs;

    public ProductController(WarehouseSystem warehouse) {
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

    public void changeProductQuantity(int productId, String name, String brand, int price, int amountInPack, int newQt, int changedQt, int packCapacity , String type){

        if (warehouse.getWarehouseCapacity()-(changedQt*packCapacity)<0) {
            Alert alertError = new Alert(Alert.AlertType.ERROR);
            alertError.setTitle("Can Not Edit Product Quantity");
            alertError.setHeaderText(null);
            alertError.setContentText("Warehouse have not enough capacity");
            alertError.showAndWait();
        }
        else {
            if (changedQt>=0) {
                int qtCheck = changedQt;
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
                        if (q >= qtCheck){
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
                    alertError.setTitle("Can Not Edit Product Quantity");
                    alertError.setHeaderText(null);
                    alertError.setContentText("Can't Put Product to Pallet in Warehouse");
                    alertError.showAndWait();
                }
                else {
                    int qt = changedQt;
                    shelfs = warehouse.getAllShelf();
                    for (Shelf s : shelfs) {
                        if (qt == 0) break;
                        for (Pallet p : s.getPallets()) {
                            int quantityCanAdd = (p.getMaxCapacity() - p.getCapacity()) / packCapacity;
                            if (quantityCanAdd >= qt) {
                                warehouse.addProductToPallet(p.getId(), productId, qt, price, amountInPack, packCapacity, name, brand);
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
                            int qtCanAdd = (newPallet.getMaxCapacity()- newPallet.getCapacity()) / packCapacity;
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
                    warehouse.setProductQtCatalogue(productId, newQt);
                    warehouse.setWarehouseCapacity(warehouse.getWarehouseCapacity() - (changedQt * packCapacity));
                }
            }
            else {
                int qt = -changedQt;
                shelfs = warehouse.getAllShelf();
                for (Shelf s : shelfs){
                    if (qt==0) break;
                    for (Pallet p : s.getPallets()){
                        if (qt==0) break;
                        for (Product pd : p.getProducts()){
                            if (qt==0) break;
                            if (pd.getProductId()==productId) {
                                int haveQt = pd.getQuantity();
                                if (haveQt >= qt) {
                                    warehouse.setProductQtPallet(p.getId(),productId,haveQt-qt);
                                    qt=0;
                                    break;
                                }
                                else{
                                    warehouse.setProductQtPallet(p.getId(),productId,0);
                                    qt -= haveQt;
                                }
                            }
                        }
                    }
                }
                warehouse.setProductQtCatalogue(productId, newQt);
                warehouse.setWarehouseCapacity(warehouse.getWarehouseCapacity() - (changedQt * packCapacity));
            }
            warehouse.clearEmptyProduct();
            warehouse.createTransaction(productId,changedQt,new Date(),type);
        }
    }

    public void changeProductQuantity(int productId, int changedQt, String type) {
        int packCapacity = 0;
        int quantity = 0;
        for (Product pd : catalogueEntry.getProducts()){
            if (pd.getProductId() == productId){
                packCapacity = pd.getPackCapacity();
                quantity = pd.getQuantity();
                break;
            }
        }
        int qt = -changedQt;
        shelfs = warehouse.getAllShelf();
        for (Shelf s : shelfs){
            if (qt==0) break;
            for (Pallet p : s.getPallets()){
                if (qt==0) break;
                for (Product pd : p.getProducts()){
                    if (qt==0) break;
                    if (pd.getProductId()==productId) {
                        int haveQt = pd.getQuantity();
                        if (haveQt >= qt) {
                            warehouse.setProductQtPallet(p.getId(),productId,haveQt-qt);
                            qt=0;
                            break;
                        }
                        else{
                            warehouse.setProductQtPallet(p.getId(),productId,0);
                            qt -= haveQt;
                        }
                    }
                }
            }
        }
        warehouse.clearEmptyProduct();
        warehouse.setProductQtCatalogue(productId, quantity+changedQt);
        warehouse.setWarehouseCapacity(warehouse.getWarehouseCapacity() - (changedQt * packCapacity));
        warehouse.createTransaction(productId,changedQt,new Date(),type);
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

    public void addNewProduct(String name, String brand, int price, int amountInPack, int packCapacity, int quantity, Date date) {
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
                    if (q >= qtCheck){
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

                warehouse.createTransaction(catalogueEntry.getLastid(),quantity,date,"addNewProduct");

                int qty = quantity;
                for (Shelf s : shelfs) {
                    if (qty == 0) break;
                    for (Pallet p : s.getPallets()) {
                        int quantityCanAdd = (p.getMaxCapacity() - p.getCapacity()) / packCapacity;
                        if (quantityCanAdd >= qty) {
                            warehouse.addProductToPallet(p.getId(), productId, qty, price, amountInPack, packCapacity, name, brand);
                            qty = 0;
                            break;
                        } else if (quantityCanAdd != 0) {
                            warehouse.addProductToPallet(p.getId(), productId, quantityCanAdd, price, amountInPack, packCapacity, name, brand);
                            qty -= quantityCanAdd;
                        }
                    }
                    while (qty != 0 && (s.getMaxPallet() - s.getPallets().size()) != 0) {
                        warehouse.addPallet(s.getId(), 0, 100);
                        Pallet newPallet = s.getPallets().get(s.getPallets().size() - 1);
                        int qtCanAdd = (newPallet.getMaxCapacity()- newPallet.getCapacity()) / packCapacity;
                        if (qtCanAdd >= qty) {
                            warehouse.addProductToPallet(newPallet.getId(), productId, qty, price, amountInPack, packCapacity, name, brand);
                            qty = 0;
                            break;
                        } else if (qtCanAdd != 0) {
                            warehouse.addProductToPallet(newPallet.getId(), productId, qtCanAdd, price, amountInPack, packCapacity, name, brand);
                            qty -= qtCanAdd;
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

    public User getCurrentUser() {
        return warehouse.getCurrentUser();
    }

    public String shelfInWarehouse() {
        String st = "Shelf in Warehouse\n";
        shelfs = warehouse.getAllShelf();
        for (Shelf s : shelfs) {
            st += "\tshelf: "+s.getName()+"\t\tpallet: "+s.getPallets().size()+"/"+s.getMaxPallet()+"\n";
        }
        return st;
    }
}
