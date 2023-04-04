//package edu.ucalgary.oop;
///**
// * Represents an animal from the database
// *
// * nickname - name of the animal
// * animalID - ID of the animal in the database
// * feedingInfo - array of values about
// *
// */
//public class Animal {
//    private final String NICKNAME;
//    private final int ANIMAL_ID;
//    private final int[] FEEDING_CLEANING_INFO;
//    private final String CHRONOTYPE;
//
//    public Animal(int animalID, String animalNickName, String animalSpecies){
//
//        this.ANIMAL_ID = animalID;
//        this.NICKNAME = animalNickName;
//
//        String animalEnum = animalSpecies.toUpperCase();
//        this.CHRONOTYPE = AnimalSpecies.valueOf(animalEnum).getChronoType();
//        this.FEEDING_CLEANING_INFO = AnimalSpecies.valueOf(animalEnum).getFeedingCleaningInfo();
//    }
//
//    public String getNickName() {
//        return NICKNAME;
//    }
//
//    public int getAnimalID() {
//        return ANIMAL_ID;
//    }
//
//    public int[] getFeedingCleaningInfo() {
//        return FEEDING_CLEANING_INFO;
//    }
//
//    public String getChronotype() {
//        return CHRONOTYPE;
//    }
//}