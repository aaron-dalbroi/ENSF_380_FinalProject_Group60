package edu.ucalgary.oop;
/**
 * Represents an animal from the database
 *
 * nickname - name of the animal
 * animalID - ID of the animal in the database
 * feedingInfo - array of values about
 *
 */
public abstract class Animal {
    protected final AnimalSpecies SPECIES;
    protected final String NICKNAME;
    protected final int ANIMAL_ID;

    protected Animal( AnimalSpecies species, String nickName, int animalID){
        this.SPECIES = species;
        this.NICKNAME = nickName;
        this.ANIMAL_ID = animalID;
    }

    public String getNickName(){
        return this.NICKNAME;
    }
    public int getAnimalID() {
        return this.ANIMAL_ID;
    }
    public AnimalSpecies getSpecies() {
        return this.SPECIES;
    }
}