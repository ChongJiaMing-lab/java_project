import javax.swing.*;
import java.awt.*;

public class admin_menu extends JFrame {

    public static void main(String[] args) {
        admin_menu adminMenu = new admin_menu();
        adminMenu.setTitle("Admin Menu");
        adminMenu.setSize(800, 600);
        adminMenu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        adminMenu.setVisible(true);
    }

    public admin_menu() {

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);


        JLabel bigTitleLabel = new JLabel("Bus Ticketing System", JLabel.CENTER);
        bigTitleLabel.setFont(new Font("Arial", Font.BOLD, 30));


        ImageIcon icon = new ImageIcon("src/image/bus.jpg");
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImg);
        JLabel imageLabel = new JLabel(icon);


        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.WHITE);
        titlePanel.add(imageLabel);
        titlePanel.add(bigTitleLabel);


        JLabel subTitleLabel = new JLabel("Admin Menu", JLabel.CENTER);
        subTitleLabel.setFont(new Font("Arial", Font.BOLD, 24));


        topPanel.add(titlePanel, BorderLayout.NORTH);
        topPanel.add(subTitleLabel, BorderLayout.CENTER);


        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.LIGHT_GRAY); 
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS)); 


        JButton button1 = new JButton("Add and view schedule");
        JButton button2 = new JButton("View/edit seat");
        JButton button3 = new JButton("View user information");
        JButton button4 = new JButton("View user booking");


        button1.setAlignmentX(Component.CENTER_ALIGNMENT);
        button2.setAlignmentX(Component.CENTER_ALIGNMENT);
        button3.setAlignmentX(Component.CENTER_ALIGNMENT);
        button4.setAlignmentX(Component.CENTER_ALIGNMENT);

   
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(button1);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(button2);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(button3);
        mainPanel.add(Box.createVerticalStrut(10));
        mainPanel.add(button4);
        mainPanel.add(Box.createVerticalStrut(20));

        
        add(topPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }
}
