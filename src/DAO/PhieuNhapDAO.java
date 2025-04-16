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
    
    public static List<PhieuNhap> getAllPhieuNhap() {
        List<PhieuNhap> danhSachPhieuNhap = new ArrayList<>();
        String sql = "SELECT * FROM PhieuNhap";

        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
 
             while (rs.next()) {
                 String  maPN= rs.getString("maPN");
                 String maNV = rs.getString("maNV");
                 NhanVien nv = NhanVienDAO.getNhanVienByMaNV(maNV);
                 String maNCC = rs.getString("maNCC");
                 NhaCungCap ncc = NhaCungCapDAO.getNhaCungCapByMaNCC(maNCC);
                 String trangThai = rs.getString("trangThai");
                 Date thoiGian= rs.getDate("thoiGian");
                 // Lấy ảnh dưới dạng byte[]
                
                 // Thêm thuốc vào danh sách
                 danhSachPhieuNhap.add(new PhieuNhap(maPN, nv, ncc, trangThai,thoiGian));
             }
         } catch (SQLException e) {
             e.printStackTrace();
         }

        return danhSachPhieuNhap;
    }

     public static List<PhieuNhap> getPhieuNhapBatch(int start, int limit) {
    List<PhieuNhap> danhSachPhieuNhap = new ArrayList<>();
    String sql = "SELECT * FROM PhieuNhap ORDER BY maPN OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

    try (Connection conn = DatabaseConnection.getConnection(); 
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, start);  // OFFSET
        ps.setInt(2, limit);  // FETCH NEXT

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String maPN = rs.getString("maPN");
                String maNV = rs.getString("maNV");
                NhanVien nv = NhanVienDAO.getNhanVienByMaNV(maNV);

                String maNCC = rs.getString("maNCC");
                NhaCungCap ncc = NhaCungCapDAO.getNhaCungCapByMaNCC(maNCC);

                String trangThai = rs.getString("trangThai");
                Date thoiGian = rs.getDate("thoiGian");

                danhSachPhieuNhap.add(new PhieuNhap(maPN, nv, ncc, trangThai, thoiGian));
            }
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return danhSachPhieuNhap;
}


    public static PhieuNhap getPhieuNhapByMaPN(String maPN) {
         PhieuNhap phieuNhap = null;
         String sql = "SELECT * FROM PhieuNhap WHERE maPN = ?";
 
         try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
 
             // Set mã thuốc vào truy vấn
             ps.setString(1, maPN);
 
             // Thực thi truy vấn
             try (ResultSet rs = ps.executeQuery()) {
                 if (rs.next()) {
                     // Lấy thông tin từ ResultSet
                 String maNV = rs.getString("maNV");
                 NhanVien nv = NhanVienDAO.getNhanVienByMaNV(maNV);
                 String maNCC = rs.getString("maNCC");
                 NhaCungCap ncc = NhaCungCapDAO.getNhaCungCapByMaNCC(maNCC);
                 String trangThai = rs.getString("trangThai");
                 Date thoiGian= rs.getDate("thoiGian");
                 }
             }
 
         } catch (SQLException e) {
             e.printStackTrace(); // In lỗi nếu có vấn đề với truy vấn SQL
         }
         return phieuNhap;
     }
    public static String TaoMaPhieuNhap() {
         String prefix = "PN-";
         int maxNumber = 0;
 
         try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT maPN FROM PhieuNhap WHERE maPN LIKE 'PN-%'")) {
 
             while (rs.next()) {
                 String ma = rs.getString("maPN");
                 if (ma != null && ma.startsWith(prefix)) {
                     try {
                         int num = Integer.parseInt(ma.substring(2));
                         if (num > maxNumber) {
                             maxNumber = num;
                         }
                     } catch (NumberFormatException e) {
                         // Bỏ qua nếu không thể chuyển mã thuốc thành số
                     }
                 }
             }
         } catch (SQLException e) {
             e.printStackTrace();
         }
 
         int newNumber = maxNumber + 1;
         return prefix + String.format("%03d", newNumber);  // Đảm bảo mã thuốc có 3 chữ số
     }
}
