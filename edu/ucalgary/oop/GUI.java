package edu.ucalgary.oop;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.swing.JOptionPane;

public class GUI implements ActionListener{
    public void actionPerformed(ActionEvent event){
        // Set up connection with the database
        SqlConnection connection = null;
        ArrayList<Animal> animals = new ArrayList<>();
        try {
            connection = new SqlConnection();
            animals = connection.readAnimalsFromDataBase();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to connect to database");
        }
        // Now we must create a schedule object and use the generateSchedule method to create the schedule for the day
        Schedule schedule = new Schedule();

        //Get all medical tasks
        ArrayList<Task> medTasks = Schedule.generateMedicalTasks(animals, connection);

        //find if too many med tasks are scheduled for the same time
        ArrayList<Task> problemTasks = new ArrayList<>();
        schedule.generateSchedule(medTasks);
        for(Hour time : schedule.getFinalSchedule()){
            if(time.getTimeAvailable() < -60){
                problemTasks.addAll(time.getTasks());
            }
        }

        //Create an option list of problematic tasks for the user to choose from
        ArrayList<String> options = new ArrayList<>();
        HashMap<String, String> taskConverter = new HashMap<>();
        for (Task task : problemTasks) {
            int time = task.getStartTime();
            String timeStr = (time < 12) ? (time + " am") :  ( (time == 12 ) ? (time + " pm") : (time - 12) + " pm");
            options.add(task.getTask() + " on " + timeStr);
            taskConverter.put(task.getTask() + " on " + timeStr, task.getTask());
        }

        if(problemTasks.size() > 0){
            String chosenTask = (String) JOptionPane.showInputDialog(null, "Schedule is not possible a medical task needs to be rescheduled", "Impossible schedule", JOptionPane.ERROR_MESSAGE, null, options.toArray(), options.get(0));
            chosenTask = taskConverter.get(chosenTask);
            if(chosenTask != null){
                //Choose a time for the task to rescheduled to
                String[] times = {"12:00am","1:00am","2:00am","3:00am","4:00am","5:00am","6:00am","7:00am","8:00am","9:00am","10:00am","11:00am",
                        "12:00pm","1:00pm","2:00pm","3:00pm","4:00pm","5:00pm","6:00pm","7:00pm","8:00pm","9:00pm","10:00pm","11:00pm"};
                HashMap<String, Integer> timeConverter = new HashMap<>();
                int convertTime = 0;
                for(String time : times){
                    timeConverter.put(time, convertTime);
                    convertTime++;
                }
                String chosenTime = (String) JOptionPane.showInputDialog(null, "Pick the time to change it to", "Choose time", JOptionPane.ERROR_MESSAGE, null, times, times[0]);
                //Reschedule the task
                if(chosenTime != null){
                    for(Task Task : problemTasks){
                        if(Task.getTask().equals(chosenTask)){
                            connection.updateTreatment(Task.getAnimal().getAnimalID(), Task.getTaskID(), timeConverter.get(chosenTime));
                        }
                    }
                //If user does not reschedule the task the program will stop
                }else{
                    JOptionPane.showConfirmDialog(null, "Please Reschedual Medical Task", "Error", JOptionPane.DEFAULT_OPTION);
                    return;
                }
            }else{
                JOptionPane.showConfirmDialog(null, "Please Reschedual Medical Task", "Error", JOptionPane.DEFAULT_OPTION);
                return;
            }
        }

        //Generate tasks for the day and Create schedule
        schedule = new Schedule();
        ArrayList<Task> tasks = Schedule.generateTasks(animals, connection);
        schedule.generateSchedule(tasks);

        for(Hour time : schedule.getFinalSchedule()){
            if(time.getTimeAvailable() < -60){
                JOptionPane.showConfirmDialog(null, "Impossible Schedule Generated The Tasks Cannot Be Done In One Day", "Error", JOptionPane.DEFAULT_OPTION);
                return;
            }
        }

        //get hours of the day an extra volunteer is needed
        ArrayList<Hour> extraVolunteers = schedule.getHoursWithVolunteers();
        if(extraVolunteers.size() > 0){
            //if extra volunteers are needed inform user
            String message = "Extra volunteers are needed on: ";
            for(int i = 0; i < extraVolunteers.size(); i++){
                Hour hour = extraVolunteers.get(i);
                String timeStr = (hour.getTime() < 12) ? (hour.getTime() + " am") :  ( (hour.getTime() == 12 ) ? (hour.getTime() + " pm") : (hour.getTime() - 12) + " pm");
                if(i >= extraVolunteers.size()-1 && extraVolunteers.size() > 1){
                    message += "and " + timeStr;
                }else if(extraVolunteers.size() == 1){
                    message += timeStr;
                }else{
                    message += timeStr + ", ";
                }
            }
            int input = JOptionPane.showConfirmDialog(null, message, "Volunteer needed", JOptionPane.DEFAULT_OPTION);
            //if user does not confirm the program will stop
            if(input != 0){
                JOptionPane.showConfirmDialog(null, "Please Confirm Volunteer", "Error", JOptionPane.DEFAULT_OPTION);
                return;
            }
        }

        FileWriter outPutFile = null;
        try{
            FileWriter outputFile = new FileWriter("schedule.txt");

            String header = String.format("%-10s%-50s", "Time", "Task");
            outputFile.write(header);
            outputFile.write("\n");

            for(Hour hour: schedule.getFinalSchedule()){ String timeStr = (hour.getTime() < 12) ? (hour.getTime() + " am") :  ( (hour.getTime() == 12 ) ? (hour.getTime() + " pm") : (hour.getTime() - 12) + " pm");
                int timeSpent = 0;
                int timeAvailable = 60;
                boolean isVolunteerNeeded = false;
                for (int i = 0; i < hour.getTasks().size(); i++) {
                    timeSpent += hour.getTasks().get(i).getDuration();
                    timeAvailable -= hour.getTasks().get(i).getDuration();
                    //Sets the volunteer needed to true if time available is less than 0
                    if (timeAvailable < 0) {
                        isVolunteerNeeded = true;
                        timeAvailable = 0;
                    }
                    //prints all the statements
                    String name = hour.getTasks().get(i).getAnimal().getNickName();
                    String task = hour.getTasks().get(i).getTask();
                    String animalType = hour.getTasks().get(i).getAnimal().getAnimalSpecies();
                    if (i == 0) {
                        outputFile.write(String.format("%-10s%-50s", timeStr, task + " for " + name + " (" + animalType + ")"));
                        outputFile.write("\n");
                    } else {
                        outputFile.write(String.format("%-10s%-50s", "", task + " for " + name + " (" + animalType + ")"));
                        outputFile.write("\n");
                    }
                }
                if (isVolunteerNeeded) {
                    //If volunteer is needed the GUI will inform the user to confirm an extra volunteer
                    outputFile.write("*Backup volunteer needed");
                    outputFile.write("\n");
                }
                outputFile.write("\n");
            }
            outputFile.close();
            connection.closeConnection();

    
            Window[] windows = Window.getWindows();
            for(Window window: windows){
                window.dispose();
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Output file failed to be created");
        }
    }
}
