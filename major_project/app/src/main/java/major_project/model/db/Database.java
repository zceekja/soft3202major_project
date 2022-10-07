package major_project.model.db;
import major_project.model.http.Crypto;
import java.io.File;
import java.sql.*;
import java.util.*;

public interface Database{
    public  void createDB();
    public  void removeDB();
    public  void setupDB();
    public  void addCrypto(
        Integer id, 
        String name ,
        String symbol, 
        String slug, 
        String description,
        String startdate,
        String logo,
        String url);
    public void removeCrypto(Integer Id);
    public Crypto getCrypto(Integer Id);
    public boolean checkCryptoTimeStamp(Integer id);
    public boolean checkDatabase(Integer id);
    public void clearCache();
}