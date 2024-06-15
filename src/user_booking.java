
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
    private static int row = 0;
    private static int col = 0;
    private ArrayList<Integer> check_row, check_col;
    private JButton submit;
    private int[][] seat;

    public static void main(String[] args) {
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

            seat = new int[row][col];
            Scanner s1 = new Scanner(f);
            while (s1.hasNextLine()) {
                for (int i = 0; i < seat.length; i++) {
                    String[] n = s1.nextLine().split(" ");
                    for (int j = 0; j < n.length; j++) {
                        seat[i][j] = Integer.parseInt(n[j]);
                    }
                }
                System.out.println(Arrays.deepToString(seat));
            }
            j1 = new JCheckBox[row][col];
        } catch (IOException e) {
            e.printStackTrace();
        }
        check_col = new ArrayList<>();
        check_row = new ArrayList<>();
        for (int j = 0; j < j1.length; j++) {
            for (int k = 0; k < j1[j].length; k++) {
                int r = j;
                int c = k;
                j1[j][k] = new JCheckBox(j + ", " + k);
                j1[j][k].addActionListener((e) -> {
                    JCheckBox source = (JCheckBox) e.getSource();
                    if (source.isSelected()) {
                        check_row.add(r);
                        check_col.add(c);
                        System.out.println("Checkbox at (" + r + ", " + c + ") is selected");
                    } else {
                        int index = check_row.indexOf(r);
                        if (index != -1 && check_col.get(index) == c) {
                            check_row.remove(index);
                            check_col.remove(index);
                        }
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
        JPanel p2 = new JPanel();
        submit = new JButton("Confirm");
        p2.add(submit);

        add(p1, BorderLayout.CENTER);
        add(p2, BorderLayout.SOUTH);

        submit.addActionListener((e) -> {
            confirm_selection();
        });

    }

    public void confirm_selection() {
        System.out.println("The position you have selected is :");
        System.out.println("(" + check_row + ", " + check_col + ")");
        for (int i = 0; i < check_row.size(); i++) {
            seat[check_row.get(i)][check_col.get(i)] = 1;
        }
        System.out.println(Arrays.deepToString(seat));
    }
}
