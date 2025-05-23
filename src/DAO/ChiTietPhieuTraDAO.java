package DAO;

import ConnectDB.DatabaseConnection;
import Entity.ChiTietPhieuTra;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ChiTietPhieuTraDAO {
    public static boolean themChiTietPhieuTra(ChiTietPhieuTra ct) {
        String sql = "INSERT INTO CTPhieuTra (maPT, maThuoc, soLuong, donGia) VALUES (?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ct.getMaPT());
            ps.setString(2, ct.getMaThuoc());
            ps.setInt(3, ct.getSoLuong());
            ps.setDouble(4, ct.getDonGia());

            int rows = ps.executeUpdate();
            System.out.println("Đã thêm chi tiết: " + ct + " => Rows affected: " + rows);
            return rows > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi thêm chi tiết phiếu trả: " + e.getMessage());
            e.printStackTrace(); // In chi tiết stack trace
        }
        return false;
    }

    public static List<ChiTietPhieuTra> getChiTietByPhieuTra(String maPT) {
        List<ChiTietPhieuTra> ds = new ArrayList<>();
        String sql = "SELECT * FROM CTPhieuTra WHERE maPT = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPT);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChiTietPhieuTra ct = new ChiTietPhieuTra(
                        rs.getString("maPT"),
                        rs.getString("maThuoc"),
                        rs.getInt("soLuong"),
                        rs.getDouble("donGia"));
                ds.add(ct);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi đọc chi tiết phiếu trả: " + e.getMessage());
        }
        return ds;
    }
}
