package major_project.model;
import major_project.model.http.Request;
import major_project.model.http.Crypto;
import major_project.model.http.SendGridRequest;
import major_project.model.http.RedditToken;
import major_project.model.http.Reddit;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.ClientProtocolException;
import java.io.IOException;
import java.util.*;
import javafx.util.Pair;
import java.util.concurrent.locks.*;
public class Setting{


    private String email;
    private String redditId;
    private RedditToken redditToken;

    public Setting(){
        ;
    }
    /**
    * get eamil
    * @return email string
    */
    public String getEmail(){
        return email;
    }
    /**
    * get reddit id
    * @return reddit id 
    */
    public String getRedditId(){
        return redditId;
    }
    /**
    * Set email
    * @param email -  email string
    */
    public void setEmail(String email){
        this.email = email;
        
    }
    /**
    * Set reddit id
    * @param redditID -  reddit id string
    */
    public void setRedditId(String redditId){
        this.redditId = redditId;
    }
    /**
    * Validate an email
    * @param email -  email string
    * @return true if email contain exactly 1 @, false otherwise
    */
    public boolean validateEmail(String email){
        int count = 0;
        for (int i = 0; i < email.length(); i++){
            if (email.charAt(i) == '@'){
                count++;
            }
        }
        if (count != 1){
            return false;
        }
        return true;
    }
    /**
    * Validate reddit account and login.
    * @param username -  reddit username
    * @param password - reddit password
    * @param reddit - reddit object
    * @return true if reddit account is valid, false if reddit account is not valid.
    */
    public boolean validateReddit(String username, String password, Reddit reddit){
        redditToken = null;
        try{
            redditToken = reddit.getAccessToken(username,password);
        }catch (ClientProtocolException e){
            ;
        }
        catch (IOException ee){
            ;
        }
        catch (AuthenticationException eee){
            ;
        }
        if (redditToken == null){
            return false;
        }
        return true;
    }
    /**
    * Get reddit token
    * @return RedditToken object
    */
    public RedditToken getRedditToken(){
        return redditToken;
    }
    /**
    * Set reddit token
    * @param reditToken - redditToken object to set
    */
    public void setRedditToken(RedditToken redditToken){
        this.redditToken = redditToken;
    }

}
