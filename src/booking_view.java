import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class booking_view extends JPanel 
{
    private JTable table;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;

    public booking_view() 
    {
        setLayout(new BorderLayout());

        // Create table model with column names
        String[] columnNames = {"User Name", "Seat Number", "Ticket Date"};
        tableModel = new DefaultTableModel(columnNames, 0);

        table = new JTable(tableModel);
        scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        // Load data from file
        loadBookings("C:\\Users\\elysa\\Desktop\\MMU\\sem 5\\JAVA\\project-git\\java_project\\src\\user_booking.txt");
    }

    private void loadBookings(String filePath) 
    {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) 
        {
            String line;
            while ((line = reader.readLine()) != null) 
            {
                String[] parts = line.split(" ");
                if (parts.length == 3) 
                {
                    String userName = parts[0];
                    String seatNumber = parts[1];
                    String ticketDate = parts[2];
                    tableModel.addRow(new Object[]{userName, seatNumber, ticketDate});
                }
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) 
    {
        JFrame frame = new JFrame("View Booking");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);
        frame.add(new booking_view());
        frame.setVisible(true);
    }
}
