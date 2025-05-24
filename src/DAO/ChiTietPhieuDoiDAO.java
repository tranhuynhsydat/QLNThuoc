package DAO;

import ConnectDB.DatabaseConnection;
import Entity.ChiTietHoaDon;
import Entity.ChiTietPhieuDoi;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChiTietPhieuDoiDAO {

    public static boolean themChiTietHoaDonDoi(List<ChiTietPhieuDoi> chiTietList, String maPD) {
        String sql = "INSERT INTO CTPhieuDoi (maPD, maThuocMoi, soLuongMoi, donGiaMoi) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            for (ChiTietPhieuDoi ct : chiTietList) {
                ps.setString(1, maPD);
                ps.setString(2, ct.getMaThuocMoi());
                ps.setInt(3, ct.getSoLuongMoi());
                ps.setDouble(4, ct.getDonGiaMoi());
                ps.addBatch();
            }
            ps.executeBatch();
            return true;
        } catch (SQLException e) {
            System.err.println("Lỗi thêm chi tiết phiếu đổi: " + e.getMessage());
        }
        return false;
    }

    public static List<ChiTietPhieuDoi> getChiTietByHoaDoiId(String maPD) {
        List<ChiTietPhieuDoi> chiTietList = new ArrayList<>();
        String sql = "SELECT * FROM CTPhieuDoi WHERE maPD = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPD);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ChiTietPhieuDoi ct = new ChiTietPhieuDoi();
                    ct.setMaPD(rs.getString("maPD"));
                    ct.setMaThuocMoi(rs.getString("maThuocMoi"));
                    ct.setSoLuongMoi(rs.getInt("soLuongMoi"));
                    ct.setDonGiaMoi(rs.getDouble("donGiaMoi"));

                    chiTietList.add(ct);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi lấy chi tiết phiếu đổi: " + e.getMessage());
        }
        return chiTietList;
    }

    public static boolean capNhatSoLuongSauKhiDoi(String maPD, String maHD) {
        try (Connection conn = DatabaseConnection.getConnection()) {
            // Lấy thuốc cũ từ hóa đơn gốc
            List<ChiTietHoaDon> thuocCuList = ChiTietHoaDonDAO.getChiTietByHoaDonId(maHD);

            // Lấy thuốc mới từ phiếu đổi
            List<ChiTietPhieuDoi> thuocMoiList = ChiTietPhieuDoiDAO.getChiTietByHoaDoiId(maPD);

            conn.setAutoCommit(false);

            // Cộng thuốc cũ lại kho
            String sqlCong = "UPDATE Thuoc SET soLuong = soLuong + ? WHERE maThuoc = ?";
            try (PreparedStatement psCong = conn.prepareStatement(sqlCong)) {
                for (ChiTietHoaDon ct : thuocCuList) {
                    psCong.setInt(1, ct.getSoLuong());
                    psCong.setString(2, ct.getIdThuoc());
                    psCong.executeUpdate();
                }
            }

            // Trừ thuốc mới khỏi kho
            String sqlTru = "UPDATE Thuoc SET soLuong = soLuong - ? WHERE maThuoc = ?";
            try (PreparedStatement psTru = conn.prepareStatement(sqlTru)) {
                for (ChiTietPhieuDoi ct : thuocMoiList) {
                    psTru.setInt(1, ct.getSoLuongMoi());
                    psTru.setString(2, ct.getMaThuocMoi());
                    psTru.executeUpdate();
                }
            }

            conn.commit();
            conn.setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            System.err.println("Lỗi cập nhật kho sau thanh toán: " + e.getMessage());
            return false;
        }
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
        String sql = "SELECT SUM(soLuongMoi * donGiaMoi) AS tong_tien FROM CTPhieuDoi WHERE maPD = ?";

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