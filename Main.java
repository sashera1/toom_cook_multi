import java.io.File;
import java.util.Scanner;

public class Main{
    public static void oldTest() throws Exception{
        /*
        already set up with hardcoded test data! 
        change if you need to
        */
        String testData = "sample.in.1";

        Scanner scanner = new Scanner(new File(testData));

        while (scanner.hasNext()) {
            
            String stringA = scanner.next();
            String stringB = scanner.next();
            
            String result = ToomCookMulti.multiply(stringA, stringB).toString();
            System.out.println(result);
            System.out.println();
        }
        
        scanner.close();
    }


    public static void main(String[] args) throws Exception{
        oldTest();



        
        

    }

}