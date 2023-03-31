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

        // The following code will be used to create all the tasks to be completed in the next 24 hours
        // First, we will get all the MEDICAL TREATMENT ENTRIES
        ArrayList<Entry> medicalEntries = this.database.pullTreatmentEntries();

        // Second, we will get the FEEDING ENTRIES.
        ArrayList<Entry> feedingEntries = this.database.pullFeedingEntries();
        //NOTE, if there is an orphan animal, it's feeding entry must be deleted since it is already in medical tasks
        feedingEntries = deleteRepeateFeedTask(medicalEntries, feedingEntries);

        // Third, we will get the CLEAING ENTRIES
        ArrayList<Entry> cleaningEntries = this.database.pullCleaningEntries();

        // Now I will add all those entries into the ENTRIES array;
        ArrayList<Entry> temp = new ArrayList<>();
        temp.addAll(medicalEntries);
        temp.addAll(feedingEntries);
        temp.addAll(cleaningEntries);
        ENTRIES = temp;

        for(Entry entry: ENTRIES){
            System.out.println("ID = " + entry.getAnimalID() + ", task: " + entry.getTask());
        }

        this.ANIMALS = database.pullAnimals();
        // use animal list to generate feeding and cleaning tasks and add them to entries
    }

    private ArrayList<Entry> deleteRepeateFeedTask(ArrayList<Entry> listCheck, ArrayList<Entry> listDelete){
        //Created a private helper function to keep things short and simple

        int doubleFeedingID = -1;   //giving it an arbitrary default value of -1
        for(int i = 0; i < listCheck.size(); i++){
            // What I am doing is looking through the medicalEntries array in order to find the ID of the orphaned animals
            if (listCheck.get(i).getTask().contains("feeding")){
                doubleFeedingID = listCheck.get(i).getAnimalID();
                break;
            }
        }
        // Now, I will go into the feedingEntries array and delete the entry with the matching ID
        if(doubleFeedingID != -1){
            for(int i = 0; i < listDelete.size(); i++) {
                if (listDelete.get(i).getAnimalID() == doubleFeedingID) {
                    listDelete.remove(i);
                    return listDelete;
                }
            }
        }
        return listDelete;
    }




    static public void main(String args[]) throws SQLException{
        Schedule schedule = new Schedule();


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
