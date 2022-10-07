package major_project.model.http;
import java.util.*;

public class Convert {
    private Number price;
    private Number volume_24h;
    private Number volume_change_24h;
    private Number market_cap;


    public Number getPrice(){
        return price;
    }
    public Number getVolume_24h(){
        return volume_24h;
    }
    public Number getVolumeChange_24h(){
        return volume_change_24h;
    }
    public Number getMarketCap(){
        return market_cap;
    }
}