package DAO;

import ConnectDB.DatabaseConnection;
import Entity.HoaDonTra;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HoaDonTraDAO {

    // Get a batch of CTPhieuTra (pagination)
    public static List<HoaDonTra> getHoaDonTraBatch(int startIndex, int batchSize) {
        List<HoaDonTra> danhSachHoaDonTra = new ArrayList<>();
        String sql = "SELECT * FROM CTPhieuTra ORDER BY maPD OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, startIndex);  // Start index for pagination
            ps.setInt(2, batchSize);    // Number of records to fetch

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HoaDonTra hoaDonTra = new HoaDonTra();
                    hoaDonTra.setMaPD(rs.getString("maPD"));
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

    // Insert a new HoaDonTra (Return Invoice)
    public static boolean themHoaDonTra(HoaDonTra hoaDonTra) {
        String sql = "INSERT INTO CTPhieuTra (maPD, tenKhachHang, sdt, tenNhanVien, ngayDoi, lyDo, tongTien) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, hoaDonTra.getMaPD());
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

    // Update a HoaDonTra (Return Invoice)
    public static boolean suaHoaDonTra(HoaDonTra hoaDonTra) {
        String sql = "UPDATE CTPhieuTra SET tenKhachHang = ?, sdt = ?, tenNhanVien = ?, ngayDoi = ?, lyDo = ?, tongTien = ? WHERE maPD = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, hoaDonTra.getTenKhachHang());
            ps.setString(2, hoaDonTra.getSdt());
            ps.setString(3, hoaDonTra.getTenNhanVien());
            ps.setString(4, hoaDonTra.getNgayDoi());
            ps.setString(5, hoaDonTra.getLyDo());
            ps.setFloat(6, hoaDonTra.getTongTien());
            ps.setString(7, hoaDonTra.getMaPD());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi cập nhật hóa đơn trả trong CTPhieuTra: " + e.getMessage());
        }

        return false;
    }

    // Delete a HoaDonTra (Return Invoice)
    public static boolean xoaHoaDonTra(String maPD) {
        String sql = "DELETE FROM CTPhieuTra WHERE maPD = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maPD);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi xóa hóa đơn trả trong CTPhieuTra: " + e.getMessage());
        }

        return false;
    }
}
