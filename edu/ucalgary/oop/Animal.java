package edu.ucalgary.oop;
/*
 * Represents an animal from the database
 *
 * nickname - name of the animal
 * animalID - ID of the animal in the database
 * feedingInfo - array of values about
 *
 */
public abstract class Animal {
    private final String NICKNAME;
    private final int ANIMAL_ID;

    protected Animal(String nickName, int animalID){
        this.NICKNAME = nickName;
        this.ANIMAL_ID = animalID;
    }

    public String getNickName(){
        return this.NICKNAME;
    }
    public int getAnimalID() {
        return this.ANIMAL_ID;
    }
    public abstract int getCleaningTime();
    public abstract int getFeedingDuration();
    public abstract int getFeedingPrepTime();
    public abstract int getFeedingStartTime();
    public abstract String getAnimalSpecies();
}