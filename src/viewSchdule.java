
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
public class viewSchdule extends JFrame implements ActionListener {

    private java.util.List<Schedule> scheduleList = new ArrayList<>();
    private JTable scheduleTable;
    private DefaultTableModel tableModel;
    private JButton addButton;
    private final String FILE_NAME = "src/schdule.txt";
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static void main(String[] args) {
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String inputDate = "2023-06-29";
            Date date = dateFormat.parse(inputDate);
            viewSchdule v = new viewSchdule("KUANTAN", "PENANG", date);
            v.setVisible(true);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public viewSchdule(String from, String to, Date date) {
        setTitle("View and Add Bus Schedule");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        String[] columnNames = {"Bus Plate", "Date", "From", "To", "Price", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        scheduleTable = new JTable(tableModel);
        addButton = new JButton("Book ticket");

        loadScheduleFromFile(from, to, date);

        setLayout(new BorderLayout());
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(addButton);

        add(new JScrollPane(scheduleTable), BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        addButton.addActionListener(this);

    }

    private void loadScheduleFromFile(String from, String to, Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.format(date);
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            Schedule s;
            System.out.println(from + " " + to);
            while ((s = Schedule.fromFileString(reader)) != null) {
                if (from.equals(s.getFrom()) && to.equals(s.getTo()) && dateFormat.format(date).equals(s.getDateStr())) {
                    scheduleList.add(s);
                }
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
            if(s.getStatus().equals("not done"))
            {
                tableModel.addRow(s.toTableRow());
            }
        }
    }

    @Override
     public void actionPerformed(ActionEvent e) {
        if (e.getSource() == addButton) {
            int selectedRow = scheduleTable.getSelectedRow();
            if (selectedRow != -1) {
                String busPlate = (String) tableModel.getValueAt(selectedRow, 0);

                user_booking ub = new user_booking(busPlate);
                ub.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Please select one bus。");
            }
        }
    }
}

class Schedule {

    private String busPlate;
    private Date date;
    private String from;
    private String to;
    private double price;
    private int[][] seats;
    private String status;
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public Schedule(String busPlate, Date date, String from, String to, double price, int[][] seats, String status) {
        this.busPlate = busPlate;
        this.date = date;
        this.from = from;
        this.to = to;
        this.price = price;
        this.seats = seats;
        this.status = status;
    }
        
        public String getFrom()
        {
            return from;
        }
        
        public String getTo()
        {
            return to;
        }
        
        public String getStatus()
        {
            return status;
        }
        
        public String getDateStrr() {
        SimpleDateFormat dateFormatr = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormatr.format(date);
    }
        
        public String[] toTableRow() {
            return new String[]{busPlate, getDateStrr(), from, to, String.valueOf(price), status};
        }

    public String getDateStr() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public static Schedule fromFileString(BufferedReader reader) {
        try {
            String busPlate = reader.readLine();
            if (busPlate == null || busPlate.trim().isEmpty()) {
                return null;
            }

            String dateStr = reader.readLine();
            String from = reader.readLine();
            String to = reader.readLine();
            String priceStr = reader.readLine();
            String status = reader.readLine();

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = dateFormat.parse(dateStr);
            double price = Double.parseDouble(priceStr);

            java.util.List<int[]> seatList = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
                String[] seatRow = line.trim().split(" ");
                int[] row = new int[seatRow.length];
                for (int j = 0; j < seatRow.length; j++) {
                    row[j] = Integer.parseInt(seatRow[j]);
                }
                seatList.add(row);
            }

            int[][] seats = seatList.toArray(new int[0][]);
            return new Schedule(busPlate, date, from, to, price, seats, status);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}


