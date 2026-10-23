package hostelmanagementsystem.database;

import java.sql.Connection;
import java.sql.DriverManager;

/**
 * LAYER 2 – DATABASE
 * Centralised JDBC connection factory.
 * All DAO classes call DBConnection.getConnection().
 */
public class DBConnection {

    private static final String URL  = "jdbc:mysql://localhost:3306/hostel_db";
    private static final String USER = "root";
    private static final String PASS = "";          // change if your MySQL has a password

    public static Connection getConnection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(URL, USER, PASS);
        } catch (Exception e) {
            System.out.println("[DB] Connection failed: " + e.getMessage());
            return null;
        }
    }
}
