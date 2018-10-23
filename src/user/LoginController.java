package user;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import sample.Controller;
import sample.PageController;

public class LoginController implements Controller {
    PageController pageController;

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
                pageController.active("productList");
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
