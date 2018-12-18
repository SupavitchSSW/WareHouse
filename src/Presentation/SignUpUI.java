package Presentation;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ChoiceBox;
import Logic.Controller;
import Logic.PageController;
import Logic.UserController;

public class SignUpUI implements Controller {
    PageController pageController;
    private UserController userController;

    public SignUpUI(UserController userController,PageController pageController) {
        this.userController = userController;
        this.pageController = pageController;
    }

    @Override
    public void initilize() {
        Scene scene = pageController.getScene("signup");
        Button btn = (Button) scene.lookup("#subtn");
        TextField username = (TextField) scene.lookup("#id");
        PasswordField password = (PasswordField) scene.lookup("#pw");
        TextField firstname = (TextField) scene.lookup("#fninfo");
        TextField surname = (TextField) scene.lookup("#sninfo");
        TextField phonenum = (TextField) scene.lookup("#phoneinfo");
        Hyperlink goBack = (Hyperlink) scene.lookup("#goBack");
        ChoiceBox rolesel = (ChoiceBox) scene.lookup("#role");




        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(username.getText().isEmpty() || password.getText().isEmpty() || rolesel.getSelectionModel().isEmpty() || firstname.getText().isEmpty() || surname.getText().isEmpty() || phonenum.getText().isEmpty()) {
                    Alert alertError = new Alert(Alert.AlertType.ERROR);
                    alertError.setTitle("Sign Up Failed");
                    alertError.setHeaderText(null);
                    alertError.setContentText("Please enter your Information!");
                    alertError.showAndWait();
                    return;
                }
                else{
                    //System.out.println(rolesel.getItems());
                    userController.signup(username.getText(), password.getText(), firstname.getText(), surname.getText(), phonenum.getText());
                    username.clear();
                    password.clear();
                    firstname.clear();
                    surname.clear();
                    phonenum.clear();
                }

                pageController.active("login");
            }
        });

        goBack.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pageController.active("login");
            }
        });

    }

    @Override
    public void onActive() {

    }
}
