import javax.swing.*;
import java.awt.*;

public class admin_menu extends JFrame {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new admin_menu().setVisible(true));
    }

    public admin_menu() {
        setTitle("Admin Menu");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.WHITE);

        JLabel bigTitleLabel = new JLabel("Bus Ticketing System", JLabel.CENTER);
        bigTitleLabel.setFont(new Font("Arial", Font.BOLD, 30));

        ImageIcon icon = new ImageIcon("src/image/bus.jpg");
        Image img = icon.getImage();
        Image newImg = img.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        icon = new ImageIcon(newImg);
        JLabel imageLabel = new JLabel(icon);

        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.WHITE);
        titlePanel.add(imageLabel);
        titlePanel.add(bigTitleLabel);

        JLabel subTitleLabel = new JLabel("Admin Menu", JLabel.CENTER);
        subTitleLabel.setFont(new Font("Arial", Font.BOLD, 24));

        topPanel.add(titlePanel, BorderLayout.NORTH);
        topPanel.add(subTitleLabel, BorderLayout.CENTER);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(Color.LIGHT_GRAY);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        JButton button1 = new JButton("Add and view schedule");
        button1.addActionListener(e -> {
            add_schdule addScheduleWindow = new add_schdule();
            addScheduleWindow.setVisible(true);
            dispose();
        });

        JButton buttonViewSeat = new JButton("View Seat");
        buttonViewSeat.addActionListener(e -> {
            admin_viewseat viewSeatWindow = new admin_viewseat();
            viewSeatWindow.setVisible(true);
            dispose();
        });

        JButton buttonEditSeat = new JButton("Edit Seat");
        buttonEditSeat.addActionListener(e -> {
            admin_editseat editSeatWindow = new admin_editseat();
            editSeatWindow.setVisible(true);
            dispose();
        });

        JButton button3 = new JButton("View user information");
        button3.addActionListener(e -> {
            View_Customer_information vui = new View_Customer_information();
            vui.setVisible(true);
            dispose();
        });

        JButton button4 = new JButton("View user booking");

        button1.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonViewSeat.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonEditSeat.setAlignmentX(Component.CENTER_ALIGNMENT);
        button3.setAlignmentX(Component.CENTER_ALIGNMENT);
        button4.setAlignmentX(Component.CENTER_ALIGNMENT);

        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(button1);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(buttonViewSeat);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(buttonEditSeat);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(button3);
        mainPanel.add(Box.createVerticalStrut(20));
        mainPanel.add(button4);
        mainPanel.add(Box.createVerticalStrut(20));

        add(topPanel, BorderLayout.NORTH);
        add(mainPanel, BorderLayout.CENTER);
    }
}