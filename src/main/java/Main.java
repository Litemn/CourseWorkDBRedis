import java.util.Scanner;

/**
 * Created by litemn on 21.04.15.
 */
public class Main {
    
    
    public static void main(String[] arg){
        
        
        System.out.println("Enter commands");
        
        boolean again = true;
        Scanner scanner = new Scanner(System.in);
        while (again){
            
            String command = scanner.next();
            switch (command){
                case "exit": again = false; 
                    break;
                case "hset": 
                    if(scanner.hasNextInt()) {
                        String key = scanner.next();
                        
                    } else {
                        
                    }
                
                
            }
        }
        
    }
}
