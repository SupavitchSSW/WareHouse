package sample;

import Logic.*;
import Presentation.*;
import Storage.User;
import Storage.WarehouseSystem;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    public User currentUser = new User("","","","","","");

    @Override
    public void start(Stage primaryStage) throws IOException {

        // >>>>>>> add your fxml here <<<<<
        Pane orderListPane = FXMLLoader.load(getClass().getResource("../Presentation/orderList.fxml"));
        Pane orderDetailPane = FXMLLoader.load(getClass().getResource("../Presentation/orderDetail.fxml"));
        Pane productListPane = FXMLLoader.load(getClass().getResource("../Presentation/mainPage.fxml"));
        Pane reportMainPane = FXMLLoader.load(getClass().getResource("../Presentation/reportMain.fxml"));
        Pane reportPane = FXMLLoader.load(getClass().getResource("../Presentation/report.fxml"));
        Pane loginPane = FXMLLoader.load(getClass().getResource("../Presentation/login.fxml"));
        Pane signupPane = FXMLLoader.load(getClass().getResource("../Presentation/signup.fxml"));
        Pane userPane = FXMLLoader.load(getClass().getResource("../Presentation/userPage.fxml"));
        Pane profilePane = FXMLLoader.load(getClass().getResource("../Presentation/profile.fxml"));


        //create scene
        Scene scene = new Scene(productListPane, 1000, 600);
        primaryStage.setScene(scene);
        primaryStage.show();



        //create pageController obj
        PageController pageController = new PageController(scene);

        //db
        WarehouseSystem warehouseSystem = new WarehouseSystem();
        warehouseSystem.createCatalogueEntry(0);
        warehouseSystem.createUserManager("w","w","warisa","saisema","0123456789");

        // create controller class
        UserController userController = new UserController(warehouseSystem);
        ProductController productController = new ProductController(warehouseSystem);
        ReportController reportController = new ReportController(warehouseSystem);
        OrderController orderController = new OrderController(warehouseSystem,productController);

        // >>>>>>> add controller class here <<<<<<
        OrderDetailUI orderDetailUI = new OrderDetailUI(pageController,orderController);
        OrderListUI orderListUI = new OrderListUI(pageController,orderController);
        ProductUI ProductUI = new ProductUI(pageController, productController);
        ReportUI ReportUI = new ReportUI(reportController,pageController);
        LoginUI loginUI = new LoginUI(userController,pageController);
        SignUpUI signUpUI = new SignUpUI(userController,pageController);
        UserSearchUI userSearchUI = new UserSearchUI(userController,pageController);
        ProfileUI profileUI = new ProfileUI(userController,pageController);

        // >>>>>>>> add page to pageController <<<<<<<<
        pageController.addPage("orderList", orderListPane, orderListUI);
        pageController.addPage("orderDetail", orderDetailPane, orderDetailUI);
        pageController.addPage("productList", productListPane, ProductUI);
        pageController.addPage("reportMain", reportPane, ReportUI);
        pageController.addPage("login", loginPane, loginUI);
        pageController.addPage("signup", signupPane, signUpUI);
        pageController.addPage("user", userPane, userSearchUI);
        pageController.addPage("profile", profilePane, profileUI);

        //start page
        primaryStage.setTitle("WareHouse Management");
        pageController.active("login");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
