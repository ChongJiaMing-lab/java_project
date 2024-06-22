/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package search;

/**
 *
 * @author User
 */
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.util.HashSet;
import java.util.Set;

public class search extends JFrame implements ActionListener{
    private JLabel lbTitle,lb1,lb2;
    private JButton bt1;
    private JComboBox cb1,cb2;

    
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
        
        String[] Fromschedule = getSchedule(1);
        String[] Toschedule = getSchedule(2);
        
        cb1 = new JComboBox(Fromschedule);
        cb2 = new JComboBox(Toschedule);
        
        lbTitle = new JLabel("Bus Ticket");
        lbTitle.setFont(new Font("Serif", Font.BOLD, 20));
        lbTitle.setForeground(Color.white);
        
        
    }
    
    public String[] getSchedule(int mode)
    {
        int linenum =0;
        Set<String> FromList = new HashSet<>();
        Set<String> ToList = new HashSet<>();
        String inputFile = "src/schdule.txt";
        
        try (BufferedReader reader = new BufferedReader(new FileReader(inputFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                
                if(!line.contains(" 0 ") && !line.equals(""))
                {
                    linenum++;
                    switch (linenum) {
                        case 3 -> FromList.add(line.trim());
                        case 4 -> ToList.add(line.trim());
                        case 6 -> linenum =0;
                        default -> {
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        String[] schdule = {};

        if(mode == 1)
        {
            schdule = FromList.toArray(new String[0]);
        }
        else if(mode == 2)
        {
            schdule = ToList.toArray(new String[0]);
        }
        return schdule;
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        
    }
}
