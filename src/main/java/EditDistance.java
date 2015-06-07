import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by litemn on 29.04.15.
 */
public class EditDistance {
    
    static float distance(String s1, String s2){
        int x = s1.length();
        int y = s2.length();
        int d[][] = new int[x+1][y+1];
        d[0][0] = 0;
        for(int i = 0; i<=x; i++){
            d[i][0]=i;
        }
        for(int j =0; j<=y; j++){
            d[0][j]=j;
        }
        
        for(int i = 1; i<=x; i++){
            for(int j = 1; j<=y; j++){
                int k1 = d[i-1][j] + 1;
                int k2 = d[i][j-1] + 1;
                int k3 = 0;
                if(s1.charAt(i-1)==s2.charAt(j-1)){
                     k3 = d[i - 1][j - 1];
                }else {
                     k3 = d[i - 1][j - 1] + 2;
                }
               d[i][j]= Math.min(k1,Math.min(k2,k3));
            }
        }
        
        return d[x][y];
    }



    public static String translate(String s){
        Map<String,String> map = new HashMap();
        map.put("a","ф");
        map.put("s","ы");
        map.put("d","в");
        map.put("f","а");
        map.put("q","й");
        map.put("w","ц");
        map.put("e","у");
        map.put("r","к");
        map.put("t","е");
        map.put("y","н");
        map.put("u","г");
        map.put("i","ш");
        map.put("o","щ");
        map.put("p","з");
        map.put("g","п");
        map.put("h","р");
        map.put("j","о");
        map.put("k","л");
        map.put("l","д");
        map.put(";","ж");
        map.put("'","э");
        map.put("z","я");
        map.put("x","ч");
        map.put("c","с");
        map.put("v","м");
        map.put("b","и");
        map.put("n","т");
        map.put("m","ь");
        map.put(",","б");
        map.put(".","ю");
        map.put("[","х");
        map.put("]","ъ");
        s=s.toLowerCase();
        Pattern p = Pattern.compile("[a-z|'.'|',;'|\\[]|]");
        Matcher m = p.matcher(s);
        StringBuffer sb = new StringBuffer();
        while (m.find()){
            m.appendReplacement(sb, map.get(m.group()));
        }
        m.appendTail(sb);
        return sb.toString();

    }





}
