package major_project.model.http;
import org.apache.http.client.methods.*;
import org.apache.http.client.*;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.apache.http.protocol.HttpContext;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.Reader;
import org.apache.http.HttpHeaders;
import org.apache.http.util.EntityUtils;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.NameValuePair;
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

public class Reddit {
    private final String reddit_id = System.getenv("REDDIT_CLIENT_ID");
    private final String reddit_secret = System.getenv("REDDIT_CLIENT_SECRET");
    private Pair<Crypto,Crypto> cryptoPair;
    private String rate;
    private String title;
    private String reportContent;
    private String answer;

    /** 
    * Get Access Token from Api
    * @param username - username string
    * @param password - password string
    * @return RedditToken if username and password is valid, null otherwise
    */
    public RedditToken getAccessToken(String username, String password)throws ClientProtocolException, IOException,AuthenticationException{
        try{
            Thread.sleep(3000);
        }catch(InterruptedException e){
            ;
        }
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://www.reddit.com/api/v1/access_token");
        httpPost.setHeader("Content-type", "application/x-www-form-urlencoded");
        String encoding = Base64.getEncoder().encodeToString((reddit_id + ":" + reddit_secret).getBytes());
        httpPost.setHeader(HttpHeaders.AUTHORIZATION, "Basic " + encoding);
        httpPost.setHeader("User-Agent", "tobyTheDcuk/0.1 by kjag8888");
        String data = "{grant_type=password&username=kjag8888&password=Bowwy0410!";    
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("grant_type", "password"));
        params.add(new BasicNameValuePair("username", username));
        params.add(new BasicNameValuePair("password", password));
        httpPost.setEntity(new UrlEncodedFormEntity(params));

        
        CloseableHttpResponse response = client.execute(httpPost);

        String responseBody = EntityUtils.toString(response.getEntity());
        Gson gson = new Gson();
        
        RedditToken redditToken = gson.fromJson(responseBody,RedditToken.class);
        redditToken.setId(username);
        //Cannot validate
        client.close();
        if(redditToken.getAccessToken() == null){
            return null;
        }
        return redditToken;
    }
    /** 
    * Post to reddit
    * @param id - username string
    * @param access_token - access_token string
    */
    public void postToReddit(String id, String access_token)throws ClientProtocolException, IOException,AuthenticationException{
        try{
            Thread.sleep(3000);
        }catch(InterruptedException e){
            ;
        }
        String text = null;
        try{
            text = URLEncoder.encode(reportContent,"UTF-8");
        
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String post_title = "report%20from%20soft3202-kjag8350";
        String reddit_user_id = id;
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost("https://oauth.reddit.com/api/submit?sr="+"r/u_"+reddit_user_id+"&title=" + post_title + "&kind=self&text=" + text);
        httpPost.setHeader("Authorization", "bearer " + access_token);
        httpPost.setHeader("User-Agent", "tobyTheDcuk/0.1 by kjag8888");
        CloseableHttpResponse response = client.execute(httpPost);
        String responseBody = EntityUtils.toString(response.getEntity());

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

        String a = "# Your quote,  ";
        String b = "Currency:  "+ cryptoPair.getKey().getName()+"  ";
        String c = "Symbol: " + cryptoPair.getKey().getSymbol()+"  ";
        String d = "Description: " + cryptoPair.getKey().getDescription()+"  ";
        String e = "Date launch: " + keyDate+"  ";
        String f = "  ";
        String g = "Currency: " + cryptoPair.getValue().getName()+"  ";
        String h = "Symbol: " + cryptoPair.getValue().getSymbol()+"  ";
        String i = "Description: " + cryptoPair.getValue().getDescription()+"  ";
        String j = "Date launch: " + valueDate +"  ";
        String k = "  ";
        String l = "Exchange rate : " + rate +"  ";
        String m = title+" " + answer +"  ";
        reportContent = String.join(System.lineSeparator(), a,b,c,d,e,f,g,h,i,j,k,l,m);
        
    }
    /** 
    * Set crypto pair
    */
    public void setCryptoPair(Crypto toExchange, Crypto exchangeTo){
        cryptoPair = new Pair<Crypto,Crypto>(toExchange,exchangeTo);
    }
    /** 
    * Get report content
    * @return report content in string
    */
    public String getReportContent(){
        return reportContent;
    }
    /** 
    * Set exchange rate
    * @param rate - exchange rate
    */
    public void setRate(String rate){
        this.rate =rate;
    }
    /** 
    * Set exchange result title 
    * @param title - String title
    */
    public void setResultTitle(String title){
        this.title =title;
    }
    /** 
    * Set exchange result answer 
    * @param title - String answer
    */
    public void setResultAnswer(String answer){
        this.answer = answer;
    }
}