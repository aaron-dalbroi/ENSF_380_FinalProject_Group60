package edu.ucalgary.oop;

import java.time.format.TextStyle;
/*
 * Represents a task in the database
 * is connected to an animal through their ID
 * tells when to start and how long to run the task
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
    public Animal getAnimal() {
        return ANIMAL;
    }
    public String getTask() {
        return TASK;
    }
    public int getStartTime() {
        return START_TIME;
    }
    public int getMaxWindow() {
        return MAX_WINDOW;
    }
    public int getAssignedTime() {
        return assignedTime;
    }
    public int getDuration() {
        return DURATION;
    }
    public int getTaskID() {
        return this.TASK_ID;
    }

    // Setter
    public void setAssignedTime(int assignedTme) {
        this.assignedTime = assignedTme;
    }
}
