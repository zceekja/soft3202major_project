package major_project.view;


import javafx.scene.control.*;
import java.util.*;
import javafx.util.Pair;
import major_project.model.http.Crypto;
import javafx.scene.shape.*;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.layout.VBox;
import javafx.geometry.Pos;
public class Dialogs{

    private ChoiceDialog<String> addCurrencyDialog;
    private ChoiceDialog<String> removeCurrencyDialog;
    private ChoiceDialog<String> apiOrDatabaseDialog;
    private TextInputDialog setEmailDialog;
    private TextInputDialog setThresholdDialog;
    private Alert largeDifferentAlert;
    private Alert valueOutOfRangeAlert;
    private Dialog<List<String>> setRedditDialog;
    private Alert duplicateCoinAlert;
    private Alert emailSentAlert;
    private Alert invalidAmountAlert;
    private Alert movingTooFastAlert;
    private Alert emptyFieldAlert;
    private Alert clearCacheAlert;
    private Alert aboutAlert;
    private Alert emailNotSetAlert;
    private Alert thisIsNotEmailAlert;
    private Alert redditClientIdNotSetAlert;
    private Alert redditClientSecretNotSetAlert;
    private Alert redditValidationFailAlert;
    private Alert redditAccountNotSetAlert;
    private Alert redditPostAlert;
    

    public Dialogs(){
        setAlert();
    }
    /**
    * Set all alert and dialogs
    */
    public void setAlert(){
        largeDifferentAlert = new Alert(Alert.AlertType.INFORMATION);
        largeDifferentAlert.setTitle("Alert");
        largeDifferentAlert.setHeaderText("Large difference in currency values");
        valueOutOfRangeAlert = new Alert(Alert.AlertType.ERROR);
        valueOutOfRangeAlert.setTitle("Alert");
        valueOutOfRangeAlert.setHeaderText("Value out of range");
        redditAccountNotSetAlert = new Alert(Alert.AlertType.ERROR);
        redditAccountNotSetAlert.setTitle("Error");
        redditAccountNotSetAlert.setHeaderText("You are not login to reddit. Please login in Settings");
        redditValidationFailAlert = new Alert(Alert.AlertType.ERROR);
        redditValidationFailAlert.setTitle("Error");
        redditValidationFailAlert.setHeaderText("We cannot validate your account. Please check your username and password.");
        redditClientIdNotSetAlert = new Alert(Alert.AlertType.ERROR);
        redditClientIdNotSetAlert.setTitle("Error");
        redditClientIdNotSetAlert.setHeaderText("REDDIT_CLIENT_ID env not set.");
        redditClientSecretNotSetAlert = new Alert(Alert.AlertType.ERROR);
        redditClientSecretNotSetAlert.setTitle("Error");
        redditClientSecretNotSetAlert.setHeaderText("REDDIT_CLIENT_SECRET env not set.");
        thisIsNotEmailAlert = new Alert(Alert.AlertType.ERROR);
        thisIsNotEmailAlert.setTitle("Error");
        thisIsNotEmailAlert.setHeaderText("Please enter valid email.");
        emailNotSetAlert = new Alert(Alert.AlertType.ERROR);
        emailNotSetAlert.setTitle("Error");
        emailNotSetAlert.setHeaderText("Email is not set. Please set an email in Settings.");
        duplicateCoinAlert = new Alert(Alert.AlertType.ERROR);
        duplicateCoinAlert.setTitle("Error");
        duplicateCoinAlert.setHeaderText("This coin is already exists in the list.");
        emailSentAlert = new Alert(Alert.AlertType.INFORMATION);
        emailSentAlert.setTitle("Success");
        emailSentAlert.setHeaderText("An email has been sent. Please check your inbox.");
        redditPostAlert = new Alert(Alert.AlertType.INFORMATION);
        redditPostAlert.setTitle("Success");
        redditPostAlert.setHeaderText("A post has been created on your reddit profile.");

        invalidAmountAlert = new Alert(Alert.AlertType.ERROR);
        invalidAmountAlert.setTitle("Error");
        invalidAmountAlert.setHeaderText("Invalid input. Value is not number.");
        movingTooFastAlert = new Alert(Alert.AlertType.ERROR);
        movingTooFastAlert.setTitle("Error");
        movingTooFastAlert.setHeaderText("You are moving too fast. We are responding to the previous request.");
        emptyFieldAlert = new Alert(Alert.AlertType.ERROR);
        emptyFieldAlert.setTitle("Error");
        emptyFieldAlert.setHeaderText("Some field is missing.");
        clearCacheAlert = new Alert(Alert.AlertType.INFORMATION);
        clearCacheAlert.setTitle("Success");
        clearCacheAlert.setHeaderText("Cache has been cleared.");
        aboutAlert = new Alert(Alert.AlertType.INFORMATION);
        aboutAlert.setTitle("About");
        String content= "Currencry Exchange (Coinmarketcap)\n";
        content +="Developer: Kittibhumi Jaggabatara\n";
        content +="Reference:\n";
        content +="[1] Splash image: https://somsubhra.com/cryptocurrency-trading-made-simple/\n";
        aboutAlert.setHeaderText(content);
        setApiOrDatabaseDialog();
        setSetEmailDialog();
        setSetRedditDialog();
        setSetThresholdDialog();
    }
    public Alert getRedditValidationFailAlert(){
        return redditValidationFailAlert;
    }
    public Alert getRedditClientIdNotSetAlert(){
        return redditClientIdNotSetAlert;
    }
    public Alert getRedditClientSecretNotSetAlert(){
        return redditClientSecretNotSetAlert;
    }
    public Alert getEmptyFieldAlert(){
        return emptyFieldAlert;
    }
    public Alert getSentAlert(){
        return emailSentAlert;
    }
    public Alert getDuplicateAlert(){
        return duplicateCoinAlert;
    }
    public Alert getInvalidAmountAlert(){
        return invalidAmountAlert;
    }
    public Alert getMovingTooFastAlert(){
        return movingTooFastAlert;
    }
    public Alert getClearCacheAlert(){
        return clearCacheAlert;
    }
    public Alert getAboutAlert(){
        return aboutAlert;
    }
    public TextInputDialog getSetEmailDialog(){
        return setEmailDialog;
    }
    public Dialog<List<String>> getSetRedditDialog(){
        return setRedditDialog;
    }
    public void setSetThresholdDialog(){
        setThresholdDialog = new TextInputDialog();
        setThresholdDialog.setTitle("Set threshold");
        setThresholdDialog.setHeaderText("Enter threshold value between 0.1 and 1.0");
    }
    public TextInputDialog getSetThresholdDialog(){
        return setThresholdDialog;
    }
    public void setSetEmailDialog(){
        setEmailDialog = new TextInputDialog();
        setEmailDialog.setTitle("Set Email");
        setEmailDialog.setHeaderText("Enter your email.");

    }
    public Alert getLargeDifferentAlert(){
        return largeDifferentAlert;
    }
    public Alert getValueOutOfRangeAlert(){
        return valueOutOfRangeAlert;
    }
    public void setSetRedditDialog(){

        setRedditDialog = new Dialog<>();
        setRedditDialog.setTitle("Set Reddit Account");
        setRedditDialog.setHeaderText("Enter reddit account detail.");
        setRedditDialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        PasswordField password = new PasswordField();
        TextField username = new TextField();
        VBox contents = new VBox(10);
        HBox usernameHbox = new HBox();
        usernameHbox.setAlignment(Pos.CENTER_LEFT);
        usernameHbox.setSpacing(10);
        usernameHbox.getChildren().add(new Label("Username:"));
        usernameHbox.getChildren().add(username);
        HBox passwordHbox = new HBox();
        passwordHbox.setAlignment(Pos.CENTER_LEFT);
        passwordHbox.setSpacing(10);
        passwordHbox.getChildren().add(new Label("Password: "));
        passwordHbox.getChildren().add(password);
        contents.getChildren().addAll(usernameHbox,passwordHbox);
        setRedditDialog.getDialogPane().setContent(contents);
        setRedditDialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return Arrays.asList(username.getText(), password.getText());
            }
            return null;
        });
    }
    public void setAddCurrencyDialog(Map<String,Integer> itemPair){

        addCurrencyDialog = new ChoiceDialog(null,itemPair.keySet());
        addCurrencyDialog.setTitle("Add Currency");
        addCurrencyDialog.setHeaderText("Choose currency to add. ");
        addCurrencyDialog.setContentText("");
    }
    public ChoiceDialog<String> getAddCurrencyDialog(){
        return addCurrencyDialog;
    } 

    public void setRemoveCurrencyDialog(List<Crypto> mylist){
        List<String> symbols = new ArrayList<String>();
        for(Crypto i : mylist ){
            symbols.add(i.getSymbol());
        }
        removeCurrencyDialog = new ChoiceDialog(null,symbols);
        removeCurrencyDialog.setTitle("Remove Currency");
        removeCurrencyDialog.setHeaderText("Choose currency to remove.");
        removeCurrencyDialog.setContentText("");
    }
    public ChoiceDialog<String>  getRemoveCurrencyDialog(){
        return this.removeCurrencyDialog;
    }
    public Alert getEmailNotSetAlert(){
        return emailNotSetAlert;
    }
    public void setApiOrDatabaseDialog(){
        List<String> choices = new ArrayList<String>();
        choices.add("API");
        choices.add("Database");
        apiOrDatabaseDialog = new ChoiceDialog(null,choices);
        apiOrDatabaseDialog.setTitle("Cache hit");
        apiOrDatabaseDialog.setHeaderText("There is data about this crypto in our database. Please choose method to add this data");
        apiOrDatabaseDialog.setContentText("");

    }
    public ChoiceDialog<String> getApiOrDatabaseDialog(){

        return apiOrDatabaseDialog;
    }
    public Alert getThisIsNotEmailAlert(){
        return thisIsNotEmailAlert;
    }
    public Alert getRedditAccountNotSetAlert(){
        return redditAccountNotSetAlert;
    }
    public Alert getRedditPostAlert(){
        return redditPostAlert;
    }
    
}