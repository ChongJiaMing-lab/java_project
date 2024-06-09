package test;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class frame extends JFrame {

    JLabel timeLabel = new JLabel("002:00:00");
    JCheckBox check2 = new JCheckBox("Tick this?");
    JButton button2 = new JButton("checkbox button");
    JComboBox<String> color = new JComboBox<>();
    JButton button_color = new JButton("color button");
    
    public frame(String title) {
        super(title);

        JTextField tf = new JTextField(20);
        JPanel root = new JPanel();
        JCheckBox check = new JCheckBox("Accept?");
        //display the content
//        this.setContentPane(root);

        JButton button = new JButton("Show Time");
        root.add(button);
        //time label
        root.add(timeLabel);
        root.add(tf);
        root.add(new JLabel("syntax testing"));
        root.add(check);

        //inialiste the combo
        root.add(check2);
        root.add(button2);
        check2.setSelected(false);
        button2.setEnabled(false);
        
        //combo box
        root.add(color);
        color.addItem("red");
        color.addItem("blue");
        color.addItem("yellow");
        root.add(button_color);
        //listen to button
        button.addActionListener((e) -> {
            showTime();
        });
        
        //unable button unless tick the checkbox
        check2.addActionListener((e) -> {
            if(check2.isSelected())
                button2.setEnabled(true);
            else
                button2.setEnabled(false);
        });
        
         button_color.addActionListener((e) -> {
            get_color();
        });
    }

    public void showTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String timeStr = sdf.format(new Date());
        System.out.println(timeStr);
        timeLabel.setText(timeStr);
    }
    
    public void get_color()
    {
        //get index
        int index = color.getSelectedIndex();
        System.out.println("Selected color index = "+index);
        //get value
        int count = color.getItemCount();
        String value = color.getItemAt(index);
        System.out.println("Total of options = "+count);
        System.out.println("Selected color name = "+value);
    }
}
