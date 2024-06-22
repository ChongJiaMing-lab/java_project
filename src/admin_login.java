import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class admin_login extends JFrame 
{
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JButton loginButton;
    private JPanel loginPanel;
    private JPanel loginPanel2;

    public admin_login() 
    {
        setTitle("Admin Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 250);
        setLocationRelativeTo(null);


        JPanel mainPanel = new JPanel(new BorderLayout());
        add(mainPanel, BorderLayout.CENTER);

        
        JPanel topPanel = new JPanel(new BorderLayout());
        mainPanel.add(topPanel, BorderLayout.NORTH);

       
        JLabel titleLabel = new JLabel("Admin Login", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        topPanel.add(titleLabel, BorderLayout.CENTER);

      
        JLabel logoLabel = new JLabel();
        ImageIcon logoIcon = new ImageIcon("src/image/admin.png"); 
        Image logoImage = logoIcon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH); 
        logoLabel.setIcon(new ImageIcon(logoImage));
        topPanel.add(logoLabel, BorderLayout.EAST);

        
        loginPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        mainPanel.add(loginPanel, BorderLayout.CENTER);
        
        loginPanel2 = new JPanel();
        mainPanel.add(loginPanel2, BorderLayout.SOUTH);

       
        loginPanel.add(new JLabel("Username:"));
        usernameField = new JTextField();
        loginPanel.add(usernameField);

        loginPanel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        loginPanel.add(passwordField);

     
        loginButton = new JButton("Login");
        loginPanel2.add(loginButton);
        loginPanel2.add(new JLabel("")); 
        

       
        loginButton.addActionListener(new ActionListener() 
        {
            
            public void actionPerformed(ActionEvent e) 
            {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                int result = check_detail(username, password);
                if (result == 1) 
                {
                    
                    admin_menu admin_menu = new admin_menu();
                    admin_menu.setVisible(true);
                    dispose();
                } 
                else if (result == -1)
                {
                    JOptionPane.showMessageDialog(admin_login.this, "Invalid username ", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
                else if (result == -2)
                {
                    JOptionPane.showMessageDialog(admin_login.this, "Invalid password", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
                
            }
        });
    }

    private int check_detail(String username, String password) 
    {
        String filePath = "src/admin_infor.txt";
        
        
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                String[] parts = line.split(" ");
                if (parts.length == 2) 
                {
                    String fileUsername = parts[0];
                    String filePassword = parts[1];
                    if (fileUsername.equals(username)) 
                    {
                         if (filePassword.equals(password)) 
                         {
                            return 1; 
                        } else 
                         {
                            return -2; 
                        }
                    }
                   
                    
                }
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
        return -1;
    }

   

    public static void main(String[] args) {
    
    new admin_login().setVisible(true);
}
}
