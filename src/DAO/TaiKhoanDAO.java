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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
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
        Set<Integer> existingNumbers = new HashSet<>();  // Lưu trữ các mã tài khoản đã có

        // Lấy tất cả các mã tài khoản hiện có trong cơ sở dữ liệu
        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT maTK FROM TaiKhoan WHERE maTK LIKE 'TK-%'")) {

            while (rs.next()) {
                String ma = rs.getString("maTK");
                if (ma != null && ma.startsWith(prefix)) {
                    try {
                        int num = Integer.parseInt(ma.substring(3));  // Lấy số từ mã "TK-xxx"
                        existingNumbers.add(num);  // Thêm vào danh sách các mã đã tồn tại
                    } catch (NumberFormatException e) {
                        // Nếu có lỗi trong việc chuyển đổi số, bỏ qua
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Tìm mã trống (ví dụ: nếu có TK-001, TK-003 thì TK-002 sẽ là trống)
        int newNumber = 1;
        while (existingNumbers.contains(newNumber)) {
            newNumber++;  // Tìm số tiếp theo chưa có trong cơ sở dữ liệu
        }

        // Trả về mã mới theo định dạng TK-XXX
        return prefix + String.format("%03d", newNumber);
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

            ps.setString(1, tk.getId());  // Mã tài khoản (sinh tự động)
            ps.setString(2, tk.getUsername());  // Tên đăng nhập
            ps.setString(3, tk.getPassword());  // Mật khẩu
            ps.setString(4, tk.getNhanVien().getId());  // Mã nhân viên

            return ps.executeUpdate() > 0;  // Nếu có dòng bị ảnh hưởng, trả về true

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage());
        }
        return false;
    }

    public static boolean sua(TaiKhoan tk) {
        String sql = "UPDATE TaiKhoan SET UserName = ?, Password = ?, maNV = ? WHERE maTK = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tk.getUsername());  // Tên đăng nhập mới
            ps.setString(2, tk.getPassword());  // Mật khẩu mới
            ps.setString(3, tk.getNhanVien().getId());  // ID nhân viên
            ps.setString(4, tk.getId());  // Mã tài khoản cần sửa

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;  // Nếu có ít nhất một dòng bị ảnh hưởng thì cập nhật thành công
        } catch (SQLException e) {
            e.printStackTrace();
            return false;  // Trả về false nếu có lỗi
        }
    }

    public static boolean xoa(String maTK) {
        String sql = "DELETE FROM TaiKhoan WHERE maTK = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maTK);  // Truyền mã tài khoản cần xóa vào PreparedStatement

            return ps.executeUpdate() > 0;  // Nếu có dòng bị ảnh hưởng, trả về true

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<TaiKhoan> tim(String tenTaiKhoan, String tenNhanVien, String chucVu) {
        List<TaiKhoan> taiKhoanList = new ArrayList<>();
        String sql = "SELECT * FROM TaiKhoan tk "
                + "JOIN NhanVien nv ON tk.maNV = nv.maNV "
                + "WHERE tk.UserName LIKE ? "
                + "AND nv.HoTen LIKE ? "
                + "AND nv.ChucVu LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + tenTaiKhoan + "%");
            ps.setString(2, "%" + tenNhanVien + "%");
            ps.setString(3, "%" + chucVu + "%");

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String maTK = rs.getString("maTK");
                    String username = rs.getString("UserName");
                    String password = rs.getString("Password");
                    String maNV = rs.getString("maNV");
                    NhanVien nv = NhanVienDAO.getNhanVienByMaNV(maNV);

                    TaiKhoan tk = new TaiKhoan(maTK, username, password, nv);
                    taiKhoanList.add(tk);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return taiKhoanList;
    }

    public TaiKhoan dangNhap(String tenDangNhap, String matKhau) {
        TaiKhoan tk = null;

        String sql = "SELECT tk.maTK AS id, tk.UserName, tk.Password, "
                + "nv.maNV AS MaNV, nv.HoTen, nv.ChucVu, nv.GioiTinh "
                + // thêm GioiTinh
                "FROM TaiKhoan tk "
                + "JOIN NhanVien nv ON tk.maNV = nv.maNV "
                + "WHERE tk.UserName = ? AND tk.Password = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, tenDangNhap);
            ps.setString(2, matKhau);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                NhanVien nv = new NhanVien(
                        rs.getString("MaNV"),
                        rs.getString("HoTen"),
                        rs.getString("ChucVu")
                );
                nv.setGioiTinh(rs.getString("GioiTinh"));
                
                tk = new TaiKhoan(
                        rs.getString("id"),
                        rs.getString("UserName"),
                        rs.getString("Password"),
                        nv
                );
            }

            rs.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return tk;
    }
    public static boolean kiemTraTenTaiKhoanTonTai(String userName) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean exists = false;

        try {
            conn = DatabaseConnection.getConnection(); // hoặc kết nối bạn đang dùng
            String sql = "SELECT * FROM TaiKhoan WHERE UserName = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, userName);
            rs = stmt.executeQuery();

            if (rs.next()) {
                exists = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }

        return exists;
    }
    public static boolean kiemTraNhanVienDaCoTaiKhoan(String maNV) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean exists = false;

        try {
            conn = DatabaseConnection.getConnection(); // hoặc tên phương thức kết nối DB của bạn
            String sql = "SELECT * FROM TaiKhoan WHERE maNV = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, maNV);
            rs = stmt.executeQuery();

            if (rs.next()) {
                exists = true; // Nhân viên này đã có tài khoản
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { if (rs != null) rs.close(); } catch (Exception e) {}
            try { if (stmt != null) stmt.close(); } catch (Exception e) {}
            try { if (conn != null) conn.close(); } catch (Exception e) {}
        }

        return exists;
    }
    public static boolean xoaTaiKhoanTheoMaNV(String maNV) {
    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement("DELETE FROM TaiKhoan WHERE maNV = ?")) {
        stmt.setString(1, maNV);
        int rows = stmt.executeUpdate();
        return rows > 0;
    } catch (Exception e) {
        e.printStackTrace();
        return false;
    }
}


}
