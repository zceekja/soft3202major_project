package major_project.controller;
import major_project.model.*;
import major_project.view.AppWindow;
import major_project.model.http.Request;
import major_project.model.http.SendGridRequest;
import major_project.model.db.DatabaseImpl;
import major_project.model.db.Database;
import major_project.model.http.Reddit;
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

public class AppController  {
    private final App model;
    private final AppWindow view;
    private final HostServices service;
    private final Lock lock;
    private Stage primaryStage;
    private Task<Integer> task;
    private BottomMainController bottomMainController;
    private CenterMainController centerMainController;
    private RightMainController rightMainController;
    private SplashController splashController;
    private TopMenuController topMenuController;
    private SettingController settingController;

    private final ExecutorService pool = Executors.newFixedThreadPool(5, runnable -> {
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        return thread ;
    });

    public AppController(App model, AppWindow view, HostServices service, Stage primaryStage)  {
        this.model = model;
        this.view = view;
        this.service =service;
        this.primaryStage = primaryStage;
        Request re = new Request(model.getInputApi(), new DatabaseImpl());
        this.model.setLock(new ReentrantLock(), new Object());
        this.model.setup(re, new MyCryptoList(re, model.getLock(), model.getLockObject()),new SendGridRequest(model.getOutputApi()));
        this.view.setController(this);
        this.view.setup();
        this.model.setReddit(new Reddit());
        lock = model.getLock();
        splashController = new SplashController(model,view,primaryStage);
        bottomMainController= new BottomMainController(model,view,lock, pool);
        centerMainController = new CenterMainController(model,view,service ,lock, pool);
        rightMainController = new RightMainController(model,view,re,lock, pool);
        topMenuController = new TopMenuController(model,view,this);
        settingController = new SettingController(model,view,lock, pool);
  
        view.getDialogs().setAddCurrencyDialog(model.getCryptoLists());
    }

    /** 
    * Get center main controller
    * @return CenterMainController object
    */
    public CenterMainController getCenterMainController(){
        return this.centerMainController;
    }
    /** 
    * Get bottom main controller
    * @return BottomMainController object
    */
    public BottomMainController getBottomMainController(){
        return this.bottomMainController;
    }
    /** 
    * Get right main controller
    * @return RightMainController object
    */
    public RightMainController getRightMainController(){
        return this.rightMainController;
    }
    /** 
    * Get spalsh main controller
    * @return splashController object
    */
    public SplashController getSplashController(){
        return this.splashController;
    }

}