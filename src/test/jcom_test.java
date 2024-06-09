package test;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class jcom_test {
    public static void main(String[] args) {
        frame f = new frame("testing");
        
        f.setTitle("Bus ticket");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        f.setSize(400, 300);
        f.setVisible(true);
    }
}


