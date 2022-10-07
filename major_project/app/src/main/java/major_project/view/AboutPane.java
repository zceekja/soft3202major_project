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

public class AboutPane  {

    private final App model;
    private VBox aboutVBox;
    private VBox centerVBox;


    public AboutPane( App model  ){
        this.model = model;
        centerVBox = new VBox(10);
        buildAboutVBox();
        buildAboutPane();
        
   
    }
    /**
    * build about VBox
    */
    private void buildAboutVBox(){
        aboutVBox = new VBox(10);
        aboutVBox.setPadding(new Insets(20, 20, 20, 20));
        Label detailL = new Label("Application details");
        HBox detailHeading = new HBox();
        detailHeading.setStyle("-fx-font: 20 Helvetica;");
        detailHeading.getChildren().add(detailL);
        Separator separator1 = new Separator(Orientation.HORIZONTAL);
        separator1.setPrefWidth(485);
        Label applicationNameL = new Label("Name:     soft3202-kjag8350 Currency Exchange (Coinmarketcap)");
        Label version = new Label("Version:    2.2.0 (Exam submission)");
        Label developerL = new Label("Developer:     Kittibhumi Jaggabatara");
        HBox spacer = new HBox();
        spacer.setPrefSize(35,35);
        Label referenceL = new Label("Reference");
        HBox referenceHeading = new HBox();
        referenceHeading.setStyle("-fx-font: 20 Helvetica;");
        referenceHeading.getChildren().add(referenceL);
        Separator separator2 = new Separator(Orientation.HORIZONTAL);
        separator2.setPrefWidth(485);
        Label referenceItm1 = new Label("[1] Splash image source:\n  https://somsubhra.com/cryptocurrency-trading-made-simple/");
        Label referenceItm2 = new Label("[2] Coinmarketcap api doc:\n  https://coinmarketcap.com/api/documentation/v1/");
        Label referenceItm3 = new Label("[3] Sendgrid api doc:\n  https://docs.sendgrid.com/api-reference/mail-send/mail-send");
        Label referenceItm4 = new Label("[4] Reddit api doc:\n  https://www.reddit.com/dev/api/");
        Label referenceItm5 = new Label("[5] Reddit OAuth2 Quick Start Example:\n https://github.com/reddit-archive/reddit/wiki/OAuth2-Quick-Start-Example");
        aboutVBox.getChildren().addAll(detailHeading,separator1,applicationNameL,version,developerL,spacer,referenceHeading,separator2,referenceItm1,referenceItm2,referenceItm3,referenceItm4,referenceItm5);
    }
    /**
    * build about pane
    */
    private void buildAboutPane(){

        HBox settingHeading = new HBox(10);
        Label setting = new Label("ABOUT");
        settingHeading.setAlignment(Pos.BASELINE_CENTER);
        settingHeading.setStyle("-fx-font: 24 Helvetica;");
        settingHeading.getChildren().add(setting);
        centerVBox.getChildren().add(settingHeading);
        VBox.setMargin(settingHeading, new Insets(10, 0, 10, 0));

        
        ScrollPane scrollBox = new ScrollPane(aboutVBox);
        scrollBox.setPrefSize(530,640);
        centerVBox.getChildren().add(scrollBox);
        VBox.setMargin(scrollBox, new Insets(0,25,0,25));
        centerVBox.setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"),null,null)));

    }
    /**
    * get about VBox
    * @return about VBox
    */
    public VBox getAboutVBox(){
        return centerVBox;
    }

}
