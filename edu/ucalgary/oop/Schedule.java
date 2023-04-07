package edu.ucalgary.oop;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Represents the schedule
 * entries - A list of all Task class objects that need to be ordered within the schedule
 * animals - A list of all the animals found in the database
 * database - SqLConnection object that handles querying the database
 * finalSchedule - An array of 24 hours representing a day, with each hour holding an array of tasks
 *
 */
public class Schedule {

    private final ArrayList<Task> ENTRIES;
    //private final ArrayList<Animal> ANIMALS;
    private SqlConnection database;
    private Hour[] finalSchedule = new Hour[24];


    /**
     * Schedule Constructor
     *
     * Calls the database to generate an array of treatment, feeding, and cleaning tasks
     * Then calls generateSchedule to place the tasks in an appropriate hour
     *
     */
    public Schedule(SqlConnection database, ArrayList<Animal> listOfAnimals) throws SQLException{

        // establish database connection with an SqLConnection object
        this.database = database;
        for(int i = 0; i < 24; i++){
            finalSchedule[i] = new Hour(i);
        }

        // The following code will be used to create all the tasks to be completed in the next 24 hours
        // First, we will get all the MEDICAL TREATMENT ENTRIES
        ArrayList<Task> medicalEntries = this.database.pullTreatmentEntries(listOfAnimals);

        // Second, we will get the FEEDING ENTRIES.
        ArrayList<Task> feedingEntries = this.database.pullFeedingEntries(listOfAnimals);
        //NOTE, if there is an orphan animal, it's feeding Task must be deleted since it is already in medical tasks
        feedingEntries = deleteRepeatedFeedTask(medicalEntries, feedingEntries);

        // Third, we will get the CLEANING ENTRIES
        ArrayList<Task> cleaningEntries = this.database.pullCleaningEntries(listOfAnimals);

        // Now I will add all those entries into the ENTRIES array;
        ArrayList<Task> temp = new ArrayList<>();
        temp.addAll(medicalEntries);
        temp.addAll(feedingEntries);
        temp.addAll(cleaningEntries);
        this.ENTRIES = temp;

//        for(Task Task: this.ENTRIES){
//            System.out.println("ID = " + Task.getAnimalID() + ", task: " + Task.getTask());
//        }

        //this.ANIMALS = database.pullAnimals();
        // use animal list to generate feeding and cleaning tasks and add them to entries
    }
    /**
     * deleteRepeatedFeedTask
     * @param listCheck - list of medical entries, will contain all kit feeding tasks
     * @param listDelete - list of feeding entries
     *
     *  Method looks at all kit feeding entries in listCheck, and removes the corresponding feeding tasks
     *  in listDelete so that the feedings of kits are not added twice
     *
     */
    private ArrayList<Task> deleteRepeatedFeedTask(ArrayList<Task> listCheck, ArrayList<Task> listDelete){

        ArrayList<Integer> orphanIDArray = new ArrayList<>();

        for(int i = 0; i < listCheck.size(); i++){

            // Look through the medicalEntries array in order to find the ID of the orphaned animals
            if (listCheck.get(i).getTask().contains("feeding") && !orphanIDArray.contains(listCheck.get(i).getAnimal().getAnimalID())){
                orphanIDArray.add(listCheck.get(i).getAnimal().getAnimalID());
            }
        }

        // Go into the feedingEntries array and delete the Task with the matching ID
        if(!orphanIDArray.isEmpty()){
            for(int i = 0; i < listDelete.size(); i++) {
                for (int j = 0; j < orphanIDArray.size(); j++) {
                    if (listDelete.get(i).getAnimal().getAnimalID() == orphanIDArray.get(j)) {
                        listDelete.remove(i);
                    }
                }
            }
        }
        return listDelete;
    }

    /**
     * getEntries
     * @return The array of all entries in the schedule
     *
     */
    public ArrayList<Task> getEntries() {
        return this.ENTRIES;
    }

    public SqlConnection getDatabase(){
        return this.database;
    }

    public Hour[] getFinalSchedule(){
        return this.finalSchedule;
    }

    /**
     * generateSchedule
     *
     * Method looks at all entries and places them in an appropriate Hour object depending on the
     * parameters of the Task(Start time, Max Window, etc...) If a volunteer is needed for an hour,
     * this method will also detect that and let the user know.
     *
     */
    public void generateSchedule() {
        //This method generates the schedule

        //The for loops will be used to add entries to the hours depending on their priority

        //This loop is for tasks of maxWindow <= 2 (will only be medical tasks)
        for (Task task : this.ENTRIES) {
            //Here we check if the Task Max window is <= 2
            if (task.getMaxWindow() <= 2) {

                int startTime = task.getStartTime();

                //Will iterate over N number of hours to attempt to add the Task, where N is entries max window
                //The following code will be used to check all the available hours to put the Task into and does it, else, throws exception
                checkTimeAvailable(task, startTime);
            }
        }


        //This loop is for feeding tasks
        for (Task Task : this.ENTRIES) {
            if (Task.getTask().contains("Feeding")) {

                //Will iterate over N number of hours to attempt to add the Task, where N is entries max window
                int startTime = Task.getStartTime();
                for (int i = 0; i < Task.getMaxWindow(); i++) {


                    boolean prepTime = false;

                    //This loop checks every Task in the hour we are currently in.
                    // If it contains the exact same feeding task, we know it is the same animal
                    // That means the prep time has already been accounted for in the loop.
                    for (Task taskInHour : this.finalSchedule[startTime].getTasks()) {

                        if (taskInHour.getTask().equals(Task.getTask())) {
                            prepTime = true;
                        }
                    }

                    if (prepTime == true && finalSchedule[i].getTimeAvailable() >= Task.getDuration()) {
                        checkTimeAvailable(Task, Task.getStartTime());
                        break;
                    } else{
                        // We need preptime + feedtime minutes to put in the hour
                        // Check if we have that many minutes available
                        // If we don't we move on to the next hour

                        int totalTime = Task.getDuration() + AnimalSpecies.valueOf(Task.getAnimalType().toUpperCase()).getFeedingPrepTime();
                        if(this.finalSchedule[i].getTimeAvailable() >= totalTime){
                            checkTimeAvailable(Task, startTime);
                            this.finalSchedule[i].subtractTimeAvailable(AnimalSpecies.valueOf(Task.getAnimalType().toUpperCase()).getFeedingPrepTime());
                            break;
                        }

                    }

                }
            }
        }




        //This loop is for medical tasks of maxWindow > 2
        for (Task Task : this.ENTRIES) {
            if (Task.getMaxWindow() > 2 && !Task.getTask().contains("Feeding")) {

                int startTime = Task.getStartTime();
                if(Task.getStartTime() == 23){
                    System.out.println("here: " + Task.getTask());
                }
                //Will iterate over N number of hours to attempt to add the Task, where N is entries max window
                checkTimeAvailable(Task,startTime);
            }
        }




        //This loop is for cleanings
        for (Task Task : this.ENTRIES) {
            if (Task.getTask().contains("Cage cleaning")) {
                int startTime = Task.getStartTime();
                checkTimeAvailable(Task, startTime);
            }
        }
    }
    /**
     * checkTimeAvailable
     * @param Task - The Task object we are looking to add into the schedule
     * @param startTime - The start time of the object
     *
     * Iteratively looks at each valid hour for an object (StartTime + maxWindow) and places the object
     * in the first available hour. When an Task is successfully placed, the time available in the Hour is updated.
     *
     */
    private void checkTimeAvailable(Task Task, int startTime){
        //Will iterate over N number of hours to attempt to add the Task, where N is entries max window
        System.out.println("where here");
        for(int i = 0; i < Task.getMaxWindow();i++) {
            if(startTime + i > 23){
                break;
            }
            //This checks if the hour's time available is greater than or equal to the tasks length
            if((this.finalSchedule[startTime + i].getTimeAvailable() - Task.getDuration()) >= 0) {
                //adds the Task into the hour
                this.finalSchedule[startTime+i].addTaskToHour(Task);
                //subtracts the duration of that Task from the time available
                this.finalSchedule[startTime+i].subtractTimeAvailable(Task.getDuration());
                break;
            }
            //This checks if we are in the last hour available and there is no space, we will throw an exception
            //NOTE, this will have to call another volunteer later
            if (i == Task.getMaxWindow()-1){
                this.finalSchedule[startTime+i].addTaskToHour(Task);
                this.finalSchedule[startTime+i].subtractTimeAvailable(Task.getDuration());
            }
        }

    }

    static public void main(String args[]) throws SQLException{

        SqlConnection connect = new SqlConnection();
        ArrayList<Animal> listOfAnimals = connect.readAnimalsFromDataBase();

        Schedule newSchedule = new Schedule(connect,listOfAnimals);

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
