package major_project.model.http;
import java.util.*;

public class RedditToken {
    private String access_token;
    private String token_type;
    private Number expires_in;
    private String scope;
    private String id;


    public String getAccessToken(){
        return access_token;
    }
    public String getTokenType(){
        return token_type;
    }
    public Number getExpiresIn(){
        return expires_in;
    }
    public String getScope(){
        return scope;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getId(){
        return id;
    }
}