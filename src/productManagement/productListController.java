package productManagement;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
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
    private int index = 0;

    public productListController(PageController pageController){
        this.pageController = pageController;
    }

    public void initilize(){
        Scene scene = pageController.getScene("mainPage");
        Button editBt = (Button) scene.lookup("#editButton");

        //table setup
        productListTable = (TableView) scene.lookup("#productList");

        // column name
        TableColumn<Product,Integer> idColumn = new TableColumn<>("ID");
        TableColumn<Product,String> nameColumn = new TableColumn<>("NAME");
        TableColumn<Product,String> brandColumn = new TableColumn<>("BRAND");
        TableColumn<Product,Integer> amountColumn = new TableColumn<>("AMOUNT");

        idColumn.setMinWidth(60);
        nameColumn.setMinWidth(130);
        brandColumn.setMinWidth(60);
        amountColumn.setMinWidth(60);

        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        brandColumn.setCellValueFactory(new PropertyValueFactory<>("brand"));
        amountColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        productListTable.getColumns().addAll(idColumn,nameColumn,brandColumn,amountColumn);

        onActive();

        editBt.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event){
                Dialog<ButtonType> propertyDialog = new Dialog<>();
                propertyDialog.initStyle(StageStyle.UTILITY);
                propertyDialog.setTitle("Product Property");

                GridPane propertyGrid = new GridPane();
                propertyGrid.setHgap(10);
                propertyGrid.setVgap(20);
                propertyGrid.setPadding(new Insets(20, 200, 10, 10));

                propertyGrid.add(new Label("Product ID:"), 0, 0);
                propertyGrid.add(new Label(Integer.toString(products.get(index).getId())), 1, 0);
                propertyGrid.add(new Label("Product Name:"), 0, 1);
                propertyGrid.add(new Label(products.get(index).getName()), 1, 1);
                propertyGrid.add(new Label("Product Brand:"), 0, 2);
                propertyGrid.add(new Label(products.get(index).getBrand()), 1, 2);
                propertyGrid.add(new Label("Product Amount:"), 0, 3);
                propertyGrid.add(new Label(Integer.toString(products.get(index).getQuantity())), 1, 3);
                propertyDialog.getDialogPane().setContent(propertyGrid);

                ButtonType editButtonType = new ButtonType("Edit");
                ButtonType doneButtonType = new ButtonType("Done");
                propertyDialog.getDialogPane().getButtonTypes().addAll(doneButtonType, editButtonType);

                Optional<ButtonType> propertyResult = propertyDialog.showAndWait();
                if (propertyResult.get() == editButtonType){
                    Dialog<ButtonType> editDialog = new Dialog<>();
                    editDialog.initStyle(StageStyle.UTILITY);
                    editDialog.setTitle("Edit Product Property");
                    editDialog.setHeaderText("Product ID: \t"+Integer.toString(products.get(0).getId()));

                    GridPane editGrid = new GridPane();
                    editGrid.setHgap(10);
                    editGrid.setVgap(10);
                    editGrid.setPadding(new Insets(10, 150, 20, 10));

                    TextField name = new TextField();
                    name.setText(products.get(index).getName());
                    TextField brand = new TextField();
                    brand.setText(products.get(index).getBrand());
                    TextField amount = new TextField();
                    amount.setText(Integer.toString(products.get(index).getQuantity()));

                    editGrid.add(new Label("Name:"), 0, 1);
                    editGrid.add(name, 1, 1);
                    editGrid.add(new Label("Brand:"), 0, 2);
                    editGrid.add(brand, 1, 2);
                    editGrid.add(new Label("Amount:"), 0, 3);
                    editGrid.add(amount, 1, 3);

                    editDialog.getDialogPane().setContent(editGrid);
                    editDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK,ButtonType.CANCEL);
                    Optional<ButtonType> editResult = editDialog.showAndWait();
                    if (editResult.get() == ButtonType.OK){
                        products.get(index).setName(name.getText());
                        products.get(index).setBrand(brand.getText());
                        products.get(index).setQuantity(amount.getText());
                    }
                }
            }
        });



    }

    @Override
    public void onActive() {
        productListTable.setItems(products);
    }

    public ObservableList<Product> getOrder(){
        ObservableList<Product> products = FXCollections.observableArrayList();
        products.add(new Product(1,12,"ยาสีฟัน","ฝนฝน"));
        products.add(new Product(2,2,"dss","Por_shop2"));
        products.add(new Product(3,22,"dsd","Por_shop3"));
        return products;
    }
}