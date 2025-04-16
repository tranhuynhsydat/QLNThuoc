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

    // Truy vấn doanh thu và chi phí trong 7 ngày qua
    private final String SELECT_7_DAYS_AGO = """
                                        WITH dates AS (
                                            SELECT DATEADD(DAY, -6, GETDATE()) AS date
                                            UNION ALL
                                            SELECT DATEADD(DAY, 1, date)
                                            FROM dates
                                            WHERE date < CAST(GETDATE() AS DATE)
                                        )
                                        SELECT 
                                            dates.date AS ngay,
                                            COALESCE(SUM(CTHoaDon.soLuong * Thuoc.donGia), 0) AS doanhthu,
                                            COALESCE(SUM(CTHoaDon.soLuong * Thuoc.giaNhap), 0) AS chiphi
                                        FROM dates
                                        LEFT JOIN HoaDon ON CONVERT(DATE, HoaDon.thoiGian) = CONVERT(DATE, dates.date)
                                        LEFT JOIN CTHoaDon ON CTHoaDon.maHD = HoaDon.maHD
                                        LEFT JOIN Thuoc ON Thuoc.maThuoc = CTHoaDon.maThuoc
                                        GROUP BY dates.date
                                        ORDER BY dates.date;
                                        """;

    // Truy vấn doanh thu, chi phí theo ngày trong tháng và năm
    private final String SELECT_DAYS_BY_MONTH_YEAR = """
                                                     DECLARE @thang INT = ?;
                                                     DECLARE @nam INT = ?;
                                                     
                                                     DECLARE @ngayString NVARCHAR(10) = CONVERT(NVARCHAR(10), @nam) + '-' + RIGHT('0' + CONVERT(NVARCHAR(2), @thang), 2) + '-01';
                                                     
                                                     WITH numbers AS (
                                                         SELECT ROW_NUMBER() OVER (ORDER BY (SELECT NULL)) - 1 AS number
                                                         FROM master..spt_values
                                                     )
                                                     SELECT dates.date AS ngay,
                                                     	COALESCE(SUM(CTHoaDon.soLuong * Thuoc.donGia), 0) AS doanhthu,
                                                     	COALESCE(SUM(CTHoaDon.soLuong * Thuoc.giaNhap), 0) AS chiphi
                                                     FROM (
                                                         SELECT DATEADD(DAY, c.number, @ngayString) AS date
                                                         FROM numbers c
                                                         WHERE DATEADD(DAY, c.number, @ngayString) <= DATEADD(DAY, -1, DATEADD(MONTH, DATEDIFF(MONTH, 0, @ngayString) + 1, 0))
                                                     ) AS dates
                                                         LEFT JOIN HoaDon ON CONVERT(DATE, HoaDon.thoiGian) = CONVERT(DATE, dates.date)
                                                         LEFT JOIN CTHoaDon ON CTHoaDon.maHD = HoaDon.maHD
                                                         LEFT JOIN Thuoc ON Thuoc.maThuoc = CTHoaDon.maThuoc
                                                     GROUP BY dates.date
                                                     ORDER BY dates.date;
                                                     """;

    // Truy vấn doanh thu, chi phí từ năm này sang năm khác
    private final String SELECT_FROM_YEAR_TO_YEAR = """
                                                    DECLARE @start_year INT = ?;
                                                    DECLARE @end_year INT = ?;
                                                                                                        
                                                    WITH years(year) AS (
                                                        SELECT @start_year
                                                        UNION ALL
                                                        SELECT year + 1
                                                        FROM years
                                                        WHERE year < @end_year
                                                    )
                                                    SELECT 
                                                        years.year AS nam,
                                                        COALESCE(SUM(CTHoaDon.soLuong * Thuoc.donGia), 0) AS doanhthu,
                                                        COALESCE(SUM(CTHoaDon.soLuong * Thuoc.giaNhap), 0) AS chiphi
                                                    FROM years
                                                    LEFT JOIN HoaDon ON YEAR(HoaDon.thoiGian) = years.year
                                                    LEFT JOIN CTHoaDon ON HoaDon.maHD = CTHoaDon.maHD
                                                    LEFT JOIN Thuoc ON Thuoc.maThuoc = CTHoaDon.maThuoc
                                                    GROUP BY years.year
                                                    ORDER BY years.year;
                                                    """;

    // Truy vấn doanh thu, chi phí theo tháng trong năm
    private final String SELECT_MOUNTH_BY_YEAR = """
                                          DECLARE @year INT = ?;
                                          
                                          SELECT 
                                          	months.month AS thang,
                                          	COALESCE(SUM(CTHoaDon.soLuong * Thuoc.donGia), 0) AS doanhthu,
                                          	COALESCE(SUM(CTHoaDon.soLuong * Thuoc.giaNhap), 0) AS chiphi
                                          FROM (
                                                 VALUES (1), (2), (3), (4), (5), (6), (7), (8), (9), (10), (11), (12)
                                               ) AS months(month)
                                          LEFT JOIN HoaDon ON MONTH(HoaDon.thoiGian) = months.month AND YEAR(HoaDon.thoiGian) = @year
                                          LEFT JOIN CTHoaDon ON CTHoaDon.maHD = HoaDon.maHD
                                          LEFT JOIN Thuoc ON Thuoc.maThuoc = CTHoaDon.maThuoc
                                          GROUP BY months.month
                                          ORDER BY months.month;
                                          """;

    // Phương thức truy vấn doanh thu theo ngày trong tháng và năm
    public List<ThongKe> selectDaysByMonthYear(int month, int year) {
        List<ThongKe> listE = new ArrayList<>();
        try (Connection con = DatabaseConnection.getConnection()) {
            // Sử dụng PreparedStatement để thay thế tham số vào câu lệnh SQL
            PreparedStatement stmt = con.prepareStatement(SELECT_DAYS_BY_MONTH_YEAR);
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

    // Phương thức truy vấn doanh thu và chi phí trong 7 ngày qua
    public List<ThongKe> select7DaysAgo() {
        return this.selectBySql(SELECT_7_DAYS_AGO);
    }

    // Phương thức truy vấn doanh thu, chi phí từ năm này sang năm khác
    public List<ThongKeTheoNam> selectFromYearToYear(int fromYear, int toYear) {
        return this.selectBySqlTheoNam(SELECT_FROM_YEAR_TO_YEAR, fromYear, toYear);
    }

    // Phương thức truy vấn doanh thu theo tháng trong năm
    public List<ThongKeTheoThang> selectMounthsByYear(int year) {
        return this.selectBySqlTheoThang(SELECT_MOUNTH_BY_YEAR, year);
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
