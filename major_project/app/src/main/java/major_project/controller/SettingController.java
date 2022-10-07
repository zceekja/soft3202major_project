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

public class SettingController  {
    private final App model;
    private final AppWindow view;
    private  Lock lock;
    private Task<Integer> task;
    ExecutorService pool;
    public SettingController(App model, AppWindow view, Lock lock,ExecutorService pool)  {
        this.model = model;
        this.view = view;
        this.lock = lock;
        this.pool = pool;
        setButtons();
    }
    /**
    * Set all buttons action for controller pane.
    */
    private void setButtons() {
        view.getSettingPane().getSetEmailBtn().setOnAction((event)-> setEmailAction());
        view.getSettingPane().getSetRedditBtn().setOnAction((event)-> setRedditAction());
        view.getSettingPane().getClearCacheBtn().setOnAction((event)-> clearCacheAction());

    }


    /**
    * If lock is aqquire by other thread mean, we are processing previous action, then  notify user with alert dialog.
    * launch dialog for user to input an email.
    * Use model logic to check for valid email, if email is not valid ( contain exactly 1 @) inform user with alert.
    * If email is valid, set this email.
    */
    private void setEmailAction(){
        if(!model.isLockReady()){
            view.getDialogs().getMovingTooFastAlert().showAndWait();
            return;
        }
        Optional<String> result = view.getDialogs().getSetEmailDialog().showAndWait();
        result.ifPresent(email -> {
            if (!model.getSetting().validateEmail(email)){
                view.getDialogs().getThisIsNotEmailAlert().showAndWait();
                model.getSetting().setEmail(null);
                view.getSettingPane().setEmail("Not set");
                return;
            }
            model.getSetting().setEmail(email);
            view.getSettingPane().setEmail(model.getSetting().getEmail());
        });
    }
    /**
    * If env not set inform user with alert dialog.
    * If lock is aqquire by other thread mean, we are processing previous action, then  notify user with alert dialog.
    * Launch dialog for user to type reddit username and password.
    * Create new thread to check username and password with api.
    * if input is valid add set this reddit account.
    */
    private void setRedditAction(){
        if (System.getenv("REDDIT_CLIENT_ID") == null ){
            view.getDialogs().getRedditClientIdNotSetAlert().showAndWait();
            return;
        } else if (System.getenv("REDDIT_CLIENT_SECRET") == null){
            view.getDialogs().getRedditClientSecretNotSetAlert().showAndWait();
            return;
        }
        else if(!model.isLockReady()){
            view.getDialogs().getMovingTooFastAlert().showAndWait();
            return;
        }

        Optional<List<String>> result = view.getDialogs().getSetRedditDialog().showAndWait();

        task = new Task<>()
        {
            @Override
            protected Integer call(){ 
                model.isLockReady();
                view.getPb().setVisible(true);
                lock.lock();
                result.ifPresent( resultList -> {
                    String username = resultList.get(0);
                    String password = resultList.get(1);

                    if(model.getSetting().validateReddit(username,password,new Reddit()) == false){
                        Platform.runLater(()->{
                            view.getDialogs().getRedditValidationFailAlert().showAndWait();
                            view.getSettingPane().setReddit("Not set");
                        }); 
                        
                    }
                    Platform.runLater(()->{
                        if(model.getSetting().getRedditToken() == null){
                            view.getSettingPane().setReddit("Not set");
                        }else{
                            view.getSettingPane().setReddit(model.getSetting().getRedditToken().getId());
                        }
                    }); 
                });
                lock.unlock();
                view.getPb().setVisible(false);
                return 1;
            }
        };
        pool.execute(task);
    }
    /**
    * If lock is aqquire by other thread mean, we are processing previous action, then  notify user with alert dialog.
    * Clear crypto Database;
    */
    public void clearCacheAction(){
        if(!model.isLockReady()){
            view.getDialogs().getMovingTooFastAlert().showAndWait();
            return;
        }
        lock.lock();
        model.getRequest().getDatabase().clearCache();
        view.getDialogs().getClearCacheAlert().showAndWait();
        lock.unlock();
    }
}