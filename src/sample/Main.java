package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ordermanagement.OrderController;
import ordermanagement.OrderDetailUI;
import ordermanagement.OrderListUI;
import productManagement.ProductController;
import productManagement.ProductUI;
import report.ReportController;
import user.*;
import report.ReportUI;

import connectionDB.*;

import java.io.IOException;

public class Main extends Application {
    public User currentUser = new User("","","","","","");

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
//        database.createOrder("test","reeeeeee");
//        database.addOrderproduct(7,41,"k","p",8);
//        database.addOrderproduct(7,61,"ki","pp",4);
//        database.createShelf(10,0);
//        database.addPallet(1,2,49,4,30);
//        database.createTransaction(1,2,new Date(),"dfd");
//        database.createTransaction(1,52,new Date(),"g");
//        database.createTransaction(1,26,new Date(),"ewa");
//        List<transaction.Transaction> t = database.getAllTransactionInMonth(10);
//        for (transaction.Transaction x:t){
//            System.out.println(x);
//        }
        //database.createUser("admin","1234","Manager","fon","fonfon","090");
        /*database.addOrderproduct(2,1,"qwe","qee",200);
        database.addOrderproduct(2,3,"qwe","qee",100);
        database.addOrderproduct(2,9,"qwe","qee",50);*/

        /*
        List<Transaction> results = database.getAllTransactionInMonth(11,2018);
        database.createTransaction(1,1,new Date(),"asdfghj");
        database.createTransaction(1,1,new Date(),"asdfghj");
        database.createTransaction(1,1,new Date(),"asdfghj");*/
//        s.setProductBrand(2,"luis");

//        database.closeConnection();

        // create controller class
        OrderController orderController = new OrderController(database,currentUser);
        UserController userController = new UserController(database,currentUser);
        ProductController productController = new ProductController(database);
        ReportController reportController = new ReportController(database);

        // >>>>>>> add controller class here <<<<<<
        OrderDetailUI orderDetailUI = new OrderDetailUI(pageController,orderController);
        OrderListUI orderListUI = new OrderListUI(pageController,orderController);
        ProductUI ProductUI = new ProductUI(pageController, productController);
//        ReportUI ReportUI = new ReportUI(pageController,database,currentUser);
        ReportUI ReportUI = new ReportUI(reportController,pageController);
        //reportMainController reportMainController = new reportMainController(pageController, ReportUI,currentUser,database);
        LoginUI loginUI = new LoginUI(userController,pageController);
        SignUpUI signUpUI = new SignUpUI(userController,pageController);
        UserSearchUI userSearchUI = new UserSearchUI(userController,pageController);
        ProfileUI profileUI = new ProfileUI(userController,pageController);

        // >>>>>>>> add page to pageController <<<<<<<<
        pageController.addPage("orderList", orderListPane, orderListUI);
        pageController.addPage("orderDetail", orderDetailPane, orderDetailUI);
        pageController.addPage("productList", productListPane, ProductUI);
        pageController.addPage("reportMain", reportPane, ReportUI);
        //pageController.addPage("report", reportPane, ReportUI);
        pageController.addPage("login", loginPane, loginUI);
        pageController.addPage("signup", signupPane, signUpUI);
        pageController.addPage("user", userPane, userSearchUI);
        pageController.addPage("profile", profilePane, profileUI);

        //start page
        primaryStage.setTitle("WareHouse Management");
        pageController.active("productList");

        
        //set database to OrderReadWrite
//        OrderReadWrite.setDatabase(database);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
