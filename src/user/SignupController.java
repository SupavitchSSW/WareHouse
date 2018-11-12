package user;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import sample.Controller;
import sample.PageController;

public class SignupController implements Controller {
    PageController pageController;

    public SignupController(PageController pageController) {
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
