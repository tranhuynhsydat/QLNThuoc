package ConnectDB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Date;

public class DatabaseConnection {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databasename=QLNThuoc;encrypt=true;trustServerCertificate=true;";
    private static final String USER = "sa";  
    private static final String PASSWORD = "sapassword";  

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void update(String INSERT_SQL, String maPN, String id, String id0, String trangThai, Date thoiGian) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    public static void update(String DELETE_BY_ID, String maPN) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
