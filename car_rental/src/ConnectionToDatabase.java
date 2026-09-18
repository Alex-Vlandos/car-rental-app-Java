import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionToDatabase {
    private String database = "jdbc:mysql://localhost/car_rental_db";
    private String username = "root";
    private String password = "";
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(database,username,password);
    }
}
