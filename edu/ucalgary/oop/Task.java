package edu.ucalgary.oop;

/**
 * Class task represents a task.
 * String task - A description of the task, which are stored in treatmentList of class Schedule, which are pulled from sql database
 * startTime - The requested start time from the database
 * maxWindow - The number of hours ahead of startTime the task can potentially be assigned
 * AssignedTime - The hour the program decides to place the task
 * length - The number of minutes a task takes
 * animalID - the ID of the animal associated with the task
 * Entries will have to be ordered in the Schedule object in such a way that minimizes wasted time.
 */
public class Task {
    private final String TASK;
    private final int TASK_ID;
    private int startTime;
    private final int MAX_WINDOW;
    private int assignedTime;
    private final int DURATION;
    private final Animal ANIMAL;


    public Task(String task, int startTime, int maxWindow, int duration, Animal animal, int taskId) {
        this.TASK_ID = taskId;
        this.TASK = task;
        this.startTime = startTime;
        this.MAX_WINDOW = maxWindow;
        this.DURATION = duration;
        this.ANIMAL = animal;
        this.assignedTime = -1;          // -1 to start off with but will be changed when the schedule is set.
    }
    public Task(String task, int startTime, int maxWindow, int duration, Animal animal) {
        this.TASK_ID = -1;               // -1 if it is a cleaning or feeding task (not medical
        this.TASK = task;
        this.startTime = startTime;
        this.MAX_WINDOW = maxWindow;
        this.DURATION = duration;
        this.ANIMAL = animal;
        this.assignedTime = -1;          // -1 to start off with but will be changed when the schedule is set.
    }

    /**
     * getTask
     *
     * @return Description of the task
     */
    public String getTask() {
        return this.TASK;
    }

    /**
     * getStartTime
     *
     * @return The start time of a task
     */
    public int getStartTime() {
        return this.startTime;
    }

    /**
     * getMaxWindow
     *
     * @return The max window of a task
     */
    public int getMaxWindow() {
        return this.MAX_WINDOW;
    }

    /**
     * getAssignedTime
     *
     * @return The assigned time of a task
     */
    public int getAssignedTime() {
        return this.assignedTime;
    }

    /**
     * getAssignedTime
     *
     * @return The duration of a task
     */
    public int getDuration() {
        return this.DURATION;
    }

    /**
     * getAssignedTime
     *
     * @return The ID of the animal associated with a task
     */
    public Animal getAnimal() {
        return this.ANIMAL;
    }

    /**
     * setAssignedTime
     * <p>
     * Sets the assigned time of a task
     */
    public void setAssignedTime(int time) {
        if (time < 0 || time > 23) {
            throw new IllegalArgumentException("Invalid Assigned Time");
        }
        this.assignedTime = time;
    }

    public void setStartTime(int time) {
        if (time < 0 || time > 23) {
            throw new IllegalArgumentException("Invalid Start Time");
        }
        this.startTime = time;
    }

    public int getTaskId() {
        return this.TASK_ID;
    }
}

