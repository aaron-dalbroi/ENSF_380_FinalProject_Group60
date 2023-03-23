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

    private ArrayList<Entry> entries = new ArrayList<>();
    private ArrayList<Animal> animals = new ArrayList<>();
    private ArrayList<String> treatmentList = new ArrayList<>();
    private SqLConnection database = new SqLConnection();

    public Schedule(){

        // establish database connection with SqLConnection object

        // call database to read in entries and make Entry objects

        //call database to read in animals, create Animal Objects

        // use animal list to generate feeding and cleaning tasks and add them to entries

        // call database to read in treatments. They won't have there associated ID's, but because they are read in order
        // we can just index correctly to pull the appropriate description from the array


    }




    static public void main(String args[]){


    SqLConnection database = new SqLConnection();
    database.createConnection();
    database.print();
}

}
