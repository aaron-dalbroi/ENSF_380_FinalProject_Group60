package edu.ucalgary.oop;
import org.junit.Test;

import java.sql.SQLException;
import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 *
 * To properly run these tests, ensure that YOUR LOGIN INFO is inputted into SqlConnection.java,
 * and that you have the database EWR is exists on your device. These tests DO NOT depend on the info in the database.
 *
 */

public class SqlConnectionTest {
    /**
     *
     * Tests whether program successfully connects to database
     * All the constructor for SqlConnection does is call the createConnection function
     */

    @Test
    public void testConnection(){
        try{
            SqlConnection connection = new SqlConnection();
        }catch(Exception e){
            fail("Connection failed to be established, check createConnection() in SqlConnection");
        }
    }

    /**
     *  Tests whether program successfully disconnects from database
     */

    @Test
    public void testDisconnect(){
        try{
            //establish a connection with database
            SqlConnection connection = new SqlConnection();
            //disconnect from database
            connection.closeConnection();
            //Attempt to read from database. This should throw an exception if database properly closed
            ArrayList<Entry> testArray = connection.pullTreatmentEntries();
        }catch(Exception e){
            return;
        }
        fail("Database failed to disconnect.");


}}
