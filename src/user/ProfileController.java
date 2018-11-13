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
    private boolean isEdit = false;
    public ProfileController(PageController pageController,User currentUser) {
        this.currentUser = currentUser;
        this.pageController = pageController;
        //currentUser = new User("field","naja","0836889001","staff","123456");
    }

    @Override
    public void initilize() {
        Scene scene = pageController.getScene("userPage");
        Button mainBt = (Button) scene.lookup("#mainButton");
        Button summaryBt = (Button) scene.lookup("#summaryButton");
        Button orderBt = (Button) scene.lookup("#orderButton");
        Button userSearchBt = (Button) scene.lookup("#userSearchButton");
        Button logoutBt = (Button) scene.lookup("#logoutButton");

        Button editBtn = (Button) scene.lookup("#editButton");

        nameTF = (TextField) scene.lookup("#nameTextField");
        surnameTF = (TextField) scene.lookup("#surnameTextField");
        telTF = (TextField) scene.lookup("#telTextField");
        roleTF = (TextField) scene.lookup("#roleTextField");

        nameTF.setEditable(false);
        surnameTF.setEditable(false);
        telTF.setEditable(false);
        roleTF.setEditable(false);

        editBtn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(isEdit){
                    //save
                    isEdit = false;
                    editBtn.setText("Edit");
                    currentUser.setName(nameTF.getText());
                    currentUser.setSurname(surnameTF.getText());
                    currentUser.setTel(telTF.getText());
                    nameTF.setEditable(false);
                    surnameTF.setEditable(false);
                    telTF.setEditable(false);
                }else{
                    //edit
                    isEdit = true;
                    editBtn.setText("Save");
                    nameTF.setEditable(true);
                    surnameTF.setEditable(true);
                    telTF.setEditable(true);
                }
            }
        });

        mainBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                pageController.active("productList");
            }
        });
        summaryBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
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
        logoutBt.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) { pageController.active("login"); }
        });
    }

    @Override
    public void onActive() {
        setProfile();
    }

    public void setProfile(){
        nameTF.setText(currentUser.getName());
        surnameTF.setText(currentUser.getSurname());
        telTF.setText(currentUser.getTel());
        roleTF.setText(currentUser.getRole());
    }
}
