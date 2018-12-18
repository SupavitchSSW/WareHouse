package user;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import sample.Controller;
import sample.PageController;
import connectionDB.serviceDB;

public class LoginUI implements Controller {
    PageController pageController;
    String checkUser, checkPw;
    private TextField username;
    private PasswordField password;
    private UserController userController;

    public LoginUI(UserController userController,PageController pageController) {
        this.pageController = pageController;
        this.userController = userController;
    }

    @Override
    public void initilize() {
        Scene scene = pageController.getScene("login");
        Button btn = (Button) scene.lookup("#btn");
        username = (TextField) scene.lookup("#id");
        password = (PasswordField) scene.lookup("#pw");
        Hyperlink register = (Hyperlink) scene.lookup("#register");


        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                checkUser = username.getText();
                checkPw = password.getText();
                if(userController.login(checkUser,checkPw)){
                    pageController.active("productList");
                }
                else{
                    Alert alertError = new Alert(Alert.AlertType.ERROR);
                    username.clear();
                    password.clear();
                    alertError.setTitle("Login Failed");
                    alertError.setHeaderText(null);
                    alertError.setContentText("Invalid Username or Password");
                    alertError.showAndWait();
                }
            }
        });

        register.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pageController.active("signup");
            }
        });

    }

    @Override
    public void onActive() {
        username.clear();
        password.clear();

    }

}
