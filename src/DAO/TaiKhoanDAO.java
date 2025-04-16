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
 import Entity.NhanVien;
 import Entity.TaiKhoan;
 import com.formdev.flatlaf.extras.FlatSVGIcon;
 import java.sql.*;
 import java.util.ArrayList;
 import java.util.List;
 import javax.swing.ImageIcon;
 import javax.swing.JOptionPane;
 
 /**
  *
  * @author Admin
  */
 public class TaiKhoanDAO {
     public static List<TaiKhoan> getAllTaiKhoan() {
         List<TaiKhoan> danhSachTaiKhoan = new ArrayList<>();
         String sql = "SELECT * FROM TaiKhoan";
 
         try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {
 
             while (rs.next()) {
                 String maTK = rs.getString("maTK");
                 String username = rs.getString("UserName");
                 String password = rs.getString("Password");
                 String maNV = rs.getString("maNV");
                 NhanVien nv = NhanVienDAO.getNhanVienByMaNV(maNV);
 
                 // Lấy ảnh dưới dạng byte[]
                
                 // Thêm thuốc vào danh sách
                 danhSachTaiKhoan.add(new TaiKhoan(maTK, username, password, nv));
             }
         } catch (SQLException e) {
             e.printStackTrace();
         }
 
         return danhSachTaiKhoan;
     }
     public static String TaoMaTaiKhoan() {
         String prefix = "TK-";
         int maxNumber = 0;
 
         try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT maTK FROM TaiKhoan WHERE maTK LIKE 'TK-%'")) {
 
             while (rs.next()) {
                 String ma = rs.getString("maTK");
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
     public static TaiKhoan getTaiKhoanByMaTK(String maTK) {
         TaiKhoan taiKhoan = null;
         String sql = "SELECT * FROM TaiKhoan WHERE maTK = ?";
 
         try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
 
             // Set mã thuốc vào truy vấn
             ps.setString(1, maTK);
 
             // Thực thi truy vấn
             try (ResultSet rs = ps.executeQuery()) {
                 if (rs.next()) {
                     // Lấy thông tin từ ResultSet
                     String username = rs.getString("UserName");
                     String password = rs.getString("Password");
                     String maNV = rs.getString("maNV");
                     NhanVien nv = NhanVienDAO.getNhanVienByMaNV(maNV);
                     // Tạo đối tượng Thuoc với tất cả thông tin đã lấy
                     taiKhoan = new TaiKhoan(maTK, username, password, nv);
                 }
             }
 
         } catch (SQLException e) {
             e.printStackTrace(); // In lỗi nếu có vấn đề với truy vấn SQL
         }
         return taiKhoan;
     }
     public static List<TaiKhoan> getTaiKhoanBatch(int start, int limit) {
         List<TaiKhoan> danhSachTaiKhoan = new ArrayList<>();
         String sql = "SELECT * FROM TaiKhoan ORDER BY maTK OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";  // Sử dụng cú pháp đúng cho SQL Server
 
         try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
             ps.setInt(1, start);  // Chỉ mục bắt đầu (OFFSET)
             ps.setInt(2, limit);  // Số dòng cần lấy (FETCH NEXT)
 
             try (ResultSet rs = ps.executeQuery()) {
                 while (rs.next()) {
                     String maTK = rs.getString("maTK");
                     String username = rs.getString("UserName");
                     String password = rs.getString("Password");
                     String maNV = rs.getString("maNV");
                     NhanVien nv = NhanVienDAO.getNhanVienByMaNV(maNV);
                     // Thêm thuốc vào danh sách
                     danhSachTaiKhoan.add(new TaiKhoan(maTK, username, password, nv));
                 }
             }
         } catch (SQLException e) {
             e.printStackTrace();
         }
         return danhSachTaiKhoan;
     }
    public static boolean Them(TaiKhoan tk) {
        String sql = "INSERT INTO TaiKhoan (maTK, UserName, Password, maNV) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tk.getId()); // Mã nhân viên được sinh tự động
            ps.setString(2, tk.getUsername());
            ps.setString(3, tk.getPassword());
            ps.setString(4, tk.getNhanVien().getId());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage());
        }
        return false;
    }
    public static boolean sua(TaiKhoan tk) {
        String sql = "UPDATE TaiKhoan SET maTK = ?, UserName = ?, Password = ?, maNV = ? WHERE maTK = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tk.getId()); // Mã nhân viên được sinh tự động
            ps.setString(2, tk.getUsername());
            ps.setString(3, tk.getPassword());
            ps.setString(4, tk.getNhanVien().getHoTen());
            ps.setString(5, tk.getNhanVien().getChucVu());
            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static boolean xoa(String maNV) {
        String sql = "DELETE FROM TaiKhoan WHERE maTK = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maNV);  // Truyền mã nhân viên cần xóa vào PreparedStatement

            return ps.executeUpdate() > 0;  // Thực thi câu lệnh và kiểm tra xem có bị ảnh hưởng dòng nào không

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

//    public static List<TaiKhoan> searchTaiKhoan(String UserName, String Password, String maNV) {
//    List<TaiKhoan> danhSachTaiKhoan = new ArrayList<>();
//    String sql = "SELECT * FROM TaiKhoan WHERE "
//        + "(UserName LIKE ? OR ? = '') AND "
//        + "(Password LIKE ? OR ? = '') AND "
//        + "(maNV = ? OR ? IS NULL)";
//
//    try (Connection conn = DatabaseConnection.getConnection(); 
//         PreparedStatement ps = conn.prepareStatement(sql)) {
//
//        ps.setString(1, "%" + UserName + "%");
//        ps.setString(2, UserName);
//        ps.setString(3, "%" + Password + "%");
//        ps.setString(4, Password);
//        ps.setString(5, maNV);
//        ps.setString(6, maNV);
//
//        try (ResultSet rs = ps.executeQuery()) {
//            while (rs.next()) {
//                NhanVien nv = new NhanVien();
//                nv.setMaNV(rs.getString("maNV")); // hoặc new NhanVien(rs.getString("maNV"))
//
//                TaiKhoan tk = new TaiKhoan(
//                    rs.getString("maTK"),
//                    rs.getString("UserName"),
//                    rs.getString("Password"),
//                    nv
//                );
//
//                danhSachTaiKhoan.add(tk);
//            }
//        }
//
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }
//
//    return danhSachTaiKhoan;
//}

 }