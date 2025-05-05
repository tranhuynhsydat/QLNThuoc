/*
 * DAO/HoaDonDAO.java
 */
package DAO;

import ConnectDB.DatabaseConnection;
import Entity.ChiTietPhieuDoi;
import Entity.PhieuDoi;
import Entity.Thuoc;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhieuDoiDAO {

    // Tạo mã hoá đơn
    public static String taoMaHoaDonDoi() {
        String prefix = "PD-"; // Tiền tố của mã hóa đơn
        int maxNumber = 0;

        // Truy vấn để lấy mã hóa đơn mới nhất
        String sql = "SELECT maPD FROM PhieuDoi WHERE maPD LIKE 'PD-%' ORDER BY maPD DESC";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            // Kiểm tra mã hóa đơn mới nhất
            if (rs.next()) {
                String lastMaPD = rs.getString("maPD");
                // Lấy số từ mã cuối cùng và tăng lên 1
                int lastNumber = Integer.parseInt(lastMaPD.substring(3)); // Lấy số sau tiền tố "HD-"
                maxNumber = lastNumber + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Tạo mã hóa đơn mới với số có 3 chữ số
        return prefix + String.format("%03d", maxNumber); // Đảm bảo mã hóa đơn có 3 chữ số
    }

    // Lấy tất cả hóa đơn
    public static List<PhieuDoi> getAllHoaDonDoi() {
        List<PhieuDoi> danhSachHoaDonDoi = new ArrayList<>();
        String sql = "SELECT * FROM PhieuDoi";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String maPD = rs.getString("maPD");
                Date ngayLap = rs.getTimestamp("thoiGian");
                String idNhanVien = rs.getString("maNV");
                String idKhachHang = rs.getString("maKH");
                String maHD = rs.getString("maHD");
                String lyDo = rs.getString("lyDo");

                // Tạo đối tượng hóa đơn
                PhieuDoi hoaDonDoi = new PhieuDoi(maPD, ngayLap, idNhanVien, idKhachHang);
                hoaDonDoi.setMaHD(maHD);
                hoaDonDoi.setLyDo(lyDo);

                // Lấy thông tin chi tiết từ bảng liên kết
                hoaDonDoi.setChiTietHoaDonDoi(getChiTietHoaDonByMaPD(maPD));

                // Tính tổng tiền dựa trên chi tiết
                hoaDonDoi.tinhTongTien();

                // Lấy thông tin nhân viên và khách hàng
                hoaDonDoi.setNhanVien(NhanVienDAO.getNhanVienByMaNV(idNhanVien));
                hoaDonDoi.setKhachHang(KhachHangDAO.getKhachHangByMaKH(idKhachHang));

                danhSachHoaDonDoi.add(hoaDonDoi);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi lấy danh sách hóa đơn đổi: " + e.getMessage());
        }

        return danhSachHoaDonDoi;
    }

    public static List<PhieuDoi> searchHoaDonDoi(String maHoaDonDoi, String tenKhachHang, Date ngayMua) {
        List<PhieuDoi> danhSachHoaDonDoi = new ArrayList<>();
        String sql = "SELECT * FROM PhieuDoi WHERE 1=1";
        if (maHoaDonDoi != null && !maHoaDonDoi.isEmpty()) {
            sql += " AND maPD LIKE '%" + maHoaDonDoi + "%'";
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
                PhieuDoi hoaDonDoi = new PhieuDoi();
                hoaDonDoi.setId(rs.getString("maPD"));
                hoaDonDoi.setNgayLap(rs.getDate("ngayMua"));
                hoaDonDoi.setIdNhanVien(rs.getString("maNV")); // Sử dụng idNhanVien
                hoaDonDoi.setIdKhachHang(rs.getString("maKH")); // Sử dụng idKhachHang
                // Thêm các thông tin khác nếu cần

                danhSachHoaDonDoi.add(hoaDonDoi);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi tìm kiếm hóa đơn đổi: " + e.getMessage());
        }

        return danhSachHoaDonDoi;
    }

    // Lấy hóa đơn theo mã
    public static PhieuDoi getHoaDonByMaPD(String maPD) {
        String sql = "SELECT * FROM PhieuDoi WHERE maPD = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maPD);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Date ngayLap = rs.getTimestamp("thoiGian");
                    String idNhanVien = rs.getString("maNV");
                    String idKhachHang = rs.getString("maKH");

                    // Tạo đối tượng hóa đơn
                    PhieuDoi hoaDonDoi = new PhieuDoi(maPD, ngayLap, idNhanVien, idKhachHang);

                    // Lấy thông tin chi tiết
                    hoaDonDoi.setChiTietHoaDonDoi(getChiTietHoaDonByMaPD(maPD));
                    hoaDonDoi.tinhTongTien();

                    // Lấy thông tin nhân viên và khách hàng
                    hoaDonDoi.setNhanVien(NhanVienDAO.getNhanVienByMaNV(idNhanVien));
                    hoaDonDoi.setKhachHang(KhachHangDAO.getKhachHangByMaKH(idKhachHang));

                    return hoaDonDoi;
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi lấy hóa đơn đổi theo mã: " + e.getMessage());
        }

        return null;
    }

    public static boolean them(PhieuDoi hoaDonDoi) {
        String sql = "INSERT INTO PhieuDoi (maPD, maNV, maKH, maHD, thoiGian, lyDo) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hoaDonDoi.getId()); // maPD
            ps.setString(2, hoaDonDoi.getIdNhanVien()); // maNV
            ps.setString(3, hoaDonDoi.getIdKhachHang()); // maKH
            ps.setString(4, hoaDonDoi.getMaHD()); // maHD
            ps.setTimestamp(5, new Timestamp(hoaDonDoi.getNgayLap().getTime())); // thoiGian
            ps.setString(6, hoaDonDoi.getLyDo()); // lyDo

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0) {
                return ChiTietPhieuDoiDAO.themChiTietHoaDonDoi(
                        hoaDonDoi.getChiTietHoaDonDoi(),
                        hoaDonDoi.getId());
            }
        } catch (SQLException e) {
            System.out.println("Lỗi thêm hóa đơn: " + e.getMessage());
            e.printStackTrace();
        }

        return false;
    }

    // Lấy danh sách chi tiết hóa đơn theo mã hóa đơn
    public static List<ChiTietPhieuDoi> getChiTietHoaDonByMaPD(String maPD) {
        List<ChiTietPhieuDoi> chiTietList = new ArrayList<>();
        String sql = "SELECT * FROM CTPhieuDoi WHERE maPD = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maPD);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ChiTietPhieuDoi chiTiet = new ChiTietPhieuDoi();
                    chiTiet.setIdHoaDonDoi(rs.getString("maPD"));
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
    public static List<PhieuDoi> timHoaDonDoiTheoThoiGian(Date tuNgay, Date denNgay) {
        List<PhieuDoi> danhSachHoaDonDoi = new ArrayList<>();
        String sql = "SELECT * FROM PhieuDoi WHERE thoiGian BETWEEN ? AND ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, new Timestamp(tuNgay.getTime()));
            ps.setTimestamp(2, new Timestamp(denNgay.getTime()));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String maPD = rs.getString("maPD");
                    Date ngayLap = rs.getTimestamp("thoiGian");
                    String idNhanVien = rs.getString("maNV");
                    String idKhachHang = rs.getString("maKH");

                    // Tạo đối tượng hóa đơn
                    PhieuDoi hoaDonDoi = new PhieuDoi(maPD, ngayLap, idNhanVien, idKhachHang);

                    // Lấy thông tin chi tiết
                    hoaDonDoi.setChiTietHoaDonDoi(getChiTietHoaDonByMaPD(maPD));
                    hoaDonDoi.tinhTongTien();

                    // Lấy thông tin nhân viên và khách hàng
                    hoaDonDoi.setNhanVien(NhanVienDAO.getNhanVienByMaNV(idNhanVien));
                    hoaDonDoi.setKhachHang(KhachHangDAO.getKhachHangByMaKH(idKhachHang));

                    danhSachHoaDonDoi.add(hoaDonDoi);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi tìm kiếm hóa đơn theo thời gian: " + e.getMessage());
        }

        return danhSachHoaDonDoi;
    }

    // Tìm kiếm hóa đơn theo khách hàng
    public static List<PhieuDoi> timHoaDonDoiTheoKhachHang(String maKH) {
        List<PhieuDoi> danhSachHoaDonDoi = new ArrayList<>();
        String sql = "SELECT * FROM PhieuDoi WHERE maKH = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maKH);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String maPD = rs.getString("maPD");
                    Date ngayLap = rs.getTimestamp("thoiGian");
                    String idNhanVien = rs.getString("maNV");
                    String idKhachHang = rs.getString("maKH");

                    // Tạo đối tượng hóa đơn
                    PhieuDoi hoaDonDoi = new PhieuDoi(maPD, ngayLap, idNhanVien, idKhachHang);

                    // Lấy thông tin chi tiết
                    hoaDonDoi.setChiTietHoaDonDoi(getChiTietHoaDonByMaPD(maPD));
                    hoaDonDoi.tinhTongTien();

                    // Lấy thông tin nhân viên và khách hàng
                    hoaDonDoi.setNhanVien(NhanVienDAO.getNhanVienByMaNV(idNhanVien));
                    hoaDonDoi.setKhachHang(KhachHangDAO.getKhachHangByMaKH(idKhachHang));

                    danhSachHoaDonDoi.add(hoaDonDoi);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi tìm kiếm hóa đơn theo khách hàng: " + e.getMessage());
        }

        return danhSachHoaDonDoi;
    }

    // Tìm kiếm hóa đơn theo nhân viên
    public static List<PhieuDoi> timHoaDonDoiTheoNhanVien(String maNV) {
        List<PhieuDoi> danhSachHoaDonDoi = new ArrayList<>();
        String sql = "SELECT * FROM PhieuDoi WHERE maNV = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maNV);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String maPD = rs.getString("maPD");
                    Date ngayLap = rs.getTimestamp("thoiGian");
                    String idNhanVien = rs.getString("maNV");
                    String idKhachHang = rs.getString("maKH");

                    // Tạo đối tượng hóa đơn
                    PhieuDoi hoaDonDoi = new PhieuDoi(maPD, ngayLap, idNhanVien, idKhachHang);

                    // Lấy thông tin chi tiết
                    hoaDonDoi.setChiTietHoaDonDoi(getChiTietHoaDonByMaPD(maPD));
                    hoaDonDoi.tinhTongTien();

                    // Lấy thông tin nhân viên và khách hàng
                    hoaDonDoi.setNhanVien(NhanVienDAO.getNhanVienByMaNV(idNhanVien));
                    hoaDonDoi.setKhachHang(KhachHangDAO.getKhachHangByMaKH(idKhachHang));

                    danhSachHoaDonDoi.add(hoaDonDoi);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi tìm kiếm hóa đơn theo nhân viên: " + e.getMessage());
        }

        return danhSachHoaDonDoi;
    }
}
