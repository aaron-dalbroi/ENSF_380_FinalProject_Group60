package edu.ucalgary.oop.tests;

import edu.ucalgary.oop.Animal;
import edu.ucalgary.oop.Coyote;
import org.junit.Test;
import static org.junit.Assert.*;
import edu.ucalgary.oop.Task;
public class TaskTest {
    /*
     *Information for testing
     */
    Animal testAnimal = new Coyote("Barry", 2);
    String correctTask = "Give Barry a treat";
    int correctTaskID = 3;
    int correctStart = 0;
    int correctWindow = 3;
    int correctDuration = 1;


    /**
     * Task() is called with valid input
     * All getters are called with correct entries.
     */
    @Test
    public void testGetters() {
        Task testObject = new Task(testAnimal, correctTask, correctTaskID, correctStart, correctWindow, correctDuration);


        assertEquals("Did not return correct task", correctTask, testObject.getTask());
        assertEquals("Did not output correct start", correctStart, testObject.getStartTime());
        assertEquals("Did not output correct window", correctWindow, testObject.getMaxWindow());
        assertEquals("Did not output correct duration", correctDuration, testObject.getDuration());
        assertEquals("Did not output correct id", correctTaskID, testObject.getTaskID());
        assertEquals("Did not output correct animal", testAnimal, testObject.getAnimal());
    }

    /**
     * Valid Task object is created
     * setAssignedTime() called with illegal input
     * Task() is called with Illegal argument, should throw IllegalArgumentException
     */
    @Test
    public void testAssignedTime(){
        Task testObject = new Task(testAnimal, correctTask, correctTaskID, correctStart, correctWindow, correctDuration);
        testObject.setAssignedTime(26);
        int correctAssignedTime = 26;
        assertEquals("Did not return correct assigned time", correctAssignedTime, testObject.getAssignedTime());




    }
}
