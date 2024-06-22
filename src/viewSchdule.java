
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author User
 */
public class viewSchdule extends JFrame implements ActionListener{
     private java.util.List<Schedule> scheduleList = new ArrayList<>();
    private JTable scheduleTable;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private final String FILE_NAME = "src/schdule.txt";
    
    
    public static void main(String[] args)
    {
        viewSchdule v = new viewSchdule();
    }
    
    public viewSchdule()
    {
        setTitle("View and Add Bus Schedule");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        
        loadScheduleFromFile();
        
        
        String[] columnNames = {"Bus Plate", "Date", "From", "To", "Price", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        scheduleTable = new JTable(tableModel);
        addButton = new JButton("Book ticket");
        
        setLayout(new BorderLayout());
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(addButton);
                
        add(new JScrollPane(scheduleTable), BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
        
        addButton.addActionListener(this);
        
    }
    
        private void loadScheduleFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            Schedule s;
            while ((s = Schedule.fromFileString(reader)) != null) {
                scheduleList.add(s);
            }
            updateScheduleTable();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "File not found: " + e.getMessage());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading schedule from file: " + e.getMessage());
        }
    }
        
            private void updateScheduleTable() {
        tableModel.setRowCount(0);
        for (Schedule s : scheduleList) {
            tableModel.addRow(s.toTableRow());
        }
    }
    
    @Override
    public void actionPerformed(ActionEvent e)
    {
        
    }
}
