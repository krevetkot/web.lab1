package labs;
import com.fastcgi.FCGIInterface;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Properties;

public class Main {
    private static final String HTTP_RESPONSE = """
            HTTP/1.1 200 OK
            Content-Type: application/json
            
            {"result":"%s","x":"%d","y":"%f","r":"%f","time":"%d","workTime":"%d"}
            """;
    private static final String HTTP_ERROR = """
            HTTP/1.1 400 Bad Request
            Content-Type: application/json
            
            {"result":"%s","x":"%d","y":"%f","r":"%f","time":"%d","workTime":"%d"}
            """;
    public static void main(String[] args) {

        FCGIInterface fcgi = new FCGIInterface();

        while(fcgi.FCGIaccept() >= 0) {

            Properties prop = System.getProperties();
            String QUERY_STRING = prop.getProperty("QUERY_STRING");
             //x=-1&y=0&r=2

            Validator validator = new Validator();
            try {
                LinkedHashMap <String, Float> params = parse(QUERY_STRING);
                boolean isValidated = validator.validateParams(params);
                boolean isHit = validator.isHit(params);
                Date date = new Date();
                if (isValidated){
                    System.out.println(params.get("x").toString() + params.get("y").toString() + params.get("r").toString()
                            + date.getTime());
                    System.out.println(String.format(HTTP_RESPONSE, isHit, params.get("x").intValue(), params.get("y"), params.get("r"),
                            date.getTime(), date.getTime()));

                    //отправить ответ: вы попали молодцы
                }
                else{
                    System.out.println("Недопустимые значения");
                }
            } catch (NumberFormatException e){
                //отправить что произошла ошибка: неверные значения
            }

        }
    }

    public static LinkedHashMap<String, Float> parse(String queryString){
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