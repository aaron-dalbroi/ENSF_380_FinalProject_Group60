package edu.ucalgary.oop;
import java.sql.*;
import java.util.ArrayList;
/**
 * Handles SQL connection with the program
 *
 * Connection dbConnect - SQL connection object for initializing connection with database
 * ResultSet results - The results of queries will be in this object
 *
 *
 */

public class SqlConnection {

    private Connection dbConnect;
    private ResultSet results;


    /** SqLConnection Constructor
     *  Calls createConnection method in order to establish link between program and database
     *  Throws SQL exception if connection fails
     *
     */

    public SqlConnection() throws SQLException{
        createConnection();
    }

    /** createConnection
     *
     *  attempts to connect to database, if successful assigns it to the dbConnect member.
     *
     *  THE LOGIN INFO IN THIS METHOD MUST MATCH THE LOGIN INFO FOR YOUR DATABASE TO CONNECT.
     *
     */
    public void createConnection() throws SQLException{

        try{
            //the connection info here will need to be changed depending on the user
            dbConnect = DriverManager.getConnection("jdbc:mysql://localhost/ewr", "root", "Lulumybaby1.");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * closeConnection
     *
     * Disconnects program from the database
     *
     */
    public void closeConnection() throws SQLException{
        dbConnect.close();
        results.close();


    }


    public void print(){

        try {
            Statement myStmt = dbConnect.createStatement();
            myStmt.executeQuery("USE EWR");
            results = myStmt.executeQuery("SELECT * FROM ANIMALS");

            while(results.next()){
                String result = results.getString(0);
                System.out.println(result);

            }

        }
        catch(Exception e){
            System.out.println(e);
        }

    }

    /**
     * pullAnimals
     *
     * @return An array of all animals from the database
     *
     */
    public ArrayList<Animal> pullAnimals(){
        ArrayList<Animal> listOfAnimals = new ArrayList<>();

        try {
            Statement myStmt = dbConnect.createStatement();
            myStmt.executeQuery("USE EWR");
            results = myStmt.executeQuery("SELECT * FROM ANIMALS");

            while(results.next()){
                int animalID = results.getInt("AnimalID");
                String animalNickname = results.getString("AnimalNickname");
                String animalSpecies = results.getString("AnimalSpecies");

                Animal newAnimal = new Animal(animalID,animalNickname,animalSpecies);
                listOfAnimals.add(newAnimal);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }

        return listOfAnimals;
    }
    /**
     *pullTreatmentEntries
     *
     * @return An array of Entries for every medical task in the database
     *
     */
    public ArrayList<Entry> pullTreatmentEntries(){

        ArrayList<Entry> listOfEntries = new ArrayList<>();
        try {
            Statement myStmt = dbConnect.createStatement();
            myStmt.executeQuery("USE EWR");
            results = myStmt.executeQuery("SELECT a.AnimalNickname,a.animalID,a.AnimalSpecies, t.Description, t.Duration, t.MaxWindow, tr.StartHour\n" +
                    "FROM TREATMENTS tr\n" +
                    "JOIN ANIMALS a ON tr.AnimalID = a.AnimalID\n" +
                    "JOIN TASKS t ON tr.TaskID = t.TaskID;");

            while(results.next()){
                String animalType = results.getString("AnimalSpecies");
                String task = results.getString("Description");
                String animalName = results.getString("AnimalNickName");
                int startTime = results.getInt("StartHour");
                int maxWindow = results.getInt("MaxWindow");
                int duration = results.getInt("Duration");
                int animalID = results.getInt("animalID");


                Entry newEntry = new Entry(task,startTime,maxWindow,duration,animalID, animalType,animalName);
                listOfEntries.add(newEntry);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return listOfEntries;
    }

    /**
     * pullCleaningEntries
     *
     * Looks at every animal in the database and generates an appropriate cleaning Entry
     * @return An array of all cleaning tasks generated
     *
     */
    public ArrayList<Entry> pullCleaningEntries(){
        ArrayList<Entry> listOfEntries = new ArrayList<>();
        try {
            Statement myStmt = dbConnect.createStatement();
            myStmt.executeQuery("USE EWR");
            results = myStmt.executeQuery("SELECT * FROM ANIMALS");

            while(results.next()){
                String task = "Cage cleaning";
                String animalType = results.getString("AnimalSpecies");
                int startTime = 0;
                int maxWindow = 24;

                int animalID = results.getInt("animalID");
                String animalName = results.getString("AnimalNickname");
                String animalEnum = results.getString("AnimalSpecies").toUpperCase();
                int duration = AnimalSpecies.valueOf(animalEnum).getCleaningTime();



                Entry newEntry = new Entry(task,startTime,maxWindow,duration,animalID, animalType,animalName);
                listOfEntries.add(newEntry);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return listOfEntries;
    }
    /**
     * pullFeedingEntries
     *
     * Looks at every animal in the database and generates an appropriate feeding Entry
     * @return An array of all feeding tasks generated
     *
     */
    public ArrayList<Entry> pullFeedingEntries() throws IllegalArgumentException{
        ArrayList<Entry> listOfEntries = new ArrayList<>();
        try {
            Statement myStmt = dbConnect.createStatement();
            myStmt.executeQuery("USE EWR");
            results = myStmt.executeQuery("SELECT * FROM ANIMALS");

            while(results.next()){
                String task = results.getString("AnimalSpecies") + " Feeding";
                int animalID = results.getInt("animalID");
                String animalType = results.getString("AnimalSpecies");
                String animalName = results.getString("AnimalNickname");

                //getting the startTime, maxWindow and duration from the animal Enum
                String animalEnum = animalType.toUpperCase();
                // These if statements are used to get the value of startTime based on the chronotype
                String chronoType = AnimalSpecies.valueOf(animalEnum).getChronoType();
                int startTime;
                if (chronoType.equals("nocturnal")){
                    startTime = 0;
                } else if (chronoType.equals("crepuscular")) {
                    startTime = 19;
                } else if (chronoType.equals("diurnal")) {
                    startTime = 8;
                } else {
                    throw new IllegalArgumentException("Chronotype not found");
                }
                //The max feeding window for any chronotype is always 3 hours.
                int maxWindow = 3;
                int duration = AnimalSpecies.valueOf(animalEnum).getFeedingDuration();

                Entry newEntry = new Entry(task,startTime,maxWindow,duration,animalID, animalType,animalName);
                listOfEntries.add(newEntry);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return listOfEntries;
    }

}
