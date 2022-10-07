/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package major_project;

import major_project.model.MyCryptoList;
import major_project.model.http.*;
import major_project.model.db.*;
import major_project.model.Setting;
import java.util.*;
import major_project.model.App;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.IsEqual.equalTo;
import org.mockito.MockedStatic;
import major_project.model.http.SendGridRequest;
import java.util.*;
import javafx.util.Pair;
import java.util.concurrent.locks.*;
import java.io.IOException;
import org.apache.http.auth.AuthenticationException;
import org.apache.http.client.ClientProtocolException;

class AppTest {
    @Test void appTest() {
        //Online
        Setting mockSetting = mock(Setting.class);

        App app = new App("offline","offline",mockSetting);
        assertFalse(app.getInputApi());
        assertFalse(app.getOutputApi());
        assertTrue("1.0000000".equals(app.calculate(1.0,1.0)));
        assertTrue("0.1500000".equals(app.calculate(0.5,0.3)));
        assertTrue(app.checkDouble("1"));
        assertFalse(app.checkDouble("one"));
        assertTrue(app.checkDouble("0.1"));
        Request mockRequest = mock(Request.class);
        Database mockDatabase = new MockDatabase();
        when(mockRequest.getDatabase()).thenReturn(mockDatabase);

        

        Map<String,Integer> cryptoList = new HashMap<String,Integer>();
        cryptoList.put("BTC: Bitcoin", 1);
        cryptoList.put("BNB: Binance Coin", 1839);
        when(mockRequest.fetchAllCoin()).thenReturn(cryptoList);

        Crypto mockBTC = mock(Crypto.class);
        when(mockBTC.getId()).thenReturn(1);
        when(mockBTC.getSymbol()).thenReturn("BTC");

        Crypto mockBNB = mock(Crypto.class);
        when(mockBNB.getId()).thenReturn(1839);
        when(mockBNB.getSymbol()).thenReturn("BNB");

        when(mockRequest.fetchCrypto("1")).thenReturn(mockBTC);
        when(mockRequest.fetchCrypto("1839")).thenReturn(mockBTC);
        when(mockRequest.fetchRate("1","BNB")).thenReturn(97.6915778);

        Lock lock = new ReentrantLock();
        Object lockObject = new Object();
        MyCryptoList myCryptoList = new MyCryptoList(mockRequest,lock, lockObject);
        app.setLock(lock,lockObject);
        assertEquals(app.getLock(),lock);
        assertEquals(app.getLockObject(),lockObject);
        Reddit mockReddit =mock(Reddit.class);
        app.setReddit(mockReddit);
        assertEquals(app.getReddit(),mockReddit);
        assertEquals(app.getSetting(),mockSetting);



        SendGridRequest mockSendGridRequest = mock(SendGridRequest.class);
        
        app.setup(mockRequest,myCryptoList, mockSendGridRequest);
        MockPane mockPane = new MockPane(app);
        assertEquals(app.getMyCryptoList(),myCryptoList);
        assertEquals(app.getRequest(),mockRequest);
        assertEquals(app.getSendGridRequest(),mockSendGridRequest);
        // Cerate separate thread to notify main thread.
        Thread t1 = new Thread(() -> {
            {
                try{    
                    Thread.sleep(2000);
                    synchronized(lockObject){
                        lockObject.notify();
                    }
                }
                catch(InterruptedException e){
                    ;
                }
                }
        
        });
        t1.start();
        
        
        assertEquals(app.getCryptoLists().get("BTC: Bitcoin"), 1);
        assertEquals(97.6915778, app.getRate("1","BNB"));
        assertEquals(0, myCryptoList.size());
        

        myCryptoList.addCryptoToList("API","BTC: Bitcoin",cryptoList);
        //assertTrue(myCryptoList.checkCryptoExist("This not exist",cryptoList));

        assertEquals(mockBTC,myCryptoList.getCryptoToAdd());
        assertEquals(null,myCryptoList.getCryptoToRemove());
        assertEquals(1, myCryptoList.size());

        // Cerate separate thread to notify main thread.
        Thread t2 = new Thread(() -> {
            {
                try{    
                    Thread.sleep(2000);
                    synchronized(lockObject){
                        lockObject.notify();
                    }
                }
                catch(InterruptedException e){
                    ;
                }
                }
        
        });
        t2.start();
        myCryptoList.addCurrencyListFromApi("BTC: Bitcoin", cryptoList);
        
        assertEquals("1",myCryptoList.getIdBySymbol("BTC"));
        assertEquals(null,myCryptoList.getIdBySymbol("No coin"));
        myCryptoList.clearCurrencyList();
        assertEquals(0,myCryptoList.size());
       
        myCryptoList.add(mockBTC);
        myCryptoList.add(mockBNB);
        assertFalse(myCryptoList.checkCryptoExist("BNB: Binance Coin",cryptoList));
        assertEquals(2, myCryptoList.size());
        
        List<String> testStrings = myCryptoList.getMyCryptoListString();
    
        assertTrue(testStrings.contains("BNB"));
        assertTrue(testStrings.contains("BTC"));
        assertFalse(testStrings.contains("1"));
    
        List<Crypto> testCryptos = myCryptoList.getList();
        assertTrue(testCryptos.contains(mockBNB));
        assertTrue(testCryptos.contains(mockBTC));
        assertEquals(mockBNB,app.getCryptoBySymbol("BNB"));

        myCryptoList.removeCurrencyList("BNB");
        assertTrue(myCryptoList.checkCryptoExist("BNB: Binance Coin",cryptoList));
        assertEquals(null,myCryptoList.getCryptoToAdd());
        assertEquals(mockBNB,myCryptoList.getCryptoToRemove());
        assertEquals(1,myCryptoList.size());
        //Add a crypto from mock database
        Thread t3 = new Thread(() -> {
            {
                try{    
                    Thread.sleep(2000);
                    synchronized(lockObject){
                        lockObject.notify();
                    }
                }
                catch(InterruptedException e){
                    ;
                }
                }
        
        });
        t3.start();        
        myCryptoList.addCryptoToList("DB","BNB: Binance Coin",cryptoList);
        //list should be 2.
        assertEquals(2,myCryptoList.size());
        assertTrue(myCryptoList.checkCryptoDatabase("BNB: Binance Coin",cryptoList));
        
        
    }

    
    @Test void RequestTest() {
        Database mockDatabase = new MockDatabase();

        Request re = new Request(false, mockDatabase);
        Map<String, Integer> cryptoList = new HashMap<String,Integer>();
        cryptoList.put("BTC: Bitcoin", 1);
        cryptoList.put("BNB: Binance Coin", 1839);
        App mockApp = mock(App.class);
        re.setApp(mockApp);
        re.fetchAllCoin();
        Crypto crypto = re.fetchCrypto("1");
        Double rate = re.fetchRate("1","BNB");
        assertFalse(re.getMode());
        assertEquals(re.getDatabase(),mockDatabase);
    }
    @Test void SendTest(){
        SendGridRequest sr = new SendGridRequest(false);
        Crypto mockBTC = mock(Crypto.class);
        when(mockBTC.getId()).thenReturn(1);
        when(mockBTC.getName()).thenReturn("Bit coin");
        when(mockBTC.getSymbol()).thenReturn("BTC");
        when(mockBTC.getLogo()).thenReturn("logo");
        when(mockBTC.getDescription()).thenReturn("Hello");
        when(mockBTC.getDate_launched()).thenReturn(null);
        when(mockBTC.getDate_launched_s()).thenReturn(null);

        Crypto mockBNB = mock(Crypto.class);
        when(mockBNB.getId()).thenReturn(1839);
        when(mockBNB.getSymbol()).thenReturn("BNB");
        when(mockBNB.getName()).thenReturn("Binance");
        when(mockBNB.getLogo()).thenReturn("logo");
        when(mockBNB.getDescription()).thenReturn("Hello");
        when(mockBNB.getDate_launched()).thenReturn(null);
        when(mockBNB.getDate_launched_s()).thenReturn(null);

        sr.setCryptoPair(mockBNB,mockBTC);
        sr.setRate("1");
        sr.setEmail("test@gmail.com");
        sr.setResultTitle("title");
        sr.setResultAnswer("answer");
        sr.setReportContent();
        sr.sendEmail();
        String mockContent;
        mockContent = "<h3>Your quote,</h3>";
        mockContent += "<img src=\\\"" +"logo"+ "\\\" alt=\\\"alternatetext\\\"><br>";
        mockContent += "<p1>Currency: " + "Binance" + "</p1><br>";
        mockContent += "<p1>Symbol: " + "BNB" + "</p1><br>";
        mockContent += "<p1>Description: " + "Hello" + "</p1><br>";
        mockContent += "<p1>Date launch: " + "N/A" + "</p1><br><br>";
        
        mockContent += "<img src=\\\"" + "logo" + "\\\" alt=\\\"alternatetext\\\"><br>";
        mockContent += "<p1>Currency: " + "Bit coin" + "</p1><br>";
        mockContent += "<p1>Symbol: " + "BTC" + "</p1><br>";
        mockContent += "<p1>Description: " + "Hello" + "</p1><br>";
        mockContent += "<p1>Date launch: " + "N/A" + "</p1><br><br>";

        mockContent += "<p1>Exchange rate : " + "1" + "<br>";
        mockContent += "<p1>" + "title" + "</p1>";
        mockContent += "<p1>" + "answer" + "</p1>";
        assertTrue(sr.getReportContent().equals(mockContent));
    }
    @Test void settingTest() throws ClientProtocolException, IOException,AuthenticationException{
        Setting a = new Setting();
        a.setEmail("hello");
        assertTrue(a.getEmail().equals("hello"));
        a.setRedditId("reddit_id");
        assertTrue(a.getRedditId().equals("reddit_id"));
        assertTrue(a.validateEmail("example@email.com"));
        assertFalse(a.validateEmail("thisIsNotAnEmail"));

        Reddit mockReddit = mock(Reddit.class);
        RedditToken mockRedditToken = mock(RedditToken.class);

        when(mockReddit.getAccessToken("username","password")).thenReturn(mockRedditToken);
        assertTrue(a.validateReddit("username","password",mockReddit));

        Reddit mockReddit2 = mock(Reddit.class);
        when(mockReddit.getAccessToken("username","password")).thenReturn(null);
        assertFalse(a.validateReddit("username","password",mockReddit2));

        

        a.setRedditToken(mockRedditToken);
        assertEquals(a.getRedditToken(), mockRedditToken);

    }
    @Test void redditTest() throws ClientProtocolException, IOException,AuthenticationException{
        Reddit a = new Reddit();
        Crypto mockBTC = mock(Crypto.class);
        when(mockBTC.getId()).thenReturn(1);
        when(mockBTC.getName()).thenReturn("Bit coin");
        when(mockBTC.getSymbol()).thenReturn("BTC");
        when(mockBTC.getLogo()).thenReturn("logo");
        when(mockBTC.getDescription()).thenReturn("Hello");
        when(mockBTC.getDate_launched()).thenReturn(null);
        when(mockBTC.getDate_launched_s()).thenReturn(null);

        Crypto mockBNB = mock(Crypto.class);
        when(mockBNB.getId()).thenReturn(1839);
        when(mockBNB.getSymbol()).thenReturn("BNB");
        when(mockBNB.getName()).thenReturn("Binance");
        when(mockBNB.getLogo()).thenReturn("logo");
        when(mockBNB.getDescription()).thenReturn("Hello");
        when(mockBNB.getDate_launched()).thenReturn(null);
        when(mockBNB.getDate_launched_s()).thenReturn(null);

        a.setCryptoPair(mockBNB,mockBTC);
        a.setRate("1");
        a.setResultTitle("title");
        a.setResultAnswer("answer");
        a.setReportContent();

        String mockContent;
        String x = "# Your quote,  ";
        String b = "Currency:  "+ "Binance"+"  ";
        String c = "Symbol: " + "BNB"+"  ";
        String d = "Description: " + "Hello"+"  ";
        String e = "Date launch: " + "N/A"+"  ";
        String f = "  ";
        String g = "Currency: " + "Bit coin"+"  ";
        String h = "Symbol: " + "BTC"+"  ";
        String i = "Description: " + "Hello"+"  ";
        String j = "Date launch: " + "N/A" +"  ";
        String k = "  ";
        String l = "Exchange rate : " + "1" +"  ";
        String m = "title"+" " +"answer" +"  ";
        mockContent = String.join(System.lineSeparator(), x,b,c,d,e,f,g,h,i,j,k,l,m);
        assertEquals(a.getReportContent(),mockContent);
    }
    
    @Test void tresholdTest(){
        Setting mockSetting = mock(Setting.class);
        App app = new App("offline","offline",mockSetting);

        assertFalse(app.checkRange("-2"));
        assertFalse(app.checkRange("0.1"));
        assertFalse(app.checkRange("1"));
        assertTrue(app.checkRange("0.2"));
        assertTrue(app.checkRange("0.66"));

        assertEquals(app.getThreshold(), 0);
        app.setThreshold("0.5");
        assertEquals(app.getThreshold(), 0.5);

        assertTrue(app.isLessThanThreshold(0.4));
        assertFalse(app.isLessThanThreshold(0.5));
        assertFalse(app.isLessThanThreshold(0.88));
        assertTrue(app.isLessThanThreshold(0.001));

    }
}