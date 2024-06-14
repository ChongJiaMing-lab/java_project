import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class add_schdule extends JFrame {
    private List<Schedule> scheduleList = new ArrayList<>();
    private JList<Schedule> scheduleJList;
    private DefaultListModel<Schedule> listModel;
    private JButton addButton, editButton, deleteButton;
    private final String FILE_NAME = "src/schdule.txt";

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new add_schdule().setVisible(true));
    }

    public add_schdule () {
        setTitle("View and Add Bus Schedule");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        initComponents();
        loadScheduleFromFile();
    }

    private void initComponents() {
        listModel = new DefaultListModel<>();
        scheduleJList = new JList<>(listModel);
        addButton = new JButton("Add Schedule");
        editButton = new JButton("Edit Schedule");
        deleteButton = new JButton("Delete Schedule");

        setLayout(new BorderLayout());
        JPanel controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(addButton);
        controlPanel.add(editButton);
        controlPanel.add(deleteButton);

        add(new JScrollPane(scheduleJList), BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Schedule newSchedule = createNewSchedule();
                if (newSchedule != null) {
                    scheduleList.add(newSchedule);
                    updateScheduleList();
                    saveScheduleToFile();
                }
            }
        });

        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Schedule selectedSchedule = scheduleJList.getSelectedValue();
                if (selectedSchedule != null) {
                    editSchedule(selectedSchedule);
                    updateScheduleList();
                    saveScheduleToFile();
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Schedule selectedSchedule = scheduleJList.getSelectedValue();
                if (selectedSchedule != null) {
                    scheduleList.remove(selectedSchedule);
                    updateScheduleList();
                    saveScheduleToFile();
                }
            }
        });
    }

    private Schedule createNewSchedule() {
        String busPlate = JOptionPane.showInputDialog(this, "Enter bus plate:");
        if (busPlate != null && !busPlate.trim().isEmpty()) {
            String dateStr = JOptionPane.showInputDialog(this, "Enter date (yyyy-MM-dd):");
            String time = JOptionPane.showInputDialog(this,"Enter time (HH:mm):");
            String from = JOptionPane.showInputDialog(this,"Enter  departure location:");
            String to = JOptionPane.showInputDialog(this,"Enter destination:");
            String priceStr = JOptionPane.showInputDialog(this,"Enter price:");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date date = dateFormat.parse(dateStr + " " + time);
                double price = Double.parseDouble(priceStr);
                return new Schedule(busPlate, date, from, to, price);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Schedule not added.");
            }
        }
        return null;
    }

    private void editSchedule(Schedule s) {
        String newBusPlate = JOptionPane.showInputDialog(this, "Enter new bus plate:", s.getName());
        if (newBusPlate != null && !newBusPlate.trim().isEmpty()) {
            String newDateStr = JOptionPane.showInputDialog(this, "Enter new date (yyyy-MM-dd):");
            String newTime = JOptionPane.showInputDialog(this, "Enter new time (HH:mm):");
            String newFrom = JOptionPane.showInputDialog(this, "Enter new departure location:");
            String newTo = JOptionPane.showInputDialog(this, "Enter new destination:");
            String newPriceStr = JOptionPane.showInputDialog(this, "Enter new price:");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date newDate = dateFormat.parse(newDateStr + " " + newTime);
                double newPrice = Double.parseDouble(newPriceStr);
                s.setName(newBusPlate);
                s.setDate(newDate);
                s.setFrom(newFrom);
                s.setTo(newTo);
                s.setPrice(newPrice);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid date format. Schedule not edited.");
            }
        }
    }

    private void updateScheduleList() {
        listModel.clear();
        for (Schedule s : scheduleList) {
            listModel.addElement(s);
        }
    }

    private void saveScheduleToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Schedule s : scheduleList) {
                writer.write(s.toFileString());
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving schedule to file: " + e.getMessage());
        }
    }

    private void loadScheduleFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = reader.readLine()) != null) {
                Schedule s = Schedule.fromFileString(line);
                if (s != null) {
                    scheduleList.add(s);
                }
            }
            updateScheduleList();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "File not found: " + e.getMessage());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading schedule from file: " + e.getMessage());
        }
    }
}

class Schedule {
    private String busPlate;
    private Date date;
    private String from;
    private String to;
    private double price;

    public Schedule(String busPlate, Date date, String from, String to, double price) {
        this.busPlate = busPlate;
        this.date = date;
        this.from = from;
        this.to = to;
        this.price = price;
    }

    public String getName() {
        return busPlate;
    }

    public void setName(String busPlate) {
        this.busPlate = busPlate;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDateStr() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormat.format(date);
    }

    public String toFileString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return busPlate + "," + dateFormat.format(date) + "," + from + "," + to + "," + price;
    }

    public static Schedule fromFileString(String fileString) {
        String[] parts = fileString.split(",");
        if (parts.length == 5) {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                String busPlate = parts[0];
                Date date = dateFormat.parse(parts[1]);
                String from = parts[2];
                String to = parts[3];
                double price = Double.parseDouble(parts[4]);
                return new Schedule(busPlate, date, from, to, price);
            } catch (Exception e) {
               
            }
        }
        return null;
    }


public String toString() {
    return "Bus Plate: " + busPlate + "\n From: " + from + "\n To: " + to + "\n Price: " + price;
}

}