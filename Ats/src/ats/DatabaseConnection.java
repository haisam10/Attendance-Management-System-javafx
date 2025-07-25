package ats;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {

    public Connection getConnection() {
        Connection connection;
        try {
            String url = "jdbc:mysql://localhost:3306/ats_data";
            String user = "root"; // Change if you use a password
            String password = ""; // Or "your_password"

            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            System.out.println("Error connecting to DB: " + e.getMessage());
            connection = null;
        }
        return connection;
    }
    
    
        public static Connection getConnection(String url, String user, String pass) {
        Connection connection;
        try {
            connection = DriverManager.getConnection(url, user, pass);
        } catch (SQLException e) {
            System.out.println("Error connecting to DB: " + e.getMessage());
            connection = null;
        }
        return connection;
    }

    // Optional default version
    public static Connection getDefaultConnection() {
        return getConnection("jdbc:mysql://localhost:3306/ats_data", "root", "");
        
    }

    static class DBConnector {

        static Connection getConnection() {
            throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
        }

        public DBConnector() {
        }
    }
}
