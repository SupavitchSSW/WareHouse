package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {

        Pane orderListPane = FXMLLoader.load(getClass().getResource("orderList.fxml"));
        Pane orderDetailPane = FXMLLoader.load(getClass().getResource("orderDetail.fxml"));

        Scene scene = new Scene(orderListPane,1000,600);
        primaryStage.setScene(scene);
        primaryStage.show();
        PageController pageController = new PageController(scene);

        OrderDetailController orderDetailController = new OrderDetailController(pageController);
        OrderListController orderListController = new OrderListController(pageController,orderDetailController);

        pageController.addPage("orderList",orderListPane,orderListController);
        pageController.addPage("orderDetail",orderDetailPane,orderDetailController);

        primaryStage.setTitle("Hello World");
        pageController.active("orderList");



    }

    public static void main(String[] args) {
        launch(args);
    }
}
