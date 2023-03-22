import java.sql.*;
public class SqLConnection {

    private Connection dbConnect;
    private ResultSet results;


    public void createConnection(){

        try{
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
            String result = results.getString("Description");
                System.out.println(result);

            }

            }
            catch(Exception e){
            System.out.println(e);
            }

        }


    }

