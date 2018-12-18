package Presentation;

import Logic.UserController;
import Storage.User;
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
import Logic.Controller;
import Logic.PageController;

import java.util.Optional;

public class UserSearchUI implements Controller{
    PageController pageController;
    private UserController userController;
    private TableView userTable;
    private User user;
    private ObservableList<User> users;
    private ObservableList<User> subEntries;
    private User selectUser;
    private int index, lastID;

    public UserSearchUI(UserController userController ,PageController pageController){
        this.pageController = pageController;
        this.userController = userController;

    }

    public void initilize(){
        Scene scene = pageController.getScene("userPage");
        Button mainBt = (Button) scene.lookup("#mainButton");
        Button summaryBt = (Button) scene.lookup("#summaryButton");

        Button orderBt = (Button) scene.lookup("#orderButton");
        Button userSearchBt = (Button) scene.lookup("#userSearchButton");
        summaryBt = (Button) scene.lookup("#summaryButton");
        TextField searchBox = (TextField) scene.lookup("#searchBox");

        Button logoutBt = (Button) scene.lookup("#logoutButton");
        Button userInfoBt = (Button) scene.lookup("#userInfo");
        Button addMBt = (Button) scene.lookup("#addManagerButton");

        searchBox.setPromptText("Search");
        searchBox.textProperty().addListener((observable, oldVal, newVal) -> {
            handleSearchByKey((String) oldVal, (String) newVal);
        });

        userTable = (TableView) scene.lookup("#userList");


        TableColumn <User,String> firstNameCol = new TableColumn("First Name");
        firstNameCol.setMinWidth(200);
        firstNameCol.setCellValueFactory(
                new PropertyValueFactory<>("firstname"));

        TableColumn <User,String> surnameCol = new TableColumn("Surname");
        surnameCol.setMinWidth(200);
        surnameCol.setCellValueFactory(
                new PropertyValueFactory<>("surname"));

        TableColumn <User, String> telCol = new TableColumn("Phone Number");
        telCol.setMinWidth(200);
        telCol.setCellValueFactory(
                new PropertyValueFactory<>("phoneNumber"));

        TableColumn <User, String> roleCol = new TableColumn("Role");
        roleCol.setMinWidth(200);
        roleCol.setCellValueFactory(
                new PropertyValueFactory<>("role"));


        userTable.getColumns().addAll(firstNameCol, surnameCol,telCol,roleCol);
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
        userTable.setOnMouseClicked(new EventHandler<MouseEvent>(){
            @Override
            public void handle(MouseEvent event) {
                if (event.getClickCount() == 2) {
                    index = userTable.getSelectionModel().getSelectedIndex();

                    Dialog<ButtonType> propertyDialog = new Dialog<>();
                    propertyDialog.initStyle(StageStyle.UTILITY);
                    propertyDialog.setTitle("User Information");

                    GridPane propertyGrid = new GridPane();
                    propertyGrid.setHgap(10);
                    propertyGrid.setVgap(20);
                    propertyGrid.setPadding(new Insets(20, 200, 10, 20));

                    if (userTable.getItems() == subEntries) {
                        selectUser = subEntries.get(index);
                    } else {
                        selectUser = users.get(index);
                    }

                    propertyGrid.add(new Label("Name               :"), 0, 0);
                    propertyGrid.add(new Label(selectUser.getFirstname()), 1, 0);
                    propertyGrid.add(new Label("Surname           :"), 0, 1);
                    propertyGrid.add(new Label(selectUser.getSurname()), 1, 1);
                    propertyGrid.add(new Label("Phone Number :"), 0, 2);
                    propertyGrid.add(new Label(selectUser.getPhoneNumber()), 1, 2);
                    propertyGrid.add(new Label("Role                  :"), 0, 3);
                    propertyGrid.add(new Label(selectUser.getRole()), 1, 3);
                    propertyDialog.getDialogPane().setContent(propertyGrid);

                    ButtonType doneButtonType = new ButtonType("Done");
                    ButtonType deleteButtonType = new ButtonType("Delete User");

                    propertyDialog.getDialogPane().getButtonTypes().addAll(deleteButtonType, doneButtonType);

                    Optional<ButtonType> propertyResult = propertyDialog.showAndWait();

                    if (propertyResult.get() == deleteButtonType) {
                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                        alert.setTitle("Delete User");
                        alert.setHeaderText(null);
                        alert.setContentText("Are you sure you want to delete this user?");
                        Optional<ButtonType> result = alert.showAndWait();
                        if (result.get() == ButtonType.OK){
                            if (userTable.getItems() == subEntries) {

                                searchBox.clear();
                            }
                            userController.removeUser(selectUser.getId());
                            users = FXCollections.observableArrayList(userController.getAllUser());
                            userTable.setItems(users);
                        }


                    }
                }
            }
        });

        logoutBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) { pageController.active("login"); }
        });

        addMBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                Dialog<ButtonType> addManagerDialog = new Dialog<>();
                addManagerDialog.initStyle(StageStyle.UTILITY);
                addManagerDialog.setTitle("Add Manager Information");

                GridPane addManagerGrid = new GridPane();
                addManagerGrid.setHgap(10);
                addManagerGrid.setVgap(20);
                addManagerGrid.setPadding(new Insets(20, 120, 10, 20));

                TextField musername = new TextField();
                PasswordField mpassword = new PasswordField();
                TextField mfirstname = new TextField();
                TextField msurname = new TextField();
                TextField mphonenum = new TextField();


                addManagerGrid.add(new Label("Username:"), 0, 0);
                addManagerGrid.add(musername, 1, 0);
                addManagerGrid.add(new Label("Password:"), 0, 1);
                addManagerGrid.add(mpassword, 1, 1);
                addManagerGrid.add(new Label("First Name:"), 0, 2);
                addManagerGrid.add(mfirstname, 1, 2);
                addManagerGrid.add(new Label("Surname:"), 0, 3);
                addManagerGrid.add(msurname, 1, 3);
                addManagerGrid.add(new Label("Phone Number:"), 0, 4);
                addManagerGrid.add(mphonenum, 1, 4);



                addManagerDialog.getDialogPane().setContent(addManagerGrid);

                ButtonType confirmButtonType = new ButtonType("Confirm");
                ButtonType cancelButtonType = new ButtonType("Cancel");

                addManagerDialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, cancelButtonType);

                Optional<ButtonType> addManagerResult = addManagerDialog.showAndWait();
                if (addManagerResult.get() == confirmButtonType ) {
                    userController.createManager(musername.getText(), mpassword.getText(), mfirstname.getText(), msurname.getText(), mphonenum.getText());
                    users = FXCollections.observableArrayList(userController.getAllUser());
                    userTable.setItems(users);
                    userTable.refresh();
                }
            }
        });

        userInfoBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) { pageController.active("profile"); }
        });

    }


    public void onActive() {
        users = FXCollections.observableArrayList(userController.getAllUser());
        userTable.setItems(users);
        userTable.refresh();
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
            userTable.setItems(users);
        }

        String[] parts = newValue.toUpperCase().split(" ");

        subEntries = FXCollections.observableArrayList();
        for ( Object entry: userTable.getItems() ) {
            boolean match = true;
            User entryP = (User) entry;
            String detailEntryP = entryP.getFirstname().toUpperCase()+entryP.getSurname().toUpperCase()+entryP.getPhoneNumber();
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
        userTable.setItems(subEntries);
    }
}

