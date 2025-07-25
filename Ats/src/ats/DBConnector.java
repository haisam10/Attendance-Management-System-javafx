package ats;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnector {

    public static Connection getConnection() {
        Connection connection;
        try {
            String url = "jdbc:mysql://localhost:3306/ats_data";
            String user = "root"; // তোমার MySQL username
            String password = ""; // যদি password থাকে এখানে দাও
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Error connecting to DB: " + e.getMessage());
            connection = null;
        }
        return connection;
    }
}
