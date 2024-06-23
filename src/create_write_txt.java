
import java.io.File;  // Import the File class
import java.io.IOException;  // Import the IOException class to handle errors
import java.io.FileWriter;   // Import the FileWriter class
import java.io.BufferedWriter;

public class create_write_txt {

    public static void main(String[] args) {
//    try {
//      File myObj = new File("filename.txt");
//      if (myObj.createNewFile()) {
//        System.out.println("File created: " + myObj.getName());
//      } else {
//        System.out.println("File already exists.");
//      }
//    } catch (IOException e) {
//      System.out.println("An error occurred.");
//      e.printStackTrace();

        try {
            FileWriter fileWriter = new FileWriter("filename.txt");
            BufferedWriter myWriter = new BufferedWriter(fileWriter);
            
          for (int i = 0; i < 2; i++) {
                for (int j = 0; j < 3; j++) {
                    if (j > 0) {
                        myWriter.write(" ");
                    }
                    myWriter.write("0"); 
                }
                myWriter.newLine();
            }

            myWriter.write("Files in Java might be tricky, but it is fun enou111gh!");
            myWriter.newLine(); 
            myWriter.write("Files in Java might be tricky, but it is fun enough1213!");

            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
}
}
