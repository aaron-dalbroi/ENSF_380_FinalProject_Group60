package edu.ucalgary.oop;

import java.sql.SQLException;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws SQLException {
        // Set up connection with the database
        SqlConnection connection = new SqlConnection();

        // Get all the animals from the database
        ArrayList<Animal> animals = connection.readAnimalsFromDataBase();

        // Generate all the tasks needed for the day
        ArrayList<Task> tasks = Schedule.generateTasks(animals, connection);

        // Now we must create a schedule object and use the generateSchedule method to create the schedule for the day
        Schedule schedule = new Schedule();

        schedule.generateSchedule(tasks);

        for(Hour hour: schedule.getFinalSchedule()){
            System.out.println("-----------------------------"+hour.getTime()+"-----------------------------");
            for(Task task: hour.getTasks()){
                System.out.println( "Animal: " + task.getAnimal().getNickName() + " Task: " + task.getTask());
            }
        }


        connection.closeConnection();
    }
}
