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

    public class JaroWinkler
    {
        private String compOne;
        private String compTwo;

        private String theMatchA = "";
        private String theMatchB = "";
        private int mRange = -1;

        public JaroWinkler()
        {
        }

        public JaroWinkler(String s1, String s2)
        {
            compOne = s1;
            compTwo = s2;
        }

        public double getSimilarity(String s1, String s2)
        {
            compOne = s1;
            compTwo = s2;

            mRange = Math.max(compOne.length(), compTwo.length()) / 2 - 1;

            double res = -1;

            int m = getMatch();
            int t = 0;
            if (getMissMatch(compTwo,compOne) > 0)
            {
                t = (getMissMatch(compOne,compTwo) / getMissMatch(compTwo,compOne));
            }

            int l1 = compOne.length();
            int l2 = compTwo.length();

            double f = 0.3333;
            double mt = (double)(m-t)/m;
            double jw = f * ((double)m/l1+(double)m/l2+(double)mt);
            res = jw + getCommonPrefix(compOne,compTwo) * (0.1*(1.0 - jw));

            return res;
        }

        private int getMatch()
        {

            theMatchA = "";
            theMatchB = "";

            int matches = 0;

            for (int i = 0; i < compOne.length(); i++)
            {
//Look backward
                int counter = 0;
                while(counter <= mRange && i >= 0 && counter <= i)
                {
                    if (compOne.charAt(i) == compTwo.charAt(i - counter))
                    {
                        matches++;
                        theMatchA = theMatchA + compOne.charAt(i);
                        theMatchB = theMatchB + compTwo.charAt(i);
                    }
                    counter++;
                }


                counter = 1;
                while(counter <= mRange && i < compTwo.length() && counter + i < compTwo.length())
                {
                    if (compOne.charAt(i) == compTwo.charAt(i + counter))
                    {
                        matches++;
                        theMatchA = theMatchA + compOne.charAt(i);
                        theMatchB = theMatchB + compTwo.charAt(i);
                    }
                    counter++;
                }
            }
            return matches;
        }

        private int getMissMatch(String s1, String s2)
        {
            int transPositions = 0;

            for (int i = 0; i < theMatchA.length(); i++)
            {
//Look Backward
                int counter = 0;
                while(counter <= mRange && i >= 0 && counter <= i)
                {
                    if (theMatchA.charAt(i) == theMatchB.charAt(i- counter) && counter > 0)
                    {
                        transPositions++;
                    }
                    counter++;
                }

//Look forward
                counter = 1;
                while(counter <= mRange && i < theMatchB.length() && (counter + i) < theMatchB.length())
                {
                    if (theMatchA.charAt(i) == theMatchB.charAt(i + counter) && counter > 0)
                    {
                        transPositions++;
                    }
                    counter++;
                }
            }
            return transPositions;
        }

        private int getCommonPrefix(String compOne, String compTwo)
        {
            int cp = 0;
            for (int i = 0; i < 4; i++)
            {
                if (compOne.charAt(i) == compTwo.charAt(i)) cp++;
            }
            return cp;
        }
    }


    public class Levenshtein
    {
        private String compOne;
        private String compTwo;
        private int[][] matrix;
        private Boolean calculated = false;

        public Levenshtein(String one, String two)
        {
            compOne = one;
            compTwo = two;
        }

        public int getSimilarity()
        {
            if (!calculated)
            {
                setupMatrix();
            }
            return matrix[compOne.length()][compTwo.length()];
        }

        public int[][] getMatrix()
        {
            setupMatrix();
            return matrix;
        }

        private void setupMatrix()
        {
            matrix = new int[compOne.length()+1][compTwo.length()+1];

            for (int i = 0; i <= compOne.length(); i++)
            {
                matrix[i][0] = i;
            }

            for (int j = 0; j <= compTwo.length(); j++)
            {
                matrix[0][j] = j;
            }

            for (int i = 1; i < matrix.length; i++)
            {
                for (int j = 1; j < matrix[i].length; j++)
                {
                    if (compOne.charAt(i-1) == compTwo.charAt(j-1))
                    {
                        matrix[i][j] = matrix[i-1][j-1];
                    }
                    else
                    {
                        int minimum = Integer.MAX_VALUE;
                        if ((matrix[i-1][j])+1 < minimum)
                        {
                            minimum = (matrix[i-1][j])+1;
                        }

                        if ((matrix[i][j-1])+1 < minimum)
                        {
                            minimum = (matrix[i][j-1])+1;
                        }

                        if ((matrix[i-1][j-1])+1 < minimum)
                        {
                            minimum = (matrix[i-1][j-1])+1;
                        }

                        matrix[i][j] = minimum;
                    }
                }
            }
            calculated = true;

        }


    }
}
