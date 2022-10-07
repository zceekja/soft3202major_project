package major_project.model.http;
import java.util.*;

class CryptoMapRequest {
    private Status status;
    private List<CryptoMap> data;

    public Status getStatus(){
        return status;
    }
    
    public List<CryptoMap> getData(){
        return data;
    }

}
