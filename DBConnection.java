import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/establishmentDB";
    private static final String USER = "root";
    private static final String PASSWORD = "12345678"; // change to your password if its different than mine 

    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.out.println("Failed to connect to database");
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
}