# ENSF_380_FinalProject_Group60

EWR schedule builder app

What it does:

The EWR Schedule Builder, is designed to access a database and retrieve all the information needed to generate the daily schedule. It will be presented as a .TXT file. Each hour of the day will be displayed along side all the tasks to be completed within that time frame.


Should there not be enough time in the day for all the tasks, a warning will promt the user that an extra volunteer is needed for a certain hour to ensure that all the daily tasks get done on time. As only a max of two people can work at the same time, if that is still not enough, the program will promt the user to change the start time of one or more medical tasks, this will also automatically update the database, ensuring that the schedule remains up-to-date.



INSTRUCTIONS:

setting up-- Go onto the SqlConnection.java, on line 40, a Connection object is defined, as DriverManager.getConnection("jdbc:mysql://localhost/ewr", "USER", "PASSWORD"). You must change USER and PASSWORD to the MySQL User (ssually root) and password needed for your local system.
-- You will also have to compile all the java files again.


Running-- Run the Schedule class to start the main method and you are good to go.