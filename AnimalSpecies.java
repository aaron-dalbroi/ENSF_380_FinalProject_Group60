/**
 * Represents a set of constant values associated with each animal species
 * chronoType - this is the time of day the animal must be fed
 * FeedingInfo - this is information like length of feeding time? (I'm not sure what this is for)
 *
 *
 */
public enum AnimalSpecies {

    FOX("nocturnal", new int[]{0,0,0}),
    COYOTE("crepuscular", new int[]{0,0,0}),
    PORCUPINE("crepuscular", new int[]{0,0,0}),
    BEAVER("diurnal", new int[]{0,0,0}),
    RACOON("nocturnal",new int[]{0,0,0});

    private String chronoType;
    private int[] feedingInfo;

    private AnimalSpecies(String chronoType, int[] feedingInfo){
        this.chronoType = chronoType;
        this.feedingInfo = feedingInfo;
    }

    public String getChronoType(){
        return this.chronoType;
    }
    public int[] getFeedingInfo(){
        return this.feedingInfo;
    }

}
