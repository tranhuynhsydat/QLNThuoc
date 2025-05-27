/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import ConnectDB.DatabaseConnection;
import Entity.ChiTietPhieuNhap;
import Entity.Thuoc;
import java.sql.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Admin
 */
public class ChiTietPhieuNhapDAO {

    public static boolean themChiTietPhieuNhap(List<ChiTietPhieuNhap> chiTietList, String maPN) {
        if (chiTietList == null || chiTietList.isEmpty()) {
            return true;  // Không có chi tiết để thêm
        }

        String sql = "INSERT INTO CTPhieuNhap (maPN, maThuoc, soLuong, donGia) VALUES (?, ?, ?, ?)";
        int successCount = 0;

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            for (ChiTietPhieuNhap chiTiet : chiTietList) {
                ps.setString(1, maPN);  // Sử dụng maHD truyền vào
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
                    System.out.println("Lỗi trong quá trình thêm chi tiết phiếu nhập!");
                    return false;  // Nếu có lỗi trong bất kỳ dòng nào của batch
                }
            }

            return successCount == chiTietList.size();  // Kiểm tra tất cả chi tiết đã được thêm
        } catch (SQLException e) {
            System.out.println("Lỗi thêm chi tiết phiếu nhập: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    private static void capNhatSoLuongThuoc(String maThuoc, int soLuongNhap) {
        // Lấy thông tin thuốc hiện tại
        Thuoc thuoc = ThuocDAO.getThuocByMaThuoc(maThuoc);
        if (thuoc != null) {
            // Cập nhật số lượng (trừ đi số lượng bán)
            int soLuongMoi = thuoc.getSoLuong() + soLuongNhap;

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

    public static List<ChiTietPhieuNhap> getChiTietByPhieuNhapId(String phieuNhapId) {
        List<ChiTietPhieuNhap> chiTietList = new ArrayList<>();
        String sql = "SELECT ct.maPN, ct.maThuoc, ct.soLuong, ct.donGia, "
                + "t.tenThuoc "
                + "FROM CTPhieuNhap ct "
                + "JOIN Thuoc t ON ct.maThuoc = t.maThuoc "
                + "WHERE ct.maPN = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, phieuNhapId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ChiTietPhieuNhap chiTiet = new ChiTietPhieuNhap();
                    chiTiet.setIdPhieuNhap(rs.getString("maPN"));
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
    public static boolean capNhatChiTietPhieuNhap(ChiTietPhieuNhap chiTietPhieuNhap) {
        String sql = "UPDATE CTPhieuNhap SET soLuong = ?, donGia = ?, thanhTien = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, chiTietPhieuNhap.getSoLuong());
            stmt.setDouble(2, chiTietPhieuNhap.getDonGia());
            stmt.setDouble(3, chiTietPhieuNhap.getThanhTien());
            stmt.setString(4, chiTietPhieuNhap.getIdPhieuNhap());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật chi tiết phiếu nhập: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Xóa một chi tiết hóa đơn
    public static boolean xoaChiTietPhieuNhap(int chiTietId) {
        String sql = "DELETE FROM CTPhieuNhap WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, chiTietId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa chi tiết phiếu nhập: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Xóa tất cả chi tiết của một hóa đơn
    public static boolean xoaChiTietTheoPhieuNhap(String phieuNhapId) {
        String sql = "DELETE FROM CTPhieuNhap WHERE maPN = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, phieuNhapId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa chi tiết theo phiếu nhập: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    // Tính tổng tiền của một hóa đơn
    public static double tinhTongTienPhieuNhap(String phieuNhapId) {
        String sql = "SELECT SUM(thanhTien) AS tong_tien FROM CTPhieuNhap WHERE maHD = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, phieuNhapId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("tong_tien");
                }
            }

            return 0;

        } catch (SQLException e) {
            System.err.println("Lỗi khi tính tổng tiền phiếu nhập: " + e.getMessage());
            e.printStackTrace();
            return 0;
        }
    }

    public static List<ChiTietPhieuNhap> getDSChiTietPhieuNhapTheoMa(String maPN) {
        List<ChiTietPhieuNhap> ds = new ArrayList<>();
        String sql = "SELECT * FROM CTPhieuNhap WHERE maPN = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPN);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ChiTietPhieuNhap ct = new ChiTietPhieuNhap();
                    ct.setIdPhieuNhap(rs.getString("maPN"));
                    ct.setIdThuoc(rs.getString("maThuoc"));
                    ct.setSoLuong(rs.getInt("soLuong"));
                    ct.setDonGia(rs.getDouble("donGia"));
                    ds.add(ct);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi lấy chi tiết phiếu nhập: " + e.getMessage());
        }
        return ds;
    }

}
