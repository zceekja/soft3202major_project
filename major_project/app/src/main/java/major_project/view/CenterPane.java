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
import javafx.scene.control.ProgressIndicator;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;

public class CenterPane implements Observer {

    private final App model;
    private VBox centerVBox;
    private VBox yourListVBox;
    private VBox menuVBox;
    private List<Pair<String,Integer>> cryptoLists;
    private List<Pair<HBox,String>> cryptoUrl;
    private Map<Crypto,HBox> CryptoMap = new HashMap<Crypto,HBox>();
    private CenterMainController controller;
    private Button addCurrencyBtn;
    private Button removeCurrencyBtn;
    private Button clearListBtn; 
    private List<Button> buttons;
    private HBox menuHBox;
    //private Lock lock = new ReentrantLock();


    private Task<Integer> task;

     private final ExecutorService pool = Executors.newFixedThreadPool(1, runnable -> {
        Thread thread = new Thread(runnable);
        thread.setDaemon(true);
        return thread ;
    });

    /**
    * CenterPane Constructor
    * Create currencyListPane and menu pane
    * @param model - application logic
    */
    public CenterPane( App model  ){

        this.model = model;
        model.getMyCryptoList().registerObserver(this);
        centerVBox = new VBox(10);
        buildYourCurrencyListPane();
        buildMenuPane();
        
   
    }

    /**
    * CenterPane Constructor
    * Create currencyListPane and menu pane
    * @param controller center controller
    */
    public void setController(CenterMainController controller){
        this.controller = controller;
    }

    private void runTask() {
        task = new Task<>() 
        {
            final Random rand = new Random();
            Lock lock = model.getLock();
            Object lockObject = model.getLockObject();
            @Override
            protected Integer call() {
                synchronized( lockObject){
                    if (model.getMyCryptoList().getCryptoToAdd() != null){
                        cryptoUrl = new ArrayList<Pair<HBox,String>>();
                        Crypto crypto = model.getMyCryptoList().getCryptoToAdd();
                        HBox cryptoDetail = new HBox(5);
                        Image icon = new Image(crypto.getLogo());
                        ImageView imageView = new ImageView(icon);
                        VBox imageVbox = new VBox();
                        imageView.setFitHeight(50);
                        imageView.setFitWidth(50);
                        HBox imageHbox = new HBox();
                        HBox spacer1 = new HBox();
                        spacer1.setPrefSize(30,30);
                        imageHbox.getChildren().addAll(spacer1,imageView);

                        
                        Label name = new Label(crypto.getName());
                        name.setPrefSize(130,20);
                        name.setStyle("-fx-font: 12 arial;");
                        name.setAlignment(Pos.BASELINE_CENTER);

                        Label symbol = new Label(crypto.getSymbol());
                        symbol.setPrefSize(130,20);
                        symbol.setStyle("-fx-font: 12 arial;");
                        symbol.setAlignment(Pos.BASELINE_CENTER);       

                        
                        imageVbox.getChildren().addAll(name,symbol,imageHbox);
                        imageVbox.setPrefSize(130,100);
                        cryptoDetail.getChildren().add(imageVbox);
                        VBox description = new VBox(10);
                        Label descriptionV = new Label(crypto.getDescription());
                        descriptionV.setWrapText(true);
                        descriptionV.setPrefSize(300,100);
                        descriptionV.setStyle("-fx-font: 10 arial;");
                        description.getChildren().add(descriptionV);
                        cryptoDetail.getChildren().add(description);
                        VBox dateLaunched = new VBox(10);
                        Label dateLaunchedL = new Label("Date Launched");
                        dateLaunchedL.setStyle("-fx-font: 10 arial;");
                        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
                        String strDate;

                        if(crypto.getDate_launched() == null && crypto.getDate_launched_s() == null){
                             strDate = "N/A";
                        }else if (crypto.getDate_launched_s() == null){
                            strDate = dateFormat.format(crypto.getDate_launched());  
                        } else{
                            strDate = crypto.getDate_launched_s();
                        }
                        Label dateLaunchedV = new Label(strDate);
                        dateLaunchedV.setStyle("-fx-font: 13 arial;");
                        dateLaunched.getChildren().add(dateLaunchedL);
                        dateLaunched.getChildren().add(dateLaunchedV);
                        dateLaunched.setPrefSize(90,100);
                        cryptoDetail.getChildren().add(dateLaunched);
                        
                        HBox.setMargin(cryptoDetail, new Insets(0,20,0,20));
                        VBox.setMargin(dateLaunchedL, new Insets(10,0,0,0));
                        HBox.setMargin(imageView, new Insets(0,0,0,10));
                        cryptoDetail.setPrefSize(528,100);
                        final String url = crypto.getUrls().getWebsite(); 
                        addCryptoToList(cryptoDetail,url,crypto);

                    } else if (model.getMyCryptoList().getCryptoToRemove() != null){
                        removeCryptoFromList();

                    } else {
                        clearCryptoList();
                    }
                    //Finish render, notify model
                    lockObject.notify(); 
                    return 1;
                }
                
            }
        };
        pool.execute(task);

    }
    /**
    * Add individual crypto interface to list pane
    * @param cryptoDetail detail of crypto
    * @param crypto crypto object
    * @param url crypto link
    */
    private void addCryptoToList(HBox cryptoDetail , String url,Crypto crypto){
    
        Platform.runLater(() ->{
       
            yourListVBox.getChildren().add(cryptoDetail);
            cryptoUrl.add(new Pair<HBox,String>(cryptoDetail, url));
            CryptoMap.put(crypto,cryptoDetail);
            controller.setListAction();
     
        });
    }
    /**
    * Remove crypto interface from list
    */
    private void removeCryptoFromList(){
        Platform.runLater(() ->{
            yourListVBox.getChildren().remove(CryptoMap.get(model.getMyCryptoList().getCryptoToRemove()));
            CryptoMap.remove(model.getMyCryptoList().getCryptoToRemove());
            controller.setListAction();
        });
    }
    /**
    * Remove  all crypto  from list
    */
    private void clearCryptoList(){
        Platform.runLater(() ->{
            yourListVBox.getChildren().clear();
            CryptoMap.clear();
            controller.setListAction();
        });
    }
    /**
    * Build crypto list pane
    */
    private void buildYourCurrencyListPane(){
        HBox yourListHeading = new HBox(10);
        Label yourList = new Label("CURRENCY LIST");
        yourListHeading.setAlignment(Pos.BASELINE_CENTER);
        yourListHeading.setStyle("-fx-font: 24 Helvetica;");
        yourListHeading.getChildren().add(yourList);
        yourListVBox = new VBox(10);
        ScrollPane scrollList = new ScrollPane(yourListVBox);

        scrollList.setPrefSize(530,640);
        scrollList.setFitToWidth(true);
        //scrollList.setStyle("-fx-background: #DCDCDC; -fx-border-color: transparent;");
        centerVBox.getChildren().add(yourListHeading);
        centerVBox.getChildren().add(scrollList);
        VBox.setMargin(scrollList, new Insets(0,25,0,25));
        VBox.setMargin(yourListHeading, new Insets(10, 0, 10, 0));
        centerVBox.setBackground(new Background(new BackgroundFill(Color.web("#FFFFFF"),null,null)));
    }
    /**
    * build menu pane
    */
    private void buildMenuPane(){
        
        buttons = new ArrayList<Button>();
        addCurrencyBtn = new Button("Add Currency");
        removeCurrencyBtn = new Button("Remove Currency");
        clearListBtn = new Button("Clear List");
        Collections.addAll(buttons,addCurrencyBtn ,removeCurrencyBtn,clearListBtn);
        for(Button i : buttons ){
            i.setPrefSize(200,50);
        }
        menuHBox = new HBox(10);
        menuHBox.getChildren().addAll(buttons);
        menuHBox.setAlignment(Pos.BASELINE_CENTER);

 
        menuVBox = new VBox(); 

        menuVBox.setPrefSize(1000,75);
        menuVBox.getChildren().add(menuHBox);
        VBox.setMargin(menuHBox, new Insets(12, 0, 12, 0));
        VBox.setMargin(menuVBox, new Insets(0,25,0,25));
        centerVBox.getChildren().add(menuVBox);

    }

    public List<Pair<HBox,String>> getCryptoUrl(){
        return cryptoUrl;
    }
    public VBox getCenterVBox(){
        return centerVBox;
    }
    public void setCruptoLists(List<Pair<String,Integer>> cryptoLists){
        this.cryptoLists = cryptoLists;
    }
    public Button getAddCurrencyBtn(){
        return this.addCurrencyBtn;
    }
    public Button getRemoveCurrencyBtn(){
        return this.removeCurrencyBtn;
    }
    public Button getClearCurrencyBtn(){
        return this.clearListBtn;
    }

    public void update(){
        runTask();  
    }


}
