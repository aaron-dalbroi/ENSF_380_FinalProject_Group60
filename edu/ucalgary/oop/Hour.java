package edu.ucalgary.oop;

public class Hour {

    // time will be used to determine what hour out of the 24 it is
    // timeSpent will be used to determine how much time has been spent in that hour depending on the tasks to be compleated then
    // timeAvailable will be used to determine how much time is still available in that hour.
    // volunteer will be used as a flag to determine if a volunteer is REQUIRED.
    // Note, timeSpent + timeAvalable will alwaus equal 60 UNLESS volunteer is true, in which case, it will be 120.
    private int time;
    private int timeSpent = 0;
    private int timeAvailable = 60;
    private boolean volunteerNeeded = false;

    // Constructor that takes in a int parameter and initializes this.time with it.
    public Hour(int time){
        if (time < 0 || time > 23) {
            throw new IllegalArgumentException("Invalid time");
        }
        this.time = time;
    }

    // Regular getters and setters
    public int getTime() {
        return this.time;
    }
    public void setTime(int time) {
        this.time = time;
    }

    public int getTimeAvailable() {
        return this.timeAvailable;
    }
    public void setTimeAvailable(int timeAvailable) {
        this.timeAvailable = timeAvailable;
    }

    public int getTimeSpent() {
        return this.timeSpent;
    }
    public void addTimeSpent(int timeSpent) {
        this.timeSpent += timeSpent;
        this.timeAvailable -= timeSpent;
        if (this.timeAvailable < 0) {
            setVolunteerNeeded(true);
        }
    }


    public boolean isVolunteerNeeded() {
        return this.volunteerNeeded;
    }
    public void setVolunteerNeeded(boolean volunteerNeeded) {
        this.volunteerNeeded = volunteerNeeded;
    }
}
s
