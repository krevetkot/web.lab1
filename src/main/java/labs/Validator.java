package labs;

import java.util.LinkedHashMap;

public class Validator {

    public boolean validate(LinkedHashMap<String, String> params){
        int x = Integer.parseInt(params.get("x"));
        float y = Float.parseFloat(params.get("y"));
        float r = Float.parseFloat(params.get("r"));

        if ( (x>=-r/2) && (x<=0) && (y>=0) && (y<=r) ||
                (x<=0) && (y>=-2*x-r) && (y<=0) ||
                (x*x + y*y <= r*r) && (x>=0) && (y<=0)
        ){
            return true;
        }
        return false;
    }

}
