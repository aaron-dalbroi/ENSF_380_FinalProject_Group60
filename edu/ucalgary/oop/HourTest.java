package edu.ucalgary.oop;

import org.junit.Test;
import static org.junit.Assert.*;

public class HourTest {

    @Test
    public void testGetters(){
        Hour testObject = new Hour(1);
        testObject.addTimeSpent(40);
        int correctStart = 1;
        int timeSpent = 40;
        int timeAvailable = 20;
        boolean volunteerNeeded = false;

        assertEquals("Did not output correct hour" ,correctStart, testObject.getTime());
        assertEquals("Did not output correct time spent",timeSpent, testObject.getTimeSpent());
        assertEquals("Did not output correct time available",timeAvailable, testObject.getTimeAvailable());
        assertEquals("Did not output correct id",volunteerNeeded, testObject.isVolunteerNeeded());
    }

    @Test
    public void testTimeException(){

        try{
            Hour testObject = new Hour(-1);
            fail("Did not throw IllegalArgumentException");
        }catch(IllegalArgumentException e){
            return;
        }
    }


}
