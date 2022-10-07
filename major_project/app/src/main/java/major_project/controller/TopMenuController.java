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
import javafx.scene.layout.*;

public class TopMenuController  {
    private final App model;
    private final AppWindow view;
    private AppController controller;


    public TopMenuController(App model, AppWindow view,  AppController controller)  {
        this.model = model;
        this.view = view;
        this.controller = controller;
        setMenuItems();
    }
    /**
    * Set action on menu items
    */
    private void setMenuItems() {
        view.getTopMenu().getUserAccountItm().setOnAction((event)-> controller.getBottomMainController().userAccountAction());
        view.getTopMenu().getMyCurrencyItm().setOnAction((event)-> controller.getBottomMainController().myCurrencyAction());
        view.getTopMenu().getAboutItm().setOnAction((event)-> controller.getBottomMainController().aboutAction());

    }
    
}