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


    /* SqLConnection Constructor
     * Calls createConnection method in order to establish link between program and database
     * Throws SQL exception if connection fails
     *
     */

    public SqlConnection() throws SQLException{
        createConnection();
    }

    /* SqLConnection Constructor
     * Calls createConnection method in order to establish link between program and database
     * Throws SQL exception if connection fails
     *
     */
    public void createConnection() throws SQLException{

        try{
            //the connection info here will need to be changed depending on the user
            dbConnect = DriverManager.getConnection("jdbc:mysql://localhost/ewr", "root", "AbXy219!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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

    public ArrayList<Entry> pullTreatmentEntries(){

        ArrayList<Entry> listOfEntries = new ArrayList<>();
        try {
            Statement myStmt = dbConnect.createStatement();
            myStmt.executeQuery("USE EWR");
            results = myStmt.executeQuery("SELECT a.AnimalNickname,a.animalID, t.Description, t.Duration, t.MaxWindow, tr.StartHour\n" +
                    "FROM TREATMENTS tr\n" +
                    "JOIN ANIMALS a ON tr.AnimalID = a.AnimalID\n" +
                    "JOIN TASKS t ON tr.TaskID = t.TaskID;");

            while(results.next()){
                String task = results.getString("Description");
                int startTime = results.getInt("StartHour");
                int maxWindow = results.getInt("MaxWindow");
                int duration = results.getInt("Duration");
                int animalID = results.getInt("animalID");

                Entry newEntry = new Entry(task,startTime,maxWindow,duration,animalID);
                listOfEntries.add(newEntry);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return listOfEntries;
    }
    public ArrayList<Entry> pullCleaningEntries(){
        ArrayList<Entry> listOfEntries = new ArrayList<>();
        try {
            Statement myStmt = dbConnect.createStatement();
            myStmt.executeQuery("USE EWR");
            results = myStmt.executeQuery("SELECT * FROM ANIMALS");

            while(results.next()){
                String task = "Cleaning Cage";
                int startTime = -1;                 //READ: Because cleaning tasks dont have a set start time or max
                int maxWindow = -1;               //      max window, I've set them to -1. We might want to change this later

                int animalID = results.getInt("animalID");

                String animalEnum = results.getString("AnimalSpecies").toUpperCase();
                int duration = AnimalSpecies.valueOf(animalEnum).getCleaningTime();



                Entry newEntry = new Entry(task,startTime,maxWindow,duration,animalID);
                listOfEntries.add(newEntry);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return listOfEntries;
    }

    public ArrayList<Entry> pullFeedingEntries(){
        ArrayList<Entry> listOfEntries = new ArrayList<>();
        try {
            Statement myStmt = dbConnect.createStatement();
            myStmt.executeQuery("USE EWR");
            results = myStmt.executeQuery("SELECT * FROM ANIMALS");

            while(results.next()){
                String task = "Feeding";
                int startTime = -1;                 //READ: Because cleaning tasks dont have a set start time or max
                int maxWindow = -1;                 //      max window, I've set them to -1. We might want to change this later
                int animalID = results.getInt("animalID");

                String animalEnum = results.getString("AnimalSpecies").toUpperCase();
                int duration = AnimalSpecies.valueOf(animalEnum).getFeedingDuration();

                Entry newEntry = new Entry(task,startTime,maxWindow,duration,animalID);
                listOfEntries.add(newEntry);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return listOfEntries;
    }

}