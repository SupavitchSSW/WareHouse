package productManagement;

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
    private int index;

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

                    propertyGrid.add(new Label("Product ID:"), 0, 0);
                    propertyGrid.add(new Label(Integer.toString(products.get(index).getId())), 1, 0);
                    propertyGrid.add(new Label("Product Name:"), 0, 1);
                    propertyGrid.add(new Label(products.get(index).getName()), 1, 1);
                    propertyGrid.add(new Label("Product Brand:"), 0, 2);
                    propertyGrid.add(new Label(products.get(index).getBrand()), 1, 2);
                    propertyGrid.add(new Label("Product Quantity:"), 0, 3);
                    propertyGrid.add(new Label(Integer.toString(products.get(index).getQuantity())), 1, 3);
                    propertyDialog.getDialogPane().setContent(propertyGrid);

                    ButtonType editButtonType = new ButtonType("Edit");
                    ButtonType doneButtonType = new ButtonType("Done");
                    propertyDialog.getDialogPane().getButtonTypes().addAll(doneButtonType, editButtonType);

                    Optional<ButtonType> propertyResult = propertyDialog.showAndWait();
                    if (propertyResult.get() == editButtonType) {
                        Dialog<ButtonType> editDialog = new Dialog<>();
                        editDialog.initStyle(StageStyle.UTILITY);
                        editDialog.setTitle("Edit Product Property");
                        editDialog.setHeaderText("Product ID: \t" + Integer.toString(products.get(0).getId()));

                        GridPane editGrid = new GridPane();
                        editGrid.setHgap(10);
                        editGrid.setVgap(10);
                        editGrid.setPadding(new Insets(10, 180, 20, 20));

                        TextField name = new TextField();
                        name.setText(products.get(index).getName());
                        TextField brand = new TextField();
                        brand.setText(products.get(index).getBrand());
                        TextField quantity = new TextField();
                        quantity.setText(Integer.toString(products.get(index).getQuantity()));

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
                            products.get(index).setName(name.getText());
                            products.get(index).setBrand(brand.getText());
                            products.get(index).setQuantity(quantity.getText());
                        } else if (editResult.get() == deleteButtonType) {
                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setTitle("Delete Product");
                            alert.setHeaderText(null);
                            alert.setContentText("Are you sure you want to delete this product?");
                            Optional<ButtonType> result = alert.showAndWait();
                            if (result.get() == ButtonType.OK){
                                products.remove(index);
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
                addProductGrid.setPadding(new Insets(20, 250, 10, 20));

                TextField productName = new TextField();
//                productName.setText(products.get(index).getName());
                TextField productBrand = new TextField();
//                brand.setText(products.get(index).getBrand());
                TextField productQuantity = new TextField();
//                quantity.setText(Integer.toString(products.get(index).getQuantity()));

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
                    products.add(new Product(1,Integer.parseInt(productQuantity.getText()),productName.getText(),productBrand.getText()));
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
            public void handle(MouseEvent event) {

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
        products.add(new Product(1,12,"ยาสีฟัน","ฝนฝน"));
        products.add(new Product(2,2,"dss","Por_shop2"));
        products.add(new Product(3,22,"dsd","Por_shop3"));
        return products;
    }
}