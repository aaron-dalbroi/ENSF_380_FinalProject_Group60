package edu.ucalgary.oop;

import java.time.format.TextStyle;
/**
 * Represents a task in the database
 * is connected to an animal through their ID
 * tells when to start and how long to run the task
 *
 * Animal ANIMAL - The animal associated with a task
 * String TASK - A description of the task
 * int TASK_ID - The ID associated with a task
 * int START_TIME - The first eligible hour for a task
 * int MAX_WINDOW - The number of subsequent eligible hours for a task
 * int assignedTime - The hour a task has been assigned to
 * int DURATION - The duration  of a task in minutes
 *
 *
 */
public class Task {
    private final Animal ANIMAL;
    private final String TASK;
    private final int TASK_ID;
    private final int START_TIME;
    private final int MAX_WINDOW;
    private int assignedTime = -1;       // -1 until a time is assigned
    private final int DURATION;

    public Task(Animal animal, String task, int taskID, int startTime, int maxWindow, int duration){
        this.ANIMAL = animal;
        this.TASK = task;
        this.TASK_ID = taskID;
        this.START_TIME = startTime;
        this.MAX_WINDOW = maxWindow;
        this.DURATION = duration;
    }

    // Getters
    /**
     * getAnimal
     * @return - The animal asscociated with a task
     */
    public Animal getAnimal() {
        return ANIMAL;
    }
    /**
     * getTask
     * @return - A description of the task
     */
    public String getTask() {
        return TASK;
    }
    /**
     * getStartTime
     * @return - The first valid hour for a task to be placed in
     */
    public int getStartTime() {
        return START_TIME;
    }
    /**
     * getMaxWindow
     * @return - The number of available hours a task can be placed in, which follow the initial starting hour
     */
    public int getMaxWindow() {
        return MAX_WINDOW;
    }
    /**
     * getMaxWindow
     * @return - The hour (0-24) a task has been assigned.
     */
    public int getAssignedTime() {
        return assignedTime;
    }
    /**
     * getMaxWindow
     * @return - The duration of a task in minutes.
     */
    public int getDuration() {
        return DURATION;
    }
    /**
     * getMaxWindow
     * @return - The ID of a task
     */
    public int getTaskID() {
        return this.TASK_ID;
    }

    // Setter
    /**
     * setAssignedTime
     * @param assignedTme - The hour(0-24) the task is to be assigned
     *
     * Assigns a task to the hour specified
     */
    public void setAssignedTime(int assignedTme) {
        this.assignedTime = assignedTme;
    }
}
