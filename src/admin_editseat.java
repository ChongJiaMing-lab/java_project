import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class admin_editseat extends JFrame {
    private int rows; // Adjusted dynamically based on the file content
    private int cols; // Adjusted dynamically based on the file content
    private JCheckBox[][] seats;
    private boolean[][] seatStatus;
    private java.util.List<CarInfo> carInfoList = new ArrayList<>();
    private JComboBox<String> carPlateComboBox;
    private JPanel seatPanel;
    private CarInfo selectedCarInfo;

    private ImageIcon seatSelect, av, unav;

    // Variables to keep track of the selected seat
    private int selectedRow = -1;
    private int selectedCol = -1;

    public admin_editseat() {
        setTitle("Admin - Edit Seats");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Initialize the combo box
        carPlateComboBox = new JComboBox<>();
        carPlateComboBox.addActionListener(e -> updateSeatStatus());

        // Load and scale images
        seatSelect = new ImageIcon("src/image/seat_select.png");
        av = new ImageIcon("src/image/seat_av.png");
        unav = new ImageIcon("src/image/seat_unav.png");

        int iconSize = 25; // New icon size

        Image seat_i = seatSelect.getImage();
        Image img1 = seat_i.getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
        seatSelect = new ImageIcon(img1);

        Image av_i = av.getImage();
        Image img2 = av_i.getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
        av = new ImageIcon(img2);

        Image unav_i = unav.getImage();
        Image img3 = unav_i.getScaledInstance(iconSize, iconSize, Image.SCALE_SMOOTH);
        unav = new ImageIcon(img3);

        // Create title label
        JLabel titleLabel = new JLabel("Admin - Edit Seats", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));

        // Create a panel for title and car plate combo box
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(titleLabel, BorderLayout.NORTH);
        northPanel.add(carPlateComboBox, BorderLayout.SOUTH);

        // Create seat panel
        seatPanel = new JPanel();

        // Create panel for control buttons
        JPanel controlPanel = new JPanel();
        JButton availableButton = new JButton("Available");
        JButton unavailableButton = new JButton("Unavailable");
        JButton saveButton = new JButton("Save");

        availableButton.addActionListener(e -> setSelectedSeatStatus(false));
        unavailableButton.addActionListener(e -> setSelectedSeatStatus(true));
        saveButton.addActionListener(e -> saveSeats());

        controlPanel.add(availableButton);
        controlPanel.add(unavailableButton);
        controlPanel.add(saveButton);

        // Create a back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            admin_menu menu = new admin_menu();
            menu.setVisible(true);
            dispose();
        });

        // Add the back button to the control panel
        controlPanel.add(backButton);

        // Create a panel for the notice
        JPanel noticePanel = new JPanel();
        noticePanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Add labels with icons and descriptions
        JLabel availableLabel = new JLabel("Available", av, JLabel.LEFT);
        JLabel unavailableLabel = new JLabel("Unavailable", unav, JLabel.LEFT);
        JLabel selectedLabel = new JLabel("Selected", seatSelect, JLabel.LEFT);

        noticePanel.add(availableLabel);
        noticePanel.add(unavailableLabel);
        noticePanel.add(selectedLabel);

        // Create a new panel to hold both controlPanel and noticePanel
        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.add(controlPanel, BorderLayout.NORTH);
        southPanel.add(noticePanel, BorderLayout.SOUTH);

        add(northPanel, BorderLayout.NORTH);
        add(new JScrollPane(seatPanel), BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH); // Add the new panel to the SOUTH position

        // Load car plate numbers from file after initializing the combo box
        loadCarPlates();

        // Initialize the seat status display
        if (carPlateComboBox.getItemCount() > 0) {
            carPlateComboBox.setSelectedIndex(0);
            updateSeatStatus();
        }
    }

    private void loadCarPlates() {
        try (Scanner s = new Scanner(new File("src/schdule.txt"))) {
            while (s.hasNextLine()) {
                String carPlate = s.nextLine().trim();
                carPlateComboBox.addItem(carPlate);
                for (int i = 0; i < 6; i++) 
                {
                    if (s.hasNextLine()) 
                    {
                        s.nextLine();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadCarInfo(String carPlate) {
        carInfoList.clear();
        try (Scanner s = new Scanner(new File("src/schedule_bus/" + carPlate + ".txt"))) {
            if (!s.hasNextLine()) return;
            String plate = s.nextLine().trim();
            System.out.println("Reading car plate: " + plate);

            if (!s.hasNextLine()) return;
            String date = s.nextLine().trim();
            System.out.println("Reading date: " + date);

            if (!s.hasNextLine()) return;
            String from = s.nextLine().trim();
            System.out.println("Reading from: " + from);

            if (!s.hasNextLine()) return;
            String to = s.nextLine().trim();
            System.out.println("Reading to: " + to);

            if (!s.hasNextLine()) return;
            String price = s.nextLine().trim();
            System.out.println("Reading price: " + price);

            if (!s.hasNextLine()) return;
            String status = s.nextLine().trim();
            System.out.println("Reading status: " + status);

            java.util.List<String[]> seatData = new ArrayList<>();
            while (s.hasNextLine()) {
                String line = s.nextLine().trim();
                if (!line.isEmpty()) {
                    String[] columns = line.split(" ");
                    if (columns.length == 3 || columns.length == 4) { // Ensure each row has either 3 or 4 columns
                        seatData.add(columns);
                    } else {
                        System.err.println("Error: Expected 3 or 4 columns in seat status, but got " + columns.length);
                        return;
                    }
                }
            }

            if (seatData.size() != 10) { // Ensure there are exactly 10 rows
                System.err.println("Error: Expected 10 rows of seat data, but got " + seatData.size());
                return;
            }

            rows = seatData.size();
            cols = seatData.get(0).length;
            seatStatus = new boolean[rows][cols];

            for (int i = 0; i < rows; i++) {
                String[] line = seatData.get(i);
                System.out.println("Reading seat row " + i + ": " + Arrays.toString(line));
                for (int j = 0; j < cols; j++) {
                    seatStatus[i][j] = line[j].equals("1");
                }
            }

            carInfoList.add(new CarInfo(plate, date, from, to, price, status, seatStatus));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateSeatStatus() {
        String selectedCarPlate = (String) carPlateComboBox.getSelectedItem();
        loadCarInfo(selectedCarPlate);

        for (CarInfo carInfo : carInfoList) {
            if (carInfo.carPlate.equals(selectedCarPlate)) {
                selectedCarInfo = carInfo;
                seatStatus = carInfo.seatStatus;
                break;
            }
        }

        seatPanel.removeAll();
        seatPanel.setLayout(new GridLayout(rows, cols, 10, 10)); // Adjust layout based on rows and cols

        seats = new JCheckBox[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                JCheckBox seatCheckbox = new JCheckBox();
                seatCheckbox.setIcon(seatStatus[i][j] ? unav : av);
                seatCheckbox.setSelected(seatStatus[i][j]);
                seatCheckbox.addActionListener(new SeatClickListener(i, j));
                seats[i][j] = seatCheckbox;
                seatPanel.add(seatCheckbox);
            }
        }
        seatPanel.revalidate();
        seatPanel.repaint();
    }

    private void setSelectedSeatStatus(boolean isSelected) {
        if (selectedRow != -1 && selectedCol != -1) {
            seatStatus[selectedRow][selectedCol] = isSelected;
            seats[selectedRow][selectedCol].setIcon(isSelected ? unav : av);
            seats[selectedRow][selectedCol].setSelected(isSelected);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a seat first.");
        }
    }

    private class SeatClickListener implements ActionListener {
        private int row;
        private int col;

        public SeatClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public void actionPerformed(ActionEvent e) {
            JCheckBox source = (JCheckBox) e.getSource();

            // Update the selected seat coordinates
            selectedRow = row;
            selectedCol = col;

            // Reset borders and icons for all seats before setting the selected one
            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    seats[i][j].setBorder(null);
                    if (i != row || j != col) {
                        seats[i][j].setIcon(seatStatus[i][j] ? unav : av);
                    }
                }
            }

            // Set the selected seat's border and icon
            seats[row][col].setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
            source.setIcon(seatSelect);
        }
    }

    private void saveSeats() {
        String selectedCarPlate = (String) carPlateComboBox.getSelectedItem();
        File originalFile = new File("src/schedule_bus/" + selectedCarPlate + ".txt");
        File tempFile = new File("src/schedule_bus/" + selectedCarPlate + "_temp.txt");

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {
            for (CarInfo carInfo : carInfoList) {
                bw.write(carInfo.carPlate);
                bw.newLine();
                bw.write(carInfo.date);
                bw.newLine();
                bw.write(carInfo.from);
                bw.newLine();
                bw.write(carInfo.to);
                bw.newLine();
                bw.write(carInfo.price);
                bw.newLine();
                bw.write(carInfo.status);
                bw.newLine();

                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++) {
                        bw.write((carInfo.seatStatus[i][j] ? "1" : "0") + (j < cols - 1 ? " " : ""));
                    }
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Failed to save seat status.");
            return;
        }

        // Ensure the original file can be deleted
        if (!originalFile.delete()) {
            JOptionPane.showMessageDialog(this, "Failed to delete the original file.");
            return;
        }

        // Ensure the temp file can be renamed
        if (!tempFile.renameTo(originalFile)) {
            JOptionPane.showMessageDialog(this, "Failed to rename the temp file.");
            return;
        }

        JOptionPane.showMessageDialog(this, "Seat status saved successfully.");
    }

    private class CarInfo {
        String carPlate;
        String date;
        String from;
        String to;
        String price;
        String status;
        boolean[][] seatStatus;

        public CarInfo(String carPlate, String date, String from, String to, String price, String status, boolean[][] seatStatus) {
            this.carPlate = carPlate;
            this.date = date;
            this.from = from;
            this.to = to;
            this.price = price;
            this.status = status;
            this.seatStatus = seatStatus;
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            admin_editseat frame = new admin_editseat();
            frame.setVisible(true);
        });
    }
}
