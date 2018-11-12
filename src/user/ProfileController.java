package user;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import sample.Controller;
import sample.PageController;

public class ProfileController implements Controller {
    PageController pageController;
    User currentUser;
    TextField nameTF,surnameTF,roleTF,telTF;
    public ProfileController(PageController pageController) {
        this.pageController = pageController;
        currentUser = new User("field","naja","0836889001","staff","123456");
    }

    @Override
    public void initilize() {
        Scene scene = pageController.getScene("userPage");
        Button mainBt = (Button) scene.lookup("#mainButton");
        Button summeryBt = (Button) scene.lookup("#summeryButton");
        Button orderBt = (Button) scene.lookup("#orderButton");
        Button userSearchBt = (Button) scene.lookup("#userSearchButton");

        Button editBtn = (Button) scene.lookup("#editButton");
        Button logoutBtn = (Button) scene.lookup("#logoutButton");

        nameTF = (TextField) scene.lookup("#nameTextField");
        surnameTF = (TextField) scene.lookup("#surnameTextField");
        telTF = (TextField) scene.lookup("#telTextField");
        roleTF = (TextField) scene.lookup("#roleTextField");

        nameTF.setEditable(false);
        surnameTF.setEditable(false);
        telTF.setEditable(false);
        roleTF.setEditable(false);

        mainBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pageController.active("productList");
            }
        });
        summeryBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
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
    }

    @Override
    public void onActive() {
        nameTF.setText(currentUser.getName());
        surnameTF.setText(currentUser.getSurname());
        telTF.setText(currentUser.getTel());
        roleTF.setText(currentUser.getRole());
    }
}
