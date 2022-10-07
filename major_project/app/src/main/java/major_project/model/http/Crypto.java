package major_project.model.http;
import java.util.*;

public class Crypto {
    private Integer id;
    private String name;
    private String symbol;
    private String slug;
    private String logo;
    private String description;
    private Date date_launched;
    private String date_launched_s;
    private Urls urls;
    private Map<String, Convert> quote;

    public Integer getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getSymbol(){
        return symbol;
    }
    public String getSlug(){
        return slug;
    }
    public String getLogo(){
        return logo;
    }
    public String getDescription(){
        return description;
    }
    public Date getDate_launched(){
        return date_launched;
    }
    public String getDate_launched_s(){
        return date_launched_s;
    }
    public Urls getUrls(){
        return urls;
    }
    public void setId(Integer id){
        this.id =id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setSymbol(String symbol){
        this.symbol = symbol;
    }
    public void setSlug(String slug){
        this.slug = slug;
    }
    public void setLogo(String logo){
        this.logo = logo;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setDate_launched(String date_launched_s){
        this.date_launched_s = date_launched_s;
    }
    public void setWebsite(String website){
        urls = new Urls();
        urls.createList();
        urls.addWebsite(website);

    }
    public  Map<String, Convert> getQuote(){
        return quote;
    }
}
