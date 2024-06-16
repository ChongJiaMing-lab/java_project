import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class admin_viewseat extends JFrame {
    private static final int ROWS = 10;
    private static final int COLS = 3;
    private JButton[][] seats = new JButton[ROWS][COLS];
    private boolean[][] seatStatus = new boolean[ROWS][COLS]; // True if seat is selected

    private ImageIcon seatSelect, av, unav;

    public admin_viewseat() {
        setTitle("Admin - View Seats");
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

        JPanel seatPanel = new JPanel(new GridLayout(ROWS, COLS, 10, 10)); 
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                JButton seatButton = new JButton();
                seatButton.setIcon(seatStatus[i][j] ? seatSelect : av);
                seatButton.setMargin(new Insets(0, 0, 0, 0)); 
                seatButton.addActionListener(new SeatClickListener(i, j));
                seats[i][j] = seatButton;
                seatPanel.add(seatButton);
            }
        }

        //to center
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        centerPanel.add(seatPanel, gbc);

        add(centerPanel, BorderLayout.CENTER);
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

    private class SeatClickListener implements ActionListener {
        private int row;
        private int col;

        public SeatClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public void actionPerformed(ActionEvent e) {
            // Display seat information
            JOptionPane.showMessageDialog(null, "Seat (" + row + "," + col + ") status: " +
                (seatStatus[row][col] ? "Selected" : "Available"));
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            admin_viewseat frame = new admin_viewseat();
            frame.setVisible(true);
        });
    }
}
