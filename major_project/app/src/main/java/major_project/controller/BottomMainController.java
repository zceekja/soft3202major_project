package major_project.controller;
import major_project.model.*;
import major_project.view.AppWindow;
import major_project.model.http.Request;
import major_project.model.http.SendGridRequest;
import major_project.model.db.DatabaseImpl;
import major_project.model.db.Database;
import major_project.model.http.Reddit;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.auth.AuthenticationException;
import java.io.IOException;
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
import javafx.scene.layout.*;

public class BottomMainController  {
    private final App model;
    private final AppWindow view;
    private final Lock lock;
    private Task<Integer> task;
    private ExecutorService pool;


    public BottomMainController(App model, AppWindow view,  Lock lock,ExecutorService pool)  {
        this.model = model;
        this.view = view;
        this.lock = lock;
        this.pool =pool;
        setButtons();
    }
    /* 
    * Set action for all buttons
    */
    private void setButtons() {
        view.getBottomPane().getAboutBtn().setOnAction((event)-> aboutAction());
        view.getBottomPane().getUserAccountBtn().setOnAction((event)-> userAccountAction());
        view.getBottomPane().getMyCurrencyBtn().setOnAction((event)-> myCurrencyAction());
    }   
    
    /* 
    * Set center pane to about pane
    */
    public void aboutAction(){
        view.getBorderPane().setCenter(view.getAboutPane().getAboutVBox());
    }
    /* 
    * Set center pane to setting pane
    */
    public void userAccountAction(){
        view.getBorderPane().setCenter(view.getSettingPane().getSettingVBox());
    }
    /* 
    * Set center pane to my currency list pane
    */
    public void myCurrencyAction(){
        view.getBorderPane().setCenter(view.getCenterPane().getCenterVBox());
    }
}