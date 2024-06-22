/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
import java.io.File;
import java.io.IOException;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileWriter;
import java.util.Scanner;

public class user_register extends JFrame implements ActionListener{
    private JLabel lb1,lb2,lb3,lb4,lb5,lb6,lbImage,lbTitle,lb7,lb8;
    private JButton bt1,bt2;
    private JTextField tf1,tf2,tf3,tf4;
    private JPasswordField pf1,pf2;
    private ImageIcon icon1;
    public static void main(String[] args)
    {
        user_register u = new user_register();
        u.setVisible(true);

    }
    
    public user_register()
    {
        setSize(650,350);
        setTitle("Register");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        icon1 = new ImageIcon("src/image/bus.jpg");
        lb1 = new JLabel("Full Name : ");
        lb2 = new JLabel("Phone Number : ");
        lb3 = new JLabel("Email : ");
        lb5 = new JLabel("Password : ");
        lb6 = new JLabel("Confirm Password : ");
        lb7 = new JLabel(" ");
        lb8 = new JLabel("Have an account?");
        lbImage = new JLabel(icon1);
        lbTitle = new JLabel("User Register");
        lbTitle.setFont(new Font("Serif", Font.BOLD, 20));
        lbTitle.setForeground(Color.white);

        bt1 = new JButton("Sign Up");
        bt2 = new JButton("Login");
        
        tf1 = new JTextField(20);
        tf2 = new JTextField(20);
        tf3 = new JTextField(20);
        tf4 = new JTextField(20);
        pf1 = new JPasswordField(20);
        pf2 = new JPasswordField(20);
        
        JPanel pTitle = new JPanel();
        pTitle.setBackground(Color.black);
        pTitle.add(lbTitle);
        
        JPanel pLeft = new JPanel();
        pLeft.add(lbImage);
        
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
        p1.add(tf2, gbc);// add to grid (1,1)

        gbc.gridx = 0;
        gbc.gridy = 2;
        p1.add(lb3, gbc);// add to grid (0,2)
        gbc.gridx = 1;
        p1.add(tf3, gbc);// add to grid (1,2)

        gbc.gridx = 0;
        gbc.gridy = 3;
        p1.add(lb5, gbc);// add to grid (0,4)
        gbc.gridx = 1;
        p1.add(pf1, gbc);// add to grid (1,4)

        gbc.gridx = 0;
        gbc.gridy = 4;
        p1.add(lb6, gbc);// add to grid (0,5)
        gbc.gridx = 1;
        p1.add(pf2, gbc);// add to grid (1,5)

        gbc.gridx = 1;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.CENTER;
        p1.add(bt1, gbc);
        
        JPanel pAlert = new JPanel(new GridLayout(2,1));
        JPanel pAlerr = new JPanel();
        pAlerr.add(lb7,SwingConstants.CENTER);
        JPanel pContainer = new JPanel();
        pContainer.add(lb8);
        pContainer.add(bt2);
        pAlert.add(pAlerr);
        pAlert.add(pContainer);
        
        add(pTitle,BorderLayout.NORTH);
        add(pLeft,BorderLayout.WEST);
        add(p1,BorderLayout.EAST);
        add(pAlert,BorderLayout.SOUTH);
        
        bt1.addActionListener(this);
    }
    
    public void createFile()
    {
        try {
        File myObj = new File("src/user.txt");
        if (myObj.createNewFile()) {
            System.out.println("File created: " + myObj.getName());
        } else {
            
          }
        } catch (IOException e) {
          System.out.println("An error occurred.");
          e.printStackTrace();
    }
    }
    
    public void writeFile(String name,String PH,String email,String password)
    {
                try {
            FileWriter userWrite = new FileWriter("src/user.txt",true);
            PrintWriter myWriter = new PrintWriter(userWrite);

            myWriter.write(name+"\n"+PH+"\n"+email+"\n"+password+"\n");

            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }
    
    public boolean checkMail(String mail)
    {
        try {
            File read = new File("src/user.txt");
            Scanner Reader = new Scanner(read);
            while(Reader.hasNextLine())
            {
                String name = Reader.nextLine();
                String PH = Reader.nextLine();
                String email = Reader.nextLine();
                String password = Reader.nextLine();
                
                if(mail.equals(email))
                {
                    return false;
                }

            }

        } catch(FileNotFoundException e){
            System.out.println("Fail to read file");
            e.printStackTrace();
        }
    return true;
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        String name = tf1.getText();
        String PH = tf2.getText();
        String email = tf3.getText();
        String password = new String(pf1.getPassword());
        String c_password = new String(pf2.getPassword());
        
        if(name.equals(""))
        {
            lb7.setText("Name cannot be empty.");
        }
        else if(PH.equals(""))
        {
            lb7.setText("Phone Number cannot be empty.");
        }
        else if(email.equals(""))
        {
            lb7.setText("Email cannot be empty.");
        }
        else if(!email.contains("@"))
        {
            lb7.setText("Invalid Email.");
        }
        else if(password.equals(""))
        {
            lb7.setText("Password cannot be empty.");
        }
        else if(c_password.equals(""))
        {
            lb7.setText("confirm Password cannot be empty.");
        }
        else if(!password.equals(c_password))
        {
            lb7.setText("Confirm Password not match.");
        }
        else
        {
            createFile();
            if(checkMail(email))
            {
                lb7.setText(" ");
                writeFile(name,PH,email,password);
            }
            else
            {
                 lb7.setText("Email Already Exist.");
            }
            
        }
    }
}
