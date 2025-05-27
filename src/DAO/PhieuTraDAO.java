package DAO;

import ConnectDB.DatabaseConnection;
import Entity.PhieuTra;
import Entity.ChiTietPhieuTra;
import java.sql.*;
import java.util.*;
import java.util.Date;

public class PhieuTraDAO {
    public static boolean them(PhieuTra pt) {
        String sql = "INSERT INTO PhieuTra (maPT, maNV, maKH, maHD, thoiGian, lyDo) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, pt.getMaPT());
            ps.setString(2, pt.getMaNV());
            ps.setString(3, pt.getMaKH());
            ps.setString(4, pt.getMaHD());
            ps.setTimestamp(5, new Timestamp(pt.getNgayLap().getTime()));
            ps.setString(6, pt.getLyDo());
            int rows = ps.executeUpdate();
            if (rows > 0) {
                for (ChiTietPhieuTra ct : pt.getChiTietPhieuTra()) {
                    ChiTietPhieuTraDAO.themChiTietPhieuTra(ct);
                    capNhatSoLuongKho(ct.getMaThuoc(), ct.getSoLuong());
                }
                return true;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm phiếu trả: " + e.getMessage());
        }
        return false;
    }

    public static List<PhieuTra> getPhieuTraBatch(int start, int limit) {
        List<PhieuTra> list = new ArrayList<>();
        String sql = "SELECT * FROM PhieuTra ORDER BY thoiGian DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, start);
            ps.setInt(2, limit);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                PhieuTra pt = new PhieuTra();
                pt.setMaPT(rs.getString("maPT"));
                pt.setMaNV(rs.getString("maNV"));
                pt.setMaKH(rs.getString("maKH"));
                pt.setMaHD(rs.getString("maHD"));
                pt.setNgayLap(rs.getTimestamp("thoiGian"));
                pt.setLyDo(rs.getString("lyDo"));

                pt.setNhanVien(NhanVienDAO.getNhanVienByMaNV(pt.getMaNV()));
                pt.setKhachHang(KhachHangDAO.getKhachHangByMaKH(pt.getMaKH()));
                pt.setChiTietPhieuTra(ChiTietPhieuTraDAO.getChiTietByPhieuTra(pt.getMaPT()));

                list.add(pt);
            }

        } catch (SQLException e) {
            System.out.println("Lỗi lấy danh sách phiếu trả: " + e.getMessage());
        }

        return list;
    }

    public static void capNhatSoLuongKho(String maThuoc, int soLuongTraVe) {
        String sql = "UPDATE Thuoc SET soLuong = soLuong + ? WHERE maThuoc = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, soLuongTraVe);
            ps.setString(2, maThuoc);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Lỗi cập nhật kho trả thuốc: " + e.getMessage());
        }
    }

    public static boolean daTraHang(String maHD) {
        String sql = "SELECT COUNT(*) FROM PhieuTra WHERE maHD = ?";
        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maHD);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0;
            }
        } catch (SQLException e) {
            System.out.println("Lỗi kiểm tra hóa đơn đã trả: " + e.getMessage());
        }
        return false;
    }

    public static List<PhieuTra> searchPhieuTra(String maPT, String maHD, String tenKH, String sdt, Date ngayTra) {
        List<PhieuTra> danhSach = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT pd.* FROM PhieuTra pd LEFT JOIN KhachHang kh ON pd.maKH = kh.maKH WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (maPT != null && !maPT.trim().isEmpty()) {
            sql.append(" AND pd.maPT LIKE ?");
            params.add("%" + maPT.trim() + "%");
        }
        if (maHD != null && !maHD.trim().isEmpty()) {
            sql.append(" AND pd.maHD LIKE ?");
            params.add("%" + maHD.trim() + "%");
        }
        if (tenKH != null && !tenKH.trim().isEmpty()) {
            sql.append(" AND kh.hoTen LIKE ?");
            params.add("%" + tenKH.trim() + "%");
        }
        if (sdt != null && !sdt.trim().isEmpty()) {
            sql.append(" AND kh.sdt LIKE ?");
            params.add("%" + sdt.trim() + "%");
        }
        if (ngayTra != null) {
            sql.append(" AND CONVERT(DATE, pd.thoiGian) = ?");
            params.add(new java.sql.Date(ngayTra.getTime()));
        }

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PhieuTra pd = mapResultSetToPhieuDoi(rs);
                    danhSach.add(pd);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi tìm kiếm phiếu trả: " + e.getMessage());
        }

        return danhSach;
    }

    public static List<PhieuTra> getAllPhieuTra() {
        List<PhieuTra> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM PhieuTra";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                PhieuTra pd = mapResultSetToPhieuDoi(rs);
                danhSach.add(pd);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi lấy danh sách phiếu trả: " + e.getMessage());
        }
        return danhSach;
    }

    public static String taoMaPhieuTra() {
        String prefix = "PT-";
        int maxNumber = 0;
        String sql = "SELECT maPT FROM PhieuTra WHERE maPT LIKE 'PT-%' ORDER BY maPT DESC";

        try (Connection conn = DatabaseConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                String lastMaPT = rs.getString("maPT");
                int lastNumber = Integer.parseInt(lastMaPT.substring(3));
                maxNumber = lastNumber + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return prefix + String.format("%03d", maxNumber);
    }
    // Lấy chi tiết phiếu đổi theo mã
    public static List<ChiTietPhieuTra> getChiTietHoaDonByMaPD(String maPD) {
        List<ChiTietPhieuTra> chiTietList = new ArrayList<>();
        String sql = "SELECT * FROM CTPhieuTra WHERE maPT = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maPD);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ChiTietPhieuTra ct = new ChiTietPhieuTra();
                    ct.setMaPT(rs.getString("maPT"));
                    ct.setMaThuoc(rs.getString("maThuoc"));
                    ct.setSoLuong(rs.getInt("soLuong"));
                    ct.setDonGia(rs.getDouble("donGia"));

                    chiTietList.add(ct);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi lấy chi tiết phiếu đổi: " + e.getMessage());
        }

        return chiTietList;
    }
    // Tìm kiếm phiếu đổi theo khoảng thời gian
    public static List<PhieuTra> timHoaDonDoiTheoThoiGian(Date tuNgay, Date denNgay) {
        List<PhieuTra> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM PhieuTra WHERE thoiGian BETWEEN ? AND ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, new Timestamp(tuNgay.getTime()));
            ps.setTimestamp(2, new Timestamp(denNgay.getTime()));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PhieuTra pd = mapResultSetToPhieuDoi(rs);
                    danhSach.add(pd);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi tìm kiếm phiếu đổi theo thời gian: " + e.getMessage());
        }

        return danhSach;
    }
    public static PhieuTra getHoaDonByMaPD(String maPD) {
        String sql = "SELECT * FROM PhieuTra WHERE maPT = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPD);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToPhieuDoi(rs);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi lấy phiếu trả theo mã: " + e.getMessage());
        }

        return null;
    }
    // Tìm kiếm phiếu đổi theo khách hàng
    public static List<PhieuTra> timHoaDonDoiTheoKhachHang(String maKH) {
        List<PhieuTra> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM PhieuDoi WHERE maKH = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maKH);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PhieuTra pd = mapResultSetToPhieuDoi(rs);
                    danhSach.add(pd);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi tìm kiếm phiếu đổi theo khách hàng: " + e.getMessage());
        }

        return danhSach;
    }
    // Tìm kiếm phiếu đổi theo nhân viên
    public static List<PhieuTra> timHoaDonDoiTheoNhanVien(String maNV) {
        List<PhieuTra> danhSach = new ArrayList<>();
        String sql = "SELECT * FROM PhieuTra WHERE maNV = ?";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maNV);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PhieuTra pd = mapResultSetToPhieuDoi(rs);
                    danhSach.add(pd);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi tìm kiếm phiếu đổi theo nhân viên: " + e.getMessage());
        }

        return danhSach;
    }
    private static PhieuTra mapResultSetToPhieuDoi(ResultSet rs) throws SQLException {
        String maPT = rs.getString("maPT");
        Date ngayLap = rs.getTimestamp("thoiGian");
        String idNhanVien = rs.getString("maNV");
        String idKhachHang = rs.getString("maKH");
        String maHD = rs.getString("maHD");
        String lyDo = rs.getString("lyDo");

        PhieuTra pd = new PhieuTra(maPT, ngayLap, idNhanVien, idKhachHang);
        pd.setMaHD(maHD);
        pd.setLyDo(lyDo);
        pd.setChiTietPhieuTra(getChiTietHoaDonByMaPD(maPT));
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
}