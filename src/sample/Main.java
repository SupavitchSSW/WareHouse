package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import ordermanagement.OrderDetailController;
import ordermanagement.OrderListController;
import productManagement.productListController;
import user.LoginController;

import java.io.IOException;
import java.util.Date;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        // >>>>>>> add your fxml here <<<<<
        Pane orderListPane = FXMLLoader.load(getClass().getResource("../ordermanagement/orderList.fxml"));
        Pane orderDetailPane = FXMLLoader.load(getClass().getResource("../ordermanagement/orderDetail.fxml"));
        Pane productListPane = FXMLLoader.load(getClass().getResource("../productManagement/mainPage.fxml"));
        Pane loginPane = FXMLLoader.load(getClass().getResource("../user/login.fxml"));



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
        LoginController loginController = new LoginController(pageController);



        // >>>>>>>> add page to pageController <<<<<<<<
        pageController.addPage("orderList",orderListPane,orderListController);
        pageController.addPage("orderDetail",orderDetailPane,orderDetailController);
        pageController.addPage("productList",productListPane,productListController);
        pageController.addPage("login",loginPane,loginController);

        OrderReadWrite.run();

        //start page
        primaryStage.setTitle("WareHouse");
        pageController.active("login");






    }

    public static void main(String[] args) {
        launch(args);
    }
}
