package DAO;

import ConnectDB.DatabaseConnection;
import Entity.DanhMuc;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DanhMucDAO {

    // Phương thức lấy danh mục từ cơ sở dữ liệu
    public static List<DanhMuc> getDanhMucList() {
        List<DanhMuc> danhMucList = new ArrayList<>();
        String sql = "SELECT * FROM DanhMuc";  // Truy vấn cơ sở dữ liệu để lấy tất cả danh mục

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String maDM = rs.getString("maDM");
                String tenDM = rs.getString("tenDM");
                DanhMuc danhMuc = new DanhMuc(maDM, tenDM);
                danhMucList.add(danhMuc);  // Thêm đối tượng DanhMuc vào danh sách
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhMucList;
    }

    public static String getMaDanhMucByTen(String tenDM) {
        String maDM = null;
        String sql = "SELECT maDM FROM DanhMuc WHERE tenDM = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenDM);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                maDM = rs.getString("maDM");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maDM;
    }

    // Lấy thông tin danh mục từ mã danh mục
    public static DanhMuc getDanhMucByMa(String maDM) {
        DanhMuc danhMuc = null;
        String sql = "SELECT * FROM DanhMuc WHERE maDM = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maDM);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Lấy thông tin danh mục từ ResultSet
                String tenDM = rs.getString("tenDM");
                danhMuc = new DanhMuc(maDM, tenDM);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhMuc;
    }
}
