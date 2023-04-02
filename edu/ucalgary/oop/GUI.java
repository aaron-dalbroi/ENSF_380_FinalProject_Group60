package edu.ucalgary.oop;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class GUI implements ActionListener{
    public void actionPerformed(ActionEvent event){
            SqlConnection connection = null;
            try {
                connection = new SqlConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            String result = createShedule();
            String output;
            String task;
            String time;
            if(result == "Schedule need to be updated"){
                ArrayList<Entry> entries = connection.pullTreatmentEntries();
                ArrayList<String> temp = new ArrayList<>();
                for(Entry entry : entries){
                    temp.add(entry.getTask() + " for " + entry.getName() + " at " + entry.getStartTime());
                }
                Object[] options = temp.toArray();
                task = (String) JOptionPane.showInputDialog(null, "Schedule is not possible", "Impossible schedule", JOptionPane.ERROR_MESSAGE, null, options, options[0]);
                if(output != null){
                    String[] newOptions = {"0","1","2","3","4","5","6","7","8","9","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
                    time = (String) JOptionPane.showInputDialog(null, "Pick the time to change it to", "Choose time", JOptionPane.ERROR_MESSAGE, null, newOptions, options[0]);
                }
            }else if(result == "Volunteer needs to be called"){
                int input = JOptionPane.showConfirmDialog(null, "A volunteer needs to added", "Volunteer needed", JOptionPane.DEFAULT_OPTION);
            }else{
                int input = JOptionPane.showConfirmDialog(null, "Schedule", "good", JOptionPane.DEFAULT_OPTION);
            }
        }

    private String createShedule() {
        return "Schedule need to be updated";
    }
}
