    package edu.ucalgary.oop;

    import org.junit.Test;
    import static org.junit.Assert.*;

    public class HourTest {

        @Test
        public void testGetters(){
            Hour testObject = new Hour(1);
            testObject.subtractTimeAvailable(20);
            Entry correctEntry = new Entry( "task", 4, 4, 20, 4, "coyote", "Eddie");
            testObject.addTaskToHour(correctEntry);
            int correctStart = 1;
            int timeAvailable = 40;
            boolean volunteerNeeded = false;
            assertEquals("Did not output correct hour" ,correctStart, testObject.getTime());
            assertEquals("Did not output correct time available",timeAvailable, testObject.getTimeAvailable());
            assertEquals("Did not output correct Entry", correctEntry, testObject.getTasks().get(0));
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
