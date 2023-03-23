import java.sql.*;


/**
 * Handles SQL connection with the program
 *
 * Connection dbConnect - SQL connection object for initializing connection with database
 * ResultSet results - The results of queries will be in this object
 *
 *
 */

public class SqLConnection {

    private Connection dbConnect;
    private ResultSet results;


    /* SqLConnection Constructor
     * Calls createConnection method in order to establish link between program and database
     * Throws SQL exception if connection fails
     *
     */

    public SqLConnection(){
    createConnection();
    }

    public void createConnection(){

        try{
            //the connection info here will need to be changed depending on the user
            dbConnect = DriverManager.getConnection("jdbc:mysql://localhost/ewr", "root", "AbXy219!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void print(){

        try {
            Statement myStmt = dbConnect.createStatement();
            myStmt.executeQuery("USE EWR");
            results = myStmt.executeQuery("SELECT * FROM TASKS");

            while(results.next()){
            String result = results.getString(1);
                System.out.println(result);

            }

            }
            catch(Exception e){
            System.out.println(e);
            }

        }


    }

