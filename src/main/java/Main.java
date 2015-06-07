import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Scanner;

/**
 * Created by litemn on 21.04.15.
 */
public class Main {
    private static Jedis jedis;

    private static void init(){
        JedisPool pool = new JedisPool(new JedisPoolConfig(), "localhost");
        jedis = pool.getResource();
    }
    
    public static void main(String[] arg){

        JaroWinkler jaro = new JaroWinkler();
        Levenshtein lev = new Levenshtein("test","rrr4");
        System.out.print(jaro.getSimilarity("test", "rest")+" "+lev.getSimilarity());




    }

    private static void add(){
//        jedis.s
    }

    private static void carByPrice(int minPrice, int maxPrice){
//        jedis.
        
    }
}
