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
        List<PhieuTra> danhSachPhieuTra = new ArrayList<>();
        StringBuilder sql = new StringBuilder(
                "SELECT * FROM PhieuTra pt JOIN KhachHang kh ON pt.maKH = kh.maKH WHERE 1=1");

        if (maPT != null && !maPT.isEmpty()) {
            sql.append(" AND pt.maPT LIKE ?");
        }
        if (maHD != null && !maHD.isEmpty()) {
            sql.append(" AND pt.maHD LIKE ?");
        }
        if (tenKH != null && !tenKH.isEmpty()) {
            sql.append(" AND kh.tenKH LIKE ?");
        }
        if (sdt != null && !sdt.isEmpty()) {
            sql.append(" AND kh.sdt LIKE ?");
        }
        if (ngayTra != null) {
            sql.append(" AND CONVERT(DATE, pt.thoiGian) = ?");
        }

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            int paramIndex = 1;
            if (maPT != null && !maPT.isEmpty()) {
                ps.setString(paramIndex++, "%" + maPT + "%");
            }
            if (maHD != null && !maHD.isEmpty()) {
                ps.setString(paramIndex++, "%" + maHD + "%");
            }
            if (tenKH != null && !tenKH.isEmpty()) {
                ps.setString(paramIndex++, "%" + tenKH + "%");
            }
            if (sdt != null && !sdt.isEmpty()) {
                ps.setString(paramIndex++, "%" + sdt + "%");
            }
            if (ngayTra != null) {
                ps.setDate(paramIndex++, new java.sql.Date(ngayTra.getTime()));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    PhieuTra pt = new PhieuTra();
                    pt.setMaPT(rs.getString("maPT"));
                    pt.setNgayLap(rs.getTimestamp("thoiGian"));
                    pt.setMaNV(rs.getString("maNV"));
                    pt.setMaKH(rs.getString("maKH"));
                    pt.setMaHD(rs.getString("maHD"));
                    pt.setLyDo(rs.getString("lyDo"));

                    pt.setChiTietPhieuTra(ChiTietPhieuTraDAO.getChiTietByPhieuTra(pt.getMaPT()));
                    pt.setNhanVien(NhanVienDAO.getNhanVienByMaNV(pt.getMaNV()));
                    pt.setKhachHang(KhachHangDAO.getKhachHangByMaKH(pt.getMaKH()));
                    pt.tinhTongTien();

                    danhSachPhieuTra.add(pt);
                }
            }

        } catch (SQLException e) {
            System.out.println("Lỗi tìm kiếm phiếu trả: " + e.getMessage());
        }

        return danhSachPhieuTra;
    }

    public static List<PhieuTra> getAllPhieuTra() {
        List<PhieuTra> danhSachPhieuTra = new ArrayList<>();
        String sql = "SELECT * FROM PhieuTra";

        try (Connection conn = DatabaseConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String maPT = rs.getString("MaPT");
                Timestamp ngayLap = rs.getTimestamp("ThoiGian");
                String maNV = rs.getString("MaNV");
                String maKH = rs.getString("MaKH");
                String maHD = rs.getString("MaHD");
                String lyDo = rs.getString("LyDo");

                // Tạo đối tượng phiếu trả
                PhieuTra phieuTra = new PhieuTra();
                phieuTra.setMaPT(maPT);
                phieuTra.setNgayLap(ngayLap);
                phieuTra.setMaNV(maNV);
                phieuTra.setMaKH(maKH);
                phieuTra.setMaHD(maHD);
                phieuTra.setLyDo(lyDo);


                // Lấy thông tin chi tiết từ bảng liên kết
                phieuTra.setChiTietPhieuTra(ChiTietPhieuTraDAO.getChiTietByPhieuTra(maPT));

                // Tính tổng tiền từ chi tiết
                phieuTra.tinhTongTien();

                // Lấy thông tin nhân viên và khách hàng
                phieuTra.setNhanVien(NhanVienDAO.getNhanVienByMaNV(maNV));
                phieuTra.setKhachHang(KhachHangDAO.getKhachHangByMaKH(maKH));

                danhSachPhieuTra.add(phieuTra);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi lấy danh sách phiếu trả: " + e.getMessage());
        }

        return danhSachPhieuTra;
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
}