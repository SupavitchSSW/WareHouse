package user;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import sample.Controller;
import sample.PageController;
import connectionDB.serviceDB;

public class LoginController implements Controller {
    PageController pageController;
    String user = "test";
    String pw = "1234";
    String checkUser, checkPw;
    User currentUser;
    serviceDB database;

    public LoginController(PageController pageController, serviceDB database, User currentUser) {
        this.database = database;
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
                checkUser = username.getText();
                checkPw = password.getText();
                User u = database.authen(checkUser,checkPw);
                if (u != null) {
                    //currentUser = database.authen(checkUser,checkPw);
                    currentUser.setFirstname(u.getFirstname());
                    currentUser.setPhoneNumber(u.getPhoneNumber());
                    currentUser.setSurname(u.getSurname());
                    currentUser.setRole(u.getRole());
                    currentUser.setUsername(u.getUsername());
                    currentUser.setPassword(u.getPassword());
                    currentUser.setId(u.getId());
                    username.setText("");
                    password.setText("");
                    System.out.println("CCC"+u.toString());
                    pageController.active("productList");
                }
//                if(checkUser.equals(user) && checkPw.equals(pw)){
//                    //set login user
//                    currentUser.setFirstname("test");
//                    currentUser.setSurname("naja");
//                    currentUser.setPhoneNumber("085555555");
//                    currentUser.setRole("Staff");
//                    currentUser.setId(555);
//
//                    pageController.active("productList");
//                }
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

    }

}
