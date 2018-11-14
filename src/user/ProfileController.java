package user;

import connectionDB.serviceDB;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.StageStyle;
import sample.Controller;
import sample.PageController;

import java.util.Optional;

public class ProfileController implements Controller {
    PageController pageController;
    User currentUser;
    TextField nameTF,surnameTF,roleTF,telTF;
    private boolean isEdit = false;
    private serviceDB database;
    private Button summaryBt,userSearchBt;

    public ProfileController(PageController pageController, serviceDB database,User currentUser) {
        this.currentUser = currentUser;
        this.pageController = pageController;
        this.database = database;
        //currentUser = new User("field","naja","0836889001","staff","123456");
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

                    database.setFirstname(currentUser.getId(),nameTF.getText());
                    database.setSurname(currentUser.getId(),surnameTF.getText());
                    database.setPhoneNumber(currentUser.getId() ,telTF.getText());

                    currentUser.setFirstname(nameTF.getText());
                    currentUser.setSurname(surnameTF.getText());
                    currentUser.setPhoneNumber(telTF.getText());


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
                            database.setPassword(currentUser.getId(), newPass.getText());

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
        setProfile();

        //check permission
        if(currentUser.getRole().equals("Staff")){
            summaryBt.setDisable(true);
            userSearchBt.setDisable(true);
        }else{
            summaryBt.setDisable(false);
            userSearchBt.setDisable(false);
        }
    }

    public void setProfile(){
        nameTF.setText(currentUser.getFirstname());
        surnameTF.setText(currentUser.getSurname());
        telTF.setText(currentUser.getPhoneNumber());
        roleTF.setText(currentUser.getRole());
    }
}
