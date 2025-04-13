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
import Entity.NhanVien;
import javax.swing.JOptionPane;

public class KhachHangDAO {

    // Phương thức tạo mã khách hàng tự động
    public static String TaoMaKhachHang() {
        String prefix = "KH-";
        Set<Integer> existingNumbers = new HashSet<>();  // Lưu trữ các số mã khách hàng đã tồn tại

        // Lấy tất cả các mã khách hàng hiện có trong cơ sở dữ liệu
        try (Connection conn = DatabaseConnection.getConnection(); 
             Statement stmt = conn.createStatement(); 
             ResultSet rs = stmt.executeQuery("SELECT maKH FROM KhachHang WHERE maKH LIKE 'KH-%'")) {

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

    // Phương thức thêm khách hàng vào cơ sở dữ liệu
    public static boolean themKhachHang(String maKH, String hoTen, String gioiTinh, String sdt, int tuoi) {
        String sql = "INSERT INTO KhachHang (maKH, tenKH, gioiTinh, SDT, tuoi) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            // Cài đặt giá trị cho PreparedStatement
            stmt.setString(1, maKH);  // Thêm mã khách hàng
            stmt.setString(2, hoTen);
            stmt.setString(3, gioiTinh);
            stmt.setString(4, sdt);
            stmt.setInt(5, tuoi);

            // Thực hiện câu lệnh SQL và kiểm tra nếu thêm thành công
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;  // Trả về false nếu có lỗi
    }
}
