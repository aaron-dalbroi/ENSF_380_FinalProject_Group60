package edu.ucalgary.oop.tests;

import org.junit.Test;
import static org.junit.Assert.*;
import edu.ucalgary.oop.Animal;
import edu.ucalgary.oop.Coyote;
import edu.ucalgary.oop.Beaver;
public class AnimalTest {

    /**
     * Animal() is called with valid arguments
     * creates a Coyote Animal with its given default values
     * all getters are tested with correct values
     */

    @Test
    public void testGettersCoyote() {
        Animal testObject = new Coyote("Barry", 2);
        String correctName = "Barry";
        int correctID = 2;
        int correctFeedingDuration = 5;
        int correctFeedingPrepTime = 0;
        int correctCleaningTime = 5;
        int correctFeedingStart = 19;
        String correctAnimalType = "Coyote";

        assertEquals("Did not return correct name", correctName, testObject.getNickName());
        assertEquals("Did not output correct ID", correctID, testObject.getAnimalID());
        assertEquals("Did not output correct feeding duration", correctFeedingDuration, testObject.getFeedingDuration());
        assertEquals("Did not output correct feeding prep time", correctFeedingPrepTime, testObject.getFeedingPrepTime());
        assertEquals("Did not output correct cleaning time", correctCleaningTime, testObject.getCleaningTime());
        //assertEquals("Did not output correct feeding start", correctFeedingStart, testObject.getFeedingStart());
        //assertEquals("Did not output correct animal type", correctAnimalType, testObject.getAnimalSpecies());
    }


    /**
     * Same as above, but for Beaver
     * checks if getters work on different animals
     */
    @Test
    public void testGettersBeaver() {
        Animal testObject = new Beaver("Steve", 7);
        String correctName = "Steve";
        int correctID = 7;
        int correctFeedingDuration = 5;
        int correctFeedingPrepTime = 0;
        int correctCleaningTime = 5;
        int correctFeedingStart = 8;
        String correctAnimalType = "Beaver";

        assertEquals("Did not return correct name", correctName, testObject.getNickName());
        assertEquals("Did not output correct ID", correctID, testObject.getAnimalID());
        assertEquals("Did not output correct feeding duration", correctFeedingDuration, testObject.getFeedingDuration());
        assertEquals("Did not output correct feeding prep time", correctFeedingPrepTime, testObject.getFeedingPrepTime());
        assertEquals("Did not output correct cleaning time", correctCleaningTime, testObject.getCleaningTime());
        //assertEquals("Did not output correct feeding start", correctFeedingStart, testObject.getFeedingStart());
        //assertEquals("Did not output correct animal type", correctAnimalType, testObject.getAnimalSpecies());
    }



}

