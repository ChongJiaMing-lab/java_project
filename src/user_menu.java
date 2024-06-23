
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class user_menu extends JFrame implements ActionListener {

    public user_menu() {
        String current_name = user_login.current_name;
        JLabel lb1 = new JLabel("Welcome, " + current_name);
        setTitle("User Menu");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(600, 400);
        setLocationRelativeTo(null);

        JPanel p1 = new JPanel();
        lb1.setFont(new Font("Arial", Font.BOLD, 24));
        p1.add(lb1);
        JButton view_bookingButton = new JButton("Search for a bus");
        JButton view_scheduleButton = new JButton("View Booking");
        JButton logoutButton = new JButton("Logout");

        view_bookingButton.addActionListener(this);
        view_scheduleButton.addActionListener(this);
        logoutButton.addActionListener(this);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        buttonPanel.add(view_bookingButton);
        buttonPanel.add(view_scheduleButton);
        buttonPanel.add(logoutButton);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.add(p1, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        add(mainPanel);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String actionCommand = e.getActionCommand();
        switch (actionCommand) {
            case "Search for a bus":
                view_schedule();
                break;
            case "View Booking":
                view_booking();
                break;
            case "Logout":
                user_logout();
                break;
            default:
                break;
        }
    }

    private void view_schedule() {
        search s = new search();
        s.setVisible(true);
        dispose();
    }

    private void view_booking() {
        
    }

    private void user_logout() {
        user_login u2 = new user_login();
        u2.setVisible(true);
        dispose();
    }

    public static void main(String[] args) {
        user_menu u = new user_menu();
    }
}
