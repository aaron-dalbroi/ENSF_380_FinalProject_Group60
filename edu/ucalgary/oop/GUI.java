
package edu.ucalgary.oop;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * This is the GUI functionality when the button is pressed
 */
public class GUI implements ActionListener {
    //Action performed when the button is pressed
    public void actionPerformed(ActionEvent event) {
        //Create the schedule
        Schedule schedule = null;
        try {
            schedule = new Schedule();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Check if the medical task to be done in the database are possible
        //Create an arraylist of medical entries that overlap and go beyond the work of two volunteers
        ArrayList<Entry> medEntries = schedule.getDatabase().pullTreatmentEntries();
        ArrayList<Entry> problemEntries = new ArrayList<>();
        HashMap<Integer, Integer> checker = new HashMap<>();

        for (int i = 0; i < 24; i++) {
            checker.put(i, 0);
        }
        for (Entry entry : medEntries) {
            checker.put(entry.getStartTime(), checker.get(entry.getStartTime()) + entry.getDuration());
        }
        for (Integer hour : checker.keySet()) {
            if (checker.get(hour) > 120) {
                for (Entry entry : medEntries) {
                    if (entry.getStartTime() == hour) {
                        problemEntries.add(entry);
                    }
                }
            }
        }

        //Create an option list of problematic tasks for the user to choose from
        ArrayList<String> options = new ArrayList<>();
        for (Entry entry : problemEntries) {
            String timeStr = (entry.getStartTime() < 13) ? (entry.getStartTime() + " am") : ((entry.getStartTime() - 12) + " pm");
            options.add(entry.getTask() + " for " + entry.getName() + " on " + timeStr);
        }

        if(problemEntries.size() > 0){
            String chosenTask = (String) JOptionPane.showInputDialog(null, "Schedule is not possible a medical task needs to be rescheduled", "Impossible schedule", JOptionPane.ERROR_MESSAGE, null, options.toArray(), options.get(0));
            if(chosenTask != null){
                //Choose a time for the task to rescheduled to
                String[] times = {"12:00am","1:00am","2:00am","3:00am","4:00am","5:00am","6:00am","7:00am","8:00am","9:00am","10:00am","11:00am",
                        "12:00pm","1:00pm","2:00pm","3:00pm","4:00pm","5:00pm","6:00pm","7:pm","8:00pm","9:00pm","10:00pm","11:00pm"};
                HashMap<String, Integer> converter = new HashMap<>();
                int convertTime = 0;
                for(String time : times){
                    converter.put(time, convertTime);
                    convertTime++;
                }
                String chosenTime = (String) JOptionPane.showInputDialog(null, "Pick the time to change it to", "Choose time", JOptionPane.ERROR_MESSAGE, null, times, times[0]);
                //Reschedule the task
                if(chosenTime != null){
                    for(Entry entry : problemEntries){
                        if(entry.getTask() == chosenTask){
                            entry.setStartTime(converter.get(chosenTime));
                        }
                    }
                    //If user does not reschedule the task the program will stop
                }else{
                    System.out.println("ERROR Schedule could not be completed: Reschedule medical task.");
                    return;
                }
            }else{
                System.out.println("ERROR Schedule could not be completed: Reschedule medical task.");
                return;
            }
        }

        schedule.generateSchedule();
        //to be used for the GUI and message for if volunteer is needed
        boolean isVolunteerNeeded = false;

        System.out.printf("%-10s%-50s%-15s%-15s%n", "Time", "Task", "Time spent", "Time Available");
        for (Hour hour : schedule.getFinalSchedule()) {
            String timeStr = (hour.getTime() < 13) ? (hour.getTime() + " am") : ((hour.getTime() - 12) + " pm");
            int timeSpent = 0;
            int timeAvailable = 60;
            for (int i = 0; i < hour.getTasks().size(); i++) {
                timeSpent += hour.getTasks().get(i).getDuration();
                timeAvailable -= hour.getTasks().get(i).getDuration();
                //Sets the volunteer needed to true if time available is less than 0
                if (timeAvailable < 0) {
                    isVolunteerNeeded = true;
                    timeAvailable = 0;
                }
                //prints all the statements
                String name = hour.getTasks().get(i).getName();
                String task = hour.getTasks().get(i).getTask();
                String animalType = hour.getTasks().get(i).getAnimalType();
                if (i == 0) {
                    System.out.printf("%-10s%-50s%-15d%-15d%n", timeStr, task + " for " + name + " (" + animalType + ")", timeSpent, timeAvailable);
                } else {
                    System.out.printf("%-10s%-50s%-15d%-15d%n", "", task + " for " + name + " (" + animalType + ")", timeSpent, timeAvailable);
                }
            }
            if (isVolunteerNeeded) {
                //If volunteer is needed the GUI will inform the user to confirm an extra volunteer
                //If they don't confirm the GUI will stop creating the schedule
                String message = "An extra volunteer is needed on " + timeStr;
                int input = JOptionPane.showConfirmDialog(null, message, "Volunteer needed", JOptionPane.DEFAULT_OPTION);
                if (input != 0) {
                    System.out.println("ERROR Schedule could not be completed: Confirm volunteer.");
                    return;
                }
                System.out.println("*Backup volunteer needed");
            }
            System.out.print("\n");
        }
    }
}
