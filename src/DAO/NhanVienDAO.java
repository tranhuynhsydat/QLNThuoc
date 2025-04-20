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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;

/**
 *
 * @author roxan
 */
public class NhanVienDAO {

    public static List<NhanVien> getAllNhanVien() {
        List<NhanVien> danhSachNhanVien = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                NhanVien nv = new NhanVien(
                        rs.getString("maNV"),
                        rs.getString("hoTen"),
                        rs.getString("sdt"),
                        rs.getString("gioiTinh"),
                        rs.getDate("dtSinh"),
                        rs.getDate("ngayVaoLam"),
                        rs.getString("chucVu"),
                        rs.getString("cccd")
                );
                danhSachNhanVien.add(nv);  // Thêm nhân viên vào danh sách
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhSachNhanVien;
    }

    public static List<NhanVien> getNhanVienBatch(int start, int limit) {
        List<NhanVien> danhSachNhanVien = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien ORDER BY maNV OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";  // SQL Server syntax

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, start);  // Chỉ mục bắt đầu (OFFSET)
            ps.setInt(2, limit);  // Số dòng cần lấy (FETCH NEXT)

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    NhanVien nv = new NhanVien(
                            rs.getString("maNV"),
                            rs.getString("hoTen"),
                            rs.getString("sdt"),
                            rs.getString("gioiTinh"),
                            rs.getDate("dtSinh"),
                            rs.getDate("ngayVaoLam"),
                            rs.getString("chucVu"),
                            rs.getString("cccd")
                    );
                    danhSachNhanVien.add(nv);  // Thêm nhân viên vào danh sách
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhSachNhanVien;
    }

    public static NhanVien getNhanVienBySdt(String sdt) {
        NhanVien nv = null;
        String sql = "SELECT * FROM NhanVien WHERE sdt = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sdt);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    nv = new NhanVien(
                            rs.getString("maNV"),
                            rs.getString("hoTen"),
                            rs.getString("gioiTinh"),
                            rs.getString("sdt"),
                            rs.getDate("dtSinh"),
                            rs.getDate("ngayVaoLam"),
                            rs.getString("chucVu"),
                            rs.getString("cccd")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nv;
    }

    public static NhanVien getNhanVienByMaNV(String maNV) {
        NhanVien nv = null;
        String sql = "SELECT * FROM NhanVien WHERE maNV = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maNV);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    nv = new NhanVien(
                            rs.getString("maNV"),
                            rs.getString("hoTen"),
                            rs.getString("sdt"),
                            rs.getString("gioiTinh"),
                            rs.getDate("dtSinh"),
                            rs.getDate("ngayVaoLam"),
                            rs.getString("CCCD"),
                            rs.getString("chucVu")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nv;
    }

    public static NhanVien getNhanVienHoTenChucVu(String maNV) {
        NhanVien nv = null;
        String sql = "SELECT maNV, hoTen, chucVu FROM NhanVien WHERE maNV = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maNV);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    nv = new NhanVien(
                            rs.getString("maNV"),
                            rs.getString("hoTen"),
                            rs.getString("chucVu")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return nv;
    }

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

    public static boolean sua(NhanVien nv) {
        String sql = "UPDATE NhanVien SET hoTen = ?, sdt = ?, gioiTinh = ?, dtSinh = ?, ngayVaoLam = ?, chucVu = ?, cccd = ? WHERE maNV = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nv.getHoTen());
            ps.setString(2, nv.getSdt());
            ps.setString(3, nv.getGioiTinh());
            ps.setDate(4, new java.sql.Date(nv.getDtSinh().getTime()));
            ps.setDate(5, new java.sql.Date(nv.getNgayVaoLam().getTime()));
            ps.setString(6, nv.getChucVu());
            ps.setString(7, nv.getCccd());
            ps.setString(8, nv.getId());  // Mã nhân viên không thay đổi

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean xoa(String maNV) {
        String sql = "DELETE FROM NhanVien WHERE maNV = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maNV);  // Truyền mã nhân viên cần xóa vào PreparedStatement

            return ps.executeUpdate() > 0;  // Thực thi câu lệnh và kiểm tra xem có bị ảnh hưởng dòng nào không

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<NhanVien> searchNhanVien(String hoTen, String gioiTinh, Date ngaySinh, Date ngayVaoLam, String sdt, String cccd, String chucVu) {
        List<NhanVien> danhSachNhanVien = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE "
                + "(hoTen LIKE ? OR ? = '') AND "
                + "(gioiTinh LIKE ? OR ? = '') AND "
                + "(dtSinh = ? OR ? IS NULL) AND "
                + "(ngayVaoLam = ? OR ? IS NULL) AND "
                + "(sdt LIKE ? OR ? = '') AND "
                + "(cccd LIKE ? OR ? = '') AND "
                + "(chucVu LIKE ? OR ? = '')";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + hoTen + "%");
            ps.setString(2, hoTen);
            ps.setString(3, "%" + gioiTinh + "%");
            ps.setString(4, gioiTinh);
            ps.setDate(5, ngaySinh != null ? new java.sql.Date(ngaySinh.getTime()) : null);
            ps.setDate(6, ngaySinh != null ? new java.sql.Date(ngaySinh.getTime()) : null);
            ps.setDate(7, ngayVaoLam != null ? new java.sql.Date(ngayVaoLam.getTime()) : null);
            ps.setDate(8, ngayVaoLam != null ? new java.sql.Date(ngayVaoLam.getTime()) : null);
            ps.setString(9, "%" + sdt + "%");
            ps.setString(10, sdt);
            ps.setString(11, "%" + cccd + "%");
            ps.setString(12, cccd);
            ps.setString(13, "%" + chucVu + "%");
            ps.setString(14, chucVu);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    NhanVien nv = new NhanVien(
                            rs.getString("maNV"),
                            rs.getString("hoTen"),
                            rs.getString("sdt"),
                            rs.getString("gioiTinh"),
                            rs.getDate("dtSinh"),
                            rs.getDate("ngayVaoLam"),
                            rs.getString("chucVu"),
                            rs.getString("cccd")
                    );
                    danhSachNhanVien.add(nv);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhSachNhanVien;
    }

}
