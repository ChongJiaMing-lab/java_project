
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.text.DecimalFormat;
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
    private String[] info = new String[6];
    private JLabel lb1, lb2, lb3, lb4, lb5, lb6, lb7, lb8, t, lbi1, lbi2;
    private JLabel lb9, lb10, lb11, lb12;
    private double price;
    private JTextField t1, t2, t3, t4;
    private double price_total;
    private double t4_total;
    private ButtonGroup bg1;
    private JRadioButton r1, r2;
    private int total_pass = 0;
    private double insurance = 0;

    public static void main(String[] args) {
        user_booking f = new user_booking();
        f.setSize(800, 700);
        f.setTitle("User Booking");
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public user_booking() {
        DecimalFormat df = new DecimalFormat("0.00");
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
        File f = new File("src/schedule_bus/JKK 6778.txt");
        try {
            Scanner s = new Scanner(f);
            info[0] = s.nextLine();
            info[1] = s.nextLine();
            info[2] = s.nextLine();
            info[3] = s.nextLine();
            info[4] = s.nextLine();
            price = Double.parseDouble(info[4]);
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
                j1[j][k] = new JCheckBox();
                j1[j][k].setOpaque(false);
                if (seat[j][k] == 1) {
                    j1[j][k].setIcon(unav);
                    j1[j][k].setEnabled(false);
                } else {
                    j1[j][k].setIcon(av);
                }
                
                price_total = 0.0;
                total_pass = 0;
                j1[j][k].addActionListener((e) -> {
                    JCheckBox source = (JCheckBox) e.getSource();
                    if (source.isSelected()) {
                        check_row.add(r);
                        check_col.add(c);
                        j1[r][c].setIcon(seat_select);
                        total_pass += 1;
                        price_total = total_pass * price;
                        if (r1.isSelected()) {
                            insurance = 2;
                        } else {
                            insurance = 0;
                        }
                        t2.setText("" + total_pass);
                        t3.setText("RM " + String.valueOf(df.format(price_total)));
                        t4_total = price_total + insurance;
                        t4.setText("RM " + String.valueOf(df.format(t4_total)));
                        System.out.println("Checkbox at (" + r + ", " + c + ") is selected");
                    } else {
                        int index = check_row.indexOf(r);
                            check_row.remove(index);
                            check_col.remove(index);
                            j1[r][c].setIcon(av);
                            total_pass -= 1;
                            price_total -= price;
                            if (r1.isSelected()) {
                                insurance = 2;
                            } else {
                                insurance = 0;
                            }
                            t2.setText("" + total_pass);
                            t3.setText("RM " + String.valueOf(df.format(price_total)));
                            t4_total = price_total + insurance;
                            t4.setText("RM " + String.valueOf(df.format(t4_total)));
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
        int dimension = 300;
        int bo = 275;
        if (col > 3) {
            dimension = 315;
            bo = 300;
        }
        JLayeredPane lp = new JLayeredPane();
        lp.setPreferredSize(new Dimension(dimension, 300));//big = 400, small = 300
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

        op.setBounds(20, 0, bo, 600);//third, big = 375, small = 275
        op.setOpaque(false);
        lp.add(op, JLayeredPane.PALETTE_LAYER);

        Font f1 = new Font("Calibri", Font.ITALIC, 40);

        t = new JLabel("Book a Seat");
        t.setFont(f1);
        JPanel title = new JPanel();
        title.add(t);

        JPanel p2 = new JPanel(new GridLayout(4, 1));
        JPanel sp2 = new JPanel();
        JPanel sp222 = new JPanel(new GridLayout(5, 1));
        lb1 = new JLabel("Available");
        lb2 = new JLabel("Unavailable");
        lb3 = new JLabel("Ticket Price : RM" + df.format(price));
        lb9 = new JLabel("Car Plate: " + info[0]);
        lb10 = new JLabel("Boarding Time: " + info[1]);
        lb11 = new JLabel("From: " + info[2]);
        lb12 = new JLabel("To: " + info[3]);
        sp222.add(lb3);
        sp222.add(lb9);
        sp222.add(lb10);
        sp222.add(lb11);
        sp222.add(lb12);
        lbi1 = new JLabel(av);
        lbi2 = new JLabel(unav);
        sp2.add(lb1);
        sp2.add(lbi1);
        sp2.add(lb2);
        sp2.add(lbi2);
//        sp2.add(lb3);
        Font f2 = new Font("Calibri", Font.BOLD, 20);
        Font f3 = new Font("Calibri", Font.PLAIN, 30);
        JPanel sp22 = new JPanel(new GridLayout(4, 2));
        submit = new JButton("Confirm");
        JPanel p_b = new JPanel();
        p_b.add(submit);
//        lb3.setFont(f3);

        bg1 = new ButtonGroup();
        r1 = new JRadioButton("Yes");
        r2 = new JRadioButton("No");
        bg1.add(r1);
        bg1.add(r2);
        lb4 = new JLabel("Travel Insurance?(RM2):");
        t1 = new JTextField(10);
        lb5 = new JLabel("Total Passenger:");
        t2 = new JTextField(10);
        t2.setText("0");
        lb6 = new JLabel("SubTotal:");
        t3 = new JTextField(10);
        t3.setText("RM 0.00");
        lb7 = new JLabel("Total:");
        t4 = new JTextField(10);
        t4.setText("RM 0.00");
        JPanel rb_p = new JPanel();
        rb_p.add(r1);
        rb_p.add(r2);
        sp22.add(lb4);
        sp22.add(rb_p);
        sp22.add(lb5);
        sp22.add(t2);
        sp22.add(lb6);
        sp22.add(t3);
        sp22.add(lb7);
        sp22.add(t4);
        lb4.setFont(f2);
        lb5.setFont(f2);
        lb6.setFont(f2);
        lb7.setFont(f2);
        t1.setEditable(false);
        t2.setEditable(false);
        t3.setEditable(false);
        t4.setEditable(false);
        t1.setFont(f2);
        t2.setFont(f2);
        t3.setFont(f2);
        t4.setFont(f2);
        p2.add(sp2);
        p2.add(sp222);
        p2.add(sp22);
        p2.add(p_b);
//        p2.setFont(f2);
        add(lp, BorderLayout.WEST);
        add(title, BorderLayout.NORTH);
        add(p2, BorderLayout.EAST);

        submit.addActionListener((e) -> {
            confirm_selection();
        });
//        
//         r1.addActionListener((e) -> {
//            if(r1.isSelected())
//            {
//                insurance =2;
//            }
//            else
//            {
//                insurance = 0;
//            }
//        });

    }

    public void confirm_selection() {

        System.out.println("The position you have selected is :");
        System.out.println("(" + check_row + ", " + check_col + ")");
        for (int i = 0; i < check_row.size(); i++) {
            seat[check_row.get(i)][check_col.get(i)] = 1;
        }
        System.out.println(Arrays.deepToString(seat));
        try {
            FileWriter write = new FileWriter("src/schedule_bus/JKK 6778.txt");
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
