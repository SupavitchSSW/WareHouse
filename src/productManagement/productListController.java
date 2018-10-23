package productManagement;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import javafx.util.Pair;
import sample.Controller;
import sample.PageController;
import sample.Product;

import java.util.Optional;


public class productListController implements Controller {
    PageController pageController;
    private TableView productListTable;
    private ObservableList<Product> products = getOrder();
    private ObservableList<Product> subEntries;
    private Product selectedProduct;
    private int index, lastID;

    public productListController(PageController pageController){
        this.pageController = pageController;
    }

    public void initilize(){
        Scene scene = pageController.getScene("mainPage");
        Button mainBt = (Button) scene.lookup("#mainButton");
        Button addProductBt = (Button) scene.lookup("#addProductButton");
        Button summeryBt = (Button) scene.lookup("#summeryButton");
        Button orderBt = (Button) scene.lookup("#orderButton");
        Button userSearchBt = (Button) scene.lookup("#userSearchButton");
        TextField searchBox = (TextField) scene.lookup("#searchBox");

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

        idColumn.setMinWidth(100);
        nameColumn.setMinWidth(150);
        brandColumn.setMinWidth(150);
        quantityColumn.setMinWidth(150);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        productListTable.getColumns().addAll(idColumn,nameColumn,brandColumn,quantityColumn);

        productListTable.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
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

                    propertyGrid.add(new Label("Product ID:"), 0, 0);
                    propertyGrid.add(new Label(Integer.toString(selectedProduct.getId())), 1, 0);
                    propertyGrid.add(new Label("Product Name:"), 0, 1);
                    propertyGrid.add(new Label(selectedProduct.getName()), 1, 1);
                    propertyGrid.add(new Label("Product Brand:"), 0, 2);
                    propertyGrid.add(new Label(selectedProduct.getBrand()), 1, 2);
                    propertyGrid.add(new Label("Product Quantity:"), 0, 3);
                    propertyGrid.add(new Label(Integer.toString(selectedProduct.getQuantity())), 1, 3);
                    propertyDialog.getDialogPane().setContent(propertyGrid);

                    ButtonType editButtonType = new ButtonType("Edit");
                    ButtonType doneButtonType = new ButtonType("Done");
                    propertyDialog.getDialogPane().getButtonTypes().addAll(doneButtonType, editButtonType);

                    Optional<ButtonType> propertyResult = propertyDialog.showAndWait();
                    if (propertyResult.get() == editButtonType) {
                        Dialog<ButtonType> editDialog = new Dialog<>();
                        editDialog.initStyle(StageStyle.UTILITY);
                        editDialog.setTitle("Edit Product Property");
                        editDialog.setHeaderText("Product ID: \t" + Integer.toString(selectedProduct.getId()));

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

                        editGrid.add(new Label("Name:"), 0, 1);
                        editGrid.add(name, 1, 1);
                        editGrid.add(new Label("Brand:"), 0, 2);
                        editGrid.add(brand, 1, 2);
                        editGrid.add(new Label("Quantity:"), 0, 3);
                        editGrid.add(quantity, 1, 3);

                        ButtonType deleteButtonType = new ButtonType("Delete Product");

                        editDialog.getDialogPane().setContent(editGrid);
                        editDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL, deleteButtonType);
                        Optional<ButtonType> editResult = editDialog.showAndWait();
                        if (editResult.get() == ButtonType.OK) {
                            if (tryParse(quantity.getText()) == null) {
                                Alert alertError = new Alert(Alert.AlertType.ERROR);
                                alertError.setTitle("Error to Edit Product");
                                alertError.setHeaderText(null);
                                alertError.setContentText("Invalid information");
                                alertError.showAndWait();
                            } else {
                                selectedProduct.setName(name.getText());
                                selectedProduct.setBrand(brand.getText());
                                selectedProduct.setQuantity(quantity.getText());
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
                                    products.remove(products.indexOf(subEntries.get(index)));
                                    searchBox.clear();
                                    productListTable.setItems(products);
                                } else {
                                    products.remove(index);
                                }
                            }
                        }
                    }
                }
            }
        });
        addProductBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dialog<ButtonType> addProductDialog = new Dialog<>();
                addProductDialog.initStyle(StageStyle.UTILITY);
                addProductDialog.setTitle("Add Product");

                GridPane addProductGrid = new GridPane();
                addProductGrid.setHgap(10);
                addProductGrid.setVgap(20);
                addProductGrid.setPadding(new Insets(20, 150, 10, 20));

                TextField productName = new TextField();
                TextField productBrand = new TextField();
                TextField productQuantity = new TextField();

                addProductGrid.add(new Label("Name:"), 0, 1);
                addProductGrid.add(productName, 1, 1);
                addProductGrid.add(new Label("Brand:"), 0, 2);
                addProductGrid.add(productBrand, 1, 2);
                addProductGrid.add(new Label("Quantity:"), 0, 3);
                addProductGrid.add(productQuantity, 1, 3);

                addProductDialog.getDialogPane().setContent(addProductGrid);

                addProductDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
                Optional<ButtonType> addProductResult = addProductDialog.showAndWait();
                if (addProductResult.get() == ButtonType.OK) {
                    if (productName.getText().isEmpty() || productBrand.getText().isEmpty() || productQuantity.getText().isEmpty()) {
                        Alert alertError = new Alert(Alert.AlertType.ERROR);
                        alertError.setTitle("Error to Add Product");
                        alertError.setHeaderText(null);
                        alertError.setContentText("Invalid information");
                        alertError.showAndWait();
                    } else {
                        if (tryParse(productQuantity.getText()) == null) {
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
                            products.add(new Product(++lastID, Integer.parseInt(productQuantity.getText()), productName.getText(), productBrand.getText()));
                            productListTable.refresh();
                        }
                    }
                }
            }
        });

        mainBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pageController.active("productList");
            }
        });
        summeryBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {pageController.active("report");

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

            }
        });
    }

    @Override
    public void onActive() {
        productListTable.setItems(products);
    }

    public ObservableList<Product> getOrder(){
        products = FXCollections.observableArrayList();
        products.add(new Product(1,12,"sssdwq","ssfojmw"));
        products.add(new Product(2,2,"dss","Por_shop2"));
        products.add(new Product(3,22,"dsd","Por_shop3"));
        products.add(new Product(4,1,"jud","ng"));
        products.add(new Product(5,6,"gg","pv"));
        lastID = products.size();
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
            productListTable.setItems(products);
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