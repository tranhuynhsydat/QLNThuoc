/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import ConnectDB.DatabaseConnection;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Entity.NhanVien;
import java.util.HashSet;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 *
 * @author roxan
 */
public class NhanVienDAO {

    public static String TaoMaNhanVien() {
        String prefix = "NV-";
        Set<Integer> existingNumbers = new HashSet<>();  // Lưu trữ các mã nhân viên đã có

        // Lấy tất cả các mã nhân viên hiện có trong cơ sở dữ liệu
        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT maNV FROM NhanVien WHERE maNV LIKE 'NV-%'")) {

            while (rs.next()) {
                String ma = rs.getString("maNV");
                if (ma != null && ma.startsWith(prefix)) {
                    try {
                        int num = Integer.parseInt(ma.substring(3));  // Lấy số từ mã "NV-xxx"
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

    // Hàm thêm nhân viên vào cơ sở dữ liệu
    public static boolean Them(NhanVien nv) {
        String sql = "INSERT INTO NhanVien (maNV, hoTen, sdt, gioiTinh, dtSinh, ngayVaoLam,chucVu, cccd) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nv.getId()); // Mã nhân viên được sinh tự động
            ps.setString(2, nv.getHoTen());
            ps.setString(3, nv.getSdt());
            ps.setString(4, nv.getGioiTinh());
            ps.setDate(5, new java.sql.Date(nv.getDtSinh().getTime()));
            ps.setDate(6, new java.sql.Date(nv.getNgayVaoLam().getTime()));
            ps.setString(7, nv.getChucVu());
            ps.setString(8, nv.getCccd());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage());
        }
        return false;
    }

}
