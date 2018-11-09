package user;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
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
        TextField password = (TextField) scene.lookup("#pw");
        TextField firstname = (TextField) scene.lookup("#fninfo");
        TextField surname = (TextField) scene.lookup("#sninfo");
        TextField phonenum = (TextField) scene.lookup("#phoneinfo");


        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(username.getText().isEmpty()) {
                    System.out.println("Put Your Username!");
                    return;
                }
                if(password.getText().isEmpty()) {
                    System.out.println("Put Your Password!");
                    return;
                }
                if(firstname.getText().isEmpty()) {
                    System.out.println("Put Your First Name!");
                    return;
                }
                if(surname.getText().isEmpty()) {
                    System.out.println("Put Your Surname!");
                    return;
                }
                if(phonenum.getText().isEmpty()) {
                    System.out.println("Put Your Phone Number!");
                    return;
                }

                pageController.active("login");
            }
        });

    }

    @Override
    public void onActive() {

    }
}
