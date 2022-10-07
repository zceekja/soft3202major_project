package major_project.model.http;
import major_project.model.*;
import major_project.model.db.Database;
import com.google.gson.Gson;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javafx.application.Application;
import java.util.*;
import javafx.util.Pair;
import java.text.SimpleDateFormat;  
import java.text.DateFormat;  

public class Request{
    private final String  inputApi = System.getenv("INPUT_API_KEY");
    private boolean mode; // true : Online m false : Offline
    private Database db;
    private App app;
    public Request(boolean mode, Database db){
        this.mode = mode;
        this.db = db;
        this.db.createDB();
        this.db.setupDB();
    }

    public void setApp(App app){
        this.app = app;
    }
    /** 
    * Get database instance
    * @return database instance
    */
    public Database getDatabase(){
        return db;
    }
    /** 
    * Get app mode 
    * @return true if mode is online, false otherwise 
    */
    public boolean getMode(){
        return mode;
    }
    /** 
    * Featch all coin list from api 
    * @return Map of title of coin and coin id 
    */
    public  Map<String,Integer> fetchAllCoin(){
        
        try{
            Gson gson = new Gson();
            CryptoMapRequest cryptoMapRequest = null;
            if(mode){
                HttpRequest request = HttpRequest.newBuilder(new URI("https://pro-api.coinmarketcap.com/v1/cryptocurrency/map?CMC_PRO_API_KEY="+ inputApi)).GET()
                    .build();
                HttpClient client = HttpClient.newBuilder().build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                cryptoMapRequest = gson.fromJson(response.body(),CryptoMapRequest.class);
            }else{
                cryptoMapRequest = gson.fromJson("{\"data\": [{\"id\": 1,\"rank\": 1,\"name\": \"Bitcoin\",\"symbol\": \"BTC\",\"slug\": \"bitcoin\",\"is_active\": 1,\"first_historical_data\": \"2013-04-28T18:47:21.000Z\",\"last_historical_data\": \"2020-05-05T20:44:01.000Z\",\"platform\": null},{\"id\": 1839,\"rank\": 3,\"name\": \"Binance Coin\",\"symbol\": \"BNB\",\"slug\": \"binance-coin\",\"is_active\": 1,\"first_historical_data\": \"2017-07-25T04:30:05.000Z\",\"last_historical_data\": \"2020-05-05T20:44:02.000Z\",\"platform\": {\"id\": 1027,\"name\": \"Ethereum\",\"symbol\": \"ETH\",\"slug\": \"ethereum\",\"token_address\": \"0xB8c77482e45F1F44dE1745F52C74426C631bDD52\"}}],\"status\": {\"timestamp\": \"2018-06-02T22:51:28.209Z\",\"error_code\": 0,\"error_message\": \"\",\"elapsed\": 10,\"credit_count\": 1}}",CryptoMapRequest.class);
            }
            Status queryStatus = cryptoMapRequest.getStatus();
            
            List<CryptoMap> data = cryptoMapRequest.getData();
            Map<String,Integer> ret= new HashMap<String,Integer>();
            for(int i =0; i < data.size(); i ++){
                String item = data.get(i).getSymbol() + ": " + data.get(i).getName();
                ret.put(item, data.get(i).getId());
            }

            return ret;
            
        } catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
            System.out.println(e.getMessage());
            //return "Something went wrong!!!!!!";
            return null;
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            //return "Something went very wrong!";
            return null;
        }
    }
    /** 
    * Get crypto from api 
    * @param id - crypto id in string
    * @return Crypto 
    */
    public Crypto fetchCrypto(String id){

        try{
            
            Gson gson = new Gson();
            CryptoInfoRequest cryptoInfoRequest = null;
            if(mode){
                Thread.sleep(3000);
                HttpRequest request = HttpRequest.newBuilder(new URI("https://pro-api.coinmarketcap.com/v2/cryptocurrency/info?CMC_PRO_API_KEY="+ inputApi +"&id="+id)).GET()
                    .build();
                HttpClient client = HttpClient.newBuilder().build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                 cryptoInfoRequest = gson.fromJson(response.body(),CryptoInfoRequest.class);
            } else{
                cryptoInfoRequest = gson.fromJson("{\"data\": {\"1\": {\"category\": \"coin\",\"contract_address\": [],\"date_added\": \"2013-04-28T00:00:00.000Z\",\"date_launched\": null,\"description\": \"Bitcoin (BTC) is a cryptocurrency . Users are able to generate BTC through the process of mining. Bitcoin has a current supply of 19,020,018. The last known price of Bitcoin is 39,908.58337188 USD and is up 1.03 over the last 24 hours. It is currently trading on 9357 active market(s) with $18,233,455,196.28 traded over the last 24 hours. More information can be found at https://bitcoin.org/.\",\"id\": 1,\"is_hidden\": 0,\"logo\": \"https://s2.coinmarketcap.com/static/img/coins/64x64/1.png\",\"name\": \"Bitcoin\",\"notice\": \"\",\"platform\": null,\"self_reported_circulating_supply\": null,\"self_reported_market_cap\": null,\"self_reported_tags\": null,\"slug\": \"bitcoin\",\"subreddit\": \"bitcoin\",\"symbol\": \"BTC\",\"tag-groups\": [\"OTHERS\",\"ALGORITHM\",\"ALGORITHM\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\"],\"tag-names\": [\"Mineable\",\"PoW\",\"SHA-256\",\"Store Of Value\",\"State Channel\",\"Coinbase Ventures Portfolio\",\"Three Arrows Capital Portfolio\",\"Polychain Capital Portfolio\",\"Binance Labs Portfolio\",\"Blockchain Capital Portfolio\",\"BoostVC Portfolio\",\"CMS Holdings Portfolio\",\"DCG Portfolio\",\"DragonFly Capital Portfolio\",\"Electric Capital Portfolio\",\"Fabric Ventures Portfolio\",\"Framework Ventures Portfolio\",\"Galaxy Digital Portfolio\",\"Huobi Capital Portfolio\",\"Alameda Research Portfolio\",\"a16z Portfolio\",\"1Confirmation Portfolio\",\"Winklevoss Capital Portfolio\",\"USV Portfolio\",\"Placeholder Ventures Portfolio\",\"Pantera Capital Portfolio\",\"Multicoin Capital Portfolio\",\"Paradigm Portfolio\"],\"tags\": [\"mineable\",\"pow\",\"sha-256\",\"store-of-value\",\"state-channel\",\"coinbase-ventures-portfolio\",\"three-arrows-capital-portfolio\",\"polychain-capital-portfolio\",\"binance-labs-portfolio\",\"blockchain-capital-portfolio\",\"boostvc-portfolio\",\"cms-holdings-portfolio\",\"dcg-portfolio\",\"dragonfly-capital-portfolio\",\"electric-capital-portfolio\",\"fabric-ventures-portfolio\",\"framework-ventures-portfolio\",\"galaxy-digital-portfolio\",\"huobi-capital-portfolio\",\"alameda-research-portfolio\",\"a16z-portfolio\",\"1confirmation-portfolio\",\"winklevoss-capital-portfolio\",\"usv-portfolio\",\"placeholder-ventures-portfolio\",\"pantera-capital-portfolio\",\"multicoin-capital-portfolio\",\"paradigm-portfolio\"],\"twitter_username\": \"\",\"urls\": {\"announcement\": [],\"chat\": [],\"explorer\": [\"https://blockchain.coinmarketcap.com/chain/bitcoin\",\"https://blockchain.info/\",\"https://live.blockcypher.com/btc/\",\"https://blockchair.com/bitcoin\",\"https://explorer.viabtc.com/btc\"],\"facebook\": [],\"message_board\": [\"https://bitcointalk.org\"],\"reddit\": [\"https://reddit.com/r/bitcoin\"],\"source_code\": [\"https://github.com/bitcoin/bitcoin\"],\"technical_doc\": [\"https://bitcoin.org/bitcoin.pdf\"],\"twitter\": [],\"website\": [\"https://bitcoin.org/\"]}},\"1839\": {\"category\": \"coin\",\"contract_address\": [],\"date_added\": \"2017-07-25T00:00:00.000Z\",\"date_launched\": null,\"description\": \"BNB (BNB) is a cryptocurrency . BNB has a current supply of 163,276,974.63. The last known price of BNB is 407.52156596 USD and is down -0.03 over the last 24 hours. It is currently trading on 813 active market(s) with $1,476,012,628.11 traded over the last 24 hours. More information can be found at https://www.binance.com/.\",\"id\": 1839,\"is_hidden\": 0,\"logo\": \"https://s2.coinmarketcap.com/static/img/coins/64x64/1839.png\",\"name\": \"BNB\",\"notice\": \"\",\"platform\": null,\"self_reported_circulating_supply\": null,\"self_reported_market_cap\": null,\"self_reported_tags\": null,\"slug\": \"bnb\",\"subreddit\": \"binance\",\"symbol\": \"BNB\",\"tag-groups\": [\"INDUSTRY\",\"CATEGORY\",\"INDUSTRY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"CATEGORY\",\"PLATFORM\"],\"tag-names\": [\"Marketplace\",\"Centralized Exchange\",\"Payments\",\"Smart Contracts\",\"Alameda Research Portfolio\",\"Multicoin Capital Portfolio\",\"Moonriver Ecosystem\",\"BNB Chain\"],\"tags\": [\"marketplace\",\"centralized-exchange\",\"payments\",\"smart-contracts\",\"alameda-research-portfolio\",\"multicoin-capital-portfolio\",\"moonriver-ecosystem\",\"bnb-chain\"],\"twitter_username\": \"binance\",\"urls\": {\"announcement\": [],\"chat\": [\"https://t.me/binanceexchange\"],\"explorer\": [\"https://explorer.binance.org/\",\"https://etherscan.io/token/0xB8c77482e45F1F44dE1745F52C74426C631bDD52\",\"https://bscscan.com/token/0xbb4CdB9CBd36B01bD1cBaEBF2De08d9173bc095c\",\"https://blockchair.com/ethereum/erc-20/token/0xb8c77482e45f1f44de1745f52c74426c631bdd52\"],\"facebook\": [],\"message_board\": [],\"reddit\": [\"https://reddit.com/r/binance\"],\"source_code\": [\"https://github.com/binance-exchange/binance-official-api-docs\"],\"technical_doc\": [],\"twitter\": [\"https://twitter.com/binance\"],\"website\": [\"https://www.binance.com/\"]}}},\"status\": {\"credit_count\": 1,\"elapsed\": 18,\"error_code\": 0,\"error_message\": null,\"notice\": null,\"timestamp\": \"2022-04-23T16:43:18.297Z\"}}",CryptoInfoRequest.class);
            }
            Crypto crypto = cryptoInfoRequest.getData().get(id);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
            String strDate; 
            if(crypto.getDate_launched() == null){
                 strDate = "N/A";
            }else{
                strDate = dateFormat.format(crypto.getDate_launched());  
            }
            db.removeCrypto(crypto.getId());
            if(mode){
                db.addCrypto(crypto.getId(),crypto.getName(),crypto.getSymbol(),crypto.getSlug(),crypto.getDescription(),strDate,crypto.getLogo(),crypto.getUrls().getWebsite());
            }

            return crypto;
        }catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
            System.out.println(e.getMessage());
            //return "Something went wrong!!!!!!";
            return null;
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            //return "Something went very wrong!";
            return null;
        }
    }
    /** 
    * Get exchange rate between two crypto
    * @param id - crypto id of first crypto
    * @param symbol - crypto symbol of second crypto
    * @return exchange rate in double
    */
    public Double fetchRate(String id, String symbol) {
        try{
            
            Gson gson = new Gson();
            CryptoInfoRequest cryptoInfoRequest = null;
            if(mode){
                Thread.sleep(3000);
                HttpRequest request = HttpRequest.newBuilder(new URI("https://pro-api.coinmarketcap.com/v1/cryptocurrency/quotes/latest?CMC_PRO_API_KEY="+ inputApi +"&id="+id +"&convert="+symbol)).GET()
                    .build();
                HttpClient client = HttpClient.newBuilder().build();
                HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                cryptoInfoRequest = gson.fromJson(response.body(),CryptoInfoRequest.class);
            } else{
                cryptoInfoRequest = gson.fromJson("{\"data\": {\"1839\": {\"circulating_supply\": 163276974.63,\"cmc_rank\": 4,\"date_added\": \"2017-07-25T00:00:00.000Z\",\"id\": 1839,\"is_active\": 1,\"is_fiat\": 0,\"last_updated\": \"2022-04-23T17:06:00.000Z\",\"max_supply\": 165116760,\"name\": \"BNB\",\"num_market_pairs\": 813,\"platform\": null,\"quote\": {\"BTC\": {\"fully_diluted_market_cap\": 1690467.8979802807,\"last_updated\": \"2022-04-23T17:07:00.000Z\",\"market_cap\": 1671632.1474050311,\"market_cap_dominanc\": 3.5832,\"percent_change_1h\": 0.21363561,\"percent_change_24h\": -0.62519683,\"percent_change_30d\": 8.32819407,\"percent_change_60d\": 5.71361847,\"percent_change_7d\": -0.61003773,\"percent_change_90d\": -3.46858654,\"price\": 0.010238015195915388,\"volume_24h\": 36891.2970846914,\"volume_change_24h\": -31.6476},\"BNB\": {\"fully_diluted_market_cap\": 165266023.26509017,\"last_updated\": \"2022-04-23T18:04:00.000Z\",\"market_cap\": 163424574.7545775,\"market_cap_dominance\": 3.5904,\"percent_change_1h\": 0,\"percent_change_24h\": 0,\"percent_change_30d\": 0,\"percent_change_60d\": 0,\"percent_change_7d\": 0,\"percent_change_90d\": 0,\"price\": 1,\"volume_24h\": 3568944.646663319,\"volume_change_24h\": -31.6343}},\"self_reported_circulating_supply\": null,\"self_reported_market_cap\": null,\"slug\": \"bnb\",\"symbol\": \"BNB\",\"tags\": [\"marketplace\"],\"total_supply\": 163276974.63},\"1\": {\"circulating_supply\": 19020018,\"cmc_rank\": 1,\"date_added\": \"2013-04-28T00:00:00.000Z\",\"id\": 1,\"is_active\": 1,\"is_fiat\": 0,\"last_updated\": \"2022-04-23T17:15:00.000Z\",\"max_supply\": 21000000,\"name\": \"Bitcoin\",\"num_market_pairs\": 9357,\"platform\": null,\"quote\": {\"BNB\": {\"fully_diluted_market_cap\": 2053623133.818368,\"last_updated\": \"2022-04-23T17:14:00.000Z\",\"market_cap\": 1859997570.02104,\"market_cap_dominance\": 40.8312,\"percent_change_1h\": -0.16288942,\"percent_change_24h\": 0.86800527,\"percent_change_30d\": -7.60173693,\"percent_change_60d\": -5.03953127,\"percent_change_7d\": 0.71228101,\"percent_change_90d\": 3.80873601,\"price\": 97.79157780087485,\"volume_24h\": 43953319.94128719,\"volume_change_24h\": -64.1835},\"BTC\": {\"fully_diluted_market_cap\": 20999999.99999999,\"last_updated\": \"2022-04-23T18:00:00.000Z\",\"market_cap\": 19020018,\"market_cap_dominance\": 40.8686,\"percent_change_1h\": 0,\"percent_change_24h\": 0,\"percent_change_30d\": 0,\"percent_change_60d\": 0,\"percent_change_7d\": 0,\"percent_change_90d\": 0,\"price\": 1,\"volume_24h\": 444069.0257947906,\"volume_change_24h\": -44.471}},\"self_reported_circulating_supply\": null,\"self_reported_market_cap\": null,\"slug\": \"bitcoin\",\"symbol\": \"BTC\",\"tags\": [\"mineable\"],\"total_supply\": 19020018}},\"status\": {\"credit_count\": 1,\"elapsed\": 33,\"error_code\": 0,\"error_message\": null,\"notice\": null,\"timestamp\": \"2022-04-23T17:07:40.383Z\"}}",CryptoInfoRequest.class);
            }
            Double rate = cryptoInfoRequest.getData().get(id).getQuote().get(symbol).getPrice().doubleValue();

            return rate;
        }catch (IOException | InterruptedException e) {
            System.out.println("Something went wrong with our request!");
            System.out.println(e.getMessage());
            //return "Something went wrong!!!!!!";
            return null;
        } catch (URISyntaxException ignored) {
            // This would mean our URI is incorrect - this is here because often the URI you use will not be (fully)
            // hard-coded and so needs a way to be checked for correctness at runtime.
            //return "Something went very wrong!";
            return null;
        }
    }   
}
