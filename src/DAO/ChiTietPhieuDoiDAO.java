package DAO;

import ConnectDB.DatabaseConnection;
import Entity.ChiTietPhieuDoi;
import Entity.Thuoc;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChiTietPhieuDoiDAO {

    public static boolean themChiTietHoaDonDoi(List<ChiTietPhieuDoi> chiTietList, String maPD) {
        String sql = "INSERT INTO CTPhieuDoi (maPD, maThuocCu, soLuongCu, donGiaCu, maThuocMoi, soLuongMoi, donGiaMoi, tongTien) "
                +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            for (ChiTietPhieuDoi ct : chiTietList) {
                ps.setString(1, maPD);
                ps.setString(2, ct.getMaThuocCu());
                ps.setInt(3, ct.getSoLuongCu());
                ps.setDouble(4, ct.getDonGiaCu());
                ps.setString(5, ct.getMaThuocMoi());
                ps.setInt(6, ct.getSoLuongMoi());
                ps.setDouble(7, ct.getDonGiaMoi());
                ps.setDouble(8, ct.getTongTien());
                ps.addBatch();
            }
            ps.executeBatch();
            return true;
        } catch (SQLException e) {
            System.out.println("Lỗi thêm chi tiết hóa đơn: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public static List<ChiTietPhieuDoi> getChiTietByHoaDoiId(String hoaDonDoiId) {
        List<ChiTietPhieuDoi> chiTietList = new ArrayList<>();
        String sql = "SELECT * FROM CTPhieuDoi WHERE maPD = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, hoaDonDoiId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ChiTietPhieuDoi chiTiet = new ChiTietPhieuDoi();
                    chiTiet.setMaPD(rs.getString("maPD"));
                    chiTiet.setMaThuocCu(rs.getString("maThuocCu"));
                    chiTiet.setSoLuongCu(rs.getInt("soLuongCu"));
                    chiTiet.setDonGiaCu(rs.getDouble("donGiaCu"));
                    chiTiet.setMaThuocMoi(rs.getString("maThuocMoi"));
                    chiTiet.setSoLuongMoi(rs.getInt("soLuongMoi"));
                    chiTiet.setDonGiaMoi(rs.getDouble("donGiaMoi"));
                    chiTiet.setTongTien(rs.getDouble("tongTien"));
                    chiTietList.add(chiTiet);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy chi tiết hóa đơn: " + e.getMessage());
            e.printStackTrace();
        }
        return chiTietList;
    }

    public static boolean xoaChiTietTheoHoaDonDoi(String hoaDonDoiId) {
        String sql = "DELETE FROM CTPhieuDoi WHERE maPD = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, hoaDonDoiId);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa chi tiết theo hóa đơn: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static double tinhTongTienHoaDonDoi(String hoaDonDoiId) {
        String sql = "SELECT SUM(tongTien) AS tong_tien FROM CTPhieuDoi WHERE maPD = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, hoaDonDoiId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("tong_tien");
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tính tổng tiền hóa đơn: " + e.getMessage());
            e.printStackTrace();
        }
        return 0;
    }
}
