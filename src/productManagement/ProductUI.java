package productManagement;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.StageStyle;
import sample.Controller;
import sample.PageController;
import product.Product;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import connectionDB.*;
import user.Manager;
import user.Staff;

public class ProductUI implements Controller {
    PageController pageController;
    serviceDB database;
    private TableView productListTable;
    private ObservableList<Product> products;
    private ObservableList<Product> subEntries;
    private Product selectedProduct;
    private int index, lastID;
    private ProductController productController;
    private Button summaryBt,userSearchBt,addProductBt,addShelfBt;

    public ProductUI(PageController pageController, ProductController productController){
        this.pageController = pageController;
        this.productController = productController;
        this.products = this.productController.getAllProduct();
    }

    public void initilize(){
        Scene scene = pageController.getScene("mainPage");
        Button mainBt = (Button) scene.lookup("#mainButton");
        addProductBt = (Button) scene.lookup("#addProductButton");
        summaryBt = (Button) scene.lookup("#summaryButton");
        Button orderBt = (Button) scene.lookup("#orderButton");
        Button logoutBt = (Button) scene.lookup("#logoutButton");
        userSearchBt = (Button) scene.lookup("#userSearchButton");
        Button userInfoBt = (Button) scene.lookup("#userInfo");
        TextField searchBox = (TextField) scene.lookup("#searchBox");
        addShelfBt = (Button) scene.lookup("#addShelfButton");

        searchBox.setPromptText("Search");
        searchBox.textProperty().addListener((observable, oldVal, newVal) -> {
            handleSearchByKey((String) oldVal, (String) newVal);
        });

        //table setup
        productListTable = (TableView) scene.lookup("#productList");

        // column name
        TableColumn<Product,Integer> idColumn = new TableColumn<>("Product ID");
        TableColumn<Product,String> nameColumn = new TableColumn<>("Product Name");
        TableColumn<Product,String> brandColumn = new TableColumn<>("Product Brand");
        TableColumn<Product,Integer> quantityColumn = new TableColumn<>("Product Quantity");
        TableColumn<Product,Integer> priceColumn = new TableColumn<>("Product Price");

        idColumn.setMinWidth(140);
        nameColumn.setMinWidth(175);
        brandColumn.setMinWidth(175);
        quantityColumn.setMinWidth(170);
        priceColumn.setMinWidth(140);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("productId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        productListTable.getColumns().addAll(idColumn,nameColumn,brandColumn,quantityColumn,priceColumn);

        productListTable.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2 && (productController.getCurrentUser() instanceof Manager)) {
                    index = productListTable.getSelectionModel().getSelectedIndex();

                    Dialog<ButtonType> propertyDialog = new Dialog<>();
                    propertyDialog.initStyle(StageStyle.UTILITY);
                    propertyDialog.setTitle("Product Property");

                    GridPane propertyGrid = new GridPane();
                    propertyGrid.setHgap(10);
                    propertyGrid.setVgap(20);
                    propertyGrid.setPadding(new Insets(20, 250, 10, 20));

                    if (productListTable.getItems() == subEntries) {
                        selectedProduct = subEntries.get(index);
                    } else {
                        selectedProduct = products.get(index);
                    }

                    propertyDialog.setHeaderText("Product ID: \t" + Integer.toString(selectedProduct.getProductId()));
                    propertyGrid.add(new Label("Product Name:"), 0, 0);
                    propertyGrid.add(new Label(selectedProduct.getName()), 1, 0);
                    propertyGrid.add(new Label("Product Brand:"), 0, 1);
                    propertyGrid.add(new Label(selectedProduct.getBrand()), 1, 1);
                    propertyGrid.add(new Label("Product Quantity:"), 0, 2);
                    propertyGrid.add(new Label(Integer.toString(selectedProduct.getQuantity())), 1, 2);
                    propertyGrid.add(new Label("Product Price:"), 0, 3);
                    propertyGrid.add(new Label(Integer.toString(selectedProduct.getPrice())), 1, 3);
                    propertyGrid.add(new Label("Product Amount in Pack:"), 0, 4);
                    propertyGrid.add(new Label(Integer.toString(selectedProduct.getAmountInPack())), 1, 4);
                    propertyGrid.add(new Label("Pack Capacity:"), 0, 5);
                    propertyGrid.add(new Label(Integer.toString(selectedProduct.getPackCapacity())), 1, 5);
                    propertyDialog.getDialogPane().setContent(propertyGrid);

                    ButtonType editButtonType = new ButtonType("Edit");
                    ButtonType doneButtonType = new ButtonType("Done");
                    propertyDialog.getDialogPane().getButtonTypes().addAll(doneButtonType, editButtonType);

                    Optional<ButtonType> propertyResult = propertyDialog.showAndWait();
                    if (propertyResult.get() == editButtonType) {
                        Dialog<ButtonType> editDialog = new Dialog<>();
                        editDialog.initStyle(StageStyle.UTILITY);
                        editDialog.setTitle("Edit Product Property");
                        editDialog.setHeaderText("Product ID: \t" + Integer.toString(selectedProduct.getProductId()));

                        GridPane editGrid = new GridPane();
                        editGrid.setHgap(10);
                        editGrid.setVgap(10);
                        editGrid.setPadding(new Insets(10, 180, 20, 20));

                        TextField name = new TextField();
                        name.setText(selectedProduct.getName());
                        TextField brand = new TextField();
                        brand.setText(selectedProduct.getBrand());
                        TextField quantity = new TextField();
                        quantity.setText(Integer.toString(selectedProduct.getQuantity()));
                        TextField price = new TextField();
                        price.setText(Integer.toString(selectedProduct.getPrice()));

                        editGrid.add(new Label("Name:"), 0, 1);
                        editGrid.add(name, 1, 1);
                        editGrid.add(new Label("Brand:"), 0, 2);
                        editGrid.add(brand, 1, 2);
                        editGrid.add(new Label("Quantity:"), 0, 3);
                        editGrid.add(quantity, 1, 3);
                        editGrid.add(new Label("Price:"), 0, 4);
                        editGrid.add(price, 1, 4);

                        ButtonType deleteButtonType = new ButtonType("Delete Product");

                        editDialog.getDialogPane().setContent(editGrid);
                        editDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL, deleteButtonType);
                        Optional<ButtonType> editResult = editDialog.showAndWait();
                        if (editResult.get() == ButtonType.OK) {
                            if (tryParse(quantity.getText()) == null || tryParse(price.getText()) == null) {
                                Alert alertError = new Alert(Alert.AlertType.ERROR);
                                alertError.setTitle("Error to Edit Product");
                                alertError.setHeaderText(null);
                                alertError.setContentText("Invalid information");
                                alertError.showAndWait();
                            } else {
//                                <<<<< edit quantity create Transaction >>>>>
                                if (Integer.parseInt(quantity.getText()) != selectedProduct.getQuantity()) {
                                    int newQt = Integer.parseInt(quantity.getText());
                                    productController.changeProductQuantity(selectedProduct.getProductId(),name.getText(),brand.getText(),
                                            Integer.parseInt(price.getText()),selectedProduct.getAmountInPack(),newQt,newQt-selectedProduct.getQuantity(),
                                            selectedProduct.getPackCapacity(),"editQuantity");
                                }
//                                <<<<< edit Product >>>>>
                                productController.changeProductDetail(selectedProduct.getProductId(),name.getText(),brand.getText(),
                                        Integer.parseInt(price.getText()),selectedProduct.getAmountInPack(),selectedProduct.getPackCapacity());
                                productListTable.refresh();
                            }
                        } else if (editResult.get() == deleteButtonType) {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Delete Product");
                            alert.setHeaderText(null);
                            alert.setContentText("Are you sure you want to delete this product?");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK){
                                if (productListTable.getItems() == subEntries) {
                                    productController.deleteProduct(selectedProduct.getProductId(),selectedProduct.getQuantity(),selectedProduct.getPackCapacity());
                                    searchBox.clear();
                                    productListTable.setItems(products);
                                } else {
                                    productController.deleteProduct(selectedProduct.getProductId(),selectedProduct.getQuantity(),selectedProduct.getPackCapacity());
                                }
                                products = productController.getAllProduct();
                                productListTable.setItems(products);
                                productListTable.refresh();
                            }
                        }
                    }
                }
            }
        });
        addProductBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
//                if (currentUser.getRole().equals("Manager")) {
                    Dialog<ButtonType> addProductDialog = new Dialog<>();
                    addProductDialog.initStyle(StageStyle.UTILITY);
                    addProductDialog.setTitle("Add Product");

                    GridPane addProductGrid = new GridPane();
                    addProductGrid.setHgap(10);
                    addProductGrid.setVgap(20);
                    addProductGrid.setPadding(new Insets(20, 50, 10, 20));

                    TextField productName = new TextField();
                    TextField productBrand = new TextField();
                    TextField productQuantity = new TextField();
                    TextField productPrice = new TextField();
                    TextField productAmountInPack = new TextField();
                    TextField productPackCapacity = new TextField();

                    addProductGrid.add(new Label("Name:"), 0, 1);
                    addProductGrid.add(productName, 1, 1);
                    addProductGrid.add(new Label("Brand:"), 3, 1);
                    addProductGrid.add(productBrand, 4, 1);
                    addProductGrid.add(new Label("Quantity:"), 0, 3);
                    addProductGrid.add(productQuantity, 1, 3);
                    addProductGrid.add(new Label("Price:"), 3, 3);
                    addProductGrid.add(productPrice, 4, 3);
                    addProductGrid.add(new Label("Amount in Pack:"), 0, 5);
                    addProductGrid.add(productAmountInPack, 1, 5);
                    addProductGrid.add(new Label("Pack Capacity:"), 3, 5);
                    addProductGrid.add(productPackCapacity, 4, 5);

                    addProductDialog.getDialogPane().setContent(addProductGrid);

                    addProductDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
                    Optional<ButtonType> addProductResult = addProductDialog.showAndWait();
                    if (addProductResult.get() == ButtonType.OK) {
                        if (productName.getText().isEmpty() || productBrand.getText().isEmpty() || productQuantity.getText().isEmpty()
                                || productPrice.getText().isEmpty() || productPackCapacity.getText().isEmpty()
                                || productAmountInPack.getText().isEmpty()) {
                            Alert alertError = new Alert(Alert.AlertType.ERROR);
                            alertError.setTitle("Error to Add Product");
                            alertError.setHeaderText(null);
                            alertError.setContentText("Invalid information");
                            alertError.showAndWait();
                        } else {
                            if (tryParse(productQuantity.getText()) == null || tryParse(productPrice.getText()) == null
                                    || tryParse(productAmountInPack.getText()) == null || tryParse(productPackCapacity.getText()) == null) {
                                Alert alertError = new Alert(Alert.AlertType.ERROR);
                                alertError.setTitle("Error to Add Product");
                                alertError.setHeaderText(null);
                                alertError.setContentText("Invalid information");
                                alertError.showAndWait();
                            } else {
                                if (productListTable.getItems() == subEntries) {
                                    searchBox.clear();
                                    productListTable.setItems(products);
                                }
                                productController.addNewProduct(productName.getText(), productBrand.getText(), Integer.parseInt(productPrice.getText()),
                                        Integer.parseInt(productAmountInPack.getText()), Integer.parseInt(productPackCapacity.getText()),
                                        Integer.parseInt(productQuantity.getText()),new Date());
                                products = getAllProduct();
                                productListTable.setItems(products);
                                productListTable.refresh();
                            }
                        }
                    }
//                }
            }
        });

        addShelfBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dialog<ButtonType> addShelfDialog = new Dialog<>();
                addShelfDialog.initStyle(StageStyle.UTILITY);
                addShelfDialog.setTitle("Add Shelf");
                addShelfDialog.setHeaderText(productController.shelfInWarehouse());

                GridPane addShelfGrid = new GridPane();
                addShelfGrid.setHgap(10);
                addShelfGrid.setVgap(20);
                addShelfGrid.setPadding(new Insets(20, 150, 10, 20));

                TextField shelfName = new TextField();
                TextField shelfMaxPallet = new TextField();

                addShelfGrid.add(new Label("Name:"), 0, 0);
                addShelfGrid.add(shelfName, 1, 0);
                addShelfGrid.add(new Label("Max Pallet:"), 0, 1);
                addShelfGrid.add(shelfMaxPallet, 1, 1);

                addShelfDialog.getDialogPane().setContent(addShelfGrid);

                addShelfDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
                Optional<ButtonType> addProductResult = addShelfDialog.showAndWait();
                if (addProductResult.get() == ButtonType.OK) {
                    if (shelfName.getText().isEmpty() || shelfMaxPallet.getText().isEmpty()) {
                        Alert alertError = new Alert(Alert.AlertType.ERROR);
                        alertError.setTitle("Error to Add Shelf");
                        alertError.setHeaderText(null);
                        alertError.setContentText("Invalid information");
                        alertError.showAndWait();
                    } else {
                        if (tryParse(shelfMaxPallet.getText()) == null) {
                            Alert alertError = new Alert(Alert.AlertType.ERROR);
                            alertError.setTitle("Error to Add Shelf");
                            alertError.setHeaderText(null);
                            alertError.setContentText("Invalid information");
                            alertError.showAndWait();
                        } else {
                            productController.createShelf(shelfName.getText(),Integer.parseInt(shelfMaxPallet.getText()));
                        }
                    }
                }
//                }
            }
        });

        mainBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pageController.active("productList");
            }
        });
        summaryBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                    pageController.active("reportMain");
            }
        });
        orderBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pageController.active("orderList");
            }
        });
        userSearchBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                    pageController.active("user");
            }
        });
        logoutBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
//                currentUser.clearUser();
                pageController.active("login");
            }
        });
        userInfoBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) { pageController.active("profile"); }
        });
    }

    @Override
    public void onActive() {
        if(productController.getCurrentUser() instanceof Staff){
            summaryBt.setDisable(true);
            userSearchBt.setDisable(true);
            addProductBt.setDisable(true);
            addShelfBt.setDisable(true);
        }else{
            summaryBt.setDisable(false);
            userSearchBt.setDisable(false);
            addProductBt.setDisable(false);
            addShelfBt.setDisable(false);
        }
        productListTable.setItems(products);
        productListTable.refresh();
    }

    public ObservableList<Product> getAllProduct(){
        List<Product> results = productController.getAllProduct();
        ObservableList<Product> products = FXCollections.observableArrayList(results);
        return products;
    }

    public static Integer tryParse(String text) {
        try {
            return Integer.parseInt(text);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public void handleSearchByKey(String oldValue, String newValue) {
        if ( oldValue != null && (newValue.length() < oldValue.length()) ) {
            productListTable.setItems((ObservableList) products);
        }

        String[] parts = newValue.toUpperCase().split(" ");

        subEntries = FXCollections.observableArrayList();
        for ( Object entry: productListTable.getItems() ) {
            boolean match = true;
            Product entryP = (Product) entry;
            String detailEntryP = entryP.getId()+entryP.getName().toUpperCase()+entryP.getBrand().toUpperCase();
            for ( String part: parts ) {
                if ( ! detailEntryP.contains(part) ) {
                    match = false;
                    break;
                }
            }

            if ( match ) {
                subEntries.add(entryP);
            }
        }
        productListTable.setItems(subEntries);
    }
}