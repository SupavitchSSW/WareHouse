package sample;

import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

public class SampleController implements Controller{
    PageController pageController;

    public SampleController(PageController pageController) {
        this.pageController = pageController;
        initilize();
    }

    @Override
    public void initilize() {
        Scene scene = pageController.getScene("sample");
        Button btn = (Button) scene.lookup("#switchBtn");
        btn.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event){
                System.out.print("Click");
                pageController.active("orderManagement");
            }
        });
    }

    @Override
    public void onActive() {

    }
}
