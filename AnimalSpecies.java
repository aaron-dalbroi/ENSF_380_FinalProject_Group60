/**
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
    BEAVER("diurnal", new int[]{0,0,0}),
    RACOON("nocturnal",new int[]{5,0,5});

    private String chronoType;
    private int[] feedingCleaningInfo;

    private AnimalSpecies(String chronoType, int[] feedingInfo){
        this.chronoType = chronoType;
        this.feedingCleaningInfo = feedingInfo;
    }

    public String getChronoType(){
        return this.chronoType;
    }
    public int getFeedingDuration(){
        return this.feedingCleaningInfo[0];
    }

    public int getFeedingPrepTime(){
        return this.feedingCleaningInfo[1];
    }
    public int getCleaningTime(){
        return this.feedingCleaningInfo[2];
    }
    public int[] getFeedingCleaningInfo(){
        return this.feedingCleaningInfo;
    }


}
