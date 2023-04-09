package edu.ucalgary.oop;

/**
 *          !!! DEPRECIATED, NO LONGER USED ANYWHERE IN PROGRAM !!!
 *
 * AnimalSpecices (enum)
 *
 * Represents a set of constant values associated with each animal species
 * chronoType - this is the time of day the animal must be fed
 * FeedingCleaningInfo - this is information like length of feeding and cleaning
 * index 0 is feeding time
 * index 1 is prep time
 * index 2 is cleaning time
 *
 *
 */
public enum AnimalSpecies {

    FOX("nocturnal", new int[]{5,5,5}),
    COYOTE("crepuscular", new int[]{5,10,5}),
    PORCUPINE("crepuscular", new int[]{5,0,10}),
    BEAVER("diurnal", new int[]{5,0,5}),
    RACOON("nocturnal",new int[]{5,0,5});

    private final String CHRONOTYPE;
    private final int[] FEEDING_CLEANING_INFO;

    private AnimalSpecies(String chronoType, int[] feedingInfo){
        this.CHRONOTYPE = chronoType;
        this.FEEDING_CLEANING_INFO = feedingInfo;
    }

    public String getChronoType(){
        return this.CHRONOTYPE;
    }
    public int getFeedingDuration(){
        return this.FEEDING_CLEANING_INFO[0];
    }

    public int getFeedingPrepTime(){
        return this.FEEDING_CLEANING_INFO[1];
    }
    public int getCleaningTime(){
        return this.FEEDING_CLEANING_INFO[2];
    }
    public int[] getFeedingCleaningInfo(){
        return this.FEEDING_CLEANING_INFO;
    }


}