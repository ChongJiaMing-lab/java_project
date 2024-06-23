import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class jcomponent {
    public static void main(String[] args) {
        System.out.println("Hello World!");
        JFrame frame = new JFrame("testing");
        
        frame.setTitle("Bus ticket");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                
        JPanel panel = new JPanel();
        frame.setContentPane(panel);
        
        JLabel label = new JLabel("Hi,I Love You");
        panel.add(label);
        JButton button = new JButton("button testing");
        panel.add(button);
        
        
        
        frame.setSize(400, 300);
        frame.setVisible(true);
    }
}

