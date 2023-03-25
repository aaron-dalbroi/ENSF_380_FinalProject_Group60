package edu.ucalgary.oop;
/**
 * Represents an animal from the database
 *
 * nickname - name of the animal
 * animalID - ID of the animal in the database
 * feedingInfo - array of values about
 *
 */
public class Animal {
    public final String NICKNAME;
    public final int ANIMAL_ID;
    public final int[] FEEDING_CLEANING_INFO;
    public final String CHRONOTYPE;

    public Animal(int animalID, String animalNickName, String animalSpecies){

        this.ANIMAL_ID = animalID;
        this.NICKNAME = animalNickName;

        String animalEnum = animalSpecies.toUpperCase();
        this.CHRONOTYPE = AnimalSpecies.valueOf(animalEnum).getChronoType();
        this.FEEDING_CLEANING_INFO = AnimalSpecies.valueOf(animalEnum).getFeedingCleaningInfo();

        //call mySqLConnection to get


    }
}