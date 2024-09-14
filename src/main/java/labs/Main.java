package labs;
import com.fastcgi.FCGIInterface;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {


        while (true) {
            FCGIInterface fcgi = new FCGIInterface();

            Properties prop = System.getProperties();
            String QUERY_STRING = prop.getProperty("QUERY_STRING");
             //x=-1&y=0&r=2
        }
    }

    public static LinkedHashMap<String, String> parse(String queryString){
        LinkedHashMap<String, String> parameters = new LinkedHashMap<>();
        String[] reses = queryString.split("&");
        String x = reses[0].substring(2);
        String y = reses[1].substring(2);
        String z = reses[2].substring(2);
        parameters.put("x", x);
        parameters.put("y", y);
        parameters.put("z", z);
        return parameters;
    }
}