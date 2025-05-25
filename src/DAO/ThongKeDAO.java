package DAO;

import ConnectDB.DatabaseConnection;
import Entity.ThongKe;
import Entity.ThongKeTheoNam;
import Entity.ThongKeTheoThang;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

public class ThongKeDAO {

    

    // Truy vấn doanh thu, chi phí theo ngày trong tháng và năm
    private final String SELECT_3_DAYS_BY_MONTH_YEAR = """
                                                    DECLARE @thang INT = ?;
                                                    DECLARE @nam INT = ?;
                                                    
                                                    DECLARE @ngayString NVARCHAR(10) = CONVERT(NVARCHAR(10), @nam) + '-' + RIGHT('0' + CONVERT(NVARCHAR(2), @thang), 2) + '-01';
                                                    
                                                    WITH numbers AS (
                                                        SELECT ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) - 1 AS number
                                                        FROM master..spt_values
                                                    ),
                                                    
                                                    dates AS (
                                                        SELECT DATEADD(DAY, number, @ngayString) AS ngay
                                                        FROM numbers
                                                        WHERE DATEADD(DAY, number, @ngayString) <= EOMONTH(@ngayString)
                                                    ),
                                                    
                                                    HoaDonKhongBiDoi AS (
                                                        SELECT hd.*
                                                        FROM HoaDon hd
                                                        LEFT JOIN PhieuDoi pd ON pd.maHD = hd.maHD
                                                        WHERE pd.maHD IS NULL
                                                    ),
                                                    
                                                    DoanhThuHoaDon AS (
                                                        SELECT CONVERT(DATE, hd.thoiGian) AS ngay,
                                                               SUM(cthd.soLuong * t.donGia) AS doanhThu
                                                        FROM HoaDonKhongBiDoi hd
                                                        JOIN CTHoaDon cthd ON hd.maHD = cthd.maHD
                                                        JOIN Thuoc t ON t.maThuoc = cthd.maThuoc
                                                        WHERE YEAR(hd.thoiGian) = @nam AND MONTH(hd.thoiGian) = @thang
                                                        GROUP BY CONVERT(DATE, hd.thoiGian)
                                                    ),
                                                    
                                                    DoanhThuPhieuDoi AS (
                                                        SELECT CONVERT(DATE, pd.thoiGian) AS ngay,
                                                               SUM(ctpd.soLuongMoi * t.donGia) AS doanhThu
                                                        FROM PhieuDoi pd
                                                        JOIN CTPhieuDoi ctpd ON pd.maPD = ctpd.maPD
                                                        JOIN Thuoc t ON t.maThuoc = ctpd.maThuocMoi
                                                        WHERE YEAR(pd.thoiGian) = @nam AND MONTH(pd.thoiGian) = @thang
                                                        GROUP BY CONVERT(DATE, pd.thoiGian)
                                                    ),
                                                    
                                                    DoanhThuPhieuTra AS (
                                                        SELECT CONVERT(DATE, pt.thoiGian) AS ngay,
                                                               SUM(ctpt.soLuong * t.donGia) AS doanhThu
                                                        FROM PhieuTra pt
                                                        JOIN CTPhieuTra ctpt ON pt.maPT = ctpt.maPT
                                                        JOIN Thuoc t ON t.maThuoc = ctpt.maThuoc
                                                        WHERE YEAR(pt.thoiGian) = @nam AND MONTH(pt.thoiGian) = @thang
                                                        GROUP BY CONVERT(DATE, pt.thoiGian)
                                                    ),
                                                    
                                                    ChiPhiHoaDon AS (
                                                        SELECT CONVERT(DATE, hd.thoiGian) AS ngay,
                                                               SUM(cthd.soLuong * t.giaNhap) AS chiPhi
                                                        FROM HoaDonKhongBiDoi hd
                                                        JOIN CTHoaDon cthd ON hd.maHD = cthd.maHD
                                                        JOIN Thuoc t ON t.maThuoc = cthd.maThuoc
                                                        WHERE YEAR(hd.thoiGian) = @nam AND MONTH(hd.thoiGian) = @thang
                                                        GROUP BY CONVERT(DATE, hd.thoiGian)
                                                    )
                                                    
                                                    SELECT
                                                        d.ngay,
                                                        COALESCE(dhdo.doanhThu, 0) + COALESCE(dpdoi.doanhThu, 0) - COALESCE(dptra.doanhThu, 0) AS doanhThu,
                                                        COALESCE(chiphihd.chiPhi, 0) AS chiPhi
                                                    FROM dates d
                                                    LEFT JOIN DoanhThuHoaDon dhdo ON dhdo.ngay = d.ngay
                                                    LEFT JOIN DoanhThuPhieuDoi dpdoi ON dpdoi.ngay = d.ngay
                                                    LEFT JOIN DoanhThuPhieuTra dptra ON dptra.ngay = d.ngay
                                                    LEFT JOIN ChiPhiHoaDon chiphihd ON chiphihd.ngay = d.ngay
                                                    ORDER BY d.ngay;
                                                     """;

    // Truy vấn doanh thu, chi phí từ năm này sang năm khác
    private final String SELECT_FROM_YEAR_TO_YEAR = """
                                                    DECLARE @start_year INT = ?;
                                                    DECLARE @end_year INT = ?;
                                                    
                                                    WITH years(year) AS (
                                                        SELECT @start_year
                                                        UNION ALL
                                                        SELECT year + 1 FROM years WHERE year < @end_year
                                                    ),
                                                    
                                                    HoaDonKhongBiDoi AS (
                                                        SELECT hd.*
                                                        FROM HoaDon hd
                                                        LEFT JOIN PhieuDoi pd ON pd.maHD = hd.maHD
                                                        WHERE pd.maHD IS NULL
                                                    ),
                                                    
                                                    DoanhThuHoaDon AS (
                                                        SELECT YEAR(hd.thoiGian) AS nam,
                                                               SUM(cthd.soLuong * t.donGia) AS doanhthu
                                                        FROM HoaDonKhongBiDoi hd
                                                        JOIN CTHoaDon cthd ON hd.maHD = cthd.maHD
                                                        JOIN Thuoc t ON t.maThuoc = cthd.maThuoc
                                                        GROUP BY YEAR(hd.thoiGian)
                                                    ),
                                                    
                                                    DoanhThuPhieuDoi AS (
                                                        SELECT YEAR(pd.thoiGian) AS nam,
                                                               SUM(ctpd.soLuongMoi * t.donGia) AS doanhthu
                                                        FROM PhieuDoi pd
                                                        JOIN CTPhieuDoi ctpd ON pd.maPD = ctpd.maPD
                                                        JOIN Thuoc t ON t.maThuoc = ctpd.maThuocMoi
                                                        GROUP BY YEAR(pd.thoiGian)
                                                    ),
                                                    
                                                    DoanhThuPhieuTra AS (
                                                        SELECT YEAR(pt.thoiGian) AS nam,
                                                               SUM(ctpt.soLuong * t.donGia) AS doanhthu
                                                        FROM PhieuTra pt
                                                        JOIN CTPhieuTra ctpt ON pt.maPT = ctpt.maPT
                                                        JOIN Thuoc t ON t.maThuoc = ctpt.maThuoc
                                                        GROUP BY YEAR(pt.thoiGian)
                                                    ),
                                                    
                                                    ChiPhiHoaDon AS (
                                                        SELECT YEAR(hd.thoiGian) AS nam,
                                                               SUM(cthd.soLuong * t.giaNhap) AS chiphi
                                                        FROM HoaDonKhongBiDoi hd
                                                        JOIN CTHoaDon cthd ON hd.maHD = cthd.maHD
                                                        JOIN Thuoc t ON t.maThuoc = cthd.maThuoc
                                                        GROUP BY YEAR(hd.thoiGian)
                                                    )
                                                    
                                                    SELECT
                                                        y.year AS nam,
                                                        COALESCE(dhdo.doanhthu, 0) + COALESCE(dpdoi.doanhthu, 0) - COALESCE(dptra.doanhthu, 0) AS doanhThu,
                                                        COALESCE(chiphihd.chiphi, 0) AS chiPhi
                                                    FROM years y
                                                    LEFT JOIN DoanhThuHoaDon dhdo ON dhdo.nam = y.year
                                                    LEFT JOIN DoanhThuPhieuDoi dpdoi ON dpdoi.nam = y.year
                                                    LEFT JOIN DoanhThuPhieuTra dptra ON dptra.nam = y.year
                                                    LEFT JOIN ChiPhiHoaDon chiphihd ON chiphihd.nam = y.year
                                                    ORDER BY y.year
                                                    OPTION (MAXRECURSION 0);
                                                    """;

    // Truy vấn doanh thu, chi phí theo tháng trong năm
    private final String SELECT_MONTH_BY_YEAR = """
                                          DECLARE @year INT = ?;
                                          
                                          WITH Months AS (
                                              SELECT 1 AS thang
                                              UNION ALL
                                              SELECT thang + 1 FROM Months WHERE thang < 12
                                          ),
                                          
                                          HoaDonKhongBiDoi AS (
                                              SELECT hd.*
                                              FROM HoaDon hd
                                              LEFT JOIN PhieuDoi pd ON pd.maHD = hd.maHD
                                              WHERE pd.maHD IS NULL
                                          ),
                                          
                                          DoanhThuHoaDon AS (
                                              SELECT MONTH(hd.thoiGian) AS thang,
                                                     SUM(cthd.soLuong * t.donGia) AS doanhThu
                                              FROM HoaDonKhongBiDoi hd
                                              JOIN CTHoaDon cthd ON hd.maHD = cthd.maHD
                                              JOIN Thuoc t ON t.maThuoc = cthd.maThuoc
                                              WHERE YEAR(hd.thoiGian) = @year
                                              GROUP BY MONTH(hd.thoiGian)
                                          ),
                                          
                                          DoanhThuPhieuDoi AS (
                                              SELECT MONTH(pd.thoiGian) AS thang,
                                                     SUM(ctpd.soLuongMoi * t.donGia) AS doanhThu
                                              FROM PhieuDoi pd
                                              JOIN CTPhieuDoi ctpd ON pd.maPD = ctpd.maPD
                                              JOIN Thuoc t ON t.maThuoc = ctpd.maThuocMoi
                                              WHERE YEAR(pd.thoiGian) = @year
                                              GROUP BY MONTH(pd.thoiGian)
                                          ),
                                          
                                          DoanhThuPhieuTra AS (
                                              SELECT MONTH(pt.thoiGian) AS thang,
                                                     SUM(ctpt.soLuong * t.donGia) AS doanhThu
                                              FROM PhieuTra pt
                                              JOIN CTPhieuTra ctpt ON pt.maPT = ctpt.maPT
                                              JOIN Thuoc t ON t.maThuoc = ctpt.maThuoc
                                              WHERE YEAR(pt.thoiGian) = @year
                                              GROUP BY MONTH(pt.thoiGian)
                                          ),
                                          
                                          ChiPhiHoaDon AS (
                                              SELECT MONTH(hd.thoiGian) AS thang,
                                                     SUM(cthd.soLuong * t.giaNhap) AS chiPhi
                                              FROM HoaDonKhongBiDoi hd
                                              JOIN CTHoaDon cthd ON hd.maHD = cthd.maHD
                                              JOIN Thuoc t ON t.maThuoc = cthd.maThuoc
                                              WHERE YEAR(hd.thoiGian) = @year
                                              GROUP BY MONTH(hd.thoiGian)
                                          )
                                          
                                          SELECT
                                              m.thang,
                                              COALESCE(dhdo.doanhThu, 0) + COALESCE(dpdoi.doanhThu, 0) - COALESCE(dptra.doanhThu, 0) AS doanhThu,
                                              COALESCE(chiphihd.chiPhi, 0) AS chiPhi
                                          FROM Months m
                                          LEFT JOIN DoanhThuHoaDon dhdo ON dhdo.thang = m.thang
                                          LEFT JOIN DoanhThuPhieuDoi dpdoi ON dpdoi.thang = m.thang
                                          LEFT JOIN DoanhThuPhieuTra dptra ON dptra.thang = m.thang
                                          LEFT JOIN ChiPhiHoaDon chiphihd ON chiphihd.thang = m.thang
                                          ORDER BY m.thang
                                          OPTION (MAXRECURSION 12);
                                          """;

    // Phương thức truy vấn doanh thu theo ngày trong tháng và năm
    public List<ThongKe> select3DaysByMonthYear(int month, int year) {
        List<ThongKe> listE = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection()) {
            // Sử dụng PreparedStatement để thay thế tham số vào câu lệnh SQL
            PreparedStatement stmt = con.prepareStatement(SELECT_3_DAYS_BY_MONTH_YEAR);
            stmt.setInt(1, month);  // Thay thế tham số tháng
            stmt.setInt(2, year);   // Thay thế tham số năm

            // Thực thi truy vấn và xử lý kết quả
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                ThongKe e = new ThongKe();
                e.setThoiGian(rs.getDate("ngay"));
                e.setDoanhThu(rs.getDouble("doanhthu"));
                e.setChiPhi(rs.getDouble("chiphi"));
                listE.add(e);
            }

            rs.getStatement().getConnection().close();
            return listE;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



    // Phương thức truy vấn doanh thu, chi phí từ năm này sang năm khác
    public List<ThongKeTheoNam> selectFromYearToYear(int fromYear, int toYear) {
        return this.selectBySqlTheoNam(SELECT_FROM_YEAR_TO_YEAR, fromYear, toYear);
    }

    // Phương thức truy vấn doanh thu theo tháng trong năm
    public List<ThongKeTheoThang> selectMonthsByYear(int year) {
        return this.selectBySqlTheoThang(SELECT_MONTH_BY_YEAR, year);
    }

    // Truy vấn và chuyển đổi kết quả thành danh sách ThongKe
    protected List<ThongKe> selectBySql(String sql, Object... args) {
        List<ThongKe> listE = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ThongKe e = new ThongKe();
                e.setThoiGian(rs.getDate("ngay"));
                e.setDoanhThu(rs.getDouble("doanhthu"));
                e.setChiPhi(rs.getDouble("chiphi"));
                listE.add(e);
            }
            rs.getStatement().getConnection().close();
            return listE;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Truy vấn và chuyển đổi kết quả thành danh sách ThongKeTheoNam
    protected List<ThongKeTheoNam> selectBySqlTheoNam(String sql, Object... args) {
        List<ThongKeTheoNam> listE = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ThongKeTheoNam e = new ThongKeTheoNam();
                e.setNam(rs.getInt("nam"));
                e.setDoanhThu(rs.getDouble("doanhthu"));
                e.setChiPhi(rs.getDouble("chiphi"));
                listE.add(e);
            }
            rs.getStatement().getConnection().close();
            return listE;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    // Truy vấn và chuyển đổi kết quả thành danh sách ThongKeTheoThang
    protected List<ThongKeTheoThang> selectBySqlTheoThang(String sql, Object... args) {
        List<ThongKeTheoThang> listE = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection()) {
            PreparedStatement stmt = con.prepareStatement(sql);
            for (int i = 0; i < args.length; i++) {
                stmt.setObject(i + 1, args[i]);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                ThongKeTheoThang e = new ThongKeTheoThang();
                e.setThang(rs.getInt("thang"));
                e.setDoanhThu(rs.getDouble("doanhthu"));
                e.setChiPhi(rs.getDouble("chiphi"));
                listE.add(e);
            }
            rs.getStatement().getConnection().close();
            return listE;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
