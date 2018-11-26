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
    private boolean isInit = false;
    private Controller controller;

    public PageClass(Pane pane,Controller controller) {
        this.pane = pane;
        this.controller = controller;
    }

    public void onActive(){
        getController().onActive();
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

    public Pane getPane() {
        return pane;
    }

    public void setPane(Pane pane) {
        this.pane = pane;
    }

    public boolean isInit() {
        return isInit;
    }

    public void setInit(boolean init) {
        isInit = init;
    }
}

public class PageController {
    private HashMap<String,PageClass> pageMap = new HashMap<String, PageClass>();
    private Scene main;

    public PageController(Scene scene) {
        this.main = scene;
    }

    public Scene getMain() {
        return main;
    }

    public void setMain(Scene main) {
        this.main = main;
    }

    public void addPage(String name, Pane pane, Controller controller){
        pageMap.put(name,new PageClass(pane,controller));
    }

    public void active(String name){
        PageClass page = pageMap.get(name);
        main.setRoot(page.getPane());
        if(!page.isInit()){
            page.getController().initilize();
            page.setInit(true);
        }
        page.onActive();
    }

    public Scene getScene(String name){
        return this.main;
    }
}
