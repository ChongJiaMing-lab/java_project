import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class admin_editseat extends JFrame {
    private static final int ROWS = 10;
    private static final int COLS = 3;
    private JButton[][] seats = new JButton[ROWS][COLS];
    private boolean[][] seatStatus = new boolean[ROWS][COLS]; // True if seat is selected

    private ImageIcon seatSelect, av, unav;

    // Variables to keep track of the selected seat
    private int selectedRow = -1;
    private int selectedCol = -1;

    public admin_editseat() {
        setTitle("Admin - Edit Seats");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

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

        // Load seat status from file
        loadSeats();

        JPanel seatPanel = new JPanel(new GridLayout(ROWS, COLS, 10, 10)); // Add horizontal and vertical gaps
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                JButton seatButton = new JButton();
                seatButton.setIcon(seatStatus[i][j] ? seatSelect : av);
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

        availableButton.addActionListener(e -> setSelectedSeatStatus(false));
        unavailableButton.addActionListener(e -> setSelectedSeatStatus(true));

        controlPanel.add(availableButton);
        controlPanel.add(unavailableButton);

        add(centerPanel, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);
    }

    private void loadSeats() {
        // Load seat data from seat.txt
        try (Scanner s = new Scanner(new File("seat.txt"))) {
            for (int i = 0; s.hasNextLine() && i < ROWS; i++) {
                String[] line = s.nextLine().trim().split(",");
                for (int j = 0; j < line.length && j < COLS; j++) {
                    seatStatus[i][j] = line[j].trim().equals("1");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setSelectedSeatStatus(boolean isSelected) {
        if (selectedRow != -1 && selectedCol != -1) {
            seatStatus[selectedRow][selectedCol] = isSelected;
            seats[selectedRow][selectedCol].setIcon(isSelected ? seatSelect : av);
            saveSeats();
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
        // Save seat data to seat.txt
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("seat.txt"))) {
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    bw.write((seatStatus[i][j] ? "1" : "0") + (j < COLS - 1 ? "," : ""));
                }
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            admin_editseat frame = new admin_editseat();
            frame.setVisible(true);
        });
    }
}
