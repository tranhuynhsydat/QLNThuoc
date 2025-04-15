package DAO;

import ConnectDB.DatabaseConnection;
import Entity.DonViTinh;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DonViTinhDAO {

    // Phương thức lấy danh sách đơn vị tính từ cơ sở dữ liệu
    public static List<DonViTinh> getDonViTinhList() {
        List<DonViTinh> donViTinhList = new ArrayList<>();
        String sql = "SELECT * FROM DonViTinh";  // Truy vấn cơ sở dữ liệu để lấy tất cả đơn vị tính

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String maDVT = rs.getString("maDVT");
                String tenDVT = rs.getString("tenDVT");
                DonViTinh donViTinh = new DonViTinh(maDVT, tenDVT);
                donViTinhList.add(donViTinh);  // Thêm đối tượng DonViTinh vào danh sách
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return donViTinhList;
    }

    public static String getMaDonViTinhByTen(String tenDVT) {
        String maDVT = null;
        String sql = "SELECT maDVT FROM DonViTinh WHERE tenDVT = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, tenDVT);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                maDVT = rs.getString("maDVT");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return maDVT;
    }

    // Lấy thông tin đơn vị tính từ mã đơn vị tính
    public static DonViTinh getDonViTinhByMa(String maDVT) {
        DonViTinh donViTinh = null;
        String sql = "SELECT * FROM DonViTinh WHERE maDVT = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maDVT);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                // Lấy thông tin đơn vị tính từ ResultSet
                String tenDVT = rs.getString("tenDVT");
                donViTinh = new DonViTinh(maDVT, tenDVT);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return donViTinh;
    }
}
