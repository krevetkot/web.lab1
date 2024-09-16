package labs;
import com.fastcgi.FCGIInterface;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {


        while (true) {
            FCGIInterface fcgi = new FCGIInterface();

            Properties prop = System.getProperties();
            String QUERY_STRING = prop.getProperty("QUERY_STRING");
             //x=-1&y=0&r=2

            Validator validator = new Validator();
            try {
                LinkedHashMap <String, Float> params = parse(QUERY_STRING);
                boolean isValidated = validator.validateParams(params);
                boolean isHit = validator.isHit(params);
                Date date = new Date();
                if (isHit && isValidated){
                    System.out.println(params.get("x").toString() + params.get("y").toString() + params.get("r").toString()
                            + date.getTime());

                    //отправить ответ: вы попали молодцы
                }
                else if (!isHit){
                    System.out.println(params.get("x").toString() + params.get("y").toString() + params.get("r").toString()
                            + date.getTime());
                }
                else {
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