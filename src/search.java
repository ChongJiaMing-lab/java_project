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
import java.util.Date;
import java.text.SimpleDateFormat;
public class search extends JFrame implements ActionListener{
    private JLabel lbTitle,lb1,lb2,lb3,lb4;
    private JButton bt1;
    private JComboBox cb1,cb2;
    private JTextField tf1;
    
    public static void main(String[] args)
    {
        search s = new search();
        s.setVisible(true);
    }
    
    public search()
    {
        setSize(800,600);
        setTitle("Scedule Searching");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        String[] location = {"JOHOR","MELAKA","NEGERI SEMBILAN","PENANG","PERAK","PERLIS","KEDAH","SELANGOR","PAHANG","TERENGGANU","KELANTAN"};

        cb1 = new JComboBox(location);
        cb2 = new JComboBox(location);
        
        lbTitle = new JLabel("Bus Ticket");
        lbTitle.setFont(new Font("Serif", Font.BOLD, 20));
        lbTitle.setForeground(Color.white);
        lb1 = new JLabel("From :");
        lb2 = new JLabel("To :"); lb2 = new JLabel("To :");
        lb3 = new JLabel("Date (YYYY-MM-DD):");
        lb4 = new JLabel(" ");
        bt1 = new JButton("Search");
        tf1 = new JTextField(10);
        
        JPanel pTitle = new JPanel();
        pTitle.setBackground(Color.black);
        pTitle.add(lbTitle);
        
        JPanel p1 = new JPanel(new GridBagLayout());
         GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;
        gbc.gridy = 0;
        p1.add(lb1,gbc);
        gbc.gridx = 1;
        p1.add(cb1,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 1;
        p1.add(lb2,gbc);
        gbc.gridx = 1;
        p1.add(cb2,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 2;
        p1.add(lb3,gbc);
        gbc.gridx = 1;
        p1.add(tf1,gbc);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        p1.add(lb4,gbc);
        
        JPanel p2 = new JPanel(new GridLayout(2,1));
        p2.add(bt1);
        add(pTitle,BorderLayout.NORTH);
        add(p1,BorderLayout.CENTER);
        add(p2,BorderLayout.SOUTH);
        bt1.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        String from = cb1.getSelectedItem().toString();
        String to = cb2.getSelectedItem().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try{
        String inputDate = String.valueOf(tf1.getText());
    Date date = dateFormat.parse(inputDate);
        lb4.setText(" ");
        
            viewSchdule vs = new viewSchdule(from,to,date);
            vs.setVisible(true);
            dispose();
        }
        catch(Exception f)
        {
            lb4.setText("Invalid Date Format");
        }
    }
}