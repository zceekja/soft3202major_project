package major_project.view;
import major_project.model.MyCryptoList;
import major_project.model.App;
import major_project.model.Observer;
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
import javafx.scene.layout.Pane;
import major_project.model.http.Crypto;
import javafx.collections.FXCollections;
import javafx.application.Platform;

public class RightPane implements Observer{
    private final App model;
    private VBox rightVBox;
    private ChoiceBox<String> toConvertChoice; 
    private ChoiceBox<String> convertToChoice;
    private Button convertBtn;
    private TextField amount;
    private VBox exchangeResultVBox;
    private Button longReportBtn;
    private Button redditReportBtn;
    private VBox reportVBox;

    public RightPane(App model){
        this.model = model;
        model.getMyCryptoList().registerObserver(this);
        rightVBox = new VBox(10);
        rightVBox.setPrefWidth(200);
        buildExchangePane();
        buildReportPane();

   
    }

    public VBox getRightVBox(){
        return rightVBox;
    }
    /**
    * build exchange pane
    */
    private void buildExchangePane(){
        VBox exchangeVBox = new VBox(10);
        HBox exchangeHeading = new HBox();
        Label exchangeL = new Label("Currency Exchange");
        Pane spacer = new Pane();
        spacer.setMinSize(10,10);
        exchangeHeading.setAlignment(Pos.BASELINE_CENTER);
        exchangeHeading.setStyle("-fx-font: 17 arial;");
        exchangeHeading.getChildren().add(exchangeL);
        exchangeVBox.getChildren().add(spacer);
        exchangeVBox.getChildren().add(exchangeHeading);

        Label amountL = new Label("Amount");
        amount =  new TextField();
        Label fromL = new Label("From");
        toConvertChoice = new ChoiceBox<String>();
        toConvertChoice.setPrefWidth(210);
        Label toL = new Label("To");
        convertToChoice = new ChoiceBox<String>();
        convertToChoice.setPrefWidth(210);
        convertBtn = new Button("Convert");
        exchangeResultVBox = new VBox(10);

        exchangeVBox.getChildren().add(amountL);
        exchangeVBox.getChildren().add(amount);
        exchangeVBox.getChildren().add(fromL);
        exchangeVBox.getChildren().add(toConvertChoice);
        exchangeVBox.getChildren().add(toL);
        exchangeVBox.getChildren().add(convertToChoice);
        exchangeVBox.getChildren().add(convertBtn);
        exchangeVBox.getChildren().add(exchangeResultVBox);

        rightVBox.getChildren().add(exchangeVBox);
        rightVBox.setBackground(new Background(new BackgroundFill(Color.web("#ededed"),null,null)));

        VBox.setMargin(exchangeVBox, new Insets(0,15,0,15));
        
    }
    /**
    * build result pane
    * @param title string of title
    * @param answer result of exchange in string
    */
    public void buildExchangeResultPane(String title, String answer){
        Platform.runLater(()->{
            exchangeResultVBox.getChildren().clear();
            Label resultTitle = new Label(title);
            resultTitle.setStyle("-fx-font: 12 arial;");
            Label resultAnswer = new Label(answer);
            resultAnswer.setStyle("-fx-font: 16 arial;");
            exchangeResultVBox.getChildren().add(resultTitle);
            exchangeResultVBox.getChildren().add(resultAnswer);
            exchangeResultVBox.getChildren().add(reportVBox);
            
        });  

    }
    /**
    * Create report pane
    */
    public void buildReportPane(){
        reportVBox = new VBox(10);
        longReportBtn = new Button("Send Report to Email");
        redditReportBtn = new Button("Post Report to Reddit profile");
        reportVBox.getChildren().add(longReportBtn);
        reportVBox.getChildren().add(redditReportBtn);
        
    }

    public Button getConvertBtn(){
        return convertBtn;
    }
    public String getToConvertChoice(){
        return toConvertChoice.getValue();
    };
    public String getConvertToChoice(){
        return convertToChoice.getValue();
    }
    public String getAmount(){
        return amount.getText();
    }
    public Button getLongReportBtn(){
        return longReportBtn;
    }
    public Button getRedditReportBtn(){
        return redditReportBtn;
    }


    @Override
    public void update(){

        List<String> cryptoList =model.getMyCryptoList().getMyCryptoListString();
        Platform.runLater(() ->{
            toConvertChoice.setItems(FXCollections.observableList(cryptoList));
            convertToChoice.setItems(FXCollections.observableList(cryptoList));
        });

        
    }
}