package edu.ucalgary.oop.tests;

import org.junit.Test;
import static org.junit.Assert.*;
import edu.ucalgary.oop.Animal;
import edu.ucalgary.oop.Coyote;
public class AnimalTest {

    /**
     * Animal() is called with valid arguments
     * all getters are tested with correct values
     */

    @Test
    public void testGetters() {
        Animal testObject = new Coyote("Barry", 2);
        String correctName = "Barry";
        int correctID = 2;
        int correctFeedingDuration = 5;
        int correctFeedingPrepTime = 0;
        int correctCleaningTime = 5;
        int correctFeedingStart = 19;
        String correctAnimalType = "coyote";

        assertEquals("Did not return correct name", correctName, testObject.getNickName());
        assertEquals("Did not output correct ID", correctID, testObject.getAnimalID());
        assertEquals("Did not output correct feeding duration", correctFeedingDuration, testObject.getFeedingDuration());
        assertEquals("Did not output correct feeding prep time", correctFeedingPrepTime, testObject.getFeedingPrepTime());
        assertEquals("Did not output correct cleaning time", correctCleaningTime, testObject.getCleaningTime());
        //assertEquals("Did not output correct feeding start", correctFeedingStart, testObject.getFeedingStart());
        //assertEquals("Did not output correct animal type", correctAnimalType, testObject.getAnimalSpecies());
    }

}

