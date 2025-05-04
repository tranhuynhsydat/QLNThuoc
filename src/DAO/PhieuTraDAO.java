package DAO;

import ConnectDB.DatabaseConnection;
import Entity.PhieuTra;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PhieuTraDAO {

    // Get a batch of CTPhieuTra (pagination)
    public static List<PhieuTra> getHoaDonTraBatch(int startIndex, int batchSize) {
        List<PhieuTra> danhSachHoaDonTra = new ArrayList<>();
        String sql = "SELECT * FROM CTPhieuTra ORDER BY maPT OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, startIndex);  // Start index for pagination
            ps.setInt(2, batchSize);    // Number of records to fetch

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PhieuTra hoaDonTra = new PhieuTra();
                    hoaDonTra.setMaPT(rs.getString("maPT"));
                    hoaDonTra.setTenKhachHang(rs.getString("tenKhachHang"));
                    hoaDonTra.setSdt(rs.getString("sdt"));
                    hoaDonTra.setTenNhanVien(rs.getString("tenNhanVien"));
                    hoaDonTra.setNgayDoi(rs.getString("ngayDoi"));
                    hoaDonTra.setLyDo(rs.getString("lyDo"));
                    hoaDonTra.setTongTien(rs.getFloat("tongTien"));

                    danhSachHoaDonTra.add(hoaDonTra);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi lấy danh sách hóa đơn trả từ CTPhieuTra: " + e.getMessage());
        }

        return danhSachHoaDonTra;
    }

    // Insert a new PhieuTra (Return Invoice)
    public static boolean themHoaDonTra(PhieuTra hoaDonTra) {
        String sql = "INSERT INTO CTPhieuTra (maPT, tenKhachHang, sdt, tenNhanVien, ngayDoi, lyDo, tongTien) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, hoaDonTra.getMaPT());
            ps.setString(2, hoaDonTra.getTenKhachHang());
            ps.setString(3, hoaDonTra.getSdt());
            ps.setString(4, hoaDonTra.getTenNhanVien());
            ps.setString(5, hoaDonTra.getNgayDoi());
            ps.setString(6, hoaDonTra.getLyDo());
            ps.setFloat(7, hoaDonTra.getTongTien());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi thêm hóa đơn trả vào CTPhieuTra: " + e.getMessage());
        }

        return false;
    }

    // Update a PhieuTra (Return Invoice)
    public static boolean suaHoaDonTra(PhieuTra hoaDonTra) {
        String sql = "UPDATE CTPhieuTra SET tenKhachHang = ?, sdt = ?, tenNhanVien = ?, ngayDoi = ?, lyDo = ?, tongTien = ? WHERE maPT = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, hoaDonTra.getTenKhachHang());
            ps.setString(2, hoaDonTra.getSdt());
            ps.setString(3, hoaDonTra.getTenNhanVien());
            ps.setString(4, hoaDonTra.getNgayDoi());
            ps.setString(5, hoaDonTra.getLyDo());
            ps.setFloat(6, hoaDonTra.getTongTien());
            ps.setString(7, hoaDonTra.getMaPT());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi cập nhật hóa đơn trả trong CTPhieuTra: " + e.getMessage());
        }

        return false;
    }

    // Delete a PhieuTra (Return Invoice)
    public static boolean xoaHoaDonTra(String maPT) {
        String sql = "DELETE FROM CTPhieuTra WHERE maPT = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maPT);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi xóa hóa đơn trả trong CTPhieuTra: " + e.getMessage());
        }

        return false;
    }
}
