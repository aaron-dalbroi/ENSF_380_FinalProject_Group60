package edu.ucalgary.oop;

import java.util.ArrayList;


/**
 * Represents an hour in the database
 *
 * tasks - The list of tasks associated with that hour
 * time - Numerical value of the hour (12am = 0, 1am = 1, 1pm = 13, etc...)
 * timeAvailable - As number of tasks is added to hour, this will decrease
 *
 */
public class Hour {


    private ArrayList<Task> tasks = new ArrayList<>();
    private final int TIME;
    private int timeAvailable = 60;


    // Constructor that takes an int parameter and initializes this.time with it.
    public Hour(int time){
        if (time < 0 || time > 23) {
            throw new IllegalArgumentException("Invalid time");
        }
        this.TIME = time;
    }

    /**
     * getTime
     * @return The number associated with a given hour
     *
     */
    public int getTime() {
        return this.TIME;
    }

    /**
     * getTimeAvailable
     * @return The number of minutes left to be assigned in an hour
     *
     */
    public int getTimeAvailable() {
        return this.timeAvailable;
    }

    /**
     * addTaskToHour
     *
     * @param task - Entry object added to array of tasks within the hour.
     *  appends Entry to list of tasks
     *
     */
    public void addTaskToHour(Task task){
        this.tasks.add(task);
    }

    /**
     * getTasks
     * @return an array of tasks associated with an hour
     *
     */
    public ArrayList<Task> getTasks(){
        return this.tasks;
    }

    /**
     * subtractTimeAvailable
     *
     * @param time - Duration of the task being added
     *
     * subtracts the duration of a task added from the time available
     *
     */
    public void subtractTimeAvailable(int time){
        this.timeAvailable = this.timeAvailable - time;
    }

}

