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
import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.ClientProtocolException;
import java.io.IOException;

public class RightMainController  {
    private final App model;
    private final AppWindow view;
    private  Lock lock;
    private final Request re;
    private Task<Integer> task;
    ExecutorService pool;

    public RightMainController(App model, AppWindow view, Request re , Lock lock,ExecutorService pool)  {
        this.model = model;
        this.view = view;
        this.re = re;
        this.lock = lock;
        this.pool = pool;
        setButtons();
    }
    /** 
    * Set action for all buttons in right pane
    */
    private void setButtons() {
        view.getRightPane().getConvertBtn().setOnAction((event)-> convertAction());
        view.getRightPane().getLongReportBtn().setOnAction((event)->{
            try{
                sendReportAction();
            }catch ( IOException e){
                e.printStackTrace();
            }
        });
        view.getRightPane().getRedditReportBtn().setOnAction((event)->{
            postReportToRedditAction();
        });
    }


    /**
    * If lock is aqquire by other thread mean, we are processing previous action, then  notify user with alert dialog.
    * If string input cannot parse to double, mean user put invalid input, notify user with alert.
    * If crypto field is empty, mean user didn't select crpto to convert, notify user with alert.
    * aqquire lock and perform convert logic, when done unlock.
    */
    private void convertAction(){
        if(!model.isLockReady()){
            view.getDialogs().getMovingTooFastAlert().showAndWait();
            return;
        }
        model.isLockReady();
        if(!model.checkDouble(view.getRightPane().getAmount())){
            view.getDialogs().getInvalidAmountAlert().showAndWait();
            return;
        }
        String toConvertSymbol = view.getRightPane().getToConvertChoice();
        String convertToSymbol = view.getRightPane().getConvertToChoice();
        if (toConvertSymbol == null || convertToSymbol == null){
            view.getDialogs().getEmptyFieldAlert().showAndWait();
            return;
        }

        task = new Task<>()
        {
            @Override
            protected Integer call(){
                model.isLockReady();
                view.getPb().setVisible(true);
                lock.lock();
                String id = model.getMyCryptoList().getIdBySymbol(view.getRightPane().getToConvertChoice());
                String amount = view.getRightPane().getAmount();
                Double rate = model.getRate( id,  convertToSymbol);
                String result = model.calculate(rate,Double.parseDouble(amount));
                String resultTitle = amount + " "+ toConvertSymbol +" =";
                String resultAnswer = " " + result + " "+ convertToSymbol;
                model.getSendGridRequest().setCryptoPair(model.getCryptoBySymbol(toConvertSymbol),model.getCryptoBySymbol(convertToSymbol));
                model.getReddit().setCryptoPair(model.getCryptoBySymbol(toConvertSymbol),model.getCryptoBySymbol(convertToSymbol));
                model.getReddit().setRate(Double.toString(rate));
                model.getReddit().setResultTitle(resultTitle);
                model.getReddit().setResultAnswer(resultAnswer);
                model.getSendGridRequest().setRate(Double.toString(rate));
                model.getSendGridRequest().setResultTitle(resultTitle);
                model.getSendGridRequest().setResultAnswer(resultAnswer);
                view.getRightPane().buildExchangeResultPane(resultTitle, resultAnswer);
                lock.unlock();
                view.getPb().setVisible(false);

                if (model.isLessThanThreshold(rate)){
                    Platform.runLater(()->{
                        view.getDialogs().getLargeDifferentAlert().showAndWait();
                    });

                }
                return 1;
            }
        };

        pool.execute(task);
    }

    /** 
    * If lock is aqquire by other thread mean, we are processing previous action, then  notify user with alert dialog.
    * aqquire lock and perform sendReport logic, when done unlock and notify user with alert
    */
    private void sendReportAction()throws IOException{
        if(!model.isLockReady()){
            view.getDialogs().getMovingTooFastAlert().showAndWait();
            return;
        } else if (model.getSetting().getEmail()== null){
            view.getDialogs().getEmailNotSetAlert().showAndWait();
            return;
        }
        task = new Task<>()
        {
            @Override
            protected Integer call(){
                lock.lock();
                view.getPb().setVisible(true);
                String email = model.getSetting().getEmail();
                model.getSendGridRequest().setEmail(email);
                model.getSendGridRequest().setReportContent();
                model.getSendGridRequest().sendEmail();

                Platform.runLater(()->{
                    view.getDialogs().getSentAlert().showAndWait();
                });
                view.getPb().setVisible(false);
                lock.unlock();
                return 1;
            }
        };
        pool.execute(task);
    }
    /**
    *  If lock is aqquire by other thread mean, we are processing previous action, then  notify user with alert dialog.
    * aqquire lock and perform sendReportToReddit logic, when done unlock and notify user with alert
    */
    private void postReportToRedditAction(){
        if (model.getSetting().getRedditToken() == null){
            view.getDialogs().getRedditAccountNotSetAlert().showAndWait();
            return;
        }
        task = new Task<>()
        {
            @Override
            protected Integer call(){
                lock.lock();
                view.getPb().setVisible(true);
                try{
                    model.getReddit().setReportContent();
                    model.getReddit().postToReddit(model.getSetting().getRedditToken().getId(),model.getSetting().getRedditToken().getAccessToken());
                    Platform.runLater(()->{
                        view.getDialogs().getRedditPostAlert().showAndWait();
                    });
                }catch(ClientProtocolException e){
                    ;
                } catch( IOException ee){
                    ;
                }catch(AuthenticationException eee){
                    ;
                }
                view.getPb().setVisible(false);
                lock.unlock();
                return 1;
            }
        };
        pool.execute(task);
    }
}