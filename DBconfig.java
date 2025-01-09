package DataBaseManager;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class DBconfig {




        private static final String URL = "jdbc:mysql://localhost:3306/Jukebox";
        private static final String USERNAME = "root";
        private static final String PASSWORD = "*******";

        // Method to establish a database connection
        public static Connection getConnection() throws SQLException {
            Connection connection = null;
            try {
                connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                System.out.println("Connected to the database successfully.");
            } catch (SQLException e) {
                System.out.println("Failed to connect to the database. Error: " + e.getMessage());
                throw e;
            }
            return connection;
        }

        // Method to close the database connection
        public static void closeConnection(Connection connection) {
            if (connection != null) {
                try {
                    connection.close();
                    System.out.println("Database connection closed.");
                } catch (SQLException e) {
                    System.out.println("Error closing database connection: " + e.getMessage());
                }

            }
        }
    }


