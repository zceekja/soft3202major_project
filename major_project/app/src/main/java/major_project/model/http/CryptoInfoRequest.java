package major_project.model.http;
import java.util.*;

class CryptoInfoRequest {
    private Status status;
    private Map<String, Crypto> data;

    public Status getStatus(){
        return status;
    }
    
    public Map<String, Crypto> getData(){
        return data;
    }
    
}
