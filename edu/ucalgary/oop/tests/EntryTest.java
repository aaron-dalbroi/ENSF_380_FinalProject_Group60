//package edu.ucalgary.oop.tests;
//
//import edu.ucalgary.oop.Entry;
//import org.junit.Test;
//import static org.junit.Assert.*;
//
//public class EntryTest {
//
//    /**
//     * Entry() is called with valid input
//     * All getters are called with correct entries.
//     *
//     */
//    @Test
//    public void testGetters() {
//        Entry testObject = new Entry("Test this function", 0, 3, 1, 4, "coyote", "Eddie");
//        String correctTask = "Test this function";
//        int correctStart = 0;
//        int correctWindow = 3;
//        int correctDuration = 1;
//        int correctID = 4;
//        String correctAnimal = "coyote";
//        String correctName = "Eddie";
//
//        assertEquals("Did not return correct task", correctTask, testObject.getTask());
//        assertEquals("Did not output correct start", correctStart, testObject.getStartTime());
//        assertEquals("Did not output correct window", correctWindow, testObject.getMaxWindow());
//        assertEquals("Did not output correct duration", correctDuration, testObject.getDuration());
//        assertEquals("Did not output correct id", correctID, testObject.getAnimalID());
//        assertEquals("Did not output correct animal", correctAnimal, testObject.getAnimalType());
//        assertEquals("Did not output correct name", correctName, testObject.getName());
//    }
//
//    /**
//     * Valid Entry object is created
//     * setAssignedTime() called with illegal input
//     * Entry() is called with Illegal argument, should throw IllegalArgumentException
//     *
//     */
//    @Test
//    public void testTimeException(){
//        Entry testObject = new Entry("Test this function", 0, 3, 1, 4, "coyote", "Eddie");
//        try{
//            Entry invalidObject = new Entry("Test this function", -1, 3, 1, 4, "coyote", "Eddie");
//            testObject.setAssignedTime(26);
//            fail("Did not throw IllegalArgumentException");
//        }catch(IllegalArgumentException e){
//            return;
//        }
//    }}
//
//

