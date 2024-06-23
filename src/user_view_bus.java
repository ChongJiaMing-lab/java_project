import javax.swing.*;
import java.awt.*;
//import com.toedter.calendar.JDateChooser;

public class user_view_bus extends JFrame {

    private JComboBox<String> board;
    private JComboBox<String> point;
    private JLabel title;
    private JLabel lb1,lb2;
//    Calendar c = new Calendar();
    public static void main(String[] args) {
        user_view_bus f = new user_view_bus();
        f.setSize(1000, 700);
        f.setTitle("User Booking");
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public user_view_bus() {
        Font f1 = new Font("Arial", Font.BOLD, 40);
        title = new JLabel("Bus Ticket");
        title.setFont(f1);
        board = new JComboBox<>();
        point = new JComboBox<>();
        lb1 = new JLabel("From:");
        lb2 = new JLabel("To:");
        
        board.addItem("Johor");
        board.addItem("Kedah");
        board.addItem("Kelantan");
        board.addItem("Malacca");
        board.addItem("Negeri Sembilan");
        board.addItem("Pahang");
        board.addItem("Penang");
        board.addItem("Perak");
        board.addItem("Perlis");
        board.addItem("Sabah");
        board.addItem("Sarawak");
        board.addItem("Selangor");
        board.addItem("Terengganu");

        point.addItem("Johor");
        point.addItem("Kedah");
        point.addItem("Kelantan");
        point.addItem("Malacca");
        point.addItem("Negeri Sembilan");
        point.addItem("Pahang");
        point.addItem("Penang");
        point.addItem("Perak");
        point.addItem("Perlis");
        point.addItem("Sabah");
        point.addItem("Sarawak");
        point.addItem("Selangor");
        point.addItem("Terengganu");

        JPanel t = new JPanel();
        JPanel p1 = new JPanel(new GridLayout(2,2));
        t.add(title);
        p1.add(lb1);
        p1.add(board);
//        p1.add(Box.createVerticalStrut(10));
        p1.add(lb2);
        p1.add(point);
        add(p1, BorderLayout.WEST);
        add(t, BorderLayout.NORTH);
    }
}
