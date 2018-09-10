package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        Pane orderPane = FXMLLoader.load(getClass().getResource("orderLayout.fxml"));
        Pane samplePane = FXMLLoader.load(getClass().getResource("sample.fxml"));

        PageController pageController = new PageController(primaryStage);

        pageController.addPage("orderManagement",orderPane);
        pageController.addPage("sample",samplePane);

        primaryStage.setTitle("Hello World");
        pageController.active("orderManagement");


        OrderController orderController = new OrderController(pageController);
        SampleController sampleController = new SampleController(pageController);
        System.out.print("OK");

    }

    public static void main(String[] args) {
        launch(args);
    }
}
