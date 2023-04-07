package edu.ucalgary.oop;

public class Coyote extends Animal {
    private final int FEEDING_DURATION = 5;
    private final int FEEDING_PREPTIME = 0;
    private final int CLEANING_TIME = 5;
    private final int FEEDING_START_TIME = 19;
    private final String ANIMAL_SPECIES = "Coyote";

    public Coyote(String nickName, int animalID){
        super(nickName, animalID);
    }

    @Override
    public int getFeedingDuration(){
        return this.FEEDING_DURATION;
    }
    @Override
    public int getFeedingPrepTime(){
        return this.FEEDING_PREPTIME;
    }
    @Override
    public int getCleaningTime(){
        return this.CLEANING_TIME;
    }
    public int getFeedingStartTime(){
        return this.FEEDING_START_TIME;
    }
    @Override
    public String getAnimalSpecies() {
        return this.ANIMAL_SPECIES;
    }
}