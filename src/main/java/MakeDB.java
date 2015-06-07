import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import redis.clients.jedis.Jedis;

import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by litemn on 21.04.15.
 */
public class MakeDB {

    public static final String ID_CAR = "id_car";
    public static final String MODEL = "model";
    public static final String BRAND = "brand";
    public static final String ENGINE = "engine";
    public static final String BODY_TYPE = "bodyType";
    public static final String DATE_RELEASE = "date_release";
    public static final String MILEAGE = "mileage";
    public static final String PTS = "pts";
    public static final String COLOR = "color";
    public static final String PRICE = "price";
    public static final String PHOTO_ID = "photo_id";
    public static final String CARSPRICE = "CARSPRICE";
    public static final String ZMILAGE = "MILAGE";
    public static final String MAXID = "MAXID";
    public static final String ID_PREFIX = "car:id:";
    public static final String COLOR_SET = "COLOR_SET";
    public static final String BRAND_SET = "BRAND_SET";

    public static void main(String[] arg){
        Gson gson = new Gson();
        int maxCarId = 0;
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/main/resources/cars2.json"));
            Jedis jedis = new Jedis("localhost");

            JSONArray   ar  = (JSONArray)obj;

           // int i = 1;
            for(Object s: ar){
                Car car = gson.fromJson(s.toString(),Car.class);
                Date date = new Date(car.getDate_realease()*1000);
                HashMap<String, String> map = new HashMap<String, String>();
                map.put(ID_CAR,car.getId_car()+"");
                map.put(MODEL,car.getModel());
                map.put(ENGINE,car.getEngine());
                map.put(BODY_TYPE, car.getBodyType() );
                map.put(DATE_RELEASE, new SimpleDateFormat("dd MMMM yyyy").format(date));
                map.put(MILEAGE, car.getMileage() + "");
                map.put(PTS,car.getPts());
                map.put(COLOR, car.getColor());
                map.put(PRICE,car.getPrice()+"");
                map.put(PHOTO_ID, car.getPhoto_id());
                map.put(BRAND, car.getBrand());

                if(car.getId_car()>maxCarId){
                    maxCarId = car.getId_car();
                }
                jedis.sadd(car.getColor().toLowerCase(),car.getId_car()+"");
                jedis.zadd(CARSPRICE, car.getPrice(), car.getId_car() + "");

                jedis.zadd(ZMILAGE,car.getMileage(),car.getId_car()+"");
                jedis.sadd(COLOR_SET, car.getColor().toLowerCase());
                jedis.sadd(BRAND_SET, car.getBrand());

                jedis.set(MAXID, maxCarId+"");
                jedis.hmset(ID_PREFIX + car.getId_car(),map);




            }


        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
