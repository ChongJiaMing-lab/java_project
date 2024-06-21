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

    public add_schdule() {
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
            String time = JOptionPane.showInputDialog(this, "Enter time (HH:mm):");
            String from = JOptionPane.showInputDialog(this, "Enter departure location:");
            String to = JOptionPane.showInputDialog(this, "Enter destination:");
            String priceStr = JOptionPane.showInputDialog(this, "Enter price:");
            String status = JOptionPane.showInputDialog(this, "Enter status (done/not done):");
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date date = dateFormat.parse(dateStr + " " + time);
                double price = Double.parseDouble(priceStr);

                int rows = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter number of seat rows:"));
                int cols = Integer.parseInt(JOptionPane.showInputDialog(this, "Enter number of seat columns:"));
                int[][] seats = new int[rows][cols];
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        seats[i][j] = 0;
                    }
                }

                return new Schedule(busPlate, date, from, to, price, seats, status);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid input format. Schedule not added.");
            }
        }
        return null;
    }

    private void editSchedule(Schedule s) {
        String newBusPlate = JOptionPane.showInputDialog(this, "Enter new bus plate:", s.getName());
        if (newBusPlate != null && !newBusPlate.trim().isEmpty()) {
            String newDateStr = JOptionPane.showInputDialog(this, "Enter new date (yyyy-MM-dd):", new SimpleDateFormat("yyyy-MM-dd").format(s.getDate()));
            String newTime = JOptionPane.showInputDialog(this, "Enter new time (HH:mm):", new SimpleDateFormat("HH:mm").format(s.getDate()));
            String newFrom = JOptionPane.showInputDialog(this, "Enter new departure location:", s.getFrom());
            String newTo = JOptionPane.showInputDialog(this, "Enter new destination:", s.getTo());
            String newPriceStr = JOptionPane.showInputDialog(this, "Enter new price:", String.valueOf(s.getPrice()));
            String newStatus = JOptionPane.showInputDialog(this, "Enter new status (done/not done):", s.getStatus());

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            try {
                Date newDate = dateFormat.parse(newDateStr + " " + newTime);
                double newPrice = Double.parseDouble(newPriceStr);
                s.setName(newBusPlate);
                s.setDate(newDate);
                s.setFrom(newFrom);
                s.setTo(newTo);
                s.setPrice(newPrice);
                s.setStatus(newStatus);
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
            for (int i = 0; i < scheduleList.size(); i++) {
                Schedule s = scheduleList.get(i);
                writer.write(s.toFileString());
                if (i < scheduleList.size() - 1) {
                    writer.newLine(); 
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving schedule to file: " + e.getMessage());
        }
    }

    private void loadScheduleFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            Schedule s;
            while ((s = Schedule.fromFileString(reader)) != null) {
                scheduleList.add(s);
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
    private int[][] seats;
    private String status; 

    public Schedule(String busPlate, Date date, String from, String to, double price, int[][] seats, String status) {
        this.busPlate = busPlate;
        this.date = date;
        this.from = from;
        this.to = to;
        this.price = price;
        this.seats = seats;
        this.status = status;
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

    public int[][] getSeats() {
        return seats;
    }

    public void setSeats(int[][] seats) {
        this.seats = seats;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDateStr() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return dateFormat.format(date);
    }

    public String toFileString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        StringBuilder sb = new StringBuilder();
        sb.append(busPlate).append(System.lineSeparator());
        sb.append(dateFormat.format(date)).append(System.lineSeparator());
        sb.append(from).append(System.lineSeparator());
        sb.append(to).append(System.lineSeparator());
        sb.append(price).append(System.lineSeparator());
        sb.append(status).append(System.lineSeparator()); 
        for (int[] row : seats) {
            for (int seat : row) {
                sb.append(seat).append(" ");
            }
            sb.deleteCharAt(sb.length() - 1);
            sb.append(System.lineSeparator());
        }
        return sb.toString().trim();
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

            // Validate date and price
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date;
            double price;

            try {
                date = dateFormat.parse(dateStr);
            } catch (Exception e) {
                System.err.println("Invalid date format: " + dateStr);
                return null;
            }

            try {
                price = Double.parseDouble(priceStr);
            } catch (NumberFormatException e) {
                System.err.println("Invalid price format: " + priceStr);
                return null;
            }

            List<int[]> seatList = new ArrayList<>();
            String line;
            while ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
                if (line.matches("\\d+( \\d+)*")) {
                    String[] seatRow = line.trim().split(" ");
                    int[] row = new int[seatRow.length];
                    for (int j = 0; j < seatRow.length; j++) {
                        try {
                            row[j] = Integer.parseInt(seatRow[j]);
                        } catch (NumberFormatException e) {
                            System.err.println("Invalid seat format: " + seatRow[j]);
                            return null;
                        }
                    }
                    seatList.add(row);
                } else {
                    break;
                }
            }

            int[][] seats = seatList.toArray(new int[0][]);
            return new Schedule(busPlate, date, from, to, price, seats, status);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String toString() {
        return "Bus Plate: " + busPlate + ", Date: " + getDateStr() + ", From: " + from + ", To: " + to + ", Price: " + price + ", Status: " + status;
    }

}