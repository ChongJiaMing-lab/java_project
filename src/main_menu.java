import javax.swing.*;
import java.awt.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class main_menu extends JFrame {
    
    public static void main(String[] args) {
        main_menu main_menu = new main_menu();
        main_menu.setTitle("Main Menu");
        main_menu.setSize(800, 600);
        main_menu.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        main_menu.setVisible(true);
    }
    
    public main_menu()
    {
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);


        JLabel Title = new JLabel("Bus Ticketing System", JLabel.CENTER);
        Title.setFont(new Font("Arial", Font.BOLD, 35));


        ImageIcon icon = new ImageIcon("src/image/bus.jpg");
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImg);
        JLabel imageLabel = new JLabel(icon);


        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.WHITE);
        titlePanel.add(imageLabel);
        titlePanel.add(Title);

        topPanel.add(titlePanel, BorderLayout.NORTH);
        

        JPanel mainPanel = new JPanel(new FlowLayout(FlowLayout.CENTER,20,0));
        mainPanel.setBackground(Color.LIGHT_GRAY); 
        mainPanel.setSize(60, 100);
         

        
        
        JButton button1 = new JButton("Admin");
        button1.addActionListener(e -> {

            admin_login login = new admin_login();   
            login.setVisible(true);  
            dispose();
        });
        JButton button2 = new JButton("User");
        


        button1.setAlignmentX(Component.CENTER_ALIGNMENT);
        button2.setAlignmentX(Component.CENTER_ALIGNMENT);
        

   
        mainPanel.add(Box.createVerticalStrut(100));
        mainPanel.add(button1);
        mainPanel.add(Box.createVerticalStrut(100));
        mainPanel.add(button2);
        
        
        
        add(topPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }
    
}
