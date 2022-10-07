
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
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.Spinner;

public class CenterMainController  {
    private final App model;
    private final AppWindow view;
    private final HostServices service;
    private final Lock lock;
    private Task<Integer> task;
    private ExecutorService pool;

    public CenterMainController(App model, AppWindow view, HostServices service, Lock lock,ExecutorService pool)  {
        this.model = model;
        this.view = view;
        this.service =service;
        this.lock = lock;
        this.pool = pool;
        setButtons();
        view.getDialogs().setAddCurrencyDialog(model.getCryptoLists());
        view.getCenterPane().setController(this);

    }

    /**
    * Set center buttons action.
    */
    private void setButtons() {
        view.getCenterPane().getAddCurrencyBtn().setOnAction((event)-> addCurrencyAction());
        view.getCenterPane().getRemoveCurrencyBtn().setOnAction((event)-> removeCurrencyAction());
        view.getCenterPane().getClearCurrencyBtn().setOnAction((event)-> clearCurrencyAction());

    } 
    /** 
    * Set center url action. When click on crypto item, program will launch corresponding url.
    */
    public void setListAction(){
        List<Pair<HBox,String>> pair = view.getCenterPane().getCryptoUrl();
        if (pair == null || pair.size() == 0){
            return;
        }
        else{
            for(int i = 0; i < pair.size(); i++){
                final HBox crypto  =  pair.get(i).getKey();
                final String url = pair.get(i).getValue();
                crypto.setOnMouseEntered(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        crypto.setStyle("-fx-background-color:#dae7f3;");
                    }
                });
                crypto.setOnMouseExited(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        crypto.setStyle("-fx-background-color:transparent;");
                    }
                });
                crypto.setOnMouseClicked(new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent t) {
                        service.showDocument(url);
                    }
                });
            }
        }
    }
    /** 
    * If lock is aqquire by other thread mean, we are processing previous action, then  notify user with alert dialog.
    * Acquire lock and perform addCurrency logic, when done unlock.
    * If user choice of crypto is already exist in user crypto list, then we notify user with alert dialog.
    */
    public void addCurrencyAction(){
        if(!model.isLockReady()){
            view.getDialogs().getMovingTooFastAlert().showAndWait();
            return;
        }
        Optional<String> result = view.getDialogs().getAddCurrencyDialog().showAndWait();
        result.ifPresent(choice ->  {

            lock.lock();
            // If crypto already exist in list
            if( !model.getMyCryptoList().checkCryptoExist(choice, model.getCryptoLists())){
                Platform.runLater(()->{
                    view.getDialogs().getDuplicateAlert().showAndWait();
                    lock.unlock();
                });

            } else {
                // Offline mode, no need to fetch data from cache;
                if (!model.getRequest().getMode()){
                      task = new Task<>(){
                        @Override
                        protected Integer call(){
                            lock.lock();
                            view.getPb().setVisible(true);
                            model.getMyCryptoList().addCurrencyListFromApi(choice,model.getCryptoLists() );
                            view.getPb().setVisible(false);
                            lock.unlock();
                            return 1;
                        }
                    };
                }
                // If crypto is exist in database
                else if(model.getMyCryptoList().checkCryptoDatabase(choice, model.getCryptoLists())){
                    Optional<String> answer = view.getDialogs().getApiOrDatabaseDialog().showAndWait();
                    answer.ifPresent(ans -> {
                        task = new Task<>(){
                            @Override
                            protected Integer call(){
                                lock.lock();
                                view.getPb().setVisible(true);
                                model.getMyCryptoList().addCryptoToList(ans, choice,model.getCryptoLists());
                                view.getPb().setVisible(false);
                                lock.unlock();
                                return 1;
                            }
                        };
                    });  
                }// If crypto is not exist in database
                else{


                    task = new Task<>(){
                        @Override
                        protected Integer call(){
                            lock.lock();
                            view.getPb().setVisible(true);
                            model.getMyCryptoList().addCurrencyListFromApi(choice,model.getCryptoLists() );
                            view.getPb().setVisible(false);

                            
                            lock.unlock();
                            return 1;
                        }
                    };
                    
                    
                }
                lock.unlock();
                pool.execute(task);
            
                
            }
          
        });
    }

    /** 
    * If lock is aqquire by other thread mean, we are processing previous action, then  notify user with alert dialog.
    * Perform remove currency logic
    */
    public void removeCurrencyAction(){
        if(!model.isLockReady()){
            view.getDialogs().getMovingTooFastAlert().showAndWait();
            return;
        }
        view.getDialogs().setRemoveCurrencyDialog(model.getMyCryptoList().getList());
        Optional<String> result = view.getDialogs().getRemoveCurrencyDialog().showAndWait();
        result.ifPresent(choice -> model.getMyCryptoList().removeCurrencyList(choice));
    }

    /** 
    * If lock is aqquire by other thread mean, we are processing previous action, then  notify user with alert dialog.
    * Perform clear currency logic
    */
    public void clearCurrencyAction(){

        if(!model.isLockReady()){
            view.getDialogs().getMovingTooFastAlert().showAndWait();
            return;
        }
        model.getMyCryptoList().clearCurrencyList();
    }

}