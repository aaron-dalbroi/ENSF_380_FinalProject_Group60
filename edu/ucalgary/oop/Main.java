package edu.ucalgary.oop;

import java.sql.SQLException;
import java.awt.*;
import javax.swing.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        //Setup GUI frames
        EventQueue.invokeLater(() -> {
            JFrame frame = new JFrame("Schedule Creator");
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
}
