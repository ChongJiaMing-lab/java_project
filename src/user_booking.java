
import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class user_booking extends JFrame {

    private JCheckBox[][] j1;
    private ArrayList<JCheckBox> selected;
    private static int row=0;
    private static int col=0;
    public static void main(String[] args) {
//	ArrayList<ArrayList<Integer>> groceryList = new ArrayList();123
//		
//		ArrayList<Integer> bakeryList = new ArrayList();
//		bakeryList.add(0);
//		bakeryList.add(0);
//		bakeryList.add(0);
//		
//		ArrayList<Integer> produceList = new ArrayList();
//		produceList.add(0);
//		produceList.add(0);
//		produceList.add(0);
//		
//		groceryList.add(bakeryList);
//		groceryList.add(produceList);
//		
//		int[] i = new int[3];
//
//        // 将 ArrayList 中的整数存储到 int 数组中
//        for (int index = 0; index < 3; index++) {
//            i[index] = bakeryList.get(index)+1;
//        }
//        
//          System.out.println("Array i:");
//        for (int value : i) {
//            System.out.println(value);
//        }12
        user_booking f = new user_booking();
        f.setSize(780, 400);
        //f.pack();
        f.setTitle("User Booking");
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public user_booking() {
        File f = new File("seat.txt");
        try {
            Scanner s = new Scanner(f);

            while (s.hasNextLine()) {
                row++;
                String[] line = s.nextLine().trim().split(" ");
                while (col < line.length) {
                    col++;
                }
            }
            System.out.println("row" + row);
            System.out.println("column" + col);

            int[][] arr = new int[row][col];
            Scanner s1 = new Scanner(f);
            while (s1.hasNextLine()) {
                for (int i = 0; i < arr.length; i++) {
                    String[] n = s1.nextLine().split(" ");
                    for (int j = 0; j < n.length; j++) {
                        arr[i][j] = Integer.parseInt(n[j]);
                    }
                }
                System.out.println(Arrays.deepToString(arr));
            }
            j1 = new JCheckBox[row][col];
        } catch (IOException e) {
            e.printStackTrace();
        }
        selected = new ArrayList<>();
        for (int j = 0; j < j1.length; j++) {
            for (int k = 0; k < j1[j].length; k++) {
                int r = j;
                int c = k;
                j1[j][k] = new JCheckBox(j+", "+k);
                j1[j][k].addActionListener((e) -> {
                    JCheckBox source = (JCheckBox) e.getSource();
                    if (source.isSelected()) {
                        selected.add(source);
                        System.out.println("Checkbox at (" + r + ", " + c + ") is selected");
                    } else {
                        selected.remove(source);
                        System.out.println("Checkbox at (" + r + ", " + c + ") is un-selected");
                    }
                });
            }
        }
        JPanel p1 = new JPanel(new GridLayout(row, col));
        for (JCheckBox[] r : j1) {
            for (JCheckBox checkBox : r) {
                p1.add(checkBox);
            }
        }
        add(p1, BorderLayout.CENTER);
    }
}

