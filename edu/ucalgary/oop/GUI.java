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
            Boolean error = true;
            //input = 0 if "ok" is pressed
            int input;
            if(error){
                input = JOptionPane.showConfirmDialog(null, createShedule(), "Error", JOptionPane.DEFAULT_OPTION);
            }else{
                input = JOptionPane.showConfirmDialog(null, createShedule(), "Good", JOptionPane.DEFAULT_OPTION);
            }
        }

    private String createShedule() {
        return "error has occured";
    }
}