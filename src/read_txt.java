//import java.io.File;  // Import the File class
//import java.io.FileNotFoundException;  // Import this class to handle errors
//import java.util.Scanner; // Import the Scanner class to read text files
//
//public class read_txt {
//  public static void main(String[] args) {
//    try {
//      File myObj = new File("read_test.txt");
//      Scanner myReader = new Scanner(myObj);
//      while (myReader.hasNextLine()) {
//        String data = myReader.nextLine();
//        System.out.println(data);
//      }
//      myReader.close();
//    } catch (FileNotFoundException e) {
//      System.out.println("An error occurred.");
//      e.printStackTrace();
//    }
//  }
//}

//----------------------------------------------------------------------------------------------------------------------------------------------------------------
// Java Program to illustrate Reading from FileReader
// using BufferedReader Class
 
// Importing input output classes
import java.io.*;
 
// Main class
public class read_txt {
 
    // main driver method
    public static void main(String[] args) throws Exception
    {
 
        
           // File path is passed as parameter
        File file = new File("read_test.txt");
 
        // Note:  Double backquote is to avoid compiler
        // interpret words
        // like \test as \t (ie. as a escape sequence)
 
        // Creating an object of BufferedReader class
        BufferedReader br
            = new BufferedReader(new FileReader(file));
 
        // Declaring a string variable
        String st;
        // Condition holds true till
        // there is character in a string
        while ((st = br.readLine()) != null)
 
            // Print the string
            System.out.println(st);
        
//        // Passing the path to the file as a parameter
//        FileReader fr = new FileReader(
//            "read_test.txt");
// 
//        // Declaring loop variable
//        int i;
//        // Holds true till there is nothing to read
//        while ((i = fr.read()) != -1)
// 
//            // Print all the content of a file
//            System.out.print((char)i);

//--------------------------------------------------------------------------------------------------------
//import java.io.File;
//import java.io.FileNotFoundException;
//import java.util.Scanner;
// 
//public class read_txt {
//    public static void main(String[] args)
//        throws FileNotFoundException
//    {
//        File file = new File(
//            "read_test.txt");
//        Scanner sc = new Scanner(file);
// 
//        // we just need to use \\Z as delimiter
//        sc.useDelimiter("\\Z");
// 
//        System.out.println(sc.next());
//    }
//}
    }
}



