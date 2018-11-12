package user;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import sample.Controller;
import sample.PageController;

public class LoginController implements Controller {
    PageController pageController;
    String user = "test";
    String pw = "1234";
    String checkUser, checkPw;

    public LoginController(PageController pageController) {
        this.pageController = pageController;
    }

    @Override
    public void initilize() {
        Scene scene = pageController.getScene("login");
        Button btn = (Button) scene.lookup("#btn");
        TextField username = (TextField) scene.lookup("#id");
        TextField password = (TextField) scene.lookup("#pw");
        Hyperlink register = (Hyperlink) scene.lookup("#register");


        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                checkUser = username.getText().toString();
                checkPw = password.getText().toString();
                if(checkUser.equals(user) && checkPw.equals(pw)){
                    pageController.active("productList");
                }
                else{
                    Alert alertError = new Alert(Alert.AlertType.ERROR);
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

    }
}
