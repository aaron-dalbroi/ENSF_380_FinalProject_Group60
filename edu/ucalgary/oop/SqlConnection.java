package edu.ucalgary.oop;
import java.sql.*;
import java.util.ArrayList;
/**
 * Handles SQL connection with the program
 * * Connection dbConnect - SQL connection object for initializing connection with database
 * ResultSet results - The results of queries will be in this object
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
     *  attempts to connect to database, if successful assigns it to the dbConnect member.
     *  THE LOGIN INFO IN THIS METHOD MUST MATCH THE LOGIN INFO FOR YOUR DATABASE TO CONNECT.
     */
    public void createConnection(){

        try{
            //the connection info here will need to be changed depending on the user
            dbConnect = DriverManager.getConnection("jdbc:mysql://localhost/ewr", "root", "AbXy219!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    /**
     * closeConnection
     * Disconnects program from the database
     */
    public void closeConnection() throws SQLException{
        dbConnect.close();
        results.close();
    }
    /**
     * readAnimalsFromDataBase
     *
     * @return - An array list of animals from the database
     *
     */
    public ArrayList<Animal> readAnimalsFromDataBase() throws SQLException{
        ArrayList<Animal> listOfAnimals = new ArrayList<>();

        Statement myStmt = dbConnect.createStatement();
        myStmt.executeQuery("USE EWR");
        results = myStmt.executeQuery("SELECT * FROM ANIMALS");

        while(results.next()){
            int id = results.getInt("AnimalID");
            String name = results.getString("AnimalNickName");
            String species = results.getString("AnimalSpecies");
            if(species.equals("coyote")){
                listOfAnimals.add(new Coyote(name,id));
            }
            else if(species.equals("fox")){
                listOfAnimals.add(new Fox(name,id));
            }
            else if(species.equals("beaver")){
                listOfAnimals.add(new Beaver(name,id));
            }
            else if(species.equals("porcupine")){
                listOfAnimals.add(new Porcupine(name,id));
            }
            else if(species.equals("raccoon")){
                listOfAnimals.add(new Raccoon(name,id));
            }
        }

        return listOfAnimals;
    }

    /**
     *pullTreatmentEntries
     *
     * @return An array of Entries for every medical task in the database
     *
     */
    public ArrayList<ArrayList<String>> pullTreatmentTasks(){

        ArrayList<ArrayList<String>> arrTaskInfo = new ArrayList<>();
        try {
            Statement myStmt = dbConnect.createStatement();
            myStmt.executeQuery("USE EWR");
            results = myStmt.executeQuery("SELECT a.animalID, t.Description, t.Duration, t.MaxWindow, tr.StartHour, tr.TaskID\n" +
                    "FROM TREATMENTS tr\n" +
                    "JOIN ANIMALS a ON tr.AnimalID = a.AnimalID\n" +
                    "JOIN TASKS t ON tr.TaskID = t.TaskID;");

            while(results.next()){
                ArrayList<String> taskInfo = new ArrayList<>();

                Integer animalID = results.getInt("animalID");
                taskInfo.add(animalID.toString());
                String task = results.getString("Description");
                taskInfo.add(task);
                Integer startTime = results.getInt("StartHour");
                taskInfo.add(startTime.toString());
                Integer maxWindow = results.getInt("MaxWindow");
                taskInfo.add(maxWindow.toString());
                Integer duration = results.getInt("Duration");
                taskInfo.add(duration.toString());
                Integer taskId = results.getInt("TaskID");
                taskInfo.add(taskId.toString());


                arrTaskInfo.add(taskInfo);
            }
        }
        catch(Exception e){
            System.out.println(e);
        }
        return arrTaskInfo;
    }

    public void updateTreatment(int animalID, int taskID, int newStartHour) {
        try {
            PreparedStatement myStmt = dbConnect.prepareStatement("UPDATE TREATMENTS SET StartHour = ? WHERE AnimalID = ? AND TaskID = ?");
            myStmt.setInt(1, newStartHour);
            myStmt.setInt(2, animalID);
            myStmt.setInt(3, taskID);
            myStmt.executeUpdate();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

}