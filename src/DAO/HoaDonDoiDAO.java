package DAO;

import ConnectDB.DatabaseConnection;
import Entity.HoaDonDoi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HoaDonDoiDAO {

    // Get a batch of CTPhieuDoi (pagination)
    public static List<HoaDonDoi> getCTPhieuDoiBatch(int startIndex, int batchSize) {
        List<HoaDonDoi> danhSachHoaDonDoi = new ArrayList<>();

        // SQL Server pagination using OFFSET and FETCH NEXT
        String sql = "SELECT * FROM CTPhieuDoi ORDER BY maPD OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, startIndex); // Start index for pagination (e.g., 0)
            ps.setInt(2, batchSize); // Number of records to fetch (e.g., 10)

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HoaDonDoi hoaDonDoi = new HoaDonDoi();
                    // Assuming the CTPhieuDoi table has similar fields to HoaDonDoi
                    hoaDonDoi.setMaPD(rs.getString("maPD"));
                    hoaDonDoi.setTenKhachHang(rs.getString("tenKhachHang"));
                    hoaDonDoi.setSdt(rs.getString("sdt"));
                    hoaDonDoi.setTenNhanVien(rs.getString("tenNhanVien"));
                    hoaDonDoi.setNgayDoi(rs.getString("ngayDoi"));
                    hoaDonDoi.setLyDo(rs.getString("lyDo"));
                    hoaDonDoi.setTongTien(rs.getFloat("tongTien"));

                    danhSachHoaDonDoi.add(hoaDonDoi);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi lấy danh sách hóa đơn đổi từ CTPhieuDoi: " + e.getMessage());
        }

        return danhSachHoaDonDoi;
    }

    // Search invoice changes (HoaDonDoi) by certain parameters
    public static List<HoaDonDoi> searchHoaDonDoi(String maPD, String tenKhachHang, String ngayDoi) {
        List<HoaDonDoi> danhSachHoaDonDoi = new ArrayList<>();
        String sql = "SELECT * FROM HoaDonDoi WHERE 1=1";
        if (maPD != null && !maPD.isEmpty()) {
            sql += " AND maPD LIKE '%" + maPD + "%'";
        }
        if (tenKhachHang != null && !tenKhachHang.isEmpty()) {
            sql += " AND tenKhachHang LIKE '%" + tenKhachHang + "%'";
        }
        if (ngayDoi != null && !ngayDoi.isEmpty()) {
            sql += " AND ngayDoi LIKE '%" + ngayDoi + "%'";
        }

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                HoaDonDoi hoaDonDoi = new HoaDonDoi();
                hoaDonDoi.setMaPD(rs.getString("maPD"));
                hoaDonDoi.setTenKhachHang(rs.getString("tenKhachHang"));
                hoaDonDoi.setSdt(rs.getString("sdt"));
                hoaDonDoi.setTenNhanVien(rs.getString("tenNhanVien"));
                hoaDonDoi.setNgayDoi(rs.getString("ngayDoi"));
                hoaDonDoi.setLyDo(rs.getString("lyDo"));
                hoaDonDoi.setTongTien(rs.getFloat("tongTien"));

                danhSachHoaDonDoi.add(hoaDonDoi);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi tìm kiếm hóa đơn đổi: " + e.getMessage());
        }

        return danhSachHoaDonDoi;
    }

    // Get invoice change by its ID (maPD)
    public static HoaDonDoi getHoaDonDoiByMaPD(String maPD) {
        String sql = "SELECT * FROM HoaDonDoi WHERE maPD = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maPD);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    HoaDonDoi hoaDonDoi = new HoaDonDoi();
                    hoaDonDoi.setMaPD(rs.getString("maPD"));
                    hoaDonDoi.setTenKhachHang(rs.getString("tenKhachHang"));
                    hoaDonDoi.setSdt(rs.getString("sdt"));
                    hoaDonDoi.setTenNhanVien(rs.getString("tenNhanVien"));
                    hoaDonDoi.setNgayDoi(rs.getString("ngayDoi"));
                    hoaDonDoi.setLyDo(rs.getString("lyDo"));
                    hoaDonDoi.setTongTien(rs.getFloat("tongTien"));

                    return hoaDonDoi;
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi lấy hóa đơn đổi theo mã: " + e.getMessage());
        }

        return null;
    }

    // Insert a new invoice change (HoaDonDoi)
    public static boolean themHoaDonDoi(HoaDonDoi hoaDonDoi) {
        String sql = "INSERT INTO HoaDonDoi (maPD, tenKhachHang, sdt, tenNhanVien, ngayDoi, lyDo, tongTien) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, hoaDonDoi.getMaPD());
            ps.setString(2, hoaDonDoi.getTenKhachHang());
            ps.setString(3, hoaDonDoi.getSdt());
            ps.setString(4, hoaDonDoi.getTenNhanVien());
            ps.setString(5, hoaDonDoi.getNgayDoi());
            ps.setString(6, hoaDonDoi.getLyDo());
            ps.setFloat(7, hoaDonDoi.getTongTien());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi thêm hóa đơn đổi: " + e.getMessage());
        }

        return false;
    }

    // Update an invoice change (HoaDonDoi)
    public static boolean suaHoaDonDoi(HoaDonDoi hoaDonDoi) {
        String sql = "UPDATE HoaDonDoi SET tenKhachHang = ?, sdt = ?, tenNhanVien = ?, ngayDoi = ?, lyDo = ?, tongTien = ? WHERE maPD = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, hoaDonDoi.getTenKhachHang());
            ps.setString(2, hoaDonDoi.getSdt());
            ps.setString(3, hoaDonDoi.getTenNhanVien());
            ps.setString(4, hoaDonDoi.getNgayDoi());
            ps.setString(5, hoaDonDoi.getLyDo());
            ps.setFloat(6, hoaDonDoi.getTongTien());
            ps.setString(7, hoaDonDoi.getMaPD());

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi cập nhật hóa đơn đổi: " + e.getMessage());
        }

        return false;
    }

    // Delete an invoice change (HoaDonDoi)
    public static boolean xoaHoaDonDoi(String maPD) {
        String sql = "DELETE FROM HoaDonDoi WHERE maPD = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maPD);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi xóa hóa đơn đổi: " + e.getMessage());
        }

        return false;
    }
}
