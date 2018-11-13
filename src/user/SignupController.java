package user;

import connectionDB.serviceDB;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.ChoiceBox;
import sample.Controller;
import sample.PageController;

public class SignupController implements Controller {
    PageController pageController;
    private User currentUser;
    private serviceDB database;

    public SignupController(PageController pageController, serviceDB database,User currentUser) {
        this.currentUser = currentUser;
        this.pageController = pageController;
        this.database = database;
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
                if(username.getText().isEmpty() || password.getText().isEmpty() || firstname.getText().isEmpty() || surname.getText().isEmpty() || phonenum.getText().isEmpty()) {
                    Alert alertError = new Alert(Alert.AlertType.ERROR);
                    alertError.setTitle("Sign Up Failed");
                    alertError.setHeaderText(null);
                    alertError.setContentText("Please enter your Information!");
                    alertError.showAndWait();
                    return;
                }
                else{
                    database.createUser(username.getText(), password.getText(), rolesel.getAccessibleText(), firstname.getText(), surname.getText(), phonenum.getText());
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

        rolesel.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {

            }
        });

    }

    @Override
    public void onActive() {

    }
}
