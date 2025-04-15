/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import ConnectDB.DatabaseConnection;
import Entity.NhaCungCap;
import Entity.NhanVien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 *
 * @author roxan
 */
public class NhaCungCapDAO {
    public static List<NhaCungCap> getAllNhaCungCap() {
        List<NhaCungCap> danhSachNCC = new ArrayList<>();
        String sql = "SELECT * FROM NhaCungCap";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                NhaCungCap ncc = new NhaCungCap(
                        rs.getString("maNCC"),
                        rs.getString("tenNCC"),
                        rs.getString("diaChiNCC"),
                        rs.getString("SDT")            
                );
                danhSachNCC.add(ncc);  // Thêm nhân viên vào danh sách
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhSachNCC;
    }
    public static NhaCungCap getNhaCungCapByMaNCC(String maNCC) {
        NhaCungCap ncc = null;
        String sql = "SELECT * FROM NhaCungCap WHERE maNCC = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maNCC);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    ncc = new NhaCungCap(
                            rs.getString("maNCC"),
                            rs.getString("tenNCC"),
                            rs.getString("diaChiNCC"),
                            rs.getString("SDT")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ncc;
    }
    public static String TaoMaNCC() {
        String prefix = "NCC-";
        Set<Integer> existingNumbers = new HashSet<>();  // Lưu trữ các mã nhân viên đã có

        // Lấy tất cả các mã nhân viên hiện có trong cơ sở dữ liệu
        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT maNCC FROM NhaCungCap WHERE maNCC LIKE 'NCC-%'")) {

            while (rs.next()) {
                String ma = rs.getString("maNCC");
                if (ma != null && ma.startsWith(prefix)) {
                    try {
                        int num = Integer.parseInt(ma.substring(4));  // Lấy số từ mã "NV-xxx"
                        existingNumbers.add(num);  // Thêm vào danh sách các mã đã tồn tại
                    } catch (NumberFormatException e) {
                        // Nếu có lỗi trong việc chuyển đổi số, bỏ qua
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Tìm mã trống (ví dụ: nếu có NV-001, NV-003 thì NV-002 sẽ là trống)
        int newNumber = 1;
        while (existingNumbers.contains(newNumber)) {
            newNumber++;  // Tìm số tiếp theo chưa có trong cơ sở dữ liệu
        }

        // Trả về mã mới theo định dạng NV-xxx
        return prefix + String.format("%03d", newNumber);
    }
    public static boolean sua(NhaCungCap ncc) {
        String sql = "UPDATE NhaCungCap SET tenNCC = ?, diaChiNCC = ?, SDT = ? WHERE maNCC = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ncc.getTenNhaCungCap());
            ps.setString(2, ncc.getDiaChi());
            ps.setString(3, ncc.getSdt());
            ps.setString(4, ncc.getId());  // Mã nhân viên không thay đổi

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    // Hàm thêm nhân viên vào cơ sở dữ liệu
    public static boolean Them(NhaCungCap ncc) {
        String sql = "INSERT INTO NhaCungCap (maNCC, tenNCC, diaChiNCC, SDT) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ncc.getId()); // Mã nhân viên được sinh tự động
            ps.setString(2, ncc.getTenNhaCungCap());
            ps.setString(3, ncc.getDiaChi());
            ps.setString(4, ncc.getSdt());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage());
        }
        return false;
    }
    public static boolean xoa(String maNCC) {
        String sql = "DELETE FROM NhaCungCap WHERE maNCC = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maNCC);  // Truyền mã nhân viên cần xóa vào PreparedStatement

            return ps.executeUpdate() > 0;  // Thực thi câu lệnh và kiểm tra xem có bị ảnh hưởng dòng nào không

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public static List<NhaCungCap> getNhaCungCapBatch(int start, int limit) {
        List<NhaCungCap> danhSachNhaCungCap = new ArrayList<>();
        String sql = "SELECT * FROM NhaCungCap ORDER BY maNCC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";  // Sử dụng cú pháp đúng cho SQL Server

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, start);  // Chỉ mục bắt đầu (OFFSET)
            ps.setInt(2, limit);  // Số dòng cần lấy (FETCH NEXT)

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String maNCC = rs.getString("maNCC");
                    String tenNCC = rs.getString("tenNCC");
                    String diaChiNCC = rs.getString("diaChiNCC");
                    String sdt = rs.getString("SDT");
                    // Thêm thuốc vào danh sách
                    danhSachNhaCungCap.add(new NhaCungCap(maNCC, tenNCC, diaChiNCC, sdt));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachNhaCungCap;
    }
}
