
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

    public user_view_booking() {
        setTitle("Display Booking Data");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        String[] columnNames = {"Email", "Boarding Time", "From", "To", "Password", "Row", "Column"};

        tableModel = new DefaultTableModel(columnNames, 0);

        table = new JTable(tableModel);

        bookingData = new ArrayList<>();

        loadDataFromFile("src/user_booking.txt");

        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton viewSeatButton = new JButton("View Seat");
//        viewSeatButton.addActionListener(e -> view_seat());
        add(viewSeatButton, BorderLayout.SOUTH);
    }

    private void loadDataFromFile(String fileName) {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            String line2;
            int c=0;
            while ((line = reader.readLine()) != null) {
                if (line.contains("@")) {
                    String email = line;
                    String boardingTime = reader.readLine();
                    String from = reader.readLine();
                    String to = reader.readLine();
                    String password = reader.readLine();
                    List<String[]> seatData = new ArrayList<>();
                    while ((line2 = reader.readLine())!=null && !line.contains("@")) {
//                        String row_col = reader.readLine();
                        String[] parts = line2.split(" ");
                        seatData.add(parts);
                        c += 1;
                    }
                    System.out.println("Number of seats for " + email + ": " + c);
                }
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
