import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class admin_editseat extends JFrame {
    private static final int ROWS = 6; // Adjusted to match the seat status data
    private static final int COLS = 3;
    private JButton[][] seats = new JButton[ROWS][COLS];
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

        // Load car information from file
        loadCarInfo();

        // Load and scale images
        seatSelect = new ImageIcon("src/image/seat_select.png");
        av = new ImageIcon("src/image/seat_av.png");
        unav = new ImageIcon("src/image/seat_unav.png");

        Image seat_i = seatSelect.getImage();
        Image img1 = seat_i.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        seatSelect = new ImageIcon(img1);

        Image av_i = av.getImage();
        Image img2 = av_i.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        av = new ImageIcon(img2);

        Image unav_i = unav.getImage();
        Image img3 = unav_i.getScaledInstance(35, 35, Image.SCALE_SMOOTH);
        unav = new ImageIcon(img3);

        // Create title label
        JLabel titleLabel = new JLabel("Admin - Edit Seats", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));

        // Create car plate combo box
        carPlateComboBox = new JComboBox<>();
        for (CarInfo carInfo : carInfoList) {
            carPlateComboBox.addItem(carInfo.carPlate);
        }
        carPlateComboBox.addActionListener(e -> updateSeatStatus());

        // Create a panel for title and car plate combo box
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(titleLabel, BorderLayout.NORTH);
        northPanel.add(carPlateComboBox, BorderLayout.SOUTH);

        // Create seat panel
        seatPanel = new JPanel(new GridLayout(ROWS, COLS, 10, 10)); // Add horizontal and vertical gaps
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                JButton seatButton = new JButton();
                seatButton.setMargin(new Insets(0, 0, 0, 0)); // Remove button margin
                seatButton.addActionListener(new SeatClickListener(i, j));
                seats[i][j] = seatButton;
                seatPanel.add(seatButton);
            }
        }

        // Create a panel with GridBagLayout to center seatPanel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20); // Add padding around the seatPanel
        centerPanel.add(seatPanel, gbc);

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

        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        // Initialize the seat status display
        if (carInfoList.size() > 0) {
            carPlateComboBox.setSelectedIndex(0);
            updateSeatStatus();
        }
    }

    private void loadCarInfo() {
        try (Scanner s = new Scanner(new File("C:/Users/elysa/Desktop/MMU/sem 5/JAVA/project-git/java_project/build/classes/schdule.txt"))) {
            while (s.hasNextLine()) {
                String carPlate = s.nextLine().trim();
                if (carPlate.isEmpty()) continue; // Skip blank lines
                String date = s.nextLine().trim();
                String from = s.nextLine().trim();
                String to = s.nextLine().trim();
                String price = s.nextLine().trim();
                String status = s.nextLine().trim();

                boolean[][] seatStatus = new boolean[6][3];
                boolean validSeatData = true;

                for (int i = 0; i < 6; i++) {
                    if (!s.hasNextLine()) {
                        validSeatData = false;
                        break;
                    }
                    String[] line = s.nextLine().trim().split(" ");
                    if (line.length != 3) {
                        validSeatData = false;
                        break;
                    }
                    for (int j = 0; j < 3; j++) {
                        seatStatus[i][j] = line[j].equals("1");
                    }
                }

                if (validSeatData) {
                    carInfoList.add(new CarInfo(carPlate, date, from, to, price, status, seatStatus));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateSeatStatus() {
        String selectedCarPlate = (String) carPlateComboBox.getSelectedItem();
        for (CarInfo carInfo : carInfoList) {
            if (carInfo.carPlate.equals(selectedCarPlate)) {
                selectedCarInfo = carInfo;
                seatStatus = carInfo.seatStatus;
                break;
            }
        }

        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                seats[i][j].setIcon(seatStatus[i][j] ? seatSelect : av);
            }
        }
    }

    private void setSelectedSeatStatus(boolean isSelected) {
        if (selectedRow != -1 && selectedCol != -1) {
            seatStatus[selectedRow][selectedCol] = isSelected;
            seats[selectedRow][selectedCol].setIcon(isSelected ? seatSelect : av);
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
            // Update the selected seat coordinates
            selectedRow = row;
            selectedCol = col;
            seats[row][col].setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
        }
    }

    private void saveSeats() {
        File originalFile = new File("C:/Users/elysa/Desktop/MMU/sem 5/JAVA/project-git/java_project/build/classes/schdule.txt");
        File tempFile = new File("C:/Users/elysa/Desktop/MMU/sem 5/JAVA/project-git/java_project/build/classes/schdule_temp.txt");

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

                for (int i = 0; i < ROWS; i++) {
                    for (int j = 0; j < COLS; j++) {
                        bw.write((carInfo.seatStatus[i][j] ? "1" : "0") + (j < COLS - 1 ? " " : ""));
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