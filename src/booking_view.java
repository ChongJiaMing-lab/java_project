import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class booking_view extends JFrame {
    private JTable table;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;

    public booking_view() {
        setTitle("View User Booking");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Create table model with column names
        String[] columnNames = {"User Email", "Bus Date and Time", "From", "Destination", "Bus Plate", "Seat Row", "Seat Column"};
        tableModel = new DefaultTableModel(columnNames, 0);

        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Load data from file
        loadBookings("src/user_booking.txt");

        // Create a back button and add it to the bottom of the panel
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            admin_menu adminMenuWindow = new admin_menu();
            adminMenuWindow.setVisible(true);
            dispose();
        });

        JPanel southPanel = new JPanel();
        southPanel.add(backButton);
        add(southPanel, BorderLayout.SOUTH);
    }

    private void loadBookings(String filePath) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String userEmail = line;
                String busDateTime = reader.readLine();
                String from = reader.readLine();
                String destination = reader.readLine();
                String busPlate = reader.readLine();
                String seat = reader.readLine();

                // Check if all required lines are present
                if (userEmail != null && busDateTime != null && from != null &&
                    destination != null && busPlate != null && seat != null) {

                    // Check if seat information is properly formatted
                    String[] seatParts = seat.split(" ");
                    if (seatParts.length == 2) {
                        String seatRow = seatParts[0];
                        String seatColumn = seatParts[1];

                        // Add a row to the table model
                        tableModel.addRow(new Object[]{userEmail, busDateTime, from, destination, busPlate, seatRow, seatColumn});
                    } else {
                        System.err.println("Invalid seat format: " + seat);
                    }
                } else {
                    System.err.println("Missing booking information in file.");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new booking_view().setVisible(true));
    }
}