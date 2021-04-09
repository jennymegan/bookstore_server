import java.sql.*;

public class BookstoreDBConnector {

    //Connect to the database and return the connection
    public static Connection connect() throws SQLException{
        return DriverManager.getConnection("jdbc:mysql://localhost:3306/online_bookstore?useSSL=false", "root", "password");
    }

}

