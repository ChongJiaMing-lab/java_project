import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

public class admin_viewseat extends JFrame {
    private java.util.List<String> carPlateList = new ArrayList<>();
    private java.util.List<CarInfo> carInfoList = new ArrayList<>();
    private JPanel carPanel;
    private JPanel infoPanel;
    private JPanel seatPanel;

    private ImageIcon availableIcon;
    private ImageIcon unavailableIcon;

    public admin_viewseat() {
        setTitle("Admin - View Seats");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Load car plate numbers from file
        loadCarPlates();

        // Load and resize icons
        availableIcon = resizeIcon(new ImageIcon("src/image/seat_av.png"), 20, 20);
        unavailableIcon = resizeIcon(new ImageIcon("src/image/seat_unav.png"), 20, 20);

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
        for (String carPlate : carPlateList) {
            JButton carButton = new JButton(carPlate);
            carButton.addActionListener(new CarButtonClickListener(carPlate));
            carPanel.add(carButton);
        }

        // Create info and seat panels
        infoPanel = new JPanel(new BorderLayout());
        seatPanel = new JPanel(); // Grid layout will be set dynamically

        // Create a split pane to hold carPanel and infoPanel
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, new JScrollPane(carPanel), createRightPanel());
        splitPane.setDividerLocation(200);

        // Create a back button
        JButton backButton = new JButton("Back");
        backButton.addActionListener(e -> {
            admin_menu menu = new admin_menu();
            menu.setVisible(true);
            dispose();
        });

        // Create a panel for the back button and add it to the bottom
        JPanel southPanel = new JPanel();
        southPanel.add(backButton);

        // Add panels to the frame
        add(northPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER);
        add(southPanel, BorderLayout.SOUTH);
    }

    private JPanel createRightPanel() {
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.add(infoPanel, BorderLayout.NORTH);
        rightPanel.add(new JScrollPane(seatPanel), BorderLayout.CENTER);
        return rightPanel;
    }

    private void loadCarPlates() {
        try (Scanner s = new Scanner(new File("src/schedule_bus/bus_plate.txt"))) {
            while (s.hasNextLine()) {
                String carPlate = s.nextLine().trim();
                if (!carPlate.isEmpty()) {
                    carPlateList.add(carPlate);
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

            java.util.List<boolean[]> seatStatusList = new ArrayList<>();
            boolean validSeatData = true;
            int maxColumns = 0;

            while (s.hasNextLine()) {
                String line = s.nextLine().trim();
                if (line.isEmpty()) continue; // Skip empty lines

                String[] columns = line.split(" ");
                System.out.println("Reading seat status line: " + Arrays.toString(columns));
                if (columns.length < 3 || columns.length > 4) {
                    System.err.println("Error: Expected 3 or 4 columns in seat status, but got " + columns.length);
                    validSeatData = false;
                    break;
                }
                maxColumns = Math.max(maxColumns, columns.length);
                boolean[] seatRow = new boolean[columns.length];
                for (int j = 0; j < columns.length; j++) {
                    seatRow[j] = columns[j].equals("1");
                }
                seatStatusList.add(seatRow);
            }

            if (validSeatData) {
                boolean[][] seatStatus = seatStatusList.toArray(new boolean[0][]);
                carInfoList.add(new CarInfo(plate, date, from, to, price, status, seatStatus, maxColumns));
            } else {
                System.err.println("Error: Invalid seat data for car plate " + carPlate);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private class CarButtonClickListener implements ActionListener {
        private String carPlate;

        public CarButtonClickListener(String carPlate) {
            this.carPlate = carPlate;
        }

        public void actionPerformed(ActionEvent e) {
            // Load car information for the selected car plate
            loadCarInfo(carPlate);

            // Display car info and seat status for the selected car
            infoPanel.removeAll();
            seatPanel.removeAll();

            for (CarInfo carInfo : carInfoList) {
                // Display car info
                JTextArea infoArea = new JTextArea();
                infoArea.setEditable(false);
                infoArea.setText("Car Plate: " + carInfo.carPlate + "\n" +
                                 "Date: " + carInfo.date + "\n" +
                                 "From: " + carInfo.from + "\n" +
                                 "To: " + carInfo.to + "\n" +
                                 "Price: " + carInfo.price + "\n" +
                                 "Status: " + carInfo.status);
                infoPanel.add(infoArea, BorderLayout.CENTER);

                // Set the seat panel layout based on the maximum number of columns
                seatPanel.setLayout(new GridLayout(0, carInfo.maxColumns, 5, 5));

                // Display seat status
                for (int i = 0; i < carInfo.seatStatus.length; i++) {
                    for (int j = 0; j < carInfo.seatStatus[i].length; j++) {
                        JLabel seatLabel = new JLabel(carInfo.seatStatus[i][j] ? unavailableIcon : availableIcon);
                        seatPanel.add(seatLabel);
                    }
                }
            }

            // Create a panel for the legend
            JPanel legendPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
            JLabel availableLabel = new JLabel("Available ", availableIcon, JLabel.CENTER);
            JLabel unavailableLabel = new JLabel("Unavailable ", unavailableIcon, JLabel.CENTER);
            legendPanel.add(availableLabel);
            legendPanel.add(unavailableLabel);

            // Add the legend panel to the infoPanel
            infoPanel.add(legendPanel, BorderLayout.SOUTH);

            infoPanel.revalidate();
            infoPanel.repaint();
            seatPanel.revalidate();
            seatPanel.repaint();
        }
    }

    private ImageIcon resizeIcon(ImageIcon icon, int width, int height) {
        Image img = icon.getImage();
        Image resizedImg = img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImg);
    }

    private class CarInfo {
        String carPlate;
        String date;
        String from;
        String to;
        String price;
        String status;
        boolean[][] seatStatus;
        int maxColumns;

        public CarInfo(String carPlate, String date, String from, String to, String price, String status, boolean[][] seatStatus, int maxColumns) {
            this.carPlate = carPlate;
            this.date = date;
            this.from = from;
            this.to = to;
            this.price = price;
            this.status = status;
            this.seatStatus = seatStatus;
            this.maxColumns = maxColumns;
        }
    }

       public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            admin_viewseat frame = new admin_viewseat();
            frame.setVisible(true);
        });
    }
}