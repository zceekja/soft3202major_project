package major_project.model.db;
import major_project.model.http.Crypto;
import java.io.File;
import java.sql.*;
import java.util.*;

public class DatabaseImpl implements Database{
    private  final String dbName = "test.db";
    private  final String dbURL = "jdbc:sqlite:" + dbName;
    
    /**
    * Create database if it not exist
    */
    public  void createDB() {
        File dbFile = new File(dbName);
        if (dbFile.exists()) {
            //Database already created

            return;
        }
        try (Connection ignored = DriverManager.getConnection(dbURL)) {
            // If we get here that means no exception raised from getConnection - meaning it worked
            System.out.println("A new database has been created.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
    /** 
    * Remove Database
    */
    public  void removeDB() {
        File dbFile = new File(dbName);
        if (dbFile.exists()) {
            boolean result = dbFile.delete();
            if (!result) {
                // Couldn't delete existing db file"
                System.exit(-1);
            } else {
                //"Removed existing DB file.
                ;
            }
        } else {
            //No existing DB file.
            ;
        }
    }
    /**
    * Create cryptos database
    * Note: server timezome is +0 therefore it is 10 hours back. ! ! ! !
    */ 

    public  void setupDB() {
        String createCryptoTableSQL =
                """
                CREATE TABLE IF NOT EXISTS cryptos (
                    id integer PRIMARY KEY,
                    name varchar(1000) NOT NULL,
                    symbol varchar(100) NOT NULL,
                    slug varchar(100) ,
                    description varchar(3000) ,
                    startdate varchar(100),
                    logo varchar(300) ,
                    url varchar(200),
                    last_cache timestamp DEFAULT CURRENT_TIMESTAMP

                );
                """;

        try (Connection conn = DriverManager.getConnection(dbURL);
                Statement statement = conn.createStatement()) {
            statement.execute(createCryptoTableSQL);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
    /**
    * Add crypto to cryptos database
    */ 
    public  void addCrypto(
        Integer id, 
        String name ,
        String symbol, 
        String slug, 
        String description,
        String startdate,
        String logo,
        String url) 
        {

        String addUserParametersSQL =
                """
                INSERT INTO cryptos(id, name, symbol,slug,description,startdate,logo,url) VALUES
                    (?,?,?,?,?,?,?,?)
                """;
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement preparedStatement = conn.prepareStatement(addUserParametersSQL)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, symbol);
            preparedStatement.setString(4, slug);
            preparedStatement.setString(5, description);
            preparedStatement.setString(6, startdate);
            preparedStatement.setString(7, logo);
            preparedStatement.setString(8, url);

            preparedStatement.executeUpdate();


        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }

    }
    /**
    * Remove crypto from cryptos database by id
    * @param Id - crypto id to remove
    */ 
    public void removeCrypto(Integer Id){
        String removeCryptoParametersSQL =
                """
                DELETE FROM cryptos WHERE id = ?
                """;
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement preparedStatement = conn.prepareStatement(removeCryptoParametersSQL)) {
            preparedStatement.setInt(1, Id);
            preparedStatement.executeUpdate();

            //System.out.println("crypto removed");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }
    }
    /** 
    *  Get informations of crypto by id from cryptos database
    * @param Id - crypto id to search for
    */ 
    public Crypto getCrypto(Integer Id){
        

        String SearchCryptoSQL =
                """
                SELECT *
                FROM cryptos WHERE id=?
                """;
        try (Connection conn = DriverManager.getConnection(dbURL);
            PreparedStatement preparedStatement = conn.prepareStatement(SearchCryptoSQL)) {
            preparedStatement.setInt(1, Id);

            ResultSet results = preparedStatement.executeQuery();
            if (results.next() == false){

                return null;
            }else{
                Crypto crypto = new Crypto();
                crypto.setDate_launched(results.getString("startdate"));
                crypto.setSymbol(results.getString("symbol"));
                crypto.setSlug(results.getString("slug"));
                crypto.setDescription(results.getString("description"));
                crypto.setLogo(results.getString("logo"));
                crypto.setWebsite(results.getString("url"));
                crypto.setName(results.getString("name"));
                crypto.setId(Id);
                
                return crypto;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
            return null;
        }


    }
    /**
    *  Check whether crypto is exist in database or not by crypto id
    * @param Id - crypto id to check for
    * @return - true id exist, false otherwise
    */ 
    public boolean checkDatabase(Integer id){
        String SearchSaveSQL =
                """
                SELECT id
                FROM cryptos WHERE id=?
                """;
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement preparedStatement = conn.prepareStatement(SearchSaveSQL)) {
            preparedStatement.setInt(1, id);

            ResultSet results = preparedStatement.executeQuery();
            if (results.next() == false){
                return false;
            }else{
   
                return true;
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
            return false;
        }
    }
    public void clearCache(){
        String clearCacheSQL =
            """
            DELETE FROM cryptos;
            """;
        try (Connection conn = DriverManager.getConnection(dbURL);
            Statement statement = conn.createStatement()) {
            statement.execute(clearCacheSQL);
            return;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
            return;
        }
    }
    /**
    * Check whether crypto data is still valid, last cache is with last 24 hours consider valid
    * Note: this function is no longer use in application, because this lovely function conflict with milestone2 requirement.
    * @param Id - crypto id to search for
    * @return true is valid, false is not valid
    */ 
    public boolean checkCryptoTimeStamp(Integer id){
        String SearchSaveSQL =
                """
                SELECT last_cache
                FROM cryptos WHERE id=?
                """;
        try (Connection conn = DriverManager.getConnection(dbURL);
             PreparedStatement preparedStatement = conn.prepareStatement(SearchSaveSQL)) {
            preparedStatement.setInt(1, id);

            ResultSet results = preparedStatement.executeQuery();
            if (results.next() == false){
             
                return false;
            }else{
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                Timestamp lastCache =results.getTimestamp("last_cache");

                if (timestamp.getTime() - lastCache.getTime() < 34*60*60*1000){
                    // cache data is still valid
                    return true;
                }
                else{
                    removeCrypto(id);
                    // cache data is no longer valid - remove it from database!
                    return false;
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.exit(-1);
            return false;
        }

    }

}
