/*
 * Entity/HoaDon.java
 */
package Entity;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class HoaDon {
    private String id;
    private Date ngayLap;
    private String idNhanVien;
    private String idKhachHang;
    private double tongTien;
    private NhanVien nhanVien;
    private KhachHang khachHang;
    private List<ChiTietHoaDon> chiTietHoaDon;

    public HoaDon() {
        this.chiTietHoaDon = new ArrayList<>();
    }

    public HoaDon(String id, Date ngayLap, String idNhanVien, String idKhachHang, double tongTien) {
        this.id = id;
        this.ngayLap = ngayLap;
        this.idNhanVien = idNhanVien;
        this.idKhachHang = idKhachHang;
        this.tongTien = tongTien;
        this.chiTietHoaDon = new ArrayList<>();
    }
    
    public HoaDon(String id, Date ngayLap, String idNhanVien, String idKhachHang) {
        this.id = id;
        this.ngayLap = ngayLap;
        this.idNhanVien = idNhanVien;
        this.idKhachHang = idKhachHang;
        this.tongTien = 0;
        this.chiTietHoaDon = new ArrayList<>();
    }

    // Getters
    public String getId() { return id; }
    public Date getNgayLap() { return ngayLap; }
    public String getIdNhanVien() { return idNhanVien; }
    public String getIdKhachHang() { return idKhachHang; }
    public double getTongTien() { return tongTien; }
    public NhanVien getNhanVien() { return nhanVien; }
    public KhachHang getKhachHang() { return khachHang; }
    public List<ChiTietHoaDon> getChiTietHoaDon() { return chiTietHoaDon; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setNgayLap(Date ngayLap) { this.ngayLap = ngayLap; }
    public void setIdNhanVien(String idNhanVien) { this.idNhanVien = idNhanVien; }
    public void setIdKhachHang(String idKhachHang) { this.idKhachHang = idKhachHang; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }
    public void setNhanVien(NhanVien nhanVien) { this.nhanVien = nhanVien; }
    public void setKhachHang(KhachHang khachHang) { this.khachHang = khachHang; }
    public void setChiTietHoaDon(List<ChiTietHoaDon> chiTietHoaDon) { this.chiTietHoaDon = chiTietHoaDon; }

    // Thêm chi tiết hoá đơn
    public void addChiTietHoaDon(ChiTietHoaDon chiTiet) {
        this.chiTietHoaDon.add(chiTiet);
        this.tongTien += chiTiet.getThanhTien();
    }

    // Tính tổng tiền
    public void tinhTongTien() {
        double tong = 0;
        for (ChiTietHoaDon chiTiet : chiTietHoaDon) {
            tong += chiTiet.getThanhTien();
        }
        this.tongTien = tong;
    }
    
    @Override
    public String toString() {
        return "HoaDon{" + "id=" + id + ", ngayLap=" + ngayLap + ", nhanVien=" + idNhanVien + ", khachHang=" + idKhachHang + ", tongTien=" + tongTien + '}';
    }
}