package labs;
import com.fastcgi.FCGIInterface;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Objects;
import java.util.Properties;

import java.io.*;

public class Main {
    private static final String HTTP_RESPONSE = """
        Content-Type: application/json
        
        {"result":"%s","x":"%d","y":"%f","r":"%f","time":"%d","workTime":"%d"}
        """;
    private static final String HTTP_ERROR = """
        Content-Type: application/json
        
        {"result":"%s","time":"%d","workTime":"%d"}
        """;
    public static void main(String[] args) {

        FCGIInterface fcgi = new FCGIInterface();

        int status = 0;

        while(fcgi.FCGIaccept() >= 0) {
            if (FCGIInterface.request.params.getProperty("REQUEST_METHOD").equals("GET")) {

                Properties prop = System.getProperties();
                String QUERY_STRING = prop.getProperty("QUERY_STRING");
                //x=-1&y=0&r=2
                if (!QUERY_STRING.isBlank()) {

                    Validator validator = new Validator();
                    Date date = new Date();
                    try {
                        LinkedHashMap<String, Float> params = parse(QUERY_STRING);
                        boolean isValidated = validator.validateParams(params);
                        boolean isHit = validator.isHit(params);

                        if (isValidated) {
                            System.out.println(String.format(HTTP_RESPONSE, isHit, params.get("x").intValue(), params.get("y"), params.get("r"),
                                    date.getTime(), date.getTime()));

                            //отправить ответ: вы попали молодцы
                        } else {
                            System.out.println("Недопустимые значения");
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Недопустимые значения");
                    } catch (Exception e){
                        System.out.println(String.format(HTTP_ERROR, e.getMessage(), date.getTime(), date.getTime()));
                    }
                }
                else {
                    System.out.println("Заполните значения");
                }
            }

        }
    }

    public static LinkedHashMap<String, Float> parse(String queryString) throws IndexOutOfBoundsException{
        LinkedHashMap<String, Float> parameters = new LinkedHashMap<>();
        String[] reses = queryString.split("&");
        float x = Float.parseFloat(reses[0].substring(2));
        float y = Float.parseFloat(reses[1].substring(2));
        float r = Float.parseFloat(reses[2].substring(2));
        parameters.put("x", x);
        parameters.put("y", y);
        parameters.put("r", r);
        return parameters;
    }
}