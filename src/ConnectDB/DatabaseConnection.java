package ConnectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databasename=QLNThuoc;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa";  
    private static final String PASSWORD = "sapassword";  

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
