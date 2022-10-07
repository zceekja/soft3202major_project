package major_project.model.http;
import java.util.*;

public class Urls {
    private List<String> website;
  

    public String getWebsite(){
        return website.get(0);
    }
    public void createList(){
        website = new ArrayList();
    }
    public void addWebsite(String url){
        website.add(url);
    }

}
