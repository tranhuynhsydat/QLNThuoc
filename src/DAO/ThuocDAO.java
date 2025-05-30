package DAO;

import ConnectDB.DatabaseConnection;
import Entity.DanhMuc;
import Entity.DonViTinh;
import Entity.Thuoc;
import Entity.XuatXu;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class ThuocDAO {

    public static Thuoc getThuocByMaThuoc(String maThuoc) {
        Thuoc thuoc = null;

        String sql = "SELECT * FROM Thuoc WHERE maThuoc = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            // Set mã thuốc vào truy vấn
            ps.setString(1, maThuoc);

            // Thực thi truy vấn
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    // Lấy thông tin từ ResultSet
                    String tenThuoc = rs.getString("tenThuoc");
                    String thanhPhan = rs.getString("thanhPhanThuoc");
                    double giaNhap = rs.getDouble("giaNhap");
                    double giaBan = rs.getDouble("donGia");
                    Date hsd = rs.getDate("HSD");
                    String maDM = rs.getString("maDM");
                    String maDVT = rs.getString("maDVT");
                    String maXX = rs.getString("maXX");
                    int soLuong = rs.getInt("soLuong");

                    // Lấy thông tin về Danh Mục, Đơn Vị Tính, Xuất Xứ từ các bảng khác
                    DanhMuc danhMuc = DanhMucDAO.getDanhMucByMa(maDM);
                    DonViTinh donViTinh = DonViTinhDAO.getDonViTinhByMa(maDVT);
                    XuatXu xuatXu = XuatXuDAO.getXuatXuByMa(maXX);

                    // Lấy ảnh thuốc dưới dạng byte[]
                    byte[] anh = rs.getBytes("anh"); // Giả sử tên trường ảnh là "anh"

                    // Tạo đối tượng Thuoc với tất cả thông tin đã lấy
                    thuoc = new Thuoc(maThuoc, tenThuoc, anh, thanhPhan, danhMuc, donViTinh, xuatXu, soLuong, giaNhap, giaBan, hsd);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi nếu có vấn đề với truy vấn SQL
        }
        return thuoc;
    }

    public static List<Thuoc> getThuocByKeyword(String keyword) {
        List<Thuoc> thuocList = new ArrayList<>();
        String sql = "SELECT * FROM Thuoc WHERE tenThuoc LIKE ?";  // Tìm thuốc có tên chứa từ khóa

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + keyword + "%");  // Thêm dấu `%` để tìm kiếm chứa từ khóa

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                // Đọc thông tin thuốc từ ResultSet
                String maThuoc = rs.getString("maThuoc");
                String tenThuoc = rs.getString("tenThuoc");
                String thanhPhan = rs.getString("thanhPhanThuoc");
                double giaNhap = rs.getDouble("giaNhap");
                double giaBan = rs.getDouble("donGia");
                Date hsd = rs.getDate("HSD");
                String maDM = rs.getString("maDM");
                String maDVT = rs.getString("maDVT");
                String maXX = rs.getString("maXX");
                int soLuong = rs.getInt("soLuong");

                // Lấy thông tin về Danh Mục, Đơn Vị Tính, Xuất Xứ từ các bảng khác
                DanhMuc danhMuc = DanhMucDAO.getDanhMucByMa(maDM);
                DonViTinh donViTinh = DonViTinhDAO.getDonViTinhByMa(maDVT);
                XuatXu xuatXu = XuatXuDAO.getXuatXuByMa(maXX);

                // Lấy ảnh thuốc dưới dạng byte[]
                byte[] anh = rs.getBytes("anh");  // Giả sử tên trường ảnh là "anh"

                // Tạo đối tượng Thuoc với tất cả thông tin đã lấy
                Thuoc thuoc = new Thuoc(maThuoc, tenThuoc, anh, thanhPhan, danhMuc, donViTinh, xuatXu, soLuong, giaNhap, giaBan, hsd);
                thuocList.add(thuoc);
            }
        } catch (SQLException e) {
            e.printStackTrace(); // In lỗi nếu có vấn đề với truy vấn SQL
        }
        return thuocList;
    }

    public static List<Thuoc> getAllThuoc() {
        List<Thuoc> danhSachThuoc = new ArrayList<>();
        String sql = "SELECT * FROM Thuoc";

        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                String maThuoc = rs.getString("maThuoc");
                String tenThuoc = rs.getString("tenThuoc");
                String thanhPhan = rs.getString("thanhPhanThuoc");
                double giaNhap = rs.getDouble("giaNhap");
                double donGia = rs.getDouble("donGia");
                Date hsdSql = rs.getDate("HSD");  // Lấy java.sql.Date từ ResultSet
                java.util.Date hsd = null;
                if (hsdSql != null) {
                    hsd = new java.util.Date(hsdSql.getTime());  // Chuyển java.sql.Date thành java.util.Date
                }
                String maDM = rs.getString("maDM");
                String maDVT = rs.getString("maDVT");
                String maXX = rs.getString("maXX");
                int soLuong = rs.getInt("soLuong");

                // Lấy đối tượng DanhMuc, DonViTinh, XuatXu từ mã
                DanhMuc danhMuc = DanhMucDAO.getDanhMucByMa(maDM);
                DonViTinh donViTinh = DonViTinhDAO.getDonViTinhByMa(maDVT);
                XuatXu xuatXu = XuatXuDAO.getXuatXuByMa(maXX);

                // Lấy ảnh dưới dạng byte[]
                byte[] anh = rs.getBytes("anh");
                ImageIcon imageIcon = null;

                // Kiểm tra ảnh có null không
                if (anh != null && anh.length > 0) {
                    imageIcon = new ImageIcon(anh);  // Tạo ImageIcon từ byte[]
                } else {
                    // Nếu ảnh null, sử dụng ảnh mặc định từ SVG
                    imageIcon = new FlatSVGIcon("./icon/image.svg");  // Đặt đường dẫn đến icon SVG mặc định
                }

                // Thêm thuốc vào danh sách
                danhSachThuoc.add(new Thuoc(maThuoc, tenThuoc, anh, thanhPhan, danhMuc, donViTinh, xuatXu, soLuong, giaNhap, donGia, hsd));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhSachThuoc;
    }

    public static boolean xoa(String maThuoc) {
        String sql = "DELETE FROM Thuoc WHERE maThuoc = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maThuoc);  // Truyền mã nhân viên cần xóa vào PreparedStatement

            return ps.executeUpdate() > 0;  // Thực thi câu lệnh và kiểm tra xem có bị ảnh hưởng dòng nào không

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Phương thức tạo mã thuốc tự động theo định dạng T-XXX
    public static String TaoMaThuoc() {
        String prefix = "T-";
        int maxNumber = 0;

        try (Connection conn = DatabaseConnection.getConnection(); Statement stmt = conn.createStatement(); ResultSet rs = stmt.executeQuery("SELECT maThuoc FROM Thuoc WHERE maThuoc LIKE 'T-%'")) {

            while (rs.next()) {
                String ma = rs.getString("maThuoc");
                if (ma != null && ma.startsWith(prefix)) {
                    try {
                        int num = Integer.parseInt(ma.substring(2));
                        if (num > maxNumber) {
                            maxNumber = num;
                        }
                    } catch (NumberFormatException e) {
                        // Bỏ qua nếu không thể chuyển mã thuốc thành số
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int newNumber = maxNumber + 1;
        return prefix + String.format("%03d", newNumber);  // Đảm bảo mã thuốc có 3 chữ số
    }

    // Phương thức thêm thuốc vào cơ sở dữ liệu
    public static boolean them(Thuoc thuoc) {
        String sql = "INSERT INTO Thuoc (maThuoc, tenThuoc, thanhPhanThuoc, giaNhap, donGia, HSD, maDM, maDVT, maXX, soLuong, anh) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, thuoc.getId());  // Mã thuốc
            ps.setString(2, thuoc.getTenThuoc());
            ps.setString(3, thuoc.getThanhPhan());
            ps.setDouble(4, thuoc.getGiaNhap());
            ps.setDouble(5, thuoc.getDonGia()); // Giá bán
            ps.setDate(6, new java.sql.Date(thuoc.getHsd().getTime()));  // Hạn sử dụng
            ps.setString(7, thuoc.getDanhMuc().getId()); // Mã danh mục
            ps.setString(8, thuoc.getDonViTinh().getId());  // Mã đơn vị tính
            ps.setString(9, thuoc.getXuatXu().getId());  // Mã xuất xứ
            ps.setInt(10, thuoc.getSoLuong());
            ps.setBytes(11, thuoc.getHinhAnh());  // Lưu ảnh dưới dạng byte[]

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static List<Thuoc> getThuocBatch(int start, int limit) {
        List<Thuoc> danhSachThuoc = new ArrayList<>();
        String sql = "SELECT * FROM Thuoc ORDER BY maThuoc OFFSET ? ROWS FETCH NEXT ? ROWS ONLY";  // Sử dụng cú pháp đúng cho SQL Server

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, start);  // Chỉ mục bắt đầu (OFFSET)
            ps.setInt(2, limit);  // Số dòng cần lấy (FETCH NEXT)

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String maThuoc = rs.getString("maThuoc");
                    String tenThuoc = rs.getString("tenThuoc");
                    String thanhPhan = rs.getString("thanhPhanThuoc");
                    double giaNhap = rs.getDouble("giaNhap");
                    double donGia = rs.getDouble("donGia");
                    Date hsdSql = rs.getDate("HSD");
                    java.util.Date hsd = null;
                    if (hsdSql != null) {
                        hsd = new java.util.Date(hsdSql.getTime());  // Chuyển java.sql.Date thành java.util.Date
                    }
                    String maDM = rs.getString("maDM");
                    String maDVT = rs.getString("maDVT");
                    String maXX = rs.getString("maXX");
                    int soLuong = rs.getInt("soLuong");

                    // Lấy đối tượng DanhMuc, DonViTinh, XuatXu từ mã
                    DanhMuc danhMuc = DanhMucDAO.getDanhMucByMa(maDM);
                    DonViTinh donViTinh = DonViTinhDAO.getDonViTinhByMa(maDVT);
                    XuatXu xuatXu = XuatXuDAO.getXuatXuByMa(maXX);

                    // Lấy ảnh dưới dạng byte[]
                    byte[] anh = rs.getBytes("anh");
                    ImageIcon imageIcon = null;

                    // Kiểm tra ảnh có null không
                    if (anh != null && anh.length > 0) {
                        imageIcon = new ImageIcon(anh);  // Tạo ImageIcon từ byte[]
                    } else {
                        // Nếu ảnh null, sử dụng ảnh mặc định từ SVG
                        imageIcon = new FlatSVGIcon("./icon/image.svg");  // Đặt đường dẫn đến icon SVG mặc định
                    }

                    // Thêm thuốc vào danh sách
                    danhSachThuoc.add(new Thuoc(maThuoc, tenThuoc, anh, thanhPhan, danhMuc, donViTinh, xuatXu, soLuong, giaNhap, donGia, hsd));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return danhSachThuoc;
    }

    public static boolean sua(Thuoc thuoc) {
        String sql = "UPDATE Thuoc SET tenThuoc = ?, thanhPhanThuoc = ?, giaNhap = ?, donGia = ?, HSD = ?, maDM = ?, maDVT = ?, maXX = ?, soLuong = ?, anh = ? WHERE maThuoc = ?";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

            // Đặt các giá trị vào câu lệnh SQL
            ps.setString(1, thuoc.getTenThuoc());
            ps.setString(2, thuoc.getThanhPhan());
            ps.setDouble(3, thuoc.getGiaNhap());
            ps.setDouble(4, thuoc.getDonGia());
            ps.setDate(5, new java.sql.Date(thuoc.getHsd().getTime()));  // Chuyển đổi HSD từ java.util.Date thành java.sql.Date
            ps.setString(6, thuoc.getDanhMuc().getId());  // Mã danh mục
            ps.setString(7, thuoc.getDonViTinh().getId());  // Mã đơn vị tính
            ps.setString(8, thuoc.getXuatXu().getId());  // Mã xuất xứ
            ps.setInt(9, thuoc.getSoLuong());  // Số lượng
            ps.setBytes(10, thuoc.getHinhAnh());  // Hình ảnh thuốc dưới dạng byte[]
            ps.setString(11, thuoc.getId());  // Mã thuốc để xác định bản ghi cần cập nhật

            // Thực thi câu lệnh UPDATE và kiểm tra xem có cập nhật thành công không
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();  // In ra thông tin chi tiết về lỗi
            JOptionPane.showMessageDialog(null, "Lỗi: " + e.getMessage());  // Hiển thị thông báo lỗi chi tiết
        }
        return false;
    }

    public static List<Thuoc> searchThuoc(String tenThuoc, String thanhPhanThuoc, double giaNhap, double giaBan, Date hsd, String danhMuc, String donViTinh, String xuatXu) {
        List<Thuoc> danhSachThuoc = new ArrayList<>();
        String sql = "SELECT * FROM Thuoc WHERE "
                + "(tenThuoc LIKE ? OR ? = '') AND "
                + "(thanhPhanThuoc LIKE ? OR ? = '') AND "
                + "(giaNhap >= ? OR ? = -1) AND "
                + "(donGia >= ? OR ? = -1) AND "
                + "(HSD >= ? OR ? IS NULL) AND "
                + "(maDM = ? OR ? = '') AND "
                + "(maDVT = ? OR ? = '') AND "
                + "(maXX = ? OR ? = '')";

        try (Connection conn = DatabaseConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + tenThuoc + "%");
            ps.setString(2, tenThuoc);
            ps.setString(3, "%" + thanhPhanThuoc + "%");
            ps.setString(4, thanhPhanThuoc);
            ps.setDouble(5, giaNhap);
            ps.setDouble(6, giaNhap);
            ps.setDouble(7, giaBan);
            ps.setDouble(8, giaBan);
            ps.setDate(9, hsd != null ? new java.sql.Date(hsd.getTime()) : null);
            ps.setDate(10, hsd != null ? new java.sql.Date(hsd.getTime()) : null);
            ps.setString(11, danhMuc);
            ps.setString(12, danhMuc);
            ps.setString(13, donViTinh);
            ps.setString(14, donViTinh);
            ps.setString(15, xuatXu);
            ps.setString(16, xuatXu);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Thuoc thuoc = new Thuoc(
                            rs.getString("maThuoc"),
                            rs.getString("tenThuoc"),
                            rs.getBytes("anh"),
                            rs.getString("thanhPhanThuoc"),
                            DanhMucDAO.getDanhMucByMa(rs.getString("maDM")),
                            DonViTinhDAO.getDonViTinhByMa(rs.getString("maDVT")),
                            XuatXuDAO.getXuatXuByMa(rs.getString("maXX")),
                            rs.getInt("soLuong"),
                            rs.getDouble("giaNhap"),
                            rs.getDouble("donGia"),
                            rs.getDate("HSD")
                    );
                    danhSachThuoc.add(thuoc);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return danhSachThuoc;
    }

}
