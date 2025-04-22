/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import java.sql.Connection;
 import java.sql.Statement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
import ConnectDB.DatabaseConnection;
import Entity.NhaCungCap;
import Entity.NhanVien;
import Entity.PhieuNhap;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
/**
 *
 * @author Admin
 */
public class PhieuNhapDAO {
    public static String taoMaPhieuNhap() {
        String prefix = "PN-";  // Tiền tố của mã hóa đơn
        int maxNumber = 0;

        // Truy vấn để lấy mã hóa đơn mới nhất
        String sql = "SELECT maPN FROM PhieuNhap WHERE maPN LIKE 'PN-%' ORDER BY maPN DESC";

        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            // Kiểm tra mã hóa đơn mới nhất
            if (rs.next()) {
                String lastMaHD = rs.getString("maPN");
                // Lấy số từ mã cuối cùng và tăng lên 1
                int lastNumber = Integer.parseInt(lastMaHD.substring(3));  // Lấy số sau tiền tố "HD-"
                maxNumber = lastNumber + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Tạo mã hóa đơn mới với số có 3 chữ số
        return prefix + String.format("%03d", maxNumber);  // Đảm bảo mã hóa đơn có 3 chữ số
    }

    // Lấy tất cả hóa đơn
    
    
}


    

