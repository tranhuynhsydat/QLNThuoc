/*
 * DAO/HoaDonDAO.java
 */
package DAO;

import ConnectDB.DatabaseConnection;
import Entity.ChiTietHoaDon;
import Entity.HoaDon;
import Entity.KhachHang;
import Entity.NhanVien;
import Entity.Thuoc;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HoaDonDAO {

    // Lấy tất cả hóa đơn
    public static List<HoaDon> getAllHoaDon() {
        List<HoaDon> danhSachHoaDon = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String maHD = rs.getString("maHD");
                Date ngayLap = rs.getTimestamp("thoiGian");
                String idNhanVien = rs.getString("maNV");
                String idKhachHang = rs.getString("maKH");

                // Tạo đối tượng hóa đơn
                HoaDon hoaDon = new HoaDon(maHD, ngayLap, idNhanVien, idKhachHang);

                // Lấy thông tin chi tiết từ bảng liên kết
                hoaDon.setChiTietHoaDon(getChiTietHoaDonByMaHD(maHD));

                // Tính tổng tiền dựa trên chi tiết
                hoaDon.tinhTongTien();

                // Lấy thông tin nhân viên và khách hàng
                hoaDon.setNhanVien(NhanVienDAO.getNhanVienByMaNV(idNhanVien));
                hoaDon.setKhachHang(KhachHangDAO.getKhachHangByMaKH(idKhachHang));

                danhSachHoaDon.add(hoaDon);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi lấy danh sách hóa đơn: " + e.getMessage());
        }

        return danhSachHoaDon;
    }

    public static List<HoaDon> searchHoaDon(String maHoaDon, String tenKhachHang, Date ngayMua) {
        List<HoaDon> danhSachHoaDon = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon WHERE 1=1";
        if (maHoaDon != null && !maHoaDon.isEmpty()) {
            sql += " AND maHD LIKE '%" + maHoaDon + "%'";
        }
        if (tenKhachHang != null && !tenKhachHang.isEmpty()) {
            sql += " AND tenKhachHang LIKE '%" + tenKhachHang + "%'";
        }
        if (ngayMua != null) {
            java.sql.Date sqlDate = new java.sql.Date(ngayMua.getTime());
            sql += " AND ngayMua = '" + sqlDate + "'";
        }

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                HoaDon hoaDon = new HoaDon();
                hoaDon.setId(rs.getString("maHD"));
                hoaDon.setNgayLap(rs.getDate("ngayMua"));
                hoaDon.setIdNhanVien(rs.getString("maNV")); // Sử dụng idNhanVien
                hoaDon.setIdKhachHang(rs.getString("maKH")); // Sử dụng idKhachHang
                // Thêm các thông tin khác nếu cần

                danhSachHoaDon.add(hoaDon);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi tìm kiếm hóa đơn: " + e.getMessage());
        }

        return danhSachHoaDon;
    }

    // Lấy hóa đơn theo mã
    public static HoaDon getHoaDonByMaHD(String maHD) {
        String sql = "SELECT * FROM HoaDon WHERE maHD = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maHD);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Date ngayLap = rs.getTimestamp("thoiGian");
                    String idNhanVien = rs.getString("maNV");
                    String idKhachHang = rs.getString("maKH");

                    // Tạo đối tượng hóa đơn
                    HoaDon hoaDon = new HoaDon(maHD, ngayLap, idNhanVien, idKhachHang);

                    // Lấy thông tin chi tiết
                    hoaDon.setChiTietHoaDon(getChiTietHoaDonByMaHD(maHD));
                    hoaDon.tinhTongTien();

                    // Lấy thông tin nhân viên và khách hàng
                    hoaDon.setNhanVien(NhanVienDAO.getNhanVienByMaNV(idNhanVien));
                    hoaDon.setKhachHang(KhachHangDAO.getKhachHangByMaKH(idKhachHang));

                    return hoaDon;
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi lấy hóa đơn theo mã: " + e.getMessage());
        }

        return null;
    }

    // Tạo mã hóa đơn mới
    public static String taoMaHoaDon() {
        String maHD = "HD-";
        String sql = "SELECT TOP 1 maHD FROM HoaDon ORDER BY maHD DESC";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                String lastMaHD = rs.getString("maHD");
                // Lấy số từ mã cuối cùng và tăng lên 1
                int lastNumber = Integer.parseInt(lastMaHD.substring(3));
                maHD += String.format("%03d", lastNumber + 1);
            } else {
                // Nếu không có hóa đơn nào, bắt đầu từ 001
                maHD += "001";
            }
        } catch (SQLException e) {
            System.out.println("Lỗi tạo mã hóa đơn: " + e.getMessage());
            // Trường hợp lỗi, tạo mã dựa vào thời gian
            maHD += System.currentTimeMillis() % 1000;
        }

        return maHD;
    }

    // Thêm hóa đơn mới
    public static boolean them(HoaDon hoaDon) {
        String sql = "INSERT INTO HoaDon (maHD, maNV, maKH, thoiGian) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, hoaDon.getId());
            ps.setString(2, hoaDon.getIdNhanVien());
            ps.setString(3, hoaDon.getIdKhachHang());
            ps.setTimestamp(4, new Timestamp(hoaDon.getNgayLap().getTime()));

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                // Thêm chi tiết hóa đơn
                return themChiTietHoaDon(hoaDon.getChiTietHoaDon());
            }
        } catch (SQLException e) {
            System.out.println("Lỗi thêm hóa đơn: " + e.getMessage());
        }

        return false;
    }

    // Cập nhật hóa đơn
    public static boolean sua(HoaDon hoaDon) {
        String sql = "UPDATE HoaDon SET maNV = ?, maKH = ?, thoiGian = ? WHERE maHD = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, hoaDon.getIdNhanVien());
            ps.setString(2, hoaDon.getIdKhachHang());
            ps.setTimestamp(3, new Timestamp(hoaDon.getNgayLap().getTime()));
            ps.setString(4, hoaDon.getId());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                // Cập nhật chi tiết hóa đơn
                // Xóa chi tiết cũ và thêm chi tiết mới
                xoaChiTietHoaDon(hoaDon.getId());
                return themChiTietHoaDon(hoaDon.getChiTietHoaDon());
            }
        } catch (SQLException e) {
            System.out.println("Lỗi cập nhật hóa đơn: " + e.getMessage());
        }

        return false;
    }

    // Xóa hóa đơn
    public static boolean xoa(String maHD) {
        // Xóa chi tiết hóa đơn trước
        if (xoaChiTietHoaDon(maHD)) {
            String sql = "DELETE FROM HoaDon WHERE maHD = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setString(1, maHD);

                int rowsAffected = ps.executeUpdate();
                return rowsAffected > 0;
            } catch (SQLException e) {
                System.out.println("Lỗi xóa hóa đơn: " + e.getMessage());
            }
        }

        return false;
    }

    // Lấy danh sách chi tiết hóa đơn theo mã hóa đơn
    public static List<ChiTietHoaDon> getChiTietHoaDonByMaHD(String maHD) {
        List<ChiTietHoaDon> chiTietList = new ArrayList<>();
        String sql = "SELECT * FROM CTHoaDon WHERE maHD = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maHD);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ChiTietHoaDon chiTiet = new ChiTietHoaDon();
                    chiTiet.setIdHoaDon(rs.getString("maHD"));
                    chiTiet.setIdThuoc(rs.getString("maThuoc"));
                    chiTiet.setSoLuong(rs.getInt("soLuong"));
                    chiTiet.setDonGia(rs.getDouble("donGia"));

                    // Lấy thông tin thuốc
                    chiTiet.setThuoc(ThuocDAO.getThuocByMaThuoc(rs.getString("maThuoc")).getTenThuoc());

                    chiTietList.add(chiTiet);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi lấy chi tiết hóa đơn: " + e.getMessage());
        }

        return chiTietList;
    }

    // Thêm chi tiết hóa đơn
    public static boolean themChiTietHoaDon(List<ChiTietHoaDon> chiTietList) {
        if (chiTietList == null || chiTietList.isEmpty()) {
            return true; // Không có chi tiết để thêm
        }

        String sql = "INSERT INTO CTHoaDon (maHD, maThuoc, soLuong, donGia) VALUES (?, ?, ?, ?)";
        int successCount = 0;

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            for (ChiTietHoaDon chiTiet : chiTietList) {
                ps.setString(1, chiTiet.getIdHoaDon());
                ps.setString(2, chiTiet.getIdThuoc());
                ps.setInt(3, chiTiet.getSoLuong());
                ps.setDouble(4, chiTiet.getDonGia());

                ps.addBatch();
                successCount++;

                // Cập nhật số lượng thuốc trong kho
                capNhatSoLuongThuoc(chiTiet.getIdThuoc(), chiTiet.getSoLuong());
            }

            ps.executeBatch();
            return successCount == chiTietList.size();
        } catch (SQLException e) {
            System.out.println("Lỗi thêm chi tiết hóa đơn: " + e.getMessage());
        }

        return false;
    }

    // Xóa chi tiết hóa đơn
    public static boolean xoaChiTietHoaDon(String maHD) {
        // Lấy danh sách chi tiết để hoàn trả số lượng thuốc
        List<ChiTietHoaDon> chiTietList = getChiTietHoaDonByMaHD(maHD);

        String sql = "DELETE FROM CTHoaDon WHERE maHD = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maHD);

            int rowsAffected = ps.executeUpdate();

            // Hoàn trả số lượng thuốc
            for (ChiTietHoaDon chiTiet : chiTietList) {
                capNhatSoLuongThuoc(chiTiet.getIdThuoc(), -chiTiet.getSoLuong());
            }

            return rowsAffected > 0;
        } catch (SQLException e) {
            System.out.println("Lỗi xóa chi tiết hóa đơn: " + e.getMessage());
        }

        return false;
    }

    // Cập nhật số lượng thuốc (trừ khi bán, cộng khi hủy hóa đơn)
    private static void capNhatSoLuongThuoc(String maThuoc, int soLuongBan) {
        // Lấy thông tin thuốc hiện tại
        Thuoc thuoc = ThuocDAO.getThuocByMaThuoc(maThuoc);
        if (thuoc != null) {
            // Cập nhật số lượng (trừ đi số lượng bán)
            int soLuongMoi = thuoc.getSoLuong() - soLuongBan;

            // Cập nhật vào database
            String sql = "UPDATE Thuoc SET soLuong = ? WHERE maThuoc = ?";

            try (Connection conn = DatabaseConnection.getConnection();
                    PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, soLuongMoi);
                ps.setString(2, maThuoc);

                ps.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Lỗi cập nhật số lượng thuốc: " + e.getMessage());
            }
        }
    }

    // Tìm kiếm hóa đơn theo khoảng thời gian
    public static List<HoaDon> timHoaDonTheoThoiGian(Date tuNgay, Date denNgay) {
        List<HoaDon> danhSachHoaDon = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon WHERE thoiGian BETWEEN ? AND ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, new Timestamp(tuNgay.getTime()));
            ps.setTimestamp(2, new Timestamp(denNgay.getTime()));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String maHD = rs.getString("maHD");
                    Date ngayLap = rs.getTimestamp("thoiGian");
                    String idNhanVien = rs.getString("maNV");
                    String idKhachHang = rs.getString("maKH");

                    // Tạo đối tượng hóa đơn
                    HoaDon hoaDon = new HoaDon(maHD, ngayLap, idNhanVien, idKhachHang);

                    // Lấy thông tin chi tiết
                    hoaDon.setChiTietHoaDon(getChiTietHoaDonByMaHD(maHD));
                    hoaDon.tinhTongTien();

                    // Lấy thông tin nhân viên và khách hàng
                    hoaDon.setNhanVien(NhanVienDAO.getNhanVienByMaNV(idNhanVien));
                    hoaDon.setKhachHang(KhachHangDAO.getKhachHangByMaKH(idKhachHang));

                    danhSachHoaDon.add(hoaDon);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi tìm kiếm hóa đơn theo thời gian: " + e.getMessage());
        }

        return danhSachHoaDon;
    }

    // Tìm kiếm hóa đơn theo khách hàng
    public static List<HoaDon> timHoaDonTheoKhachHang(String maKH) {
        List<HoaDon> danhSachHoaDon = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon WHERE maKH = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maKH);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String maHD = rs.getString("maHD");
                    Date ngayLap = rs.getTimestamp("thoiGian");
                    String idNhanVien = rs.getString("maNV");
                    String idKhachHang = rs.getString("maKH");

                    // Tạo đối tượng hóa đơn
                    HoaDon hoaDon = new HoaDon(maHD, ngayLap, idNhanVien, idKhachHang);

                    // Lấy thông tin chi tiết
                    hoaDon.setChiTietHoaDon(getChiTietHoaDonByMaHD(maHD));
                    hoaDon.tinhTongTien();

                    // Lấy thông tin nhân viên và khách hàng
                    hoaDon.setNhanVien(NhanVienDAO.getNhanVienByMaNV(idNhanVien));
                    hoaDon.setKhachHang(KhachHangDAO.getKhachHangByMaKH(idKhachHang));

                    danhSachHoaDon.add(hoaDon);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi tìm kiếm hóa đơn theo khách hàng: " + e.getMessage());
        }

        return danhSachHoaDon;
    }

    // Tìm kiếm hóa đơn theo nhân viên
    public static List<HoaDon> timHoaDonTheoNhanVien(String maNV) {
        List<HoaDon> danhSachHoaDon = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon WHERE maNV = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maNV);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String maHD = rs.getString("maHD");
                    Date ngayLap = rs.getTimestamp("thoiGian");
                    String idNhanVien = rs.getString("maNV");
                    String idKhachHang = rs.getString("maKH");

                    // Tạo đối tượng hóa đơn
                    HoaDon hoaDon = new HoaDon(maHD, ngayLap, idNhanVien, idKhachHang);

                    // Lấy thông tin chi tiết
                    hoaDon.setChiTietHoaDon(getChiTietHoaDonByMaHD(maHD));
                    hoaDon.tinhTongTien();

                    // Lấy thông tin nhân viên và khách hàng
                    hoaDon.setNhanVien(NhanVienDAO.getNhanVienByMaNV(idNhanVien));
                    hoaDon.setKhachHang(KhachHangDAO.getKhachHangByMaKH(idKhachHang));

                    danhSachHoaDon.add(hoaDon);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi tìm kiếm hóa đơn theo nhân viên: " + e.getMessage());
        }

        return danhSachHoaDon;
    }
}