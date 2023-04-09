package edu.ucalgary.oop.tests;

import edu.ucalgary.oop.Schedule;
import edu.ucalgary.oop.Animal;
import edu.ucalgary.oop.Coyote;
import edu.ucalgary.oop.Hour;
import edu.ucalgary.oop.Task;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class ScheduleTest {

    Animal testAnimal = new Coyote("Barry", 2);

    ArrayList<Task> taskList = new ArrayList<Task>();
    Task testTask = new Task(testAnimal, "Feed Barry some berries", 7, 2, 4, 20);
    Task testTask2 = new Task(testAnimal, "Give Barry a bath", 7, 2, 4, 40);
    Task testTask3 = new Task(testAnimal, "Tell Barry he's the best", 7, 2, 4, 10);

    Schedule testSchedule = new Schedule();


    /**
     * generateSchedule() is called with a valid schedule
     * checks if the hour is generated correctly with the overflow tasks moving into the next hour
     * Checks if algorithm is working correctly
     */

    @Test
    public void testGenerateSchedule() {
        /*
            * three tasks added totaling over 60 minutes
            * third task should overflow into the next hour
         */
        taskList.add(testTask);
        taskList.add(testTask2);
        taskList.add(testTask3);
        testSchedule.generateSchedule(taskList);

        int correctTimeAvailable = 0;


        assertEquals("Did not output correct time available", correctTimeAvailable, testSchedule.getHour(2).getTimeAvailable());
        assertEquals("Did not output correct Task", testTask, testSchedule.getHour(2).getTasks().get(0));
        assertEquals("Did not output correct Task", testTask2, testSchedule.getHour(2).getTasks().get(1));
        assertEquals("Did not adjust for overflow", testTask3, testSchedule.getHour(3).getTasks().get(0));
    }

}
