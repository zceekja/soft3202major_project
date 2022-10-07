package major_project.view;

import major_project.controller.AppController;
import major_project.model.App;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import java.util.Optional;
import java.util.*;
import javafx.animation.*;
import javafx.util.*;
import javafx.scene.control.Label;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
public class SplashPane {
    private static final double WINDOW_WIDTH = 780;
    private static final double WINDOW_HEIGHT = 780;
    private Pane pane;
    private Timeline timeline;
    private ProgressBar progress;
    public SplashPane(){
        Image splashImage = new Image("splash.png",WINDOW_WIDTH,WINDOW_HEIGHT,false,false);
        BackgroundImage bImg = new BackgroundImage(splashImage,BackgroundRepeat.NO_REPEAT,BackgroundRepeat.NO_REPEAT,BackgroundPosition.DEFAULT,BackgroundSize.DEFAULT);
        Background background =new Background(bImg);
        pane = new Pane();
        progress = new ProgressBar();
        VBox vbox = new VBox();
        vbox.setPrefSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        HBox logoPane = new HBox();
        logoPane.setPrefSize(WINDOW_WIDTH, 630);
        HBox loadingPane = new HBox();
        loadingPane.setPrefSize(WINDOW_WIDTH,200);
        loadingPane.getChildren().add(progress);
        vbox.getChildren().add(logoPane);
        vbox.getChildren().add(loadingPane);
        vbox.setBackground(background);
        pane.getChildren().add(vbox);
        loadingPane.setAlignment(Pos.BASELINE_CENTER);
        progress.setPrefSize(400,50);
    }

    public Pane getPane(){
        return pane;
    }
    public Timeline getTimeline(){
        return timeline;
    }
    public void setTimeline(Timeline timeline){
        this.timeline =timeline;
    }
    public ProgressBar getProgressBar(){
        return progress;
    }

}
