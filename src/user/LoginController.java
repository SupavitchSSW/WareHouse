package user;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import sample.Controller;
import sample.PageController;

public class LoginController implements Controller {
    PageController pageController;
    String user = "test";
    String pw = "1234";
    String checkUser, checkPw;
    User currentUser;

    public LoginController(PageController pageController,User currentUser) {
        this.currentUser = currentUser;
        this.pageController = pageController;
    }

    @Override
    public void initilize() {
        Scene scene = pageController.getScene("login");
        Button btn = (Button) scene.lookup("#btn");
        TextField username = (TextField) scene.lookup("#id");
        PasswordField password = (PasswordField) scene.lookup("#pw");
        Hyperlink register = (Hyperlink) scene.lookup("#register");


        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                checkUser = username.getText().toString();
                checkPw = password.getText().toString();
                if(checkUser.equals(user) && checkPw.equals(pw)){

                    //set login user
                    currentUser.setName("test");
                    currentUser.setSurname("naja");
                    currentUser.setTel("085555555");
                    currentUser.setRole("Staff");
                    currentUser.setId(555);

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
        //logout
        currentUser.clearUser();
    }

}
