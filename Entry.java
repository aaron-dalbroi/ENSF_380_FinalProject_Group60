/**
 * Class Entry represents a task.
 * int taskId - A numerical ID for a description of the task, which are stored in treatmentList of class Schedule, which are pulled from sql database
 * startTime - The requested start time from the database
 * maxWindow - The number of hours ahead of startTime the task can potentially be assigned
 * AssignedTime - The hour the program decides to place the task
 * length - The number of minutes a task takes
 * Entries will have to be ordered in the Schedule object in such a way that minimizes wasted time.
 */
public class Entry {
    private int taskID;
    private int startTime;
    private int maxWindow;
    private int assignedTime;
    private int length;





}


