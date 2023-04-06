package edu.ucalgary.oop;

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.*;

/**
 * Represents the schedule
 * entries - A list of all Entry class objects that need to be ordered within the schedule
 * animals - A list of all the animals found in the database
 * database - SqLConnection object that handles querying the database
 * finalSchedule - An array of 24 hours representing a day, with each hour holding an array of tasks
 *
 */
public class Schedule {

    private final ArrayList<Entry> ENTRIES;
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
    public Schedule() throws SQLException{

        // establish database connection with an SqLConnection object
        this.database = new SqlConnection();
        for(int i = 0; i < 24; i++){
            finalSchedule[i] = new Hour(i);
        }

        // The following code will be used to create all the tasks to be completed in the next 24 hours
        // First, we will get all the MEDICAL TREATMENT ENTRIES
        ArrayList<Entry> medicalEntries = this.database.pullTreatmentEntries();

        // Second, we will get the FEEDING ENTRIES.
        ArrayList<Entry> feedingEntries = this.database.pullFeedingEntries();
        //NOTE, if there is an orphan animal, it's feeding entry must be deleted since it is already in medical tasks
        feedingEntries = deleteRepeatedFeedTask(medicalEntries, feedingEntries);

        // Third, we will get the CLEANING ENTRIES
        ArrayList<Entry> cleaningEntries = this.database.pullCleaningEntries();

        // Now I will add all those entries into the ENTRIES array;
        ArrayList<Entry> temp = new ArrayList<>();
        temp.addAll(medicalEntries);
        temp.addAll(feedingEntries);
        temp.addAll(cleaningEntries);
        this.ENTRIES = temp;

//        for(Entry entry: this.ENTRIES){
//            System.out.println("ID = " + entry.getAnimalID() + ", task: " + entry.getTask());
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
    private ArrayList<Entry> deleteRepeatedFeedTask(ArrayList<Entry> listCheck, ArrayList<Entry> listDelete){

        ArrayList<Integer> orphanIDArray = new ArrayList<>();

        for(int i = 0; i < listCheck.size(); i++){

            // Look through the medicalEntries array in order to find the ID of the orphaned animals
            if (listCheck.get(i).getTask().contains("feeding") && !orphanIDArray.contains(listCheck.get(i).getAnimalID())){
                orphanIDArray.add(listCheck.get(i).getAnimalID());
            }
        }

        // Go into the feedingEntries array and delete the entry with the matching ID
        if(!orphanIDArray.isEmpty()){
            for(int i = 0; i < listDelete.size(); i++) {
                for (int j = 0; j < orphanIDArray.size(); j++) {
                    if (listDelete.get(i).getAnimalID() == orphanIDArray.get(j)) {
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
    public ArrayList<Entry> getEntries() {
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
     * parameters of the entry(Start time, Max Window, etc...) If a volunteer is needed for an hour,
     * this method will also detect that and let the user know.
     *
     */
    public void generateSchedule() {
        //This method generates the schedule

        //The for loops will be used to add entries to the hours depending on their priority

        //This loop is for tasks of maxWindow <= 2 (will only be medical tasks)
        for (Entry entry : this.ENTRIES) {
            //Here we check if the entry Max window is <= 2
            if (entry.getMaxWindow() <= 2) {

                int startTime = entry.getStartTime();

                //Will iterate over N number of hours to attempt to add the entry, where N is entries max window
                //The following code will be used to check all the available hours to put the entry into and does it, else, throws exception
                checkTimeAvailable(entry, startTime);
            }
        }


        //This loop is for feeding tasks
        for (Entry entry : this.ENTRIES) {
            if (entry.getTask().contains("Feeding")) {

                //Will iterate over N number of hours to attempt to add the entry, where N is entries max window
                int startTime = entry.getStartTime();
                for (int i = 0; i < entry.getMaxWindow(); i++) {


                    boolean prepTime = false;

                    //This loop checks every entry in the hour we are currently in.
                    // If it contains the exact same feeding task, we know it is the same animal
                    // That means the prep time has already been accounted for in the loop.
                    for (Entry entryInHour : this.finalSchedule[startTime].getTasks()) {

                        if (entryInHour.getTask().equals(entry.getTask())) {
                            prepTime = true;
                        }
                    }

                    if (prepTime == true && finalSchedule[i].getTimeAvailable() >= entry.getDuration()) {
                        checkTimeAvailable(entry, entry.getStartTime());
                        break;
                    } else{
                        // We need preptime + feedtime minutes to put in the hour
                        // Check if we have that many minutes available
                        // If we don't we move on to the next hour

                        int totalTime = entry.getDuration() + AnimalSpecies.valueOf(entry.getAnimalType().toUpperCase()).getFeedingPrepTime();
                        if(this.finalSchedule[i].getTimeAvailable() >= totalTime){
                            checkTimeAvailable(entry, startTime);
                            this.finalSchedule[i].subtractTimeAvailable(AnimalSpecies.valueOf(entry.getAnimalType().toUpperCase()).getFeedingPrepTime());
                            break;
                        }

                    }

                }
            }
        }




        //This loop is for medical tasks of maxWindow > 2
        for (Entry entry : this.ENTRIES) {
            if (entry.getMaxWindow() > 2 && !entry.getTask().contains("Feeding")) {

                int startTime = entry.getStartTime();
                if(entry.getStartTime() == 23){
                    System.out.println("here: " + entry.getTask());
                }
                //Will iterate over N number of hours to attempt to add the entry, where N is entries max window
                checkTimeAvailable(entry,startTime);
            }
        }




        //This loop is for cleanings
        for (Entry entry : this.ENTRIES) {
            if (entry.getTask().contains("Cage cleaning")) {
                int startTime = entry.getStartTime();
                checkTimeAvailable(entry, startTime);
            }
        }
    }
    /**
     * checkTimeAvailable
     * @param entry - The Entry object we are looking to add into the schedule
     * @param startTime - The start time of the object
     *
     * Iteratively looks at each valid hour for an object (StartTime + maxWindow) and places the object
     * in the first available hour. When an entry is successfully placed, the time available in the Hour is updated.
     *
     */
    private void checkTimeAvailable(Entry entry, int startTime){
            //Will iterate over N number of hours to attempt to add the entry, where N is entries max window
            System.out.println("where here");
            for(int i = 0; i < entry.getMaxWindow();i++) {
                if(startTime + i > 23){
                    break;
                }
                //This checks if the hour's time available is greater than or equal to the tasks length
                if((this.finalSchedule[startTime + i].getTimeAvailable() - entry.getDuration()) >= 0) {
                    //adds the entry into the hour
                    this.finalSchedule[startTime+i].addTaskToHour(entry);
                    //subtracts the duration of that entry from the time available
                    this.finalSchedule[startTime+i].subtractTimeAvailable(entry.getDuration());
                    break;
                }
                //This checks if we are in the last hour available and there is no space, we will throw an exception
                //NOTE, this will have to call another volunteer later
                if (i == entry.getMaxWindow()-1){
                    this.finalSchedule[startTime+i].addTaskToHour(entry);
                    this.finalSchedule[startTime+i].subtractTimeAvailable(entry.getDuration());
                }
            }

    }

    static public void main(String args[]) throws SQLException{
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
