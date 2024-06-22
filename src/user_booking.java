
import javax.swing.*;
import java.awt.*;
import static java.awt.image.ImageObserver.ABORT;
import static java.awt.image.ImageObserver.HEIGHT;
import static java.awt.image.ImageObserver.WIDTH;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class user_booking extends JFrame {

    static int choice = 1;
    private JCheckBox[][] j1;
    private static int row = 0;
    private static int col = 0;
    private ArrayList<Integer> check_row, check_col;
    private JButton submit;
    private int[][] seat;
    ImageIcon seat_select, av, unav;
    private JTable j;
    String[] info = new String[6];

    public static void main(String[] args) {
        user_booking f = new user_booking();
        f.setSize(1000, 700);
        //f.pack();
        f.setTitle("User Booking");
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public user_booking() {
        seat_select = new ImageIcon("src/image/seat_select.png");
        av = new ImageIcon("src/image/seat_av.png");
        unav = new ImageIcon("src/image/seat_unav.png");

        Image seat_i = seat_select.getImage();
        Image img1 = seat_i.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        seat_select = new ImageIcon(img1);

        Image av_i = av.getImage();
        Image img2 = av_i.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        av = new ImageIcon(img2);

        Image unav_i = unav.getImage();
        Image img3 = unav_i.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        unav = new ImageIcon(img3);
        File f = new File("seat.txt");
        try {
            Scanner s = new Scanner(f);
            info[0] = s.nextLine();
            info[1] = s.nextLine();
            info[2] = s.nextLine();
            info[3] = s.nextLine();
            info[4] = s.nextLine();
            info[5] = s.nextLine();
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
            String a;
            for (int i = 0; i < 6; i++) {
                a = s1.nextLine();
            }
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
                j1[j][k] = new JCheckBox(r + ", " + k);
                if (seat[j][k] == 1) {
                    j1[j][k].setIcon(unav);
                    j1[j][k].setEnabled(false);
                } else {
                    j1[j][k].setIcon(av);
                }
                j1[j][k].addActionListener((e) -> {
                    JCheckBox source = (JCheckBox) e.getSource();
                    if (source.isSelected()) {
                        check_row.add(r);
                        check_col.add(c);
                        j1[r][c].setIcon(seat_select);
                        System.out.println("Checkbox at (" + r + ", " + c + ") is selected");
                    } else {
                        int index = check_row.indexOf(r);
                        if (index != -1 && check_col.get(index) == c) {
                            check_row.remove(index);
                            check_col.remove(index);
                            j1[r][c].setIcon(av);
                        }
                        System.out.println("Checkbox at (" + r + ", " + c + ") is un-selected");
                    }
                });
            }
        }
        String[][] data = {
            {"Kundan Kumar Jha", "4031", "CSE"},
            {"Anand Jha", "6014", "IT"}
        };

        // Column Names
        String[] columnNames = {"Name", "Roll Number", "Department"};

        // Initializing the JTable
        j = new JTable(data, columnNames);
        JPanel p3 = new JPanel();
        // adding it to JScrollPane
        JScrollPane sp = new JScrollPane(j);
//        p3.add(sp);
        JPanel p1 = new JPanel(new GridLayout(row, col));
        for (JCheckBox[] r : j1) {
            for (JCheckBox checkBox : r) {
                p1.add(checkBox);
            }
        }

        JLayeredPane lp = new JLayeredPane();
        lp.setPreferredSize(new Dimension(500, 500));
        p1.setBounds(30, 42, 300, 480);
        lp.add(p1, JLayeredPane.DEFAULT_LAYER);

        JPanel op = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(Color.BLACK);

                int in = 10;
                int width = getWidth() - 2 * in;
                int height = getHeight() - 2 * in;

                float thick = 5.0f;
                g2.setStroke(new BasicStroke(thick));

                g2.drawLine(in, in, in + width, in);

                g2.setStroke(new BasicStroke(1.0f));

                g2.drawLine(in, in, in, in + height);//left
                g2.drawLine(in, in + height, in + width, in + height);//bottom
                g2.drawLine(in + width, in, in + width, in + height);//right
            }
        };

        op.setBounds(20, 0, 275, 600);
        op.setOpaque(false);
        lp.add(op, JLayeredPane.PALETTE_LAYER);

        JPanel p2 = new JPanel();
        submit = new JButton("Confirm");
        p2.add(submit);
        add(lp, BorderLayout.WEST);
        add(p2, BorderLayout.EAST);
        add(p3, BorderLayout.SOUTH);
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
        try {
            FileWriter write = new FileWriter("seat.txt");
            BufferedWriter book = new BufferedWriter(write);

            for (int m = 0; m < 6; m++) {
                book.write(info[m]);
                book.newLine(); 
            }
            for (int i = 0; i < row; i++) {
                for (int j = 0; j < col; j++) {
                    if (j > 0) { 
                        book.write(" ");
                    }
                    book.write(String.valueOf(seat[i][j])); 
                }
                book.newLine();
            }
            book.close();
            System.out.println("Success to book the seat.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

    }
}

class DrawPanel extends JPanel {

    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.drawRect(1, 1, 100, 100);
    }
}
