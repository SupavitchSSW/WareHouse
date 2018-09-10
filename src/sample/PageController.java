package sample;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.beans.ConstructorProperties;
import java.io.IOException;
import java.util.HashMap;

class PageClass{
    private Pane pane;
    private Scene scene;

    public PageClass(Pane pane) {
        this.pane = pane;
        this.scene = new Scene(pane,1280,800);
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }
}

public class PageController {
    private HashMap<String,PageClass> pageMap = new HashMap<String, PageClass>();
    private Stage window;

    public PageController(Stage window) {
        this.window = window;
    }

    public void addPage(String name, Pane pane){
        pageMap.put(name,new PageClass(pane));
    }

    public void active(String name){
        window.setScene(pageMap.get(name).getScene());
        window.show();
    }

    public Scene getScene(String name){
        return pageMap.get(name).getScene();
    }
}
