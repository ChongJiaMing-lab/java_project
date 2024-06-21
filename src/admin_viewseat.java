import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class admin_viewseat extends JFrame 
{
    private java.util.List<CarInfo> carInfoList = new ArrayList<>();
    private JPanel carPanel;

    public admin_viewseat() 
    {
        setTitle("Admin - View Seats");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load car information from file
        loadCarInfo();

        // Create title and instruction labels
        JLabel titleLabel = new JLabel("Admin - View Seats", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Serif", Font.BOLD, 24));
        JLabel instructionLabel = new JLabel("Please choose car plate number to view the seat status", SwingConstants.CENTER);
        instructionLabel.setFont(new Font("Serif", Font.PLAIN, 18));

        // Create a panel for title and instruction
        JPanel northPanel = new JPanel(new BorderLayout());
        northPanel.add(titleLabel, BorderLayout.NORTH);
        northPanel.add(instructionLabel, BorderLayout.CENTER);

        // Create car buttons
        carPanel = new JPanel(new GridLayout(0, 1, 10, 10)); // Dynamic rows, 1 column
        for (CarInfo carInfo : carInfoList) 
        {
            JButton carButton = new JButton(carInfo.carPlate);
            carButton.addActionListener(new CarButtonClickListener(carInfo));
            carPanel.add(carButton);
        }

        // Center panel
        JPanel centerPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 20, 20, 20);
        centerPanel.add(carPanel, gbc);

        // Add panels to the frame
        add(northPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    private void loadCarInfo() 
    {
        try (Scanner s = new Scanner(new File("C:/Users/elysa/Desktop/MMU/sem 5/JAVA/project-git/java_project/build/classes/schdule.txt"))) {
            while (s.hasNextLine()) 
            {
                String carPlate = s.nextLine().trim();
                if (carPlate.isEmpty()) continue; // Skip blank lines
                System.out.println("Reading car plate: " + carPlate);

                if (!s.hasNextLine()) break;
                String date = s.nextLine().trim();
                System.out.println("Reading date: " + date);

                if (!s.hasNextLine()) break;
                String from = s.nextLine().trim();
                System.out.println("Reading from: " + from);

                if (!s.hasNextLine()) break;
                String to = s.nextLine().trim();
                System.out.println("Reading to: " + to);

                if (!s.hasNextLine()) break;
                String price = s.nextLine().trim();
                System.out.println("Reading price: " + price);

                if (!s.hasNextLine()) break;
                String status = s.nextLine().trim();
                System.out.println("Reading status: " + status);

                boolean[][] seatStatus = new boolean[6][3];
                boolean validSeatData = true;

                for (int i = 0; i < 6; i++) 
                {
                    if (!s.hasNextLine()) 
                    {
                        validSeatData = false;
                        break;
                    }
                    String[] line = s.nextLine().trim().split(" ");
                    System.out.println("Reading seat status line " + (i + 1) + ": " + Arrays.toString(line));
                    if (line.length != 3) 
                    {
                        System.err.println("Error: Expected 3 columns in seat status, but got " + line.length + " in line " + (i + 1));
                        validSeatData = false;
                        break;
                    }
                    for (int j = 0; j < 3; j++) 
                    {
                        seatStatus[i][j] = line[j].equals("1");
                    }
                }

                if (validSeatData) 
                {
                    carInfoList.add(new CarInfo(carPlate, date, from, to, price, status, seatStatus));
                } 
                else 
                {
                    System.err.println("Error: Invalid seat data for car plate " + carPlate);
                }
            }
        } 
        catch (IOException e) 
        {
            e.printStackTrace();
        }
    }

    private class CarButtonClickListener implements ActionListener 
    {
        private CarInfo carInfo;

        public CarButtonClickListener(CarInfo carInfo) 
        {
            this.carInfo = carInfo;
        }

        public void actionPerformed(ActionEvent e) 
        {
            // Display seat status for the selected car along with date and time
            StringBuilder seatStatusString = new StringBuilder();
            for (int i = 0; i < carInfo.seatStatus.length; i++) 
            {
                for (int j = 0; j < carInfo.seatStatus[i].length; j++) 
                {
                    seatStatusString.append(carInfo.seatStatus[i][j] ? "1 " : "0 ");
                }
                seatStatusString.append("\n");
            }

            // Create a JTextArea to hold the message content
            JTextArea textArea = new JTextArea("Car Plate: " + carInfo.carPlate + 
                                               "\nDate and Time: " + carInfo.date + 
                                               "\nSeats Status:\n" + seatStatusString.toString());
            textArea.setEditable(false);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);

            // Wrap the JTextArea in a JScrollPane
            JScrollPane scrollPane = new JScrollPane(textArea);
            scrollPane.setPreferredSize(new Dimension(400, 300)); // Set the preferred size of the scroll pane

            // Display the message in a JOptionPane
            JOptionPane.showMessageDialog(null, scrollPane, "Car Seat Status", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private class CarInfo 
    {
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

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(() -> 
        {
            admin_viewseat frame = new admin_viewseat();
            frame.setVisible(true);
        });
    }
}