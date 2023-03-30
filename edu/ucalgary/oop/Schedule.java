package edu.ucalgary.oop;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Represents the schedule which we are building
 * entries - A list of all Entry class objects that need to be ordered within the schedule
 * animals - A list of all the animals found in the database
 * treatmentList - A list of all treatment descriptions
 * database - SqLConnection object that handles querying the database
 *
 */
public class Schedule {

    private final ArrayList<Entry> ENTRIES;
    private final ArrayList<Animal> ANIMALS;
    private SqlConnection database;

    public Schedule() throws SQLException{

        // establish database connection with an SqLConnection object
        this.database = new SqlConnection();
        // call database to read in entries and make Entry objects (Treatment Tasks Only)
        this.ENTRIES = database.pullTreatmentEntries();
        //call database to read in animals, create Animal Objects
        this.ANIMALS = database.pullAnimals();
        // use animal list to generate feeding and cleaning tasks and add them to entries
        ArrayList<Entry> cleaningEntries = database.pullCleaningEntries();
        ArrayList<Entry> feedingEntries = database.pullFeedingEntries();

    }




    static public void main(String args[]) throws SQLException{


        SqlConnection database = new SqlConnection();
        database.createConnection();

        ArrayList<Entry> bruh = database.pullCleaningEntries();

        for(Entry entry: bruh){
            System.out.println(entry.getDuration());
        }

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
}
