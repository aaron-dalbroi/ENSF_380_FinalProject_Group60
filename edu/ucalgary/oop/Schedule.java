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

    private Hour[] finalSchedule = new Hour[24];


    /**
     * Schedule Constructor
     *
     * Calls the database to generate an array of treatment, feeding, and cleaning tasks
     * Then calls generateSchedule to place the tasks in an appropriate hour
     *
     */
    public Schedule() {
        // Create all the hours in the day
        for(int i = 0; i < 24; i++){
            this.finalSchedule[i] = new Hour(i);
        }
    }

    public static ArrayList<Task> generateMedicalTasks(ArrayList<Animal> animals, SqlConnection database){
        //Get all medical entries
        ArrayList<ArrayList<String>> medicalEntries = database.pullTreatmentTasks();
        // Next the Task array will be created for each one
        ArrayList<Task> medicalTasks = new ArrayList<>();
        for(ArrayList<String> stringTask: medicalEntries){
            //first find the animal that corresponds to the task
            Animal taskAnimal = null;
            for(Animal animal: animals){
                int animalID = Integer.parseInt(stringTask.get(0));
                if (animalID == animal.getAnimalID()){
                    taskAnimal = animal;
                    break;
                }
            }
            if (taskAnimal == null){
                throw new IllegalArgumentException("Animal for task does not exist");
            }

            String task = stringTask.get(1);
            int startTime = Integer.parseInt(stringTask.get(2));
            int maxWindow = Integer.parseInt(stringTask.get(3));
            int duration = Integer.parseInt(stringTask.get(4));
            int taskID = Integer.parseInt(stringTask.get(5));


            Task medicalTask = new Task(taskAnimal, task, taskID, startTime, maxWindow, duration);
            medicalTasks.add(medicalTask);
        }
        return medicalTasks;
    }

    public static ArrayList<Task> generateTasks(ArrayList<Animal> animals, SqlConnection database){
        ArrayList<Task> tasks = new ArrayList<>();

        // The following code will be used to create all the tasks to be completed in the next 24 hours
        // First, we will get all the MEDICAL TREATMENT ENTRIES
        ArrayList<Task> medicalTasks = generateMedicalTasks(animals, database);

        tasks.addAll(medicalTasks);


        // Second, we will get the FEEDING ENTRIES.
        ArrayList<Task> feedingTasks = new ArrayList<>();
        for (Animal animal: animals){
            String task = animal.getAnimalSpecies() + " Feeding";
            int startTime = animal.getFeedingStartTime();
            int maxWindow = 3;
            int duration = animal.getFeedingDuration();
            int taskID = -1;        //-1 for feeding cause it does not matter

            Task feedingTask = new Task(animal, task, taskID, startTime, maxWindow, duration);
            feedingTasks.add(feedingTask);
        }

        //NOTE, if there is an orphan animal, it's feeding entry must be deleted since it is already in medical tasks
        deleteRepeatedFeedTask(medicalTasks, feedingTasks);
        tasks.addAll(feedingTasks);

        // Third, we will get the CLEANING ENTRIES
        for (Animal animal: animals){
            String task = "Cage cleaning";
            int startTime = 0;
            int maxWindow = 24;
            int duration = animal.getCleaningTime();
            int taskID = -2;        //-2 for cleaning cause it does not matter
            Task cleaningTask = new Task(animal, task, taskID, startTime, maxWindow, duration);
            tasks.add(cleaningTask);
        }

        return tasks;
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

    private static void deleteRepeatedFeedTask(ArrayList<Task> listCheck, ArrayList<Task> listDelete){

        ArrayList<Integer> orphanIDArray = new ArrayList<>();

        for(int i = 0; i < listCheck.size(); i++){

            // Look through the medicalEntries array in order to find the ID of the orphaned animals
            if (listCheck.get(i).getTask().contains("feeding") && !orphanIDArray.contains(listCheck.get(i).getAnimal().getAnimalID())){
                orphanIDArray.add(listCheck.get(i).getAnimal().getAnimalID());
            }
        }

        // Go into the feedingEntries array and delete the entry with the matching ID
        if(!orphanIDArray.isEmpty()){
            for(int i = 0; i < listDelete.size(); i++) {
                for (int j = 0; j < orphanIDArray.size(); j++) {
                    if (listDelete.get(i).getAnimal().getAnimalID() == orphanIDArray.get(j)) {
                        listDelete.remove(i);
                    }
                }
            }
        }
    }

    public Hour[] getFinalSchedule(){
        return this.finalSchedule;
    }

    /**
     * generateSchedule
     *
     * @param tasks - The list of all tasks that need to be added into the schedule
     *
     * Method looks at all entries and places them in an appropriate Hour object depending on the
     * parameters of the entry(Start time, Max Window, etc...) If a volunteer is needed for an hour,
     * this method will also detect that and let the user know.
     *
     */
    public void generateSchedule(ArrayList<Task> tasks) {
        //This method generates the schedule

        //The for loops will be used to add entries to the hours depending on their priority

        //This loop is for tasks of maxWindow <= 2 (will only be medical tasks)
        for (Task task : tasks) {
            //Here we check if the entry Max window is <= 2
            if (task.getMaxWindow() <= 2) {

                int startTime = task.getStartTime();

                //Will iterate over N number of hours to attempt to add the entry, where N is entries max window
                //The following code will be used to check all the available hours to put the entry into and does it, else, throws exception
                checkTimeAvailable(task, startTime);
            }
        }

        //This loop is for feeding tasks
        for (Task task : tasks) {
            if (task.getTask().contains("Feeding")) {

                //Will iterate over N number of hours to attempt to add the entry, where N is entries max window
                int startTime = task.getStartTime();
                for (int i = 0; i < task.getMaxWindow(); i++) {


                    boolean prepTime = false;

                    //This loop checks every task in the hour we are currently in.
                    // If it contains the exact same feeding task, we know it is the same animal
                    // That means the prep time has already been accounted for in the loop.
                    for (Task taskInHour : this.finalSchedule[startTime].getTasks()) {

                        if (taskInHour.getTask().equals(task.getTask())) {
                            prepTime = true;
                            break;
                        }
                    }

                    if (prepTime && finalSchedule[i].getTimeAvailable() >= task.getDuration()) {
                        checkTimeAvailable(task, task.getStartTime());
                        break;
                    } else{
                        // We need preptime + feedtime minutes to put in the hour
                        // Check if we have that many minutes available
                        // If we don't we move on to the next hour

                        int totalTime = task.getDuration() + task.getAnimal().getFeedingPrepTime();
                        if(this.finalSchedule[i].getTimeAvailable() >= totalTime){
                            checkTimeAvailable(task, startTime);
                            this.finalSchedule[i].subtractTimeAvailable(task.getAnimal().getFeedingPrepTime());
                            break;
                        }
                    }
                }
            }
        }

        //This loop is for medical tasks of maxWindow > 2
        for (Task task : tasks) {
            if (task.getMaxWindow() > 2 && !task.getTask().contains("Feeding")) {

                int startTime = task.getStartTime();
                //Will iterate over N number of hours to attempt to add the entry, where N is entries max window
                checkTimeAvailable(task,startTime);
            }
        }

        //This loop is for cleanings
        for (Task task : tasks) {
            if (task.getTask().contains("Cage cleaning")) {
                int startTime = task.getStartTime();
                checkTimeAvailable(task, startTime);
            }
        }
    }
    /**
     * getHoursWithVolunteers
     *
     * @return - An ArrayList of all the hours in the schedule that require volunteers
     *
     */
    public ArrayList<Hour> getHoursWithVolunteers(){
        ArrayList<Hour> volunteerHours = new ArrayList<>();
        for(Hour hour: this.finalSchedule){
            if(hour.getTimeAvailable() < 0){
                volunteerHours.add(hour);
            }
        }
        return volunteerHours;
    }

    /**
     * checkTimeAvailable
     * @param task - The Task object we are looking to add into the schedule
     * @param startTime - The start time of the object
     *
     * Iteratively looks at each valid hour for an object (StartTime + maxWindow) and places the object
     * in the first available hour. When an entry is successfully placed, the time available in the Hour is updated.
     *
     */
    private void checkTimeAvailable(Task task, int startTime){
            //Will iterate over N number of hours to attempt to add the task, where N is entries max window
            for(int i = 0; i < task.getMaxWindow();i++) {
                if(startTime + i > 23){
                    break;
                }
                //This checks if the hour's time available is greater than or equal to the tasks length
                Hour hour = this.finalSchedule[startTime+ i];
                if((hour.getTimeAvailable() - task.getDuration()) >= 0) {
                    //adds the entry into the hour
                    hour.addTaskToHour(task);
                    //subtracts the duration of that entry from the time available
                    hour.subtractTimeAvailable(task.getDuration());
                    break;
                }
                //This checks if we are in the last hour available and there is no space.
                //NOTE, this will have to call another volunteer later
                if (i == task.getMaxWindow() - 1){
                    hour.addTaskToHour(task);
                    hour.subtractTimeAvailable(task.getDuration());
                }
            }
    }

}
