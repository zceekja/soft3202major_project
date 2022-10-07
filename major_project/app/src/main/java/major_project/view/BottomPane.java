package major_project.view;
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

public class BottomPane {

    private List<Button> buttons;
    private Button userAccountBtn;
    private Button myCurrencyBtn;
    private Button aboutBtn;
    private HBox bottomHBox;
    private VBox bottomVBox;
    /**
    * BottomPane Constructor
    * create 3 Buttons (list,about,setting)
    * create HBox, and add 3 buttons in Hbox
    */
    public BottomPane(){
        buttons = new ArrayList<Button>();
        myCurrencyBtn = new Button("My Currency");
        userAccountBtn = new Button("Settings");
        aboutBtn = new Button("About");
        Collections.addAll(buttons,myCurrencyBtn ,userAccountBtn,aboutBtn);
        for(Button i : buttons ){
            i.setPrefSize(250,25);
        }
        bottomHBox = new HBox(10);
        bottomHBox.getChildren().addAll(buttons);
        bottomHBox.setAlignment(Pos.BASELINE_CENTER);
        bottomVBox = new VBox(10);
        bottomVBox.getChildren().add(bottomHBox);
        VBox.setMargin(bottomHBox, new Insets(10, 0, 10, 0));
        bottomVBox.setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"),null,null)));
        bottomVBox.setStyle("-fx-font: 18 Helvetica;");
    }

    public VBox getBottomVBox(){
        return bottomVBox;
    }

    public Button getUserAccountBtn(){
        return this.userAccountBtn;
    }
    public Button getAboutBtn(){
        return this.aboutBtn;
    }
    public Button getMyCurrencyBtn(){
        return this.myCurrencyBtn;
    }

}
