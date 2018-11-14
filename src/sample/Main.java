package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ordermanagement.OrderDetailController;
import ordermanagement.OrderListController;
import productManagement.productListController;
import report.Report;
import transaction.Transaction;
import user.*;
import report.reportMainController;
import report.reportController;

import javax.persistence.*;
import connectionDB.*;
import product.Product;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;

public class Main extends Application {
    public User currentUser = new User("","","","staff","","");


    @Override
    public void start(Stage primaryStage) throws IOException {

        // >>>>>>> add your fxml here <<<<<
        Pane orderListPane = FXMLLoader.load(getClass().getResource("../ordermanagement/orderList.fxml"));
        Pane orderDetailPane = FXMLLoader.load(getClass().getResource("../ordermanagement/orderDetail.fxml"));
        Pane productListPane = FXMLLoader.load(getClass().getResource("../productManagement/mainPage.fxml"));
        Pane reportMainPane = FXMLLoader.load(getClass().getResource("../report/reportMain.fxml"));
        Pane reportPane = FXMLLoader.load(getClass().getResource("../report/report.fxml"));
        Pane loginPane = FXMLLoader.load(getClass().getResource("../user/login.fxml"));
        Pane signupPane = FXMLLoader.load(getClass().getResource("../user/signup.fxml"));
        Pane userPane = FXMLLoader.load(getClass().getResource("../user/userPage.fxml"));
        Pane profilePane = FXMLLoader.load(getClass().getResource("../user/profile.fxml"));


        //create scene
        Scene scene = new Scene(productListPane, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();

        //create pageController obj
        PageController pageController = new PageController(scene);


        //oak db
        serviceDB database = new serviceDB();
        //Calendar d = new GregorianCalendar();
        //System.out.print(d.get(Calendar.MONTH));
        List<Transaction> results = database.getMinMonthTransection();
        System.out.println(results.get(0));



        //List<Report> results = database.getAllTransactionInMonth();
//        database.createTransaction(10,10,new Date(),"tafca");
//        database.createTransaction(10,10,new Date(),"tafca");
//        database.createTransaction(10,10,new Date(),"tafca");
//        database.createTransaction(10,10,new Date(),"tafca");
//        database.createTransaction(10,10,new Date(),"tafca");
        //for (Report p : results) {
        //    System.out.println(p);
        //}
//        database.closeConnection();


        // >>>>>>> add controller class here <<<<<<
        OrderDetailController orderDetailController = new OrderDetailController(pageController,currentUser);
        OrderListController orderListController = new OrderListController(pageController, orderDetailController,currentUser);
        productListController productListController = new productListController(pageController, database,currentUser);
        reportController reportController = new reportController(pageController,currentUser,database);
        reportMainController reportMainController = new reportMainController(pageController, reportController,currentUser,database);
        LoginController loginController = new LoginController(pageController,currentUser);
        SignupController signupController = new SignupController(pageController,currentUser);
        UserController userController = new UserController(pageController,currentUser);
        ProfileController profileController = new ProfileController(pageController,currentUser);


        // >>>>>>>> add page to pageController <<<<<<<<
        pageController.addPage("orderList", orderListPane, orderListController);
        pageController.addPage("orderDetail", orderDetailPane, orderDetailController);
        pageController.addPage("productList", productListPane, productListController);
        pageController.addPage("reportMain", reportMainPane, reportMainController);
        pageController.addPage("report", reportPane, reportController);
        pageController.addPage("login", loginPane, loginController);
        pageController.addPage("signup", signupPane, signupController);
        pageController.addPage("user", userPane, userController);
        pageController.addPage("profile", profilePane, profileController);
//        OrderReadWrite.run();

        //start page
        primaryStage.setTitle("WareHouse");
        pageController.active("productList");


    }

    public static void main(String[] args) {
        launch(args);
    }
}
