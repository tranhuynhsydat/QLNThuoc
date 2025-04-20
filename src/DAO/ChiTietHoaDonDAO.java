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
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChiTietHoaDonDAO {

    // Thêm chi tiết hóa đơn vào CSDL
    public static boolean themChiTietHoaDon(ChiTietHoaDon chiTietHoaDon) {
        String sql = "INSERT INTO CTHoaDon (maHD, maThuoc, soLuong, donGia) " +
                "VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, chiTietHoaDon.getIdHoaDon());
            stmt.setString(2, chiTietHoaDon.getIdThuoc());
            stmt.setInt(3, chiTietHoaDon.getSoLuong());
            stmt.setDouble(4, chiTietHoaDon.getDonGia());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm chi tiết hóa đơn: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static List<ChiTietHoaDon> getChiTietByHoaDonId(String hoaDonId) {
        List<ChiTietHoaDon> chiTietList = new ArrayList<>();
        String sql = "SELECT ct.maHD, ct.maThuoc, ct.soLuong, ct.donGia, " +
                "t.tenThuoc " +
                "FROM CTHoaDon ct " +
                "JOIN Thuoc t ON ct.maThuoc = t.maThuoc " +
                "WHERE ct.maHD = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

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

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

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

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

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

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

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

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {

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