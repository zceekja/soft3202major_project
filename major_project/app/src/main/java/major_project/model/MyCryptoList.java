package major_project.model;
import major_project.model.http.Request;
import major_project.model.http.Crypto;
import major_project.model.http.SendGridRequest;
import java.util.*;
import javafx.util.Pair;
import java.util.concurrent.locks.*;
public class MyCryptoList{
    private List<Crypto> list;
    private Crypto cryptoToAdd;
    private Crypto cryptoToRemove;
    private final List<Observer> observers;
    private Request re;
    private Lock lock;
    private Object lockObject;

    public MyCryptoList(Request inputApi, Lock lock, Object lockObject){
        this.lock = lock;
        this.lockObject = lockObject;
        re = inputApi;
        observers = new ArrayList<Observer>();
        list = new ArrayList<Crypto>();

    }
    /**
    * add crypto to list 
    * @param crypto Crypto object to append to a list
    */
    public void add(Crypto crypto){
        list.add(crypto);
        cryptoToAdd = crypto;
        cryptoToRemove = null;
    }
    /**
    * get list size
    * @return size of list
    */
    public Integer size(){
        return list.size();
    }
    /**
    * get list size
    * @return size of list
    */
    public void clearCurrencyList(){
        list.clear();
        cryptoToAdd = null;
        cryptoToRemove = null;
        updateObservers();
    }
    /**
    * get Crypto object to remove from list
    * @return Crypto object to remove
    */
    public Crypto getCryptoToRemove(){
        return cryptoToRemove;
    }
    /**
    * get Crypto object to add to list
    * @return Crypto object to add to list
    */
    public Crypto getCryptoToAdd(){
        return cryptoToAdd;
    }
    /**
    * remove Currency from List and update observer
    * @param symbol - Crypto symbol in String
    */
    public void removeCurrencyList(String symbol){
        for (int i =0; i < list.size(); i++){
            if (list.get(i).getSymbol().equals(symbol)){
                cryptoToAdd =null;
                cryptoToRemove = list.get(i);
                list.remove(i);
            }
        }
        updateObservers();
    }
    /**
    * add Currency to list from api call
    * @param choice - crypto choice
    * @param cryptoLists - a map of crypto name/crypto number for all cryptos
    */
    public void addCurrencyListFromApi(String choice, Map<String,Integer> cryptoLists){
        synchronized( lockObject){
            
            String id_string =  Integer.toString(cryptoLists.get(choice));
            Crypto crypto = re.fetchCrypto(id_string);

            this.add(crypto);

            updateObservers();
            try{
                lockObject.wait();
            } catch(InterruptedException e){
                ;
            }
            return ;
        }

    }
    /**
    * add Currency to list from api call
    * @param method - String of method
    * @param choice - crypto choice
    * @param cryptoLists - a map of crypto name/crypto number for all cryptos
    */
    public void addCryptoToList(String method, String choice, Map<String,Integer> cryptoLists){
        if (method.equals("API")){
            addCurrencyListFromApi(choice,cryptoLists);
        }else{
            addCurrencyListFromDatabase(choice,cryptoLists);
        }
    }
    /**
    * add Currency to list from  database
    * @param choice - crypto choice
    * @param cryptoLists - a map of crypto name/crypto number for all cryptos
    */
    public void addCurrencyListFromDatabase(String choice, Map<String,Integer> cryptoLists){
        synchronized( lockObject){
            Integer id = cryptoLists.get(choice);
            //String id_string =  Integer.toString(cryptoLists.get(choice));
            Crypto crypto =  re.getDatabase().getCrypto(id);
            this.add(crypto);
            updateObservers();
            try{
                lockObject.wait();
            } catch(InterruptedException e){
                ;
            }
            return ;
        }
    }
    /**
    * check whether crypto is exist list or not.
    * @param choice - crypto choice
    * @param cryptoLists - a map of crypto name/crypto number for all cryptos
    * @return true if crypto is not exist, false otherwise
    */
    public boolean checkCryptoExist(String choice, Map<String,Integer> cryptoLists){
        String id_string="0";
        Integer id = cryptoLists.get(choice);
        for (Crypto x : list){
            if (id.equals(x.getId())){
                return false;
            }
        }
        return true;

    }
    /**
    * check whether crypto is exist in database or not
    * @param choice - crypto choice
    * @param cryptoLists - a map of crypto name/crypto number for all cryptos
    * @return true if crypto is exist, false otherwise
    */
    public boolean checkCryptoDatabase(String choice, Map<String,Integer> cryptoLists){
        Integer id = cryptoLists.get(choice);
        return re.getDatabase().checkDatabase(id);
    }
    /**
    * get crypto id by crypto symbol
    * @param symbol - crypto symbol
    * @return crypto id in String
    */
    public String getIdBySymbol(String symbol){
        for (Crypto i : list){
            if (i.getSymbol().equals(symbol)){
                return Integer.toString(i.getId());
            }
        }
        return null;
    }
    /**
    * get list of crypto symbol String from list of Crypto object
    * @return List of crypto symbol string
    */
    public List<String> getMyCryptoListString(){
        List<String> ret = new  ArrayList<String>();
        for (Crypto x : list){
            ret.add(x.getSymbol());
        }
        return ret;
    }
    /**
    * register observer
    * @param observer - observer to register
    */
    public void registerObserver(Observer observer){
        this.observers.add(observer);
    }
    /**
    * notify all observer to update
    */
    private void updateObservers(){
        for(Observer observer: observers){
            observer.update();
        }
    }
    /**
    * get crypto list
    * @return lsit of Crypto object
    */
    public List<Crypto> getList(){
        return list;
    }
}
