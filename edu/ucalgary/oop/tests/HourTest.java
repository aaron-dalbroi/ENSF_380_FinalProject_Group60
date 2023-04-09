    package edu.ucalgary.oop.tests;

    import edu.ucalgary.oop.Animal;
    import edu.ucalgary.oop.Coyote;
    import edu.ucalgary.oop.Hour;
    import edu.ucalgary.oop.Task;
    import org.junit.Test;
    import static org.junit.Assert.*;

    public class HourTest {

        Animal testAnimal = new Coyote("Barry", 2);
        Task testTask = new Task(testAnimal, "Feed Barry some berries", 7, 2, 4, 20);
        Task testTask2 = new Task(testAnimal, "Give Barry a bath", 7, 2, 4, 40);
        Task testTask3 = new Task(testAnimal, "Tell Barry he's the best", 7, 2, 4, 10);

        /**
         * Hour() is called with a valid hour
         * subtractTimeAvailable() is called with a valid time
         * addTaskToHour() is called with a valid task
         * All getters are called
         */
        @Test
        public void testGetters(){
            Hour testObject = new Hour(2);
            testObject.addTaskToHour(testTask);
            testObject.subtractTimeAvailable(testTask.getDuration());

            int correctTime = 2;
            int correctTimeAvailable = 40;

            assertEquals("Did not output correct hour" ,correctTime, testObject.getTime());
            assertEquals("Did not output correct time available",correctTimeAvailable, testObject.getTimeAvailable());
            assertEquals("Did not output correct Entry", testTask, testObject.getTasks().get(0));
        }

        /*
            * Hour() is called with a valid hour
            * addTaskToHour() is called with a valid task
            * Hour is overflowed over 60 minutes
            * time available should be negative
         */

        @Test
        public void testOverflow(){
            Hour testObject = new Hour(2);
            testObject.addTaskToHour(testTask);
            testObject.addTaskToHour(testTask2);
            testObject.addTaskToHour(testTask3);
            testObject.subtractTimeAvailable(testTask.getDuration());
            testObject.subtractTimeAvailable(testTask2.getDuration());
            testObject.subtractTimeAvailable(testTask3.getDuration());

            int correctTime = 2;
            int correctTimeAvailable = -10;

            assertEquals("Did not output correct hour" ,correctTime, testObject.getTime());
            assertEquals("Did not output correct time available",correctTimeAvailable, testObject.getTimeAvailable());
            assertEquals("Did not output correct Entry", testTask, testObject.getTasks().get(0));
            assertEquals("Did not output correct Entry", testTask2, testObject.getTasks().get(1));
            assertEquals("Did not output correct Entry", testTask3, testObject.getTasks().get(2));
        }

        /**
         *
         * Hour() is called with an invalid-hour. IllegalArgumentException should be thrown
         *
         */
        @Test
        public void testTimeException(){
            try{
                Hour testObject = new Hour(-1);
                fail("Did not throw IllegalArgumentException");
            }catch(IllegalArgumentException e){
                return;
            }
            try{
                Hour testObject1 = new Hour(24);
                fail("Did not throw IllegalArgumentException");
            }catch(IllegalArgumentException e){
                return;
            }
        }


    }
