package major_project.model.http;
import java.util.*;

class CryptoMap {
    private Integer id;
    private String name;
    private String symbol;

    public Integer getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public String getSymbol(){
        return symbol;
    }
}

//fetch from /v1/cryptocurrency/map
