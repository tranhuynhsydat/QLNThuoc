/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author ADMIN
 */
import ConnectDB.DatabaseConnection;
import Entity.ChiTietHoaDon;
import Entity.Thuoc;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChiTietHoaDonDAO {

    public static boolean themChiTietHoaDon(List<ChiTietHoaDon> chiTietList, String maHD) {
        if (chiTietList == null || chiTietList.isEmpty()) {
            return true;  // Không có chi tiết để thêm
        }

        String sql = "INSERT INTO CTHoaDon (maHD, maThuoc, soLuong, donGia) VALUES (?, ?, ?, ?)";
        int successCount = 0;

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            for (ChiTietHoaDon chiTiet : chiTietList) {
                ps.setString(1, maHD);  // Sử dụng maHD truyền vào
                ps.setString(2, chiTiet.getIdThuoc());
                ps.setInt(3, chiTiet.getSoLuong());
                ps.setDouble(4, chiTiet.getDonGia());

                ps.addBatch();  // Thêm vào batch
                successCount++;

                // Cập nhật số lượng thuốc trong kho
                capNhatSoLuongThuoc(chiTiet.getIdThuoc(), chiTiet.getSoLuong());
            }

            // Thực thi batch
            int[] results = ps.executeBatch();
            for (int result : results) {
                if (result == Statement.EXECUTE_FAILED) {
                    System.out.println("Lỗi trong quá trình thêm chi tiết hóa đơn!");
                    return false;  // Nếu có lỗi trong bất kỳ dòng nào của batch
                }
            }

            return successCount == chiTietList.size();  // Kiểm tra tất cả chi tiết đã được thêm
        } catch (SQLException e) {
            System.out.println("Lỗi thêm chi tiết hóa đơn: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    private static void capNhatSoLuongThuoc(String maThuoc, int soLuongBan) {
        // Lấy thông tin thuốc hiện tại
        Thuoc thuoc = ThuocDAO.getThuocByMaThuoc(maThuoc);
        if (thuoc != null) {
            // Cập nhật số lượng (trừ đi số lượng bán)
            int soLuongMoi = thuoc.getSoLuong() - soLuongBan;

            // Cập nhật vào database
            String sql = "UPDATE Thuoc SET soLuong = ? WHERE maThuoc = ?";

            try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, soLuongMoi);
                ps.setString(2, maThuoc);

                ps.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Lỗi cập nhật số lượng thuốc: " + e.getMessage());
            }
        }
    }

    public static List<ChiTietHoaDon> getChiTietByHoaDonId(String hoaDonId) {
        List<ChiTietHoaDon> chiTietList = new ArrayList<>();
        String sql = "SELECT ct.maHD, ct.maThuoc, ct.soLuong, ct.donGia, "
                + "t.tenThuoc "
                + "FROM CTHoaDon ct "
                + "JOIN Thuoc t ON ct.maThuoc = t.maThuoc "
                + "WHERE ct.maHD = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, hoaDonId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ChiTietHoaDon chiTiet = new ChiTietHoaDon();
                    chiTiet.setIdHoaDon(rs.getString("maHD"));
                    chiTiet.setIdThuoc(rs.getString("maThuoc"));
                    chiTiet.setSoLuong(rs.getInt("soLuong"));
                    chiTiet.setDonGia(rs.getDouble("donGia"));
                    chiTiet.setThuoc(rs.getString("tenThuoc"));

                    // Tính thành tiền = số lượng * đơn giá
                    double thanhTien = rs.getInt("soLuong") * rs.getDouble("donGia");
                    chiTiet.setThanhTien(thanhTien);

                    chiTietList.add(chiTiet);
                }
            }

            return chiTietList;

        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy chi tiết hóa đơn: " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    // Cập nhật chi tiết hóa đơn
    public static boolean capNhatChiTietHoaDon(ChiTietHoaDon chiTietHoaDon) {
        String sql = "UPDATE CTHoaDon SET soLuong = ?, donGia = ?, thanhTien = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, chiTietHoaDon.getSoLuong());
            stmt.setDouble(2, chiTietHoaDon.getDonGia());
            stmt.setDouble(3, chiTietHoaDon.getThanhTien());
            stmt.setString(4, chiTietHoaDon.getIdHoaDon());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật chi tiết hóa đơn: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Xóa một chi tiết hóa đơn
    public static boolean xoaChiTietHoaDon(int chiTietId) {
        String sql = "DELETE FROM CTHoaDon WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, chiTietId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa chi tiết hóa đơn: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Xóa tất cả chi tiết của một hóa đơn
    public static boolean xoaChiTietTheoHoaDon(String hoaDonId) {
        String sql = "DELETE FROM CTHoaDon WHERE maHD = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, hoaDonId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa chi tiết theo hóa đơn: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Tính tổng tiền của một hóa đơn
    public static double tinhTongTienHoaDon(String hoaDonId) {
        String sql = "SELECT SUM(thanhTien) AS tong_tien FROM CTHoaDon WHERE maHD = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, hoaDonId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("tong_tien");
                }
            }

            return 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi tính tổng tiền hóa đơn: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }
}
