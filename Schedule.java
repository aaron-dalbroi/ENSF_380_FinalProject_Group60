import java.sql.*;

public class Schedule {

static public void main(String args[]){


    SqLConnection database = new SqLConnection();
    database.createConnection();
    database.print();
}

}
