/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import java.sql.Connection;
 import java.sql.Statement;
 import java.sql.ResultSet;
 import java.sql.SQLException;
import ConnectDB.DatabaseConnection;
import Entity.ChiTietPhieuNhap;
import Entity.NhaCungCap;
import Entity.NhanVien;
import Entity.PhieuNhap;
import DAO.NhaCungCapDAO;
import Entity.Thuoc;
import java.sql.Timestamp;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.ArrayList;
import java.util.Date;
/**
 *
 * @author Admin
 */
public class PhieuNhapDAO {
    public static String taoMaPhieuNhap() {
        String prefix = "PN-";  // Tiền tố của mã hóa đơn
        int maxNumber = 0;

        // Truy vấn để lấy mã hóa đơn mới nhất
        String sql = "SELECT maPN FROM PhieuNhap WHERE maPN LIKE 'PN-%' ORDER BY maPN DESC";

        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            // Kiểm tra mã hóa đơn mới nhất
            if (rs.next()) {
                String lastMaPN = rs.getString("maPN");
                // Lấy số từ mã cuối cùng và tăng lên 1
                int lastNumber = Integer.parseInt(lastMaPN.substring(3));  // Lấy số sau tiền tố "HD-"
                maxNumber = lastNumber + 1;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Tạo mã hóa đơn mới với số có 3 chữ số
        return prefix + String.format("%03d", maxNumber);  // Đảm bảo mã hóa đơn có 3 chữ số
    }
    // Lấy tất cả hóa đơn
    public static List<PhieuNhap> getAllPhieuNhap() {
        List<PhieuNhap> danhSachPhieuNhap = new ArrayList<>();
        String sql = "SELECT * FROM PhieuNhap";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                String maPN = rs.getString("maPN");
                Date ngayLap = rs.getTimestamp("thoiGian");
                String idNhanVien = rs.getString("maNV");
                String idNhaCungCap = rs.getString("maNCC");

                // Tạo đối tượng hóa đơn
                PhieuNhap phieuNhap = new PhieuNhap(maPN, ngayLap, idNhanVien, idNhaCungCap);

                // Lấy thông tin chi tiết từ bảng liên kết
                phieuNhap.setChiTietPhieuNhap(getChiTietPhieuNhapByMaPN(maPN));

                // Tính tổng tiền dựa trên chi tiết
                phieuNhap.tinhTongTien();

                // Lấy thông tin nhân viên và khách hàng
                phieuNhap.setNhanVien(NhanVienDAO.getNhanVienByMaNV(idNhanVien));
                phieuNhap.setNhaCungCap(NhaCungCapDAO.getNhaCungCapByMaNCC(idNhaCungCap));

                danhSachPhieuNhap.add(phieuNhap);
            }
        } catch (SQLException e) {
            System.out.println("Lỗi lấy danh sách hóa đơn: " + e.getMessage());
        }

        return danhSachPhieuNhap;
    }
    public static List<PhieuNhap> getAllPhieuNhap(int startIndex, int limit) {
    List<PhieuNhap> danhSachPhieuNhap = new ArrayList<>();
    String sql = "SELECT * FROM PhieuNhap ORDER BY thoiGian DESC OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setInt(1, startIndex);
        ps.setInt(2, limit);

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String maPN = rs.getString("maPN");
                Date ngayLap = rs.getTimestamp("thoiGian");
                String idNhanVien = rs.getString("maNV");
                String idNhaCungCap = rs.getString("maNCC");

                PhieuNhap phieuNhap = new PhieuNhap(maPN, ngayLap, idNhanVien, idNhaCungCap);

                phieuNhap.setChiTietPhieuNhap(getChiTietPhieuNhapByMaPN(maPN));
                phieuNhap.tinhTongTien();

                phieuNhap.setNhanVien(NhanVienDAO.getNhanVienByMaNV(idNhanVien));
                phieuNhap.setNhaCungCap(NhaCungCapDAO.getNhaCungCapByMaNCC(idNhaCungCap));

                danhSachPhieuNhap.add(phieuNhap);
            }
        }

    } catch (SQLException e) {
        System.out.println("Lỗi lấy danh sách phiếu nhập: " + e.getMessage());
    }

    return danhSachPhieuNhap;
}

    public static List<PhieuNhap> searchPhieuNhap(String maPN,String tenNV, String tenNCC, Date ngayNhap) {
    List<PhieuNhap> danhSachPhieuNhap = new ArrayList<>();

    StringBuilder sql = new StringBuilder(
        "SELECT * FROM PhieuNhap pn " +
        "JOIN NhanVien nv ON pn.maNV = nv.maNV " +
        "JOIN NhaCungCap ncc ON pn.maNCC = ncc.maNCC " +
        "WHERE 1=1"
    );
    if (tenNV != null && !tenNV.isEmpty()) {
        sql.append(" AND maPN LIKE ?");
    }
    if (tenNV != null && !tenNV.isEmpty()) {
        sql.append(" AND nv.hoTen LIKE ?");
    }
    if (tenNCC != null && !tenNCC.isEmpty()) {
        sql.append(" AND ncc.tenNCC LIKE ?");
    }
    if (ngayNhap != null) {
        sql.append(" AND CONVERT(DATE, pn.thoiGian) = ?");
    }

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql.toString())) {

        int paramIndex = 1;
        if (tenNV != null && !tenNV.isEmpty()) {
            ps.setString(paramIndex++, "%" + tenNV + "%");
        }
        if (tenNCC != null && !tenNCC.isEmpty()) {
            ps.setString(paramIndex++, "%" + tenNCC + "%");
        }
        if (ngayNhap != null) {
            ps.setDate(paramIndex++, new java.sql.Date(ngayNhap.getTime()));
        }

        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                PhieuNhap pn = new PhieuNhap();
                pn.setId(rs.getString("maPN"));
                pn.setNgayLap(rs.getTimestamp("thoiGian"));
                pn.setIdNhanVien(rs.getString("maNV"));
                pn.setIdNhaCungCap(rs.getString("maNCC"));

                // Gọi thêm các DAO nếu cần hiển thị thông tin chi tiết
                pn.setNhanVien(NhanVienDAO.getNhanVienByMaNV(pn.getIdNhanVien()));
                pn.setNhaCungCap(NhaCungCapDAO.getNhaCungCapByMaNCC(pn.getIdNhaCungCap()));
                pn.setChiTietPhieuNhap(ChiTietPhieuNhapDAO.getChiTietByPhieuNhapId(pn.getId()));
                pn.tinhTongTien();

                danhSachPhieuNhap.add(pn);
            }
        }

    } catch (SQLException e) {
        System.out.println("Lỗi tìm kiếm phiếu nhập: " + e.getMessage());
    }

    return danhSachPhieuNhap;
}


    // Lấy hóa đơn theo mã
    public static PhieuNhap getPhieuNhapByMaPN(String maPN) {
        String sql = "SELECT * FROM PhieuNhap WHERE maPN = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maPN);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Date ngayLap = rs.getTimestamp("thoiGian");
                    String idNhanVien = rs.getString("maNV");
                    String idNhaCungCap = rs.getString("maNCC");

                    // Tạo đối tượng hóa đơn
                    PhieuNhap phieuNhap = new PhieuNhap(maPN, ngayLap, idNhanVien, idNhaCungCap);

                    // Lấy thông tin chi tiết
                    phieuNhap.setChiTietPhieuNhap(getChiTietPhieuNhapByMaPN(maPN));
                    phieuNhap.tinhTongTien();

                    // Lấy thông tin nhân viên và khách hàng
                    phieuNhap.setNhanVien(NhanVienDAO.getNhanVienByMaNV(idNhanVien));
                    phieuNhap.setNhaCungCap(NhaCungCapDAO.getNhaCungCapByMaNCC(idNhaCungCap));

                    return phieuNhap;
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi lấy phiếu nhập theo mã: " + e.getMessage());
        }

        return null;
    }
    public static boolean them(PhieuNhap phieuNhap) {
    String sqlCheckNhaCungCap = "SELECT COUNT(*) FROM NhaCungCap WHERE maNCC = ?";
    String sqlInsertPhieuNhap = "INSERT INTO PhieuNhap (maPN, maNV, maNCC, thoiGian) VALUES (?, ?, ?, ?)";

    try (Connection conn = DatabaseConnection.getConnection(); 
         PreparedStatement psCheck = conn.prepareStatement(sqlCheckNhaCungCap);
         PreparedStatement psInsert = conn.prepareStatement(sqlInsertPhieuNhap)) {

        // Kiểm tra xem maNCC có tồn tại trong bảng NhaCungCap không
        psCheck.setString(1, phieuNhap.getIdNhaCungCap());
        ResultSet rs = psCheck.executeQuery();
        if (rs.next() && rs.getInt(1) == 0) {
            // Nếu maNCC không tồn tại trong bảng NhaCungCap, trả về false hoặc thông báo lỗi
            System.out.println("Mã nhà cung cấp không tồn tại trong cơ sở dữ liệu.");
            return false;
        }
        // Đảm bảo đóng ResultSet sau khi sử dụng
        rs.close();

        // Nếu maNCC hợp lệ, thực hiện thêm phiếu nhập vào bảng PhieuNhap
        psInsert.setString(1, phieuNhap.getId());  // Gán maPN vào câu lệnh SQL
        psInsert.setString(2, phieuNhap.getIdNhanVien());
        psInsert.setString(3, phieuNhap.getIdNhaCungCap());

        // Chuyển đổi thời gian sang Timestamp
        Timestamp thoiGian = new Timestamp(phieuNhap.getNgayLap().getTime());
        psInsert.setTimestamp(4, thoiGian);  // Gán thời gian vào câu lệnh SQL

        int rowsAffected = psInsert.executeUpdate();

        if (rowsAffected > 0) {
            // Sau khi thêm phiếu nhập thành công, thêm chi tiết phiếu nhập
            return ChiTietPhieuNhapDAO.themChiTietPhieuNhap(phieuNhap.getChiTietPhieuNhap(), phieuNhap.getId());
        }
    } catch (SQLException e) {
        System.out.println("Lỗi thêm phiếu nhập: " + e.getMessage());
        e.printStackTrace();
    }

    return false;
}


    
    public static List<ChiTietPhieuNhap> getChiTietPhieuNhapByMaPN(String maPN) {
        List<ChiTietPhieuNhap> chiTietList = new ArrayList<>();
        String sql = "SELECT * FROM CTPhieuNhap WHERE maPN = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maPN);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ChiTietPhieuNhap chiTiet = new ChiTietPhieuNhap();
                    chiTiet.setIdPhieuNhap(rs.getString("maPN"));
                    chiTiet.setIdThuoc(rs.getString("maThuoc"));
                    chiTiet.setSoLuong(rs.getInt("soLuong"));
                    chiTiet.setDonGia(rs.getDouble("donGia"));

                    // Lấy thông tin thuốc
                    chiTiet.setIdThuoc(ThuocDAO.getThuocByMaThuoc(rs.getString("maThuoc")).getTenThuoc());

                    chiTietList.add(chiTiet);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi lấy chi tiết phiếu nhập: " + e.getMessage());
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

            try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

                ps.setInt(1, soLuongMoi);
                ps.setString(2, maThuoc);

                ps.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Lỗi cập nhật số lượng thuốc: " + e.getMessage());
            }
        }
    }
    // Tìm kiếm hóa đơn theo khoảng thời gian
    public static List<PhieuNhap> timPhieuNhapTheoThoiGian(Date tuNgay, Date denNgay) {
        List<PhieuNhap> danhSachPhieuNhap = new ArrayList<>();
        String sql = "SELECT * FROM PhieuNhap WHERE thoiGian BETWEEN ? AND ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setTimestamp(1, new Timestamp(tuNgay.getTime()));
            ps.setTimestamp(2, new Timestamp(denNgay.getTime()));

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String maPN = rs.getString("maPN");
                    Date ngayLap = rs.getTimestamp("thoiGian");
                    String idNhanVien = rs.getString("maNV");
                    String idNhaCungCap = rs.getString("maNCC");

                    // Tạo đối tượng hóa đơn
                    PhieuNhap phieuNhap = new PhieuNhap(maPN, ngayLap, idNhanVien, idNhaCungCap);

                    // Lấy thông tin chi tiết
                    phieuNhap.setChiTietPhieuNhap(getChiTietPhieuNhapByMaPN(maPN));
                    phieuNhap.tinhTongTien();

                    // Lấy thông tin nhân viên và khách hàng
                    phieuNhap.setNhanVien(NhanVienDAO.getNhanVienByMaNV(idNhanVien));
                    phieuNhap.setNhaCungCap(NhaCungCapDAO.getNhaCungCapByMaNCC(idNhaCungCap));

                    danhSachPhieuNhap.add(phieuNhap);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi tìm kiếm phiếu nhập theo thời gian: " + e.getMessage());
        }

        return danhSachPhieuNhap;
    }
    
    // Tìm kiếm hóa đơn theo khách hàng
    public static List<PhieuNhap> timHoaDonTheoPhieuNhap(String maNV) {
        List<PhieuNhap> danhSachPhieuNhap = new ArrayList<>();
        String sql = "SELECT * FROM PhieuNhap WHERE maNV = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maNV);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String maPN = rs.getString("maPN");
                    Date ngayLap = rs.getTimestamp("thoiGian");
                    String idNhanVien = rs.getString("maNV");
                    String idNhaCungCap = rs.getString("maNCC");

                    // Tạo đối tượng hóa đơn
                    PhieuNhap phieuNhap = new PhieuNhap(maPN, ngayLap, idNhanVien, idNhaCungCap);

                    // Lấy thông tin chi tiết
                    phieuNhap.setChiTietPhieuNhap(getChiTietPhieuNhapByMaPN(maPN));
                    phieuNhap.tinhTongTien();

                    // Lấy thông tin nhân viên và khách hàng
                    phieuNhap.setNhanVien(NhanVienDAO.getNhanVienByMaNV(idNhanVien));
                    phieuNhap.setNhaCungCap(NhaCungCapDAO.getNhaCungCapByMaNCC(idNhaCungCap));

                    danhSachPhieuNhap.add(phieuNhap);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi tìm kiếm hóa đơn theo phiếu nhập: " + e.getMessage());
        }

        return danhSachPhieuNhap;
    }
    
}


    

