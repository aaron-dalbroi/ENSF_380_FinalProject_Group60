//    package edu.ucalgary.oop.tests;
//
//    import edu.ucalgary.oop.Entry;
//    import edu.ucalgary.oop.Hour;
//    import org.junit.Test;
//    import static org.junit.Assert.*;
//
//    public class HourTest {
//
//        /**
//         * Hour() is called with a valid hour
//         * subtractTimeAvailable() is called with a valid time
//         * addTaskToHour() is called with a valid task
//         * All getters are called
//         */
//        @Test
//        public void testGetters(){
//            Hour testObject = new Hour(1);
//            Entry correctEntry = new Entry( "task", 4, 4, 20, 4, "coyote", "Eddie");
//            testObject.subtractTimeAvailable(correctEntry.getDuration());
//            testObject.addTaskToHour(correctEntry);
//            int correctStart = 1;
//            int timeAvailable = 40;
//            assertEquals("Did not output correct hour" ,correctStart, testObject.getTime());
//            assertEquals("Did not output correct time available",timeAvailable, testObject.getTimeAvailable());
//            assertEquals("Did not output correct Entry", correctEntry, testObject.getTasks().get(0));
//        }
//
//        /**
//         *
//         * Hour() is called with an invalid-hour. IllegalArgumentException should be thrown
//         *
//         */
//        @Test
//        public void testTimeException(){
//
//            try{
//                Hour testObject = new Hour(-1);
//                Hour testObject1 = new Hour(24);
//                fail("Did not throw IllegalArgumentException");
//            }catch(IllegalArgumentException e){
//                return;
//            }
//        }
//
//
//    }