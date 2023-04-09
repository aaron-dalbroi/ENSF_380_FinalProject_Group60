package edu.ucalgary.oop;
/**
 * Represents an animal from the database
 *
 * nickname - name of the animal
 * animalID - ID of the animal in the database
 *
 */
public abstract class Animal {
    private final String NICKNAME;
    private final int ANIMAL_ID;

    protected Animal(String nickName, int animalID){
        this.NICKNAME = nickName;
        this.ANIMAL_ID = animalID;
    }

    /**
     * getNickName
     * @return - The name of the animal
     */
    public String getNickName(){
        return this.NICKNAME;
    }
    /**
     * getAnimalID
     * @return - The ID of the animal
     */
    public int getAnimalID() {
        return this.ANIMAL_ID;
    }
    /**
     * getCleaningTime
     * @return - The time it takes to clean the cage of an animal (Will differ depending on species)
     */
    public abstract int getCleaningTime();
    /**
     * getFeedingDuration
     * @return - The time it takes to feed an animal (Will differ depending on species)
     */
    public abstract int getFeedingDuration();
    /**
     * getFeedingDuration
     * @return - The time it takes to prepare to feed an animal (Will differ depending on species)
     */
    public abstract int getFeedingPrepTime();
    /**
     * getFeedingStartTime
     * @return - The time of day an animal must be fed within a certain window (Will differ depending on species)
     */
    public abstract int getFeedingStartTime();
    /**
     * getFeedingStartTime
     * @return - The species of the animal
     */
    public abstract String getAnimalSpecies();
}