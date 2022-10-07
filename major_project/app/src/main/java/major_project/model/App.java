package major_project.model;

import major_project.model.http.Reddit;
import major_project.model.http.Request;
import major_project.model.http.Crypto;
import major_project.model.http.SendGridRequest;
import java.util.*;
import javafx.util.Pair;
import java.util.concurrent.locks.*;


public class App {
    private Map<String,Integer> cryptoLists;
    private MyCryptoList myCryptoList;
    private Request re ;
    private SendGridRequest sendGridRequest;
    private boolean inputApi;
    private boolean outputApi;
    private Lock lock ;
    private Object lockObject ;
    private Setting setting;
    private Reddit reddit;
    private double threshold = 0;    

    public App(String input , String output, Setting setting){
        this.setting = setting;
        set(input,output);
    
    }
    
    public void setLock(Lock lock,Object lockObject){
        this.lock = lock;
        this.lockObject =lockObject;
    }
    public void setup( Request re, MyCryptoList myCryptoList,SendGridRequest sendGridRequest){
        this.re = re;
        re.setApp(this);
        this.myCryptoList = myCryptoList;
        this.sendGridRequest = sendGridRequest;
        cryptoLists = re.fetchAllCoin();
    }

    /**
     Parse api mode to boolean
    * @param input input api mode 
    * @param output output api mode
    */
    private void set(String input,String output){
        if (input.equals("online")){
            inputApi = true;
        }
        if (output.equals("online")){
            outputApi = true;
        }
    }
    /**

    * @return Lock object
    */
    public Lock getLock(){
        return lock;
    }
    /**

    * @return Object that use for synchronize 
    */
    public Object getLockObject(){
        return lockObject;
    }

    /**

    * @return map of all available cryptos in market
    */
    public Map<String,Integer> getCryptoLists(){
        return cryptoLists;
    }
    /**

    * @return MyCryptoList class
    */
    public MyCryptoList getMyCryptoList(){
        return myCryptoList;
    }

    /**
    *  Get exchange rate
    * @param id input crpto id 
    * @param symbol output crypto symbol
    * @return exchange rate in double
    */
    public Double getRate(String id, String symbol){
        return  re.fetchRate(id,symbol);
    }
    
    /**
    * Calculate exchange result
    * @param rate exchange rate
    * @param amount exchange amount
    * @return result in 7 decimal place
    */
    public String calculate(Double rate, Double amount){
        return String.format("%.7f", rate * amount);
    }

    /**
    * Check whether String can be parse to Double or not
    * @param str input string
    * @return true if can / false if cannot
    */
    public boolean checkDouble(String str){
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }

    }
    /**
    * Check whether String of Double are between 0.1 and 1.0 or not
    * @param str input string of double
    * @return true if inside range / false if outside  range
    */
    public boolean checkRange(String str){
        double value = Double.parseDouble(str);
        if (value <= 0.1|| value >= 1){
            return false;
        }
        return true;
    }

    public void setThreshold(String value){

        this.threshold = Double.parseDouble(value);
    }

    public double getThreshold(){
        return this.threshold;
    }
    
   /**
    * Check whether rate is less then threshold or not
    * @param rate conversion rate
    * @return true if rate is below threshold/ false otherwise
    */    
    public boolean isLessThanThreshold(double rate){
        if (rate < this.threshold){
            return true;
        }
        return false;
    }
    /**
    * get input api in boolean
    * @return true if online, false otherwise
    */   
    public boolean getInputApi(){
        return inputApi;
    }

    /**
    * get output api in boolean
    * @return true if online, false otherwise
    */
    public boolean getOutputApi(){
        return outputApi;
    }
    /**
    * Get sendGreidRequest 
    * @return sendGridRequest object
    */
    public SendGridRequest getSendGridRequest(){
        return sendGridRequest;
    }

    /**
    * get Crypto obejct from my crypto list by symbol 
    * @param symbol - crypto symbol in string
    * @return Crypto object
    */
    public Crypto getCryptoBySymbol(String symbol){
        for (Crypto i : myCryptoList.getList()){
            if(i.getSymbol().equals(symbol)){
                return i;
            }
        }
        return null;
    }
    /**
    * check whether lock is available or not
    * @return true if lock is available, false otherwise
    */
    public boolean isLockReady(){

        if (lock.tryLock()){
            try{
                ;
            }
            finally{
                lock.unlock();
                return true;
            }
        }
        else{
            return false;
        }
    }
    /**
    * get Request  
    * @return Request object
    */
    public Request getRequest(){
        return this.re;
    }
    /**
    * get Setting  
    * @return Setting object
    */
    public Setting getSetting(){
        return setting;
    }
    /**
    * Set Reddit  
    * @param reddit - Reddit object
    */
    public void setReddit(Reddit reddit){
        this.reddit =reddit;
    }
    /**
    * get Reddit  
    * @return Reddit object
    */
    public Reddit getReddit(){
        return reddit;
    }

}