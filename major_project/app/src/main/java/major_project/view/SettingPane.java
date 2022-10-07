package major_project.view;

import major_project.model.App;
import major_project.model.MyCryptoList;
import major_project.model.Observer;
import major_project.model.http.Crypto;
import major_project.controller.CenterMainController;
import javafx.scene.layout.HBox;
import major_project.controller.AppController;
import javafx.geometry.Pos;
import java.util.*;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.geometry.Insets;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.util.Pair;
import javafx.scene.image.ImageView;
import java.text.SimpleDateFormat;  
import java.text.DateFormat;  
import javafx.scene.image.Image;
import javafx.application.HostServices;
import javafx.scene.input.MouseEvent;
import javafx.event.EventHandler;
import javafx.concurrent.Task;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.*;
import javafx.application.Platform;
import javafx.scene.control.Separator;
import javafx.geometry.Orientation;

public class SettingPane  {

    private final App model;
    private VBox centerVBox;
    private VBox settingVBox;
    private Button setEmailBtn;
    private Button setRedditBtn;
    private Label emailA;
    private Label redditA;
    private Label thresholdA;
    private Button clearCacheBtn;


    public SettingPane( App model  ){
  
        this.model = model;
        centerVBox = new VBox(10);
        buildSettingVBox();
        buildAccountConfigurationPane();
        
   
    }

    /**
    * build setting vBox
    */
    private void buildSettingVBox(){
        settingVBox = new VBox(10);
        settingVBox.setPadding(new Insets(20, 20, 20, 20));
        Label userSettingL = new Label("User settings");
        HBox userSettingHeading = new HBox();
        userSettingHeading.setStyle("-fx-font: 20 Helvetica;");
        userSettingHeading.getChildren().add(userSettingL);
        Separator separator1 = new Separator(Orientation.HORIZONTAL);
        separator1.setPrefWidth(485);
        Label emailL = new Label("Email: ");
        emailA = new Label("Not set");
        HBox emailHBox = new HBox(10);
        emailHBox.getChildren().add(emailL);
        emailHBox.getChildren().add(emailA);
        setEmailBtn = new Button("Set Email");
        Label redditL = new Label("Reddit Account: ");
        redditA = new Label("Not set");
        HBox redditHBox = new HBox(10);
        redditHBox.getChildren().add(redditL);
        redditHBox.getChildren().add(redditA);
        setRedditBtn = new Button("Set Reddit Account");
        HBox spacer = new HBox();
        spacer.setPrefSize(35,35);
        HBox thresholdHBox = new HBox(10);
        Label thresholdL =new Label("Conversion Threshold: ");
        thresholdA = new Label("Not set");
        thresholdHBox.getChildren().add(thresholdL);
        thresholdHBox.getChildren().add(thresholdA);

        Label dataManagementL = new Label("Data management");
        HBox dataManagementHeading = new HBox();
        dataManagementHeading.setStyle("-fx-font: 20 Helvetica;");
        dataManagementHeading.getChildren().add(dataManagementL);

        Separator separator2 = new Separator(Orientation.HORIZONTAL);
        separator2.setPrefWidth(485);
        clearCacheBtn = new Button("Clear Cache");
        settingVBox.getChildren().addAll(userSettingHeading,separator1,emailHBox,setEmailBtn,redditHBox,setRedditBtn,thresholdHBox,spacer,dataManagementHeading,separator2,clearCacheBtn);

        
    }

    /**
    * build configuration pane
    */
    private void buildAccountConfigurationPane(){

        HBox settingHeading = new HBox(10);
        Label setting = new Label("SETTINGS");
        settingHeading.setAlignment(Pos.BASELINE_CENTER);
        settingHeading.setStyle("-fx-font: 24 Helvetica;");
        settingHeading.getChildren().add(setting);
        centerVBox.getChildren().add(settingHeading);
        VBox.setMargin(settingHeading, new Insets(10, 0, 10, 0));

        
        ScrollPane scrollBox = new ScrollPane(settingVBox);
        scrollBox.setPrefSize(530,640);
        centerVBox.getChildren().add(scrollBox);
        VBox.setMargin(scrollBox, new Insets(0,25,0,25));
        centerVBox.setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"),null,null)));

    }
    public void setThresholdA(String threshold){
        thresholdA.setText(threshold);
    }
    public VBox getSettingVBox(){
        return centerVBox;
    }
    public Button getSetEmailBtn(){
        return setEmailBtn;
    }
    public Button getSetRedditBtn(){
        return setRedditBtn;
    }
    public void setEmail(String email){
        emailA.setText(email);
    }
    public void setReddit(String id){
        redditA.setText(id);
    }
    public Button getClearCacheBtn(){
        return clearCacheBtn;
    }

}
