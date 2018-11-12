package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ordermanagement.OrderDetailController;
import ordermanagement.OrderListController;
import productManagement.productListController;
import report.UserController;
import report.reportController;
import user.LoginController;
import user.SignupController;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        // >>>>>>> add your fxml here <<<<<
        Pane orderListPane = FXMLLoader.load(getClass().getResource("../ordermanagement/orderList.fxml"));
        Pane orderDetailPane = FXMLLoader.load(getClass().getResource("../ordermanagement/orderDetail.fxml"));
        Pane productListPane = FXMLLoader.load(getClass().getResource("../productManagement/mainPage.fxml"));
        Pane reportPane = FXMLLoader.load(getClass().getResource("../report/report.fxml"));
        Pane loginPane = FXMLLoader.load(getClass().getResource("../user/login.fxml"));
        Pane signupPane = FXMLLoader.load(getClass().getResource("../user/signup.fxml"));
        Pane userPane = FXMLLoader.load(getClass().getResource("../user/userPage.fxml"));



        //create scene
        Scene scene = new Scene(productListPane,1000,600);
        primaryStage.setScene(scene);
        primaryStage.show();

        //create pageController obj
        PageController pageController = new PageController(scene);



        // >>>>>>> add controller class here <<<<<<
        OrderDetailController orderDetailController = new OrderDetailController(pageController);
        OrderListController orderListController = new OrderListController(pageController,orderDetailController);
        productListController productListController = new productListController(pageController);
        reportController reportController = new reportController(pageController);
        LoginController loginController = new LoginController(pageController);
        SignupController signupController = new SignupController(pageController);
        UserController userController = new UserController(pageController);



        // >>>>>>>> add page to pageController <<<<<<<<
        pageController.addPage("orderList",orderListPane,orderListController);
        pageController.addPage("orderDetail",orderDetailPane,orderDetailController);
        pageController.addPage("productList",productListPane,productListController);
        pageController.addPage("report",reportPane,reportController);
        pageController.addPage("login",loginPane,loginController);
        pageController.addPage("signup",signupPane, signupController);
        pageController.addPage("user",userPane, userController);
        OrderReadWrite.run();

        //start page
        primaryStage.setTitle("WareHouse");
        pageController.active("productList");






    }

    public static void main(String[] args) {
        launch(args);
    }
}
