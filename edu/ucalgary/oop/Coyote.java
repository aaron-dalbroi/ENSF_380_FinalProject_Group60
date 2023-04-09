package edu.ucalgary.oop;
/**
 * A coyote class animal. Has specified attributes that dictate how long certain tasks will take.
 * int FEEDING_DURATION - The number of minutes one instance of feeding will take.
 * int FEEDING_PREPTIME - The number of minutes it takes to prepare to feed this animal.
 * int CLEANING_TIME - The number of minutes it takes to clean one cage for this animal.
 * int FEEDING_START_TIME - The hour of the day this animal must be fed at with a max window of 3 hours
 * String Species - The animal of the class in String form
 *
 * Methods override Animal class methods
 */
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