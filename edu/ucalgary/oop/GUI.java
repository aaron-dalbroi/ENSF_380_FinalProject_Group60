package edu.ucalgary.oop;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI implements ActionListener{
    
    public static void main(String args[]) {

        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("My First Frame");
            frame.setSize(400, 400);
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            
            JPanel buttonsPanel = new JPanel();
            JButton myButton = new JButton("Create Schedule");

            GUI buttonListener = new GUI();
            myButton.addActionListener(buttonListener);
            buttonsPanel.add(myButton);
            frame.getContentPane().add(BorderLayout.NORTH, buttonsPanel);
            frame.setVisible(true);
        });
    }
        
    public void actionPerformed(ActionEvent event){
            String result = createShedule();
            String output;
            if(result == "Error has occured"){
                String[] options = {"option 1", "option 2", "option 3"};
                output = (String) JOptionPane.showInputDialog(null, "Error has occured", "Error", JOptionPane.ERROR_MESSAGE, null, options, options[0]);
                if(output != null){
                    String[] newOptions = {"option 4", "option 5", "option 6"};
                    output = (String) JOptionPane.showInputDialog(null, "Phase 2", "Phase 2", JOptionPane.ERROR_MESSAGE, null, newOptions, options[0]);
                }
            }else{
                JOptionPane.showConfirmDialog(null, createShedule(), "Good", JOptionPane.DEFAULT_OPTION);
            }
        }

    private String createShedule() {
        return "Error has occured";
    }
}