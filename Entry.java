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
    private String task;
    private int startTime;
    private int maxWindow;
    private int assignedTime;
    private int duration;
    private int animalID;

    public Entry(String task,int startTime,int maxWindow,int duration,int animalID){
    this.task = task;
    this.startTime = startTime;
    this.maxWindow = maxWindow;
    this.duration = duration;
    this.animalID = animalID;
    this.assignedTime = -1;
    }

    public String getTask(){
       return this.task;
    }
    public int getStartTime(){
        return this.startTime;
    }
    public int getMaxWindow(){
        return this.maxWindow;
    }
    public int getAssignedTime(){
        return this.assignedTime;
    }
    public int getDuration(){
        return this.duration;
    }
    public int getAnimalID(){
        return this.animalID;
    }
    public void setAssignedTime(int time){
        if(time < 0 || time > 23){
            throw new IllegalArgumentException("Invalid Assigned Time");
        }
        this.assignedTime = time;


    }



}


