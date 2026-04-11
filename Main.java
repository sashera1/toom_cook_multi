import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigInteger;
import java.util.Random;
import java.util.Scanner;
import java.util.function.BiFunction;

public class Main{
    public static void oldTest(String inputNums, String referenceAnswers) throws Exception{
        Scanner scanner = new Scanner(new File(inputNums));

        ToomCookMulti multiply = new ToomCookMulti(4);

        try (BufferedReader reader = new BufferedReader(new FileReader(referenceAnswers))) {
            String currentNumberString;
            
            // Reads one line at a time until the end of the file
            while ((currentNumberString = reader.readLine()) != null && scanner.hasNext()) {
                
                // currentNumberString holds the current number.
                // It will be overwritten on the next iteration.
                
                String stringA = scanner.next();
                String stringB = scanner.next();
                
                String result = multiply.multiply(stringA, stringB).toString();
                if (result.equals(currentNumberString)){
                    System.out.println("successful multi");
                }
                else{
                    throw new Exception("multiplication failed. result != reference result");
                }
                
            }
        } catch (IOException e) {
            System.err.println("I/O Error while reading: " + e.getMessage());
        } catch (Exception e){
            System.err.println("error: " + e.getMessage());
        }
        scanner.close();
    }

    /*
    method to generate data
    TRANSPARENCY: this method was coded by ai (Gemini 3.1 Pro)
    all functionality was reviewed and understood by developer (me)
    and small tweaks were made
    */
    public static void generateTestData(int pairCount, int minSize, int maxSize, String destFile){
        Random random = new Random();

        BiFunction<Integer, Random, String> generateRandomNumberString = (length, rand) -> {
            StringBuilder sb = new StringBuilder(length);
            sb.append(rand.nextInt(9) + 1);
            for (int j = 1; j < length; j++) {
                sb.append(rand.nextInt(10));
            }
            return sb.toString();
        };

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(destFile))) {
            for (int i = 0; i < pairCount; i++) {
                int length1 = random.nextInt(maxSize - minSize + 1) + minSize;
                int length2 = random.nextInt(maxSize - minSize + 1) + minSize;

                String num1 = generateRandomNumberString.apply(length1, random);
                String num2 = generateRandomNumberString.apply(length2, random);

                writer.write(num1 + " " + num2);
                writer.newLine();
            }
            System.out.println("Successfully generated testing data in " + destFile);
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the file: " + e.getMessage());
        }
    }

    /*
    method to generate correct answers for reference
    TRANSPARENCY: this method was coded by ai (Gemini 3.1 Pro)
    all functionality was reviewed and understood by developer (me)
    and small tweaks were made
    */
    public static void generateReferenceAnswers(String srcFile, String destFile){
        try (BufferedReader reader = new BufferedReader(new FileReader(srcFile));
             BufferedWriter writer = new BufferedWriter(new FileWriter(destFile))) {

            String line;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                
                // Skip empty lines
                if (line.isEmpty()) {
                    continue; 
                }

                // Using indexOf is significantly faster than String.split("\\s+") 
                // when we know exactly how the data is formatted.
                int spaceIndex = line.indexOf(' ');

                if (spaceIndex != -1) {
                    // Extract the string representations of the two numbers
                    String strNum1 = line.substring(0, spaceIndex);
                    String strNum2 = line.substring(spaceIndex + 1).trim();

                    // Parse into BigInteger
                    BigInteger num1 = new BigInteger(strNum1);
                    BigInteger num2 = new BigInteger(strNum2);

                    // Java handles the algorithm choice (Standard, Karatsuba, or Toom-Cook)
                    BigInteger product = num1.multiply(num2);

                    // Write to output and append a newline
                    writer.write(product.toString());
                    writer.newLine();
                } else {
                    System.err.println("Skipping malformed line: " + line);
                }
            }
            
            System.out.println("Reference answers saved to " + destFile);

        } catch (IOException e) {
            System.err.println("I/O Error: " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Parsing Error. Ensure the file contains only valid numbers: " + e.getMessage());
        }
    }


    public static void main(String[] args) throws Exception{
        
        //generateTestData(10, 200,1000,"first_generated_test_file");
        //generateReferenceAnswers("first_generated_test_file","first_generated_test_file_ref_res");


        oldTest("first_generated_test_file","first_generated_test_file_ref_res");
        
        

    }

}