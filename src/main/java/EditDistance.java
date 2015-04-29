import java.util.ArrayList;

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
}
