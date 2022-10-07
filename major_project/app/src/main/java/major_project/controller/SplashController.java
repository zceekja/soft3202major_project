
package major_project.controller;
import major_project.model.*;
import major_project.view.AppWindow;
import major_project.model.http.Request;
import major_project.model.http.SendGridRequest;
import major_project.model.db.DatabaseImpl;
import major_project.model.db.Database;
import javafx.scene.control.*;
import java.util.*;
import javafx.util.Pair;
import javafx.application.HostServices;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.application.HostServices;
import javafx.scene.layout.HBox;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.concurrent.Task;
import javafx.application.Platform;
import java.util.concurrent.locks.*;
import javafx.stage.Stage;
import javafx.util.*;
import javafx.scene.control.*;
import javafx.animation.*;

public class SplashController  {
    private final App model;
    private final AppWindow view;
    private Stage primaryStage;

    public SplashController(App model, AppWindow view, Stage primaryStage)  {
        this.model = model;
        this.view = view;
        this.primaryStage = primaryStage;
        setTimeline();
        view.getSplashPane().getTimeline().play();
        
    }
    /**
    * Set Splash duration to 15 second and when fisnih set Scene to MainScreen
    */
    public void setTimeline(){
        
        Timeline timeline = new Timeline(
            new KeyFrame(Duration.ZERO, new KeyValue(view.getSplashPane().getProgressBar().progressProperty(), 0)),
            new KeyFrame(Duration.seconds(15), e-> {
                Platform.runLater(()->{
                    primaryStage.setScene(view.getMainScene());
                    while (true){
                        if( model.getThreshold() != 0){
                            break;
                        }
                        Optional<String> result = view.getDialogs().getSetThresholdDialog().showAndWait();
                        result.ifPresent(input -> {
                            if(!model.checkDouble(input)){
                                view.getDialogs().getInvalidAmountAlert().showAndWait();
                            }
                            else if (!model.checkRange(input)){
                                view.getDialogs().getValueOutOfRangeAlert().showAndWait();
                            }
                            else {
                                model.setThreshold(input);
                                view.getSettingPane().setThresholdA(input);
                            }
                        });
                    }
                });
                
            }, new KeyValue(view.getSplashPane().getProgressBar().progressProperty(), 1))    
        );
        timeline.setCycleCount(1);
        view.getSplashPane().setTimeline(timeline);
        
        
    }

}