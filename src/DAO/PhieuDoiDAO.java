package DAO;

import ConnectDB.DatabaseConnection;
import Entity.ChiTietPhieuDoi;
import Entity.PhieuDoi;
import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhieuDoiDAO {

    // Tạo mã hoá đơn đổi mới
    public static String taoMaHoaDonDoi() {
        String prefix = "PD-"; // Tiền tố mã phiếu đổi
        int maxNumber = 0;

        String sql = "SELECT maPD FROM PhieuDoi WHERE maPD LIKE ? ORDER BY maPD DESC";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, prefix + "%");
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    String lastMaPD = rs.getString("maPD");
                    int lastNumber = Integer.parseInt(lastMaPD.substring(prefix.length()));
                    maxNumber = lastNumber + 1;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prefix + String.format("%03d", maxNumber);
    }

    // Lấy tất cả phiếu đổi
    public static List<PhieuDoi> getAllHoaDonDoi() {
        List<PhieuDoi> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM PhieuDoi";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                PhieuDoi pd = mapResultSetToPhieuDoi(rs);
                danhSach.add(pd);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi lấy danh sách phiếu đổi: " + e.getMessage());
        }
        return danhSach;
    }

    // Tìm kiếm phiếu đổi với điều kiện động, tránh SQL injection
    public static List<PhieuDoi> searchHoaDonDoi(String maPD, String maHD, String tenKH, String sdtKH, Date ngayTra) {
        List<PhieuDoi> danhSach = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT pd.* FROM PhieuDoi pd LEFT JOIN KhachHang kh ON pd.maKH = kh.maKH WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (maPD != null && !maPD.trim().isEmpty()) {
            sql.append(" AND pd.maPD LIKE ?");
            params.add("%" + maPD.trim() + "%");
        }
        if (maHD != null && !maHD.trim().isEmpty()) {
            sql.append(" AND pd.maHD LIKE ?");
            params.add("%" + maHD.trim() + "%");
        }
        if (tenKH != null && !tenKH.trim().isEmpty()) {
            sql.append(" AND kh.hoTen LIKE ?");
            params.add("%" + tenKH.trim() + "%");
        }
        if (sdtKH != null && !sdtKH.trim().isEmpty()) {
            sql.append(" AND kh.sdt LIKE ?");
            params.add("%" + sdtKH.trim() + "%");
        }
        if (ngayTra != null) {
            sql.append(" AND CONVERT(DATE, pd.thoiGian) = ?");
            params.add(new java.sql.Date(ngayTra.getTime()));
        }

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PhieuDoi pd = mapResultSetToPhieuDoi(rs);
                    danhSach.add(pd);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi tìm kiếm phiếu đổi: " + e.getMessage());
        }

        return danhSach;
    }

    // Lấy phiếu đổi theo mã
    public static PhieuDoi getHoaDonByMaPD(String maPD) {
        String sql = "SELECT * FROM PhieuDoi WHERE maPD = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPD);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPhieuDoi(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi lấy phiếu đổi theo mã: " + e.getMessage());
        }

        return null;
    }

    public static boolean daTraHang(String maHD) {
        String sql = "SELECT COUNT(*) FROM PhieuDoi WHERE maHD = ?";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maHD);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi kiểm tra hóa đơn đã đổi: " + e.getMessage());
        }
        return false;
    }

    // Thêm phiếu đổi và chi tiết
    public static boolean them(PhieuDoi pd) {
        String sql = "INSERT INTO PhieuDoi (maPD, maNV, maKH, maHD, thoiGian, lyDo) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, pd.getId());
            ps.setString(2, pd.getIdNhanVien());
            ps.setString(3, pd.getIdKhachHang());
            ps.setString(4, pd.getMaHD());
            ps.setTimestamp(5, new Timestamp(pd.getNgayLap().getTime()));
            ps.setString(6, pd.getLyDo());

            int rows = ps.executeUpdate();
            if (rows > 0) {
                boolean ok = ChiTietPhieuDoiDAO.themChiTietHoaDonDoi(pd.getChiTietHoaDonDoi(), pd.getId());
                if (ok) {
                    return ChiTietPhieuDoiDAO.capNhatSoLuongSauKhiDoi(pd.getId(), pd.getMaHD());
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi thêm phiếu đổi: " + e.getMessage());
        }
        return false;
    }

    // Lấy chi tiết phiếu đổi theo mã
    public static List<ChiTietPhieuDoi> getChiTietHoaDonByMaPD(String maPD) {
        List<ChiTietPhieuDoi> chiTietList = new ArrayList<>();
        String sql = "SELECT * FROM CTPhieuDoi WHERE maPD = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

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
            System.out.println("Lỗi lấy chi tiết phiếu đổi: " + e.getMessage());
        }

        return chiTietList;
    }

    // Tìm kiếm phiếu đổi theo khoảng thời gian
    public static List<PhieuDoi> timHoaDonDoiTheoThoiGian(Date tuNgay, Date denNgay) {
        List<PhieuDoi> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM PhieuDoi WHERE thoiGian BETWEEN ? AND ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, new Timestamp(tuNgay.getTime()));
            ps.setTimestamp(2, new Timestamp(denNgay.getTime()));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PhieuDoi pd = mapResultSetToPhieuDoi(rs);
                    danhSach.add(pd);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi tìm kiếm phiếu đổi theo thời gian: " + e.getMessage());
        }

        return danhSach;
    }

    // Tìm kiếm phiếu đổi theo khách hàng
    public static List<PhieuDoi> timHoaDonDoiTheoKhachHang(String maKH) {
        List<PhieuDoi> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM PhieuDoi WHERE maKH = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maKH);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PhieuDoi pd = mapResultSetToPhieuDoi(rs);
                    danhSach.add(pd);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi tìm kiếm phiếu đổi theo khách hàng: " + e.getMessage());
        }

        return danhSach;
    }

    // Tìm kiếm phiếu đổi theo nhân viên
    public static List<PhieuDoi> timHoaDonDoiTheoNhanVien(String maNV) {
        List<PhieuDoi> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM PhieuDoi WHERE maNV = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maNV);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PhieuDoi pd = mapResultSetToPhieuDoi(rs);
                    danhSach.add(pd);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi tìm kiếm phiếu đổi theo nhân viên: " + e.getMessage());
        }

        return danhSach;
    }

    // Hàm tiện ích chuyển ResultSet sang PhieuDoi và set thêm chi tiết, nhân viên,
    // khách hàng
    private static PhieuDoi mapResultSetToPhieuDoi(ResultSet rs) throws SQLException {
        String maPD = rs.getString("maPD");
        Date ngayLap = rs.getTimestamp("thoiGian");
        String idNhanVien = rs.getString("maNV");
        String idKhachHang = rs.getString("maKH");
        String maHD = rs.getString("maHD");
        String lyDo = rs.getString("lyDo");

        PhieuDoi pd = new PhieuDoi(maPD, ngayLap, idNhanVien, idKhachHang);
        pd.setMaHD(maHD);
        pd.setLyDo(lyDo);
        pd.setChiTietHoaDonDoi(getChiTietHoaDonByMaPD(maPD));
        pd.tinhTongTien();
        pd.setNhanVien(NhanVienDAO.getNhanVienByMaNV(idNhanVien));
        pd.setKhachHang(KhachHangDAO.getKhachHangByMaKH(idKhachHang));

        // Lấy tổng tiền hóa đơn gốc và lưu vào 1 biến tạm hoặc mở rộng PhieuDoi
        double tongTienHoaDon = HoaDonDAO.getTongTienHoaDon(maHD);
        // Nếu muốn lưu, bạn có thể thêm biến mới vào PhieuDoi hoặc trả về cùng.
        // Ví dụ, tạm thời dùng setter tạm (nên thêm biến trong entity)
        pd.setTongTienHoaDon(tongTienHoaDon);

        return pd;
    }

    public static PhieuDoi getPhieuDoiByMaPD(String maPD) {
        String sql = "SELECT * FROM PhieuDoi WHERE maPD = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maPD);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    java.util.Date thoiGian = rs.getTimestamp("thoiGian");
                    String maNV = rs.getString("maNV");
                    String maKH = rs.getString("maKH");
                    String maHD = rs.getString("maHD");
                    String ghiChu = null;
                    try {
                        ghiChu = rs.getString("ghiChu");
                    } catch (Exception e) {
                    }

                    // Tạo đối tượng phiếu đổi, bạn chỉnh lại constructor nếu class PhieuDoi khác
                    PhieuDoi phieuDoi = new PhieuDoi(maPD, thoiGian, maNV, maKH, maHD, ghiChu);

                    // Lấy danh sách chi tiết phiếu đổi
                    phieuDoi.setChiTietHoaDonDoi(ChiTietPhieuDoiDAO.getDSChiTietPhieuDoiTheoMa(maPD));

                    // Lấy thông tin nhân viên và khách hàng từ DAO nếu có
                    if (phieuDoi.getNhanVien() == null && NhanVienDAO.getNhanVienByMaNV(maNV) != null) {
                        phieuDoi.setNhanVien(NhanVienDAO.getNhanVienByMaNV(maNV));
                    }
                    if (phieuDoi.getKhachHang() == null && KhachHangDAO.getKhachHangByMaKH(maKH) != null) {
                        phieuDoi.setKhachHang(KhachHangDAO.getKhachHangByMaKH(maKH));
                    }

                    return phieuDoi;
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi lấy phiếu đổi theo mã: " + e.getMessage());
        }

        return null;
    }

}
