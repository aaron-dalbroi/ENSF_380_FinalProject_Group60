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


        for (Task task: tasks){
            System.out.println(task.getAnimal().getNickName() + " task:" +task.getTask());
        }

        connection.closeConnection();
    }
}
