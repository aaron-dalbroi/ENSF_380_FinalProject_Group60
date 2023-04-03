
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
    private Hour[] finalSchedule = new Hour[24];
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

        this.ANIMALS = database.pullAnimals();
        // use animal list to generate feeding and cleaning tasks and add them to entries
    }

    private ArrayList<Entry> deleteRepeatedFeedTask(ArrayList<Entry> listCheck, ArrayList<Entry> listDelete){
        //Created a private helper function to keep things short and simple

        //int doubleFeedingID = -1;   //giving it an arbitrary default value of -1
        ArrayList<Integer> orphanIDArray = new ArrayList<>();

        for(int i = 0; i < listCheck.size(); i++){
            // What I am doing is looking through the medicalEntries array in order to find the ID of the orphaned animals
            if (listCheck.get(i).getTask().contains("feeding") && !orphanIDArray.contains(listCheck.get(i).getAnimalID())){
                orphanIDArray.add(listCheck.get(i).getAnimalID());
            }
        }
//        for(Entry entry: listDelete ){
//            System.out.println(entry.getAnimalID());
//        }
        // Now, I will go into the feedingEntries array and delete the entry with the matching ID
        if(!orphanIDArray.isEmpty()){
            for(int i = 0; i < listDelete.size(); i++) {
                for (int j = 0; j < orphanIDArray.size(); j++) {
                    if (listDelete.get(i).getAnimalID() == orphanIDArray.get(j)) {
                        listDelete.remove(i);
                    }
                }
            }
        }
//        for(Entry entry: listDelete ){
//            System.out.println(entry.getAnimalID());
//        }
        return listDelete;
    }

    public ArrayList<Entry> getEntries() {
        return this.ENTRIES;
    }

    public void generateSchedule() {
        //This method generates the schedule

        //The for loops will be used to add entries to the hours depending on their priority

        //This loop is for tasks of maxWindow <= 2 (will only be medical tasks)
        for (Entry entry : this.ENTRIES) {
            //Here we check if the entry Max window is less than 3
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
                        // Check if we have 15 minutes available
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
    private void checkTimeAvailable(Entry entry, int startTime){
            //Will iterate over N number of hours to attempt to add the entry, where N is entries max window
            for(int i = 0; i < entry.getMaxWindow();i++) {
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

    static public void main(String args[]) throws SQLException {
        Schedule schedule = new Schedule();
        ArrayList<Entry> entries = schedule.getEntries();
        schedule.generateSchedule();
        //to be used for the GUI and message for if volunteer is needed
        boolean isVolunteerNeeded = false;

        System.out.printf("%-10s%-50s%-15s%-15s%n", "Time", "Task", "Time spent", "Time Available");
        for (Hour hour : schedule.finalSchedule) {
            boolean tempIsVolunteerNeeded = false;
            String timeStr = (hour.getTime() < 13) ? (hour.getTime() + " am") : ((hour.getTime() - 12) + " pm");
            int timeSpent = 0;
            int timeAvailable = 60;
            for (int i = 0; i < hour.getTasks().size(); i++) {
                timeSpent += hour.getTasks().get(i).getDuration();
                timeAvailable -= hour.getTasks().get(i).getDuration();
                //Sets the volunteer needed to true if time available is less than 0
                if (timeAvailable < 0) {
                    isVolunteerNeeded = true;
                    tempIsVolunteerNeeded = true;
                    timeAvailable = 0;
                }
                //prints all the statements
                String name = hour.getTasks().get(i).getName();
                String task = hour.getTasks().get(i).getTask();
                String animalType = hour.getTasks().get(i).getAnimalType();
                if (i == 0) {
                    System.out.printf("%-10s%-50s%-15d%-15d%n", timeStr, task + " for " + name + " (" + animalType + ")", timeSpent, timeAvailable);
                } else {
                    System.out.printf("%-10s%-50s%-15d%-15d%n", "", task + " for " + name + " (" + animalType + ")", timeSpent, timeAvailable);
                }

            }
            if (tempIsVolunteerNeeded) {
                System.out.println("*Backup volunteer needed");
            }
            System.out.print("\n");

        }

        if (isVolunteerNeeded) {
            EventQueue.invokeLater(() -> {
                JFrame frame = new JFrame("Example Wildlife Rescue");
                frame.setSize(400, 200);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

                JPanel buttonsPanel = new JPanel();
                buttonsPanel.setLayout(new BoxLayout(buttonsPanel, BoxLayout.Y_AXIS)); // specify the BoxLayout manager
                JButton myButton = new JButton("Confirm Volunteer Available");

                myButton.addActionListener(buttonListener -> {
                    System.out.println("Volunteer confirmed");
                    frame.dispose();
                });
                buttonsPanel.add(Box.createVerticalGlue()); // add a glue component to center vertically
                buttonsPanel.add(myButton);
                buttonsPanel.add(Box.createVerticalGlue()); // add another glue component to center vertically
                // Set the alignment of the button to center horizontally
                myButton.setAlignmentX(Component.CENTER_ALIGNMENT);

                frame.getContentPane().add(BorderLayout.CENTER, buttonsPanel);
                frame.setVisible(true);

            });
        }
    }
}

