import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class user_view_booking extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    public user_view_booking() {
        setTitle("Display Booking Data");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        String[] columnNames = {"Bus Plate", "Password","Boarding Time", "From", "To","Row", "Column"};

        tableModel = new DefaultTableModel(columnNames, 0);

        table = new JTable(tableModel);

        loadDataFromFile("src/user_booking.txt");

        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    private void loadDataFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            List<String[]> bookingData = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                if (line.contains("@")) {
                    String email = line;
                    String password = reader.readLine();
                    String from = reader.readLine();
                    String to = reader.readLine();
                    String plate = reader.readLine();
                    while ((line = reader.readLine()) != null && !line.contains("@")) {
                        String[] parts = line.split(" ");
                        if (parts.length == 2) {
                            bookingData.add(new String[]{email, password, from, to ,plate,parts[0], parts[1]});
                        }
                    }
                }
            }
            
            // Add rows to the table model
            for (String[] rowData : bookingData) {
                tableModel.addRow(rowData);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
           user_view_booking displayTable = new user_view_booking();
            displayTable.setVisible(true);
        });
    }
}
