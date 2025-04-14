package DAO;

import ConnectDB.DatabaseConnection;
import Entity.XuatXu;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class XuatXuDAO {

    // Phương thức lấy xuất xứ từ cơ sở dữ liệu
    public static List<XuatXu> getXuatXuList() {
        List<XuatXu> xuatXuList = new ArrayList<>();
        String sql = "SELECT * FROM XuatXu";  // Truy vấn cơ sở dữ liệu để lấy tất cả xuất xứ

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String maXX = rs.getString("maXX");
                String tenXX = rs.getString("tenXX");
                XuatXu xuatXu = new XuatXu(maXX, tenXX);
                xuatXuList.add(xuatXu);  // Thêm đối tượng XuatXu vào danh sách
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return xuatXuList;
    }

    public static String getMaXuatXuByTen(String tenXX) {
        String maXX = null;
        String sql = "SELECT maXX FROM XuatXu WHERE tenXX = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenXX);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                maXX = rs.getString("maXX");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maXX;
    }

    // Lấy thông tin xuất xứ từ mã xuất xứ
    public static XuatXu getXuatXuByMa(String maXX) {
        XuatXu xuatXu = null;
        String sql = "SELECT * FROM XuatXu WHERE maXX = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maXX);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Lấy thông tin xuất xứ từ ResultSet
                String tenXX = rs.getString("tenXX");
                xuatXu = new XuatXu(maXX, tenXX);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return xuatXu;
    }
}
