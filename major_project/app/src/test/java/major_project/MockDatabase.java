package major_project;

import major_project.model.db.*;
import major_project.model.http.Crypto;
import java.io.File;
import java.sql.*;
import java.util.*;

public class MockDatabase implements Database{

    public  void createDB(){;}
    public  void removeDB(){;}
    public  void setupDB(){;}
    public  void addCrypto(
        Integer id, 
        String name ,
        String symbol, 
        String slug, 
        String description,
        String startdate,
        String logo,
        String url){;};
    public void removeCrypto(Integer Id){;}
    public Crypto getCrypto(Integer Id){
        if(Id == 1839){
            Crypto crypto = new Crypto();
            crypto.setId(1839);
            crypto.setName("Binance Coin");
            crypto.setSymbol("BNB");  
            return crypto;
        }

        return null;
    }
    public boolean checkCryptoTimeStamp(Integer id){
        return false;
    }
    public boolean checkDatabase(Integer id){
        // 1839 is id for binance coin
        if(id == 1839){
            return true;
        }
        return false;
     }
    public void clearCache(){;}
}