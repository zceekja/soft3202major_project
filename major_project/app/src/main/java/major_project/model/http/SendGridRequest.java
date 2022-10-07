package major_project.model.http;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javafx.application.Application;
import java.util.*;
import javafx.util.Pair;
import java.text.SimpleDateFormat;  
import java.text.DateFormat;  

public class SendGridRequest {
    private final String apiKey = System.getenv("SENDGRID_API_KEY");
    private final String sendgridEmail = System.getenv("SENDGRID_API_EMAIL");
    private String email ="";
    private String reportContent ="";
    private Pair<Crypto,Crypto> cryptoPair;
    private String rate;
    private String title;
    private String answer;
    private boolean mode;

    public SendGridRequest(boolean mode){
        this.mode=mode;
    }

    /** 
    * Send report email
    */
    public void sendEmail(){
        if (!mode){
            return;
        }
        
        String payload = "{\"personalizations\": [{\"to\": [{\"email\":\"" + email +"\"}],\"subject\":\"report from soft3202-kjag8350\"}],\"content\": [{\"type\": \"text/html\",\"value\": \"" +  getReportContent() + "\"}],\"from\":{   \"email\":\""+ sendgridEmail +"\",\"name\":\"kittbhumi jaggabatara\"}}";
        try{
            Thread.sleep(3000);
            HttpRequest request = HttpRequest.newBuilder(new URI("https://api.sendgrid.com/v3/mail/send"))
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .header("Authorization","Bearer "+ apiKey)
                .header("Content-Type","application/json ")
                .build();
            HttpClient client = HttpClient.newBuilder().build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        }catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
            System.out.println(e.getMessage());
            //return "Something went wrong!!!!!!";
            return ;
        } catch (URISyntaxException ignored) {
            System.out.println("something wrong");
            return ;
        }
    }
    /** 
    * Set report content
    */
    public void setReportContent(){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
        String keyDate = "";
        String valueDate = "";
        if(cryptoPair.getKey().getDate_launched() == null && cryptoPair.getKey().getDate_launched_s() == null){
             keyDate = "N/A";
        }else if (cryptoPair.getKey().getDate_launched_s() == null){
            keyDate = dateFormat.format(cryptoPair.getKey().getDate_launched());  
        } else{
            keyDate = cryptoPair.getKey().getDate_launched_s();
        }

        if(cryptoPair.getValue().getDate_launched() == null && cryptoPair.getValue().getDate_launched_s() == null){
             valueDate = "N/A";
        }else if (cryptoPair.getValue().getDate_launched_s() == null){
            valueDate = dateFormat.format(cryptoPair.getValue().getDate_launched());  
        } else{
            valueDate = cryptoPair.getValue().getDate_launched_s();
        }


        reportContent = "<h3>Your quote,</h3>";
        reportContent += "<img src=\\\"" + cryptoPair.getKey().getLogo() + "\\\" alt=\\\"alternatetext\\\"><br>";
        reportContent += "<p1>Currency: " + cryptoPair.getKey().getName() + "</p1><br>";
        reportContent += "<p1>Symbol: " + cryptoPair.getKey().getSymbol() + "</p1><br>";
        reportContent += "<p1>Description: " + cryptoPair.getKey().getDescription() + "</p1><br>";
        reportContent += "<p1>Date launch: " + keyDate + "</p1><br><br>";
        
        reportContent += "<img src=\\\"" + cryptoPair.getValue().getLogo() + "\\\" alt=\\\"alternatetext\\\"><br>";
        reportContent += "<p1>Currency: " + cryptoPair.getValue().getName() + "</p1><br>";
        reportContent += "<p1>Symbol: " + cryptoPair.getValue().getSymbol() + "</p1><br>";
        reportContent += "<p1>Description: " + cryptoPair.getValue().getDescription() + "</p1><br>";
        reportContent += "<p1>Date launch: " + valueDate + "</p1><br><br>";

        reportContent += "<p1>Exchange rate : " + rate + "<br>";
        reportContent += "<p1>" + title + "</p1>";;
        reportContent += "<p1>" + answer + "</p1>";
        
    }
    /** 
    * Set email
    * @param email - email string
    */
    public void setEmail(String email){
        this.email = email;
    }
    /** 
    * Set Crypto pair 
    * @param toExchange - crypto to exchange
    * @param exchangeTo - exchange to crypto
    */
    public void setCryptoPair(Crypto toExchange, Crypto exchangeTo){
        cryptoPair = new Pair<Crypto,Crypto>(toExchange,exchangeTo);
    }
    /** 
    * get report content
    * @return report content
    */
    public String getReportContent(){
        return reportContent;
    }
    /** 
    * Set exchange rate 
    * @param rate - exchange rate in string
    */
    public void setRate(String rate){
        this.rate =rate;
    }
    /** 
    * Set report result title
    * @param title report title
    */
    public void setResultTitle(String title){
        this.title =title;
    }
    /** 
    * Set reprot result answer
    * @param anser report answer
    */
    public void setResultAnswer(String answer){
        this.answer = answer;
    }

}