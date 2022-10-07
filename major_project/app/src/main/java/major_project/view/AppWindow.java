package major_project.view;

import major_project.controller.AppController;
import major_project.model.App;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import java.util.Optional;
import java.util.*;
import javafx.animation.*;
import javafx.util.*;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.geometry.Pos;
public class AppWindow{

    private static final double WINDOW_WIDTH = 780;
    private static final double WINDOW_HEIGHT = 780;
    private Scene splashScene;
    private Scene mainScene;
    private BottomPane bottomPane;
    private CenterPane centerPane;
    private SettingPane settingPane;
    private RightPane rightPane;
    private SplashPane splashPane;
    private AboutPane aboutPane;
    private TopMenu topMenu;
    private Dialogs dialogs;
    private App model;
    private AppController controller;
    private Stage primaryStage;
    private BorderPane pane;
    private StackPane stackpane;
    private ProgressIndicator pb;


    public AppWindow(App model, Stage primaryStage ){
        this.primaryStage = primaryStage;
        this.model = model;
    }
    /**
    * set controller
    * @param controller - controller objec to set
    */
    public void setController(AppController controller){
        this.controller = controller;
    }
    /**
    * set controller
    * Build every pane, and progress indicator
    */
    public void setup(){
        //Build spash scene
        buildSplashPane();
        splashScene = new Scene(splashPane.getPane());

        //Build main scene
        stackpane = new StackPane();
        pane = new BorderPane();
        pane.setPrefSize(WINDOW_WIDTH,WINDOW_HEIGHT);
        Pane progressPane = new Pane();
        progressPane.setPrefSize(WINDOW_WIDTH,WINDOW_HEIGHT);

        pb = new ProgressIndicator();
        pb.setVisible(false);
        pb.setLayoutX(380);
        pb.setLayoutY(380);
        
        progressPane.getChildren().add(pb);
        progressPane.setPickOnBounds(false);

        stackpane.getChildren().add(pane);
        stackpane.getChildren().add(progressPane);


        this.dialogs = new Dialogs();
        buildMenu();
        buildBottomPane();
        buildCenterPane();
        buildRightPane();
        buildSettingPane();
        buildAboutPane();
        pane.setTop(topMenu.getMenu());
        pane.setRight(rightPane.getRightVBox());
        pane.setBottom(bottomPane.getBottomVBox());
        pane.setCenter(centerPane.getCenterVBox());
        mainScene = new Scene(stackpane);
        mainScene.getStylesheets().add(getClass().getResource("/stylesheet.css").toExternalForm());
    }

    public BorderPane getBorderPane(){
        return pane;
    }
    public StackPane getStackPane(){
        return stackpane;
    }
    /**
    * Build Splash Scene
    * create new SplashPane object
    */
    private void buildSplashPane(){
        splashPane = new SplashPane();
    }
    /**
    * Build Menu bar
    * create new TopMenu object
    */
    private void buildMenu() {
        topMenu = new TopMenu();
    }
    /**
    * Build Bottom Pane
    * create new BottomPane object.
    */
    private void buildBottomPane(){
        bottomPane = new BottomPane();
    }
    /**
    * Build Center Pane
    * create new CenterPane object.
    */
    private void buildCenterPane(){
        centerPane = new CenterPane(model);
    }
    /**
    * Build Right Pane
    * create new RightPane object.
    */
    private void buildRightPane(){
        rightPane = new RightPane(model);
    }
    /**
    * Build Setting Pane
    * create new SettingPane object.
    */
    private void buildSettingPane(){
        settingPane = new SettingPane(model);
    }
    /**
    * Build About Pane
    * create new AboutPane object.
    */
    private void buildAboutPane(){
        aboutPane = new AboutPane(model);
    }

    public Dialogs getDialogs(){
        return dialogs;
    }

    
    public Scene getSplashScene() {
        return this.splashScene;
    }
    public Scene getMainScene(){
        return this.mainScene;
    }
    public BottomPane getBottomPane(){
        return bottomPane;
    }
    public CenterPane getCenterPane(){
        return centerPane;
    }
    public RightPane getRightPane(){
        return rightPane;
    }
    public SplashPane getSplashPane(){
        return splashPane;
    }
    public SettingPane getSettingPane(){
        return settingPane;
    }
    public TopMenu getTopMenu(){
        return topMenu;
    }
    public ProgressIndicator getPb(){
        return pb;
    }
    public AboutPane getAboutPane(){
        return aboutPane;
    }
}