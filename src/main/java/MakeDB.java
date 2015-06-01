import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.parser.JSONParser;
import redis.clients.jedis.Jedis;

import java.io.FileReader;
import java.util.*;

/**
 * Created by litemn on 21.04.15.
 */
public class MakeDB {

    public static void main(String[] arg){
        Gson gson = new Gson();
        int maxCarId = 0;
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/main/resources/cars.json"));
            Jedis jedis = new Jedis("localhost");

            JSONArray   ar  = (JSONArray)obj;

           // int i = 1;
            for(Object s: ar){
                Car car = gson.fromJson(s.toString(),Car.class);
                HashMap<String,String> map = new HashMap<String, String>();
                map.put("id_car",car.getId_car()+"");
                map.put("id_model",car.getModel()+"");
                map.put("id_engine",car.getId_engine()+"");
                map.put("id_body_type", car.getId_body_type() + "");
                map.put("date_release", car.getDate_realease());
                map.put("mileage", car.getMileage() + "");
                map.put("pts",car.getPts());
                map.put("color", car.getColor());
                map.put("price",car.getPrice()+"");
                map.put("photo_id", car.getPhoto_id());
                map.put("final_price",car.getFinal_price()+"");
                if(car.getId_car()>maxCarId){
                    maxCarId = car.getId_car();
                }
                jedis.set("MAXID",maxCarId+"");
                jedis.hmset("car:id:"+ car.getId_car(),map);

            }


        }catch (Exception e){

        }

    }
}
