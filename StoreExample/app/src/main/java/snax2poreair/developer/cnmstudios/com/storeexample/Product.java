package snax2poreair.developer.cnmstudios.com.storeexample;

/**
 * Created by 2poreSnaxA on 1/20/2018.
 */

public class Product {

    private String title, url, cost;

    public Product(String t, String u, String c){
        title = t;
        url = u;
        cost = c;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url;
    }

    public String getCost(){
        return cost;
    }

}
