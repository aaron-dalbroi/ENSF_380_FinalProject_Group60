package edu.ucalgary.oop;
/**
 * Class Entry represents a task.
 * String task - A description of the task, which are stored in treatmentList of class Schedule, which are pulled from sql database
 * startTime - The requested start time from the database
 * maxWindow - The number of hours ahead of startTime the task can potentially be assigned
 * AssignedTime - The hour the program decides to place the task
 * length - The number of minutes a task takes
 * animalID - the ID of the animal associated with the task
 * Entries will have to be ordered in the Schedule object in such a way that minimizes wasted time.
 */
public class Entry {
    private final String TASK;
    private final int START_TIME;
    private final int MAX_WINDOW;
    private int assignedTime;
    private final int DURATION;
    private final int ANIMAL_ID;
    private final String ANIMAL_TYPE;
    private final String ANIMAL_NAME;

    public Entry(String task,int startTime,int maxWindow,int duration,int animalID, String animalType, String animalName){
        this.TASK = task;
        this.START_TIME = startTime;
        this.MAX_WINDOW = maxWindow;
        this.DURATION = duration;
        this.ANIMAL_ID = animalID;
        this.ANIMAL_TYPE = animalType;
        this.ANIMAL_NAME = animalName;
        this.assignedTime = -1;          // -1 to start off with but will be changed when the schedule is set.
    }

    public String getTask(){
        return this.TASK;
    }
    public int getStartTime(){
        return this.START_TIME;
    }
    public int getMaxWindow(){
        return this.MAX_WINDOW;
    }
    public int getAssignedTime(){
        return this.assignedTime;
    }
    public int getDuration(){
        return this.DURATION;
    }
    public int getAnimalID(){
        return this.ANIMAL_ID;
    }
    public void setAssignedTime(int time){
        if(time < 0 || time > 23){
            throw new IllegalArgumentException("Invalid Assigned Time");
        }
        this.assignedTime = time;
    }

    public String getAnimalType() {
        return this.ANIMAL_TYPE;
    }
    public String getName(){
        return this.ANIMAL_NAME;
    }
}
