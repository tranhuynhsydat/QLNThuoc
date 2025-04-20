package DAO;

import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.HashSet;
import java.util.Set;

import ConnectDB.DatabaseConnection;
import Entity.KhachHang;
import Entity.NhaCungCap;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

public class KhachHangDAO {

    public static List<KhachHang> getAllKhachHang() {
        List<KhachHang> danhSachKH = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                KhachHang ncc = new KhachHang(
                        rs.getString("maKH"),
                        rs.getString("tenKH"),
                        rs.getString("gioiTinh"),
                        rs.getString("SDT"),
                        rs.getInt("tuoi")
                );
                danhSachKH.add(ncc);  // Thêm nhân viên vào danh sách
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhSachKH;
    }

    public static KhachHang getKhachHangByMaKH(String maKH) {
        KhachHang kh = null;
        String sql = "SELECT * FROM KhachHang WHERE maKH = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maKH);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    kh = new KhachHang(
                            rs.getString("maKH"),
                            rs.getString("tenKH"),
                            rs.getString("gioiTinh"),
                            rs.getString("SDT"),
                            rs.getInt("tuoi")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kh;
    }

    public static KhachHang getKhachHangBySdt(String sdt) {
        KhachHang kh = null;
        String sql = "SELECT * FROM KhachHang WHERE SDT = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, sdt);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    kh = new KhachHang(
                            rs.getString("maKH"),
                            rs.getString("tenKH"),
                            rs.getString("gioiTinh"),
                            rs.getString("SDT"),
                            rs.getInt("tuoi")
                    );
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return kh;
    }

    // Phương thức tạo mã khách hàng tự động
    public static String TaoMaKhachHang() {
        String prefix = "KH-";
        Set<Integer> existingNumbers = new HashSet<>();  // Lưu trữ các số mã khách hàng đã tồn tại

        // Lấy tất cả các mã khách hàng hiện có trong cơ sở dữ liệu
        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT maKH FROM KhachHang WHERE maKH LIKE 'KH-%'")) {

            while (rs.next()) {
                String ma = rs.getString("maKH");
                if (ma != null && ma.startsWith(prefix)) {
                    try {
                        int num = Integer.parseInt(ma.substring(3));  // Lấy số từ mã "KH-XXX"
                        existingNumbers.add(num);  // Thêm vào danh sách các số đã tồn tại
                    } catch (NumberFormatException e) {
                        // Nếu có lỗi khi chuyển đổi số, bỏ qua
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Kiểm tra mã trống (ví dụ: nếu có KH-001, KH-003 thì KH-002 sẽ là trống)
        int newNumber = 1;  // Mã khách hàng bắt đầu từ KH-001
        while (existingNumbers.contains(newNumber)) {
            newNumber++;  // Tìm số tiếp theo chưa có trong cơ sở dữ liệu
        }

        // Trả về mã mới theo định dạng KH-XXX
        return prefix + String.format("%03d", newNumber);
    }

    public static boolean sua(KhachHang kh) {
        String sql = "UPDATE KhachHang SET tenKH = ?, gioiTinh = ?, SDT = ?, tuoi =?  WHERE maKH = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, kh.getHoTen());
            ps.setString(2, kh.getGioiTinh());
            ps.setString(3, kh.getSdt());
            ps.setInt(4, kh.getTuoi());
            ps.setString(5, kh.getId());  // Mã nhân viên không thay đổi

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Phương thức thêm khách hàng vào cơ sở dữ liệu
    public static boolean themKhachHang(KhachHang kh) {
        String sql = "INSERT INTO KhachHang (maKH, tenKH, gioiTinh, SDT, tuoi) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Cài đặt giá trị cho PreparedStatement
            stmt.setString(1, kh.getId());  // Thêm mã khách hàng
            stmt.setString(2, kh.getHoTen());
            stmt.setString(3, kh.getGioiTinh());
            stmt.setString(4, kh.getSdt());
            stmt.setInt(5, kh.getTuoi());

            // Thực hiện câu lệnh SQL và kiểm tra nếu thêm thành công
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;  // Trả về false nếu có lỗi
    }

    public static boolean xoa(String maKH) {
        String sql = "DELETE FROM KhachHang WHERE maKH = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maKH);  // Truyền mã nhân viên cần xóa vào PreparedStatement

            return ps.executeUpdate() > 0;  // Thực thi câu lệnh và kiểm tra xem có bị ảnh hưởng dòng nào không

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<KhachHang> getKhachHangBatch(int start, int limit) {
        List<KhachHang> danhSachKhachHang = new ArrayList<>();
        String sql = "SELECT * FROM KhachHang ORDER BY maKH OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";  // SQL Server syntax

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, start);  // Chỉ mục bắt đầu (OFFSET)
            ps.setInt(2, limit);  // Số dòng cần lấy (FETCH NEXT)

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    KhachHang kh = new KhachHang(
                            rs.getString("maKH"),
                            rs.getString("tenKH"),
                            rs.getString("gioiTinh"),
                            rs.getString("sdt"),
                            rs.getInt("tuoi")
                    );
                    danhSachKhachHang.add(kh);  // Thêm nhân viên vào danh sách
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhSachKhachHang;
    }
//    public static List<KhachHang> searchKhachHang(String tenKH, String gioiTinh, String SDT, int tuoi) {
//        List<KhachHang> danhSachKhachHang = new ArrayList<>();
//        String sql = "SELECT * FROM KhachHang WHERE "
//                + "(tenKH LIKE ? OR ? = '') AND "
//                + "(gioiTinh LIKE ? OR ? = '') AND "
//                + "(SDT LIKE ? OR ? = '') AND "
//                + "(tuoi LIKE ? OR ? = '') AND ";
//        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
//            ps.setString(1, "%" + tenKH + "%");
//            ps.setString(2, tenKH);
//            ps.setString(3, "%" + gioiTinh + "%");
//            ps.setString(4, gioiTinh);
//            ps.setString(5, "%" + SDT + "%");
//            ps.setString(6, SDT);
//            ps.setString(7, "%" + tuoi + "%");
//            ps.setInt(8, tuoi);
//
//            try (ResultSet rs = ps.executeQuery()) {
//                while (rs.next()) {
//                    KhachHang kh = new KhachHang(
//                            rs.getString("maKH"),
//                            rs.getString("tenKH"),
//                            rs.getString("gioiTinh"),
//                            rs.getString("SDT"),
//                            rs.getInt("tuoi")
//                    );
//                    danhSachKhachHang.add(kh);
//                }
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return danhSachKhachHang;
//    }

    public static List<KhachHang> searchKhachHang(String tenKH, String gioiTinh, String SDT, int tuoi) {
        List<KhachHang> danhSachKhachHang = new ArrayList<>();
        List<String> conditions = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        String sql = "SELECT * FROM KhachHang";

        if (!tenKH.isEmpty()) {
            conditions.add("tenKH LIKE ?");
            params.add("%" + tenKH + "%");
        }
        if (!gioiTinh.isEmpty()) {
            conditions.add("gioiTinh LIKE ?");
            params.add("%" + gioiTinh + "%");
        }
        if (!SDT.isEmpty()) {
            conditions.add("SDT LIKE ?");
            params.add("%" + SDT + "%");
        }
        if (tuoi > 0) {
            conditions.add("tuoi = ?");
            params.add(tuoi);
        }

        if (!conditions.isEmpty()) {
            sql += " WHERE " + String.join(" AND ", conditions);
        }

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    KhachHang kh = new KhachHang(
                            rs.getString("maKH"),
                            rs.getString("tenKH"),
                            rs.getString("gioiTinh"),
                            rs.getString("SDT"),
                            rs.getInt("tuoi")
                    );
                    danhSachKhachHang.add(kh);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhSachKhachHang;
    }

}
