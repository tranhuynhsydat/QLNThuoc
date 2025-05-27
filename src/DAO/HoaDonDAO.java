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
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HoaDonDAO {

    // Tạo mã hoá đơn
    public static String taoMaHoaDon() {
        String prefix = "HD-"; // Tiền tố của mã hóa đơn
        int maxNumber = 0;

        // Truy vấn để lấy mã hóa đơn mới nhất
        String sql = "SELECT maHD FROM HoaDon WHERE maHD LIKE 'HD-%' ORDER BY maHD DESC";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            // Kiểm tra mã hóa đơn mới nhất
            if (rs.next()) {
                String lastMaHD = rs.getString("maHD");
                // Lấy số từ mã cuối cùng và tăng lên 1
                int lastNumber = Integer.parseInt(lastMaHD.substring(3)); // Lấy số sau tiền tố "HD-"
                maxNumber = lastNumber + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Tạo mã hóa đơn mới với số có 3 chữ số
        return prefix + String.format("%03d", maxNumber); // Đảm bảo mã hóa đơn có 3 chữ số
    }

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

    public static List<HoaDon> getHoaDonBatch(int start, int limit) {
        List<HoaDon> danhSachHoaDon = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon ORDER BY thoiGian DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, start);
            ps.setInt(2, limit);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String maHD = rs.getString("maHD");
                Date ngayLap = rs.getTimestamp("thoiGian");
                String idNhanVien = rs.getString("maNV");
                String idKhachHang = rs.getString("maKH");

                HoaDon hoaDon = new HoaDon(maHD, ngayLap, idNhanVien, idKhachHang);
                hoaDon.setChiTietHoaDon(getChiTietHoaDonByMaHD(maHD));
                hoaDon.tinhTongTien();
                hoaDon.setNhanVien(NhanVienDAO.getNhanVienByMaNV(idNhanVien));
                hoaDon.setKhachHang(KhachHangDAO.getKhachHangByMaKH(idKhachHang));

                danhSachHoaDon.add(hoaDon);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi phân trang hóa đơn: " + e.getMessage());
        }

        return danhSachHoaDon;
    }

    public static double getTongTienHoaDon(String maHD) {
        String sql = "SELECT SUM(soLuong * donGia) AS tong_tien FROM CTHoaDon WHERE maHD = ?"; // Adjust table name
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, maHD);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("tong_tien");
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi lấy tổng tiền hóa đơn: " + e.getMessage());
        }
        return 0;
    }

    public static List<HoaDon> searchHoaDon(String maHD, String tenKH, String sdt, Date ngayLap) {
        List<HoaDon> danhSachHoaDon = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT * FROM HoaDon hd JOIN KhachHang kh ON hd.maKH = kh.maKH WHERE 1=1");

        if (maHD != null && !maHD.isEmpty()) {
            sql.append(" AND hd.maHD LIKE ?");
        }
        if (tenKH != null && !tenKH.isEmpty()) {
            sql.append(" AND kh.hoTen LIKE ?");
        }
        if (sdt != null && !sdt.isEmpty()) {
            sql.append(" AND kh.sdt LIKE ?");
        }
        if (ngayLap != null) {
            sql.append(" AND CONVERT(DATE, hd.thoiGian) = ?");
        }

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            int paramIndex = 1;
            if (maHD != null && !maHD.isEmpty()) {
                ps.setString(paramIndex++, "%" + maHD + "%");
            }
            if (tenKH != null && !tenKH.isEmpty()) {
                ps.setString(paramIndex++, "%" + tenKH + "%");
            }
            if (sdt != null && !sdt.isEmpty()) {
                ps.setString(paramIndex++, "%" + sdt + "%");
            }
            if (ngayLap != null) {
                ps.setDate(paramIndex++, new java.sql.Date(ngayLap.getTime()));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    HoaDon hoaDon = new HoaDon(
                            rs.getString("maHD"),
                            rs.getTimestamp("thoiGian"),
                            rs.getString("maNV"),
                            rs.getString("maKH"));
                    hoaDon.setChiTietHoaDon(getChiTietHoaDonByMaHD(rs.getString("maHD")));
                    hoaDon.tinhTongTien();
                    hoaDon.setNhanVien(NhanVienDAO.getNhanVienByMaNV(rs.getString("maNV")));
                    hoaDon.setKhachHang(KhachHangDAO.getKhachHangByMaKH(rs.getString("maKH")));
                    danhSachHoaDon.add(hoaDon);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi tìm kiếm hóa đơn: " + e.getMessage());
        }

        return danhSachHoaDon;
    }

    // Lấy hóa đơn theo mã
    public static HoaDon getHoaDonByMaHD(String maHD) {
        String sql = "SELECT * FROM HoaDon WHERE maHD = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

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

    public static boolean them(HoaDon hoaDon) {
        String sql = "INSERT INTO HoaDon (maHD, maNV, maKH, thoiGian) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            // Thêm thông tin hóa đơn vào bảng HoaDon
            ps.setString(1, hoaDon.getId()); // Gán maHD vào câu lệnh SQL
            ps.setString(2, hoaDon.getIdNhanVien());
            ps.setString(3, hoaDon.getIdKhachHang());

            // Sử dụng java.sql.Timestamp để đảm bảo ngày tháng đúng định dạng
            Timestamp thoiGian = new Timestamp(hoaDon.getNgayLap().getTime());
            ps.setTimestamp(4, thoiGian); // Chuyển ngày giờ sang Timestamp

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                // Sau khi thêm hóa đơn thành công, gọi phương thức thêm chi tiết hóa đơn
                return ChiTietHoaDonDAO.themChiTietHoaDon(hoaDon.getChiTietHoaDon(), hoaDon.getId()); // Truyền maHD vào
                                                                                                      // chi tiết hóa
                                                                                                      // đơn
            }
        } catch (SQLException e) {
            System.out.println("Lỗi thêm hóa đơn: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    // Lấy danh sách chi tiết hóa đơn theo mã hóa đơn
    public static List<ChiTietHoaDon> getChiTietHoaDonByMaHD(String maHD) {
        List<ChiTietHoaDon> chiTietList = new ArrayList<>();
        String sql = "SELECT * FROM CTHoaDon WHERE maHD = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

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
    // Tìm kiếm hóa đơn theo khoảng thời gian
    public static List<HoaDon> timHoaDonTheoThoiGian(Date tuNgay, Date denNgay) {
        List<HoaDon> danhSachHoaDon = new ArrayList<>();
        String sql = "SELECT * FROM HoaDon WHERE thoiGian BETWEEN ? AND ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

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

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

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

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

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
