/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Scanner;

public class user_login extends JFrame implements ActionListener{
    public static String current_id = "";
    public static String current_name = "";
    private JLabel lb1,lb2,lbIcon,lbTitle,lb3,lb4;
    private JButton bt1,bt2;
    private JTextField tf1;
    private JPasswordField pf1;
    ImageIcon icon1;
        private static final int name_LENGTH = 50;
    private static final int PH_LENGTH = 50;
    private static final int EMAIL_LENGTH = 50;
    private static final int PASSWORD_LENGTH = 20;
    private static final int RECORD_LENGTH = name_LENGTH + PH_LENGTH + EMAIL_LENGTH + PASSWORD_LENGTH;
    
    public static void main(String[] args)
    {
        user_login u = new user_login();
        u.setVisible(true);
    }
    
    public user_login()
    {
        setSize(600,300);
        setTitle("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        icon1 = new ImageIcon("src/image/bus.jpg");
        lbTitle = new JLabel("User Login");
        lbTitle.setFont(new Font("Serif", Font.BOLD, 20));
        lbTitle.setForeground(Color.white);
        lb1 = new JLabel("Email : ");
        lb2 = new JLabel("Password : ");
        lb3 = new JLabel(" ");
        lb4 = new JLabel("I don't have an account.");
        lbIcon = new JLabel(icon1);
        
        bt1 = new JButton("Login");
        bt2 = new JButton("Sign Up");
        
        tf1 = new JTextField(20);
        pf1 = new JPasswordField(20);
        JPanel pTitle = new JPanel();
        pTitle.setBackground(Color.black);
        pTitle.add(lbTitle);
        
        JPanel pLeft = new JPanel();
        pLeft.add(lbIcon);
        JPanel p1 = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        p1.add(bt1);
        gbc.gridx = 0;
        gbc.gridy = 0;
        p1.add(lb1, gbc);// add to grid (0,0)
        gbc.gridx = 1;
        p1.add(tf1, gbc);// add to grid (1,0)

        gbc.gridx = 0;
        gbc.gridy = 1;
        p1.add(lb2, gbc);// add to grid (0,1)
        gbc.gridx = 1;
        p1.add(pf1, gbc);// add to grid (1,1)

        gbc.gridx = 1;
        gbc.gridy = 6;
        gbc.anchor = GridBagConstraints.CENTER;
        p1.add(bt1, gbc);
        
        
        JPanel pAlert = new JPanel(new GridLayout(2,1));
        JPanel pAlerr = new JPanel();
        pAlerr.add(lb3,SwingConstants.CENTER);
        JPanel pContainer = new JPanel();
        pContainer.add(lb4);
        pContainer.add(bt2);
        pAlert.add(pAlerr);
        pAlert.add(pContainer);
        
        add(pTitle,BorderLayout.NORTH);
        add(pLeft,BorderLayout.WEST);
        add(p1,BorderLayout.CENTER);
        add(pAlert,BorderLayout.SOUTH);
        
        bt1.addActionListener(this);
        bt2.addActionListener(this);
    }
    
        public boolean checkMail(String mail,String pass)
    {
        try {
            RandomAccessFile raf = new RandomAccessFile("src/user.bin","r");
            long fileLength = raf.length();
            long numRecords = fileLength / RECORD_LENGTH;
            
            for(int i =0; i <numRecords;i++)
            {
                
                byte[] bnameByte = new byte[name_LENGTH];
                byte[] bPHByte = new byte[PH_LENGTH];
                byte[] bEmailByte = new byte[EMAIL_LENGTH];
                byte[] bPassByte = new byte[PASSWORD_LENGTH];
                raf.read(bnameByte);
                raf.read(bPHByte);
                raf.read(bEmailByte);
                raf.read(bPassByte);
              
                String namee = new String(bnameByte).trim();
                String PH = new String(bPHByte).trim();
                String email = new String(bEmailByte).trim();
                String password = new String(bPassByte).trim();

                
                if(mail.equals(email)&&password.equals(pass))
                {
                    current_name = namee;
                    return true;
                }
            }
        } catch(FileNotFoundException e){
            System.out.println("Fail to read file");
            e.printStackTrace();
        } catch (IOException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
    }
        System.out.println("fail");
        return false;
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        if(e.getSource()==bt1)
        {
        String email = tf1.getText();
        String password = new String(pf1.getPassword());
        
        if(email.equals(""))
        {
            lb3.setText("Please Enter Email.");
        }
        else if(password.equals(""))
        {
            lb3.setText("Please Enter Password.");
        }
        else if(checkMail(email,password))
        {
            current_id = email;
            lb3.setText(" ");
            user_menu u2 = new user_menu();
            u2.setVisible(true);
            dispose();
        }
        else
        {
            lb3.setText("Invalid Email or Password.");
        }
        }
        
        else if(e.getSource()== bt2)
        {
            user_register u2 = new user_register();
            u2.setVisible(true);
            dispose();
        }
    }
}
