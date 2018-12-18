package Presentation;

import Storage.WarehouseSystem;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.StageStyle;
import Logic.Controller;
import Logic.PageController;
import Logic.UserController;

import java.util.Optional;

public class ProfileUI implements Controller {
    PageController pageController;
    TextField nameTF,surnameTF,roleTF,telTF;
    private boolean isEdit = false;
    private WarehouseSystem database;
    private Button summaryBt,userSearchBt;
    private UserController userController;

    public ProfileUI(UserController userController,PageController pageController) {
        this.userController = userController;
        this.pageController = pageController;
    }

    @Override
    public void initilize() {
        Scene scene = pageController.getScene("userPage");
        Button mainBt = (Button) scene.lookup("#mainButton");
        summaryBt = (Button) scene.lookup("#summaryButton");
        Button orderBt = (Button) scene.lookup("#orderButton");
        userSearchBt = (Button) scene.lookup("#userSearchButton");
        Button logoutBt = (Button) scene.lookup("#logoutButton");
        Button editBtn = (Button) scene.lookup("#editButton");
        Button cpassBt = (Button) scene.lookup("#changepassButton");

        nameTF = (TextField) scene.lookup("#nameTextField");
        surnameTF = (TextField) scene.lookup("#surnameTextField");
        telTF = (TextField) scene.lookup("#telTextField");
        roleTF = (TextField) scene.lookup("#roleTextField");

        nameTF.setEditable(false);
        surnameTF.setEditable(false);
        telTF.setEditable(false);
        roleTF.setEditable(false);

        editBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(isEdit){
                    //save
                    isEdit = false;
                    editBtn.setText("Edit");

                    userController.changeUserInfo(nameTF.getText(),surnameTF.getText(),telTF.getText());

                    nameTF.setEditable(false);
                    surnameTF.setEditable(false);
                    telTF.setEditable(false);
                }else{
                    //edit
                    isEdit = true;
                    editBtn.setText("Save");
                    nameTF.setEditable(true);
                    surnameTF.setEditable(true);
                    telTF.setEditable(true);
                }
            }
        });

        cpassBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                    Dialog<ButtonType> changePassDialog = new Dialog<>();
                    changePassDialog.initStyle(StageStyle.UTILITY);
                    changePassDialog.setTitle("Change Password");

                    GridPane changePassGrid = new GridPane();
                    changePassGrid.setHgap(10);
                    changePassGrid.setVgap(20);
                    changePassGrid.setPadding(new Insets(20, 120, 10, 20));

                    PasswordField newPass = new PasswordField();
                    PasswordField cfNewPass = new PasswordField();

                    changePassGrid.add(new Label("New Password:"), 0, 0);
                    changePassGrid.add(newPass, 1, 0);
                    changePassGrid.add(new Label("Confirm Password:"), 0, 1);
                    changePassGrid.add(cfNewPass, 1, 1);

                    changePassDialog.getDialogPane().setContent(changePassGrid);

                    ButtonType confirmButtonType = new ButtonType("Confirm");
                    ButtonType cancelButtonType = new ButtonType("Cancel");

                    changePassDialog.getDialogPane().getButtonTypes().addAll(confirmButtonType, cancelButtonType);

                    Optional<ButtonType> changePassResult = changePassDialog.showAndWait();
                    if (changePassResult.get() == confirmButtonType) {
                        if (newPass.getText().equals(cfNewPass.getText())) {
                            userController.changePassword(newPass.getText());

                        } else {
                            Alert alertError = new Alert(Alert.AlertType.ERROR);
                            alertError.setTitle("Failed to Change Password");
                            alertError.setHeaderText(null);
                            alertError.setContentText("Password is not Match");
                            alertError.showAndWait();
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
            public void handle(MouseEvent event) { pageController.active("login"); }
        });
    }

    @Override
    public void onActive() {
        nameTF.setText(userController.getCurrentUser().getFirstname());
        surnameTF.setText(userController.getCurrentUser().getSurname());
        telTF.setText(userController.getCurrentUser().getPhoneNumber());
        roleTF.setText(userController.getCurrentUser().getRole());

        //check permission
        if(userController.getCurrentUser().getRole().equals("Staff")){
            summaryBt.setDisable(true);
            userSearchBt.setDisable(true);
        }else{
            summaryBt.setDisable(false);
            userSearchBt.setDisable(false);
        }
    }
}
