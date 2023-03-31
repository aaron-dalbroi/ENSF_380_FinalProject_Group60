package edu.ucalgary.oop;
import java.sql.*;
import java.util.ArrayList;

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
        // Second, we will get the FEEDING ENTRIES
        ArrayList<Entry> feedingEntries = this.database.pullCleaningEntries();
        // Third, we will get the CLEAING ENTRIES
        ArrayList<Entry> cleaningEntries = this.database.pullCleaningEntries();




        this.ANIMALS = database.pullAnimals();
        // use animal list to generate feeding and cleaning tasks and add them to entries
    }




    static public void main(String args[]) throws SQLException{

        for(Entry entry: bruh){
            System.out.println(entry.getDuration());
        }
    }

}