package Entity;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class PhieuDoi {
    private String id; // maPD
    private Date ngayLap;
    private String idNhanVien;
    private String idKhachHang;
    private String maHD; // Mã hóa đơn gốc
    private String lyDo; // Lý do đổi
    private double tongTien;

    private NhanVien nhanVien;
    private KhachHang khachHang;
    private List<ChiTietPhieuDoi> chiTietHoaDonDoi;

    public PhieuDoi() {
        this.chiTietHoaDonDoi = new ArrayList<>();
    }

    public PhieuDoi(String id, Date ngayLap, String idNhanVien, String idKhachHang, String maHD, String lyDo) {
        this.id = id;
        this.ngayLap = ngayLap;
        this.idNhanVien = idNhanVien;
        this.idKhachHang = idKhachHang;
        this.maHD = maHD;
        this.lyDo = lyDo;
        this.tongTien = 0;
        this.chiTietHoaDonDoi = new ArrayList<>();
    }

    public PhieuDoi(String id, Date ngayLap, String idNhanVien, String idKhachHang) {
        this.id = id;
        this.ngayLap = ngayLap;
        this.idNhanVien = idNhanVien;
        this.idKhachHang = idKhachHang;
        this.tongTien = 0;
        this.chiTietHoaDonDoi = new ArrayList<>();
    }

    // Getters
    public String getId() {
        return id;
    }

    public Date getNgayLap() {
        return ngayLap;
    }

    public String getIdNhanVien() {
        return idNhanVien;
    }

    public String getIdKhachHang() {
        return idKhachHang;
    }

    public String getMaHD() {
        return maHD;
    }

    public String getLyDo() {
        return lyDo;
    }

    public double getTongTien() {
        return tongTien;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public List<ChiTietPhieuDoi> getChiTietHoaDonDoi() {
        return chiTietHoaDonDoi;
    }

    // Setters
    public void setId(String id) {
        this.id = id;
    }

    public void setNgayLap(Date ngayLap) {
        this.ngayLap = ngayLap;
    }

    public void setIdNhanVien(String idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public void setIdKhachHang(String idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public void setLyDo(String lyDo) {
        this.lyDo = lyDo;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public void setChiTietHoaDonDoi(List<ChiTietPhieuDoi> chiTietHoaDonDoi) {
        this.chiTietHoaDonDoi = chiTietHoaDonDoi;
    }

    // Thêm chi tiết hoá đơn
    public void addChiTietHoaDonDoi(ChiTietPhieuDoi chiTietDoi) {
        this.chiTietHoaDonDoi.add(chiTietDoi);
        this.tongTien += chiTietDoi.getThanhTien();
    }

    // Tính tổng tiền
    public void tinhTongTien() {
        double tong = 0;
        for (ChiTietPhieuDoi chiTietDoi : chiTietHoaDonDoi) {
            tong += chiTietDoi.getThanhTien();
        }
        this.tongTien = tong;
    }

    @Override
    public String toString() {
        return "HoaDonDoi{" +
                "id=" + id +
                ", ngayLap=" + ngayLap +
                ", nhanVien=" + idNhanVien +
                ", khachHang=" + idKhachHang +
                ", maHD=" + maHD +
                ", lyDo=" + lyDo +
                ", tongTien=" + tongTien +
                '}';
    }
}
