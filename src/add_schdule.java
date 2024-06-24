import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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
    private JTable scheduleTable;
    private DefaultTableModel tableModel;
    private JButton addButton, editButton, deleteButton, returnButton;
    private final String FILE_NAME = "src/schdule.txt"; 
    private JPanel controlPanel;

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
        String[] columnNames = {"Bus Plate", "Date", "From", "To", "Price", "Status"};
        tableModel = new DefaultTableModel(columnNames, 0);
        scheduleTable = new JTable(tableModel);
        addButton = new JButton("Add Schedule");
        editButton = new JButton("Edit Schedule");
        deleteButton = new JButton("Delete Schedule");
        returnButton = new JButton("Go Back");
        returnButton.addActionListener(e -> {
            admin_menu menu = new admin_menu();
            menu.setVisible(true);
            dispose();
        });
        setLayout(new BorderLayout());
        controlPanel = new JPanel(new FlowLayout());
        controlPanel.add(addButton);
        controlPanel.add(editButton);
        controlPanel.add(deleteButton);
        controlPanel.add(returnButton);

        add(new JScrollPane(scheduleTable), BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Schedule newSchedule = createNewSchedule();
                if (newSchedule != null) {
                    scheduleList.add(newSchedule);
                    updateScheduleTable();
                    saveScheduleToFile();
                    saveScheduleToSeparateFile(newSchedule);
                }
            }
        });

        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = scheduleTable.getSelectedRow();
                if (selectedRow != -1) {
                    Schedule selectedSchedule = scheduleList.get(selectedRow);
                    editSchedule(selectedSchedule);
                    updateScheduleTable();
                    saveScheduleToFile();
                    saveScheduleToSeparateFile(selectedSchedule);
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int selectedRow = scheduleTable.getSelectedRow();
                if (selectedRow != -1) {
                    Schedule selectedSchedule = scheduleList.get(selectedRow);
                    scheduleList.remove(selectedRow);
                    updateScheduleTable();
                    saveScheduleToFile();
                    deleteScheduleFile(selectedSchedule);
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

                JRadioButton bigButton = new JRadioButton("Big");
                JRadioButton smallButton = new JRadioButton("Small");
                ButtonGroup sizeGroup = new ButtonGroup();
                sizeGroup.add(bigButton);
                sizeGroup.add(smallButton);
                JPanel busSizePanel = new JPanel();
                busSizePanel.add(bigButton);
                busSizePanel.add(smallButton);

                int option = JOptionPane.showConfirmDialog(this, busSizePanel, "Choose bus size", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
                if (option == JOptionPane.OK_OPTION) {
                    int rows, cols;
                    if (bigButton.isSelected()) {
                        rows = 10;
                        cols = 4;
                    } else if (smallButton.isSelected()) {
                        rows = 10;
                        cols = 3;
                    } else {
                        JOptionPane.showMessageDialog(this, "Please select bus size.");
                        return null;
                    }

                    int[][] seats = new int[rows][cols];
                    for (int i = 0; i < rows; i++) {
                        for (int j = 0; j < cols; j++) {
                            seats[i][j] = 0;
                        }
                    }
                    JOptionPane.showMessageDialog(this, "Add Schedule Successful.");
                    return new Schedule(busPlate, date, from, to, price, seats, status);
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Invalid input format. Schedule not added.");
            }
        }
        return null;
    }

    private void editSchedule(Schedule s) {
    String newBusPlate = JOptionPane.showInputDialog(this, "Enter new bus plate:", s.getBusPlate());
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

            
            String oldBusPlate = s.getBusPlate();

          
            s.setBusPlate(newBusPlate);
            s.setDate(newDate);
            s.setFrom(newFrom);
            s.setTo(newTo);
            s.setPrice(newPrice);
            s.setStatus(newStatus);

            
            scheduleList.remove(s); 
            scheduleList.add(s);    

           
            saveScheduleToFile();

           
            if (!oldBusPlate.equals(newBusPlate)) {
                renameScheduleFile(oldBusPlate, newBusPlate);
            }

            JOptionPane.showMessageDialog(this, "Edit Successful.");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Invalid date format. Schedule not edited.");
        }
    }
}

private void renameScheduleFile(String oldBusPlate, String newBusPlate) {
    String oldFileName = "src/schedule_bus/" + oldBusPlate + ".txt";
    String newFileName = "src/schedule_bus/" + newBusPlate + ".txt";
    File oldFile = new File(oldFileName);
    File newFile = new File(newFileName);
    if (oldFile.exists()) {
        if (oldFile.renameTo(newFile)) {
            JOptionPane.showMessageDialog(this, "File renamed successfully.");
        } else {
            JOptionPane.showMessageDialog(this, "Failed to rename file.");
        }
    } else {
        JOptionPane.showMessageDialog(this, "File not found for renaming.");
    }
}

    private void updateScheduleTable() {
        tableModel.setRowCount(0);
        for (Schedule s : scheduleList) {
            tableModel.addRow(s.toTableRow());
        }
    }

    private void saveScheduleToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Schedule s : scheduleList) {
                writer.write(s.toFileStringWithoutSeats());
                writer.newLine();
                writer.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving schedule to file: " + e.getMessage());
        }
    }

    private void loadScheduleFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            Schedule s;
            while ((s = ScheduledFromFile.fromFileString(reader)) != null) {
                scheduleList.add(s);
            }
            updateScheduleTable();
        } catch (FileNotFoundException e) {
            JOptionPane.showMessageDialog(this, "File not found: " + e.getMessage());
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error loading schedule from file: " + e.getMessage());
        }
    }

    private void saveScheduleToSeparateFile(Schedule s) {
        String busPlateFileName = "src/schedule_bus/" + s.getBusPlate() + ".txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(busPlateFileName))) {
            writer.write(s.toFileString());
            writer.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving schedule to separate file: " + e.getMessage());
        }
    }

    private void deleteScheduleFile(Schedule s) {
        String busPlateFileName = "src/schedule_bus/" + s.getBusPlate() + ".txt";
        File file = new File(busPlateFileName);
        if (file.exists() && !file.delete()) {
            JOptionPane.showMessageDialog(this, "Error deleting schedule file for " + s.getBusPlate());
        } else {
            scheduleList.remove(s);
            updateScheduleTable();
            saveScheduleToFile();
            JOptionPane.showMessageDialog(this, "Delete Schedule Successful.");
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

    public String getBusPlate() {
        return busPlate;
    }

    public void setBusPlate(String busPlate) {
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

    public String[] toTableRow() {
        return new String[]{busPlate, getDateStr(), from, to, String.valueOf(price), status};
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

    public String toFileStringWithoutSeats() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        StringBuilder sb = new StringBuilder();
        sb.append(busPlate).append(System.lineSeparator());
        sb.append(dateFormat.format(date)).append(System.lineSeparator());
        sb.append(from).append(System.lineSeparator());
        sb.append(to).append(System.lineSeparator());
        sb.append(price).append(System.lineSeparator());
        sb.append(status).append(System.lineSeparator());
        return sb.toString().trim();
    }
}

class ScheduledFromFile extends Schedule {
    public ScheduledFromFile(String busPlate, Date date, String from, String to, double price, int[][] seats, String status) {
        super(busPlate, date, from, to, price, seats, status);
    }

    public static ScheduledFromFile fromFileString(BufferedReader reader) {
        try {
            String busPlate = reader.readLine();
            if (busPlate == null || busPlate.trim().isEmpty()) {
                return null;
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date date = dateFormat.parse(reader.readLine());
            String from = reader.readLine();
            String to = reader.readLine();
            double price = Double.parseDouble(reader.readLine());
            String status = reader.readLine();

            List<int[]> seatList = new ArrayList<>();
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
            return new ScheduledFromFile(busPlate, date, from, to, price, seats, status);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}