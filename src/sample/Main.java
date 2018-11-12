package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ordermanagement.OrderDetailController;
import ordermanagement.OrderListController;
import productManagement.productListController;
import user.*;
import report.reportController;

import javax.persistence.*;
import connectionDB.*;
import product.Product;
import java.io.IOException;
import java.util.List;

public class Main extends Application {
    private User currentUser;

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
        Pane profilePane = FXMLLoader.load(getClass().getResource("../user/profile.fxml"));



        //create scene
        Scene scene = new Scene(productListPane,1000,600);
        primaryStage.setScene(scene);
        primaryStage.show();

        //create pageController obj
        PageController pageController = new PageController(scene);


        //oak db
        serviceDB database = new serviceDB();
//        List<Product> results = database.getAllProduct();
//        database.createProduct("qwe","qee",23,20);
//        database.createProduct("gtq","ggg",13,40);
//        database.createProduct("ofp","hpn",56,50);
//        database.createProduct("bnp","dfo",78,100);
//        database.createProduct("vxs","hfp",77,29);
//        database.createProduct("ipw","opd",90,48);
////        s.setProductBrand(2,"luis");
//        for (Product p : results) {
//            System.out.println(p);
//        }
//        database.closeConnection();


        // >>>>>>> add controller class here <<<<<<
        OrderDetailController orderDetailController = new OrderDetailController(pageController);
        OrderListController orderListController = new OrderListController(pageController,orderDetailController);
        productListController productListController = new productListController(pageController,database);
        reportController reportController = new reportController(pageController);
        LoginController loginController = new LoginController(pageController);
        SignupController signupController = new SignupController(pageController);
        UserController userController = new UserController(pageController);
        ProfileController profileController = new ProfileController(pageController);


        // >>>>>>>> add page to pageController <<<<<<<<
        pageController.addPage("orderList",orderListPane,orderListController);
        pageController.addPage("orderDetail",orderDetailPane,orderDetailController);
        pageController.addPage("productList",productListPane,productListController);
        pageController.addPage("report",reportPane,reportController);
        pageController.addPage("login",loginPane,loginController);
        pageController.addPage("signup",signupPane, signupController);
        pageController.addPage("user",userPane, userController);
        pageController.addPage("profile",profilePane, profileController);
//        OrderReadWrite.run();

        //start page
        primaryStage.setTitle("WareHouse");
        pageController.active("profile");


    }

    public static void main(String[] args) {
        launch(args);
    }
}
