import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class View_Customer_information extends JFrame{
    private JTable cs_table;
    private DefaultTableModel model;
     public static void main(String[] args) {
          SwingUtilities.invokeLater(() -> new View_Customer_information().setVisible(true));
    } 
     
    public View_Customer_information(){
        
         
            setTitle("Main Menu");
            setSize(800, 600);
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         
            
            JPanel toppanel = new JPanel(new BorderLayout());
            toppanel.setBackground(Color.WHITE);
            
            
            JLabel title = new JLabel ("Customer Information");
            title.setFont(new Font("Arial", Font.BOLD, 35));
            
            JPanel titlePanel = new JPanel();
            titlePanel.setBackground(Color.WHITE);
            titlePanel.add(title);
            toppanel.add(titlePanel, BorderLayout.NORTH);
            add(toppanel, BorderLayout.NORTH);
            
             cs_table = new JTable();
            JScrollPane tableScrollPane = new JScrollPane(cs_table);
            add(tableScrollPane, BorderLayout.CENTER);

        
            loadUserDataFromFile("src/user.txt");
            
            JButton gobackbutton = new JButton("Go back");
            JPanel controlPanel = new JPanel(new FlowLayout());
            gobackbutton.addActionListener(e -> {

            admin_menu menu = new admin_menu();   
            menu.setVisible(true);  
            dispose();
        });
            controlPanel.add(gobackbutton);
            add(controlPanel, BorderLayout.SOUTH);
}
    
    private void loadUserDataFromFile(String f) {
        model = new DefaultTableModel();
        model.addColumn("Name");
        model.addColumn("Phone Number");
        model.addColumn("Email");
        

        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) {
                String name = line ;
                String phone = br.readLine();
                String email = br.readLine();
                String password = br.readLine();
                String[] data = {name, phone, email};
                model.addRow(data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        cs_table.setModel(model);
    }
}
