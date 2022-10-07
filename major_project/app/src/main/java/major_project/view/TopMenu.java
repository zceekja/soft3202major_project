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

public class TopMenu {

    private MenuBar menuBar;

    private MenuItem userAccountItm;
    private MenuItem aboutItm;
    private MenuItem myCurrencyItm;


    public TopMenu(){
        Menu menu = new Menu("Menu");
        myCurrencyItm = new MenuItem("My Currency");
        userAccountItm  = new MenuItem("Settings");
        aboutItm = new MenuItem("About");
        menu.getItems().addAll(myCurrencyItm, userAccountItm, aboutItm);
        this.menuBar = new MenuBar();
        menuBar.getMenus().add(menu);
    }
    public MenuBar getMenu(){
        return menuBar;
    }
    public MenuItem getMyCurrencyItm(){
        return myCurrencyItm;
    }
    public MenuItem getUserAccountItm(){
        return userAccountItm;
    }
    public MenuItem getAboutItm(){
        return aboutItm;
    }

}
