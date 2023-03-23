import java.sql.*;
import java.util.ArrayList;

/**
 * Represents the schedule which we are building
 *
 * entries - A list of all Entry class objects that need to be ordered within the schedule
 * animals - A list of all the animals found in the database
 * treatmentList - A list of all treatment descriptions
 * database - SqLConnection object that handles querying the database
 *
 *
 */
public class Schedule {

    private ArrayList<Entry> entries;
    private ArrayList<Animal> animals;
    private SqLConnection database;

    public Schedule() throws SQLException{

        // establish database connection with an SqLConnection object
        this.database = new SqLConnection();
        // call database to read in entries and make Entry objects (Treatment Tasks Only)
        this.entries = database.pullTreatmentEntries();
        //call database to read in animals, create Animal Objects
        this.animals = database.pullAnimals();
        // use animal list to generate feeding and cleaning tasks and add them to entries
        ArrayList<Entry> cleaningEntries = database.pullCleaningEntries();
        ArrayList<Entry> feedingEntries = database.pullFeedingEntries();

    }




    static public void main(String args[]) throws SQLException{


    SqLConnection database = new SqLConnection();
    database.createConnection();

    ArrayList<Entry> bruh = database.pullCleaningEntries();

    for(Entry entry: bruh){
        System.out.println(entry.getDuration());
    }
}

}
