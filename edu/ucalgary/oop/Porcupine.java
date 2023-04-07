package edu.ucalgary.oop;

public class Porcupine extends Animal {
    private final int FEEDING_DURATION = 5;
    private final int FEEDING_PREPTIME = 0;
    private final int CLEANING_TIME = 10;

    public Porcupine(String nickName, int animalID){
        super(nickName, animalID);
    }

    public int getFeedingDuration() {
        return this.FEEDING_DURATION;
    }
    public int getFeedingPrepTime() {
        return this.FEEDING_PREPTIME;
    }
    public int getCleaningTime() {
        return this.CLEANING_TIME;
    }
}