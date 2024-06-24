import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class user_view_booking extends JFrame {

    private JTable table;
    private DefaultTableModel tableModel;
    private List<String[]> bookingData;
    private ArrayList<String> check_row, check_col;
    private String fileName = "src/user_booking.txt";
    private String current_id;
    public user_view_booking() {
        current_id = user_login.current_id;
        setTitle("View Booking");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        String[] columnNames = {"Boarding Time", "From", "To", "Bus_Plate"};
        tableModel = new DefaultTableModel(columnNames, 0);
        table = new JTable(tableModel);
        bookingData = new ArrayList<>();
        loadDataFromFile(fileName);
        add(new JScrollPane(table), BorderLayout.CENTER);
        
        JPanel p2 = new JPanel();
        JButton Back = new JButton("Back");
        Back.addActionListener((e) -> {
                 user_menu um = new user_menu();
                 um.setVisible(true);
                 dispose();
                });
        p2.add(Back);
        add(p2, BorderLayout.SOUTH);
    }

    private void loadDataFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String email = line;
                String boardingTime = reader.readLine();
                String from = reader.readLine();
                String to = reader.readLine();
                String bus_plate = reader.readLine();

                int c = Integer.parseInt(reader.readLine());
                check_col = new ArrayList<>();
                check_row = new ArrayList<>();
                for (int i = 0; i < c; i++) {
                    line = reader.readLine();
                    String[] parts = line.split(" ");
                    check_row.add(parts[0]);
                    check_col.add(parts[1]);
                }
                current_id = "jiaming@g.com1";
                 if (email.equals(current_id)) {
                    String[] booking = {boardingTime, from, to, bus_plate};
                    bookingData.add(booking);
                }
            }
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
