package Entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PhieuTra {
    private String maPT;
    private Date ngayLap;
    private String maNV;
    private String maKH;
    private String maHD;
    private String lyDo;

    private NhanVien nhanVien;
    private KhachHang khachHang;
    private List<ChiTietPhieuTra> chiTietPhieuTra;

    public PhieuTra() {
        chiTietPhieuTra = new ArrayList<>();
    }

    public String getMaPT() {
        return maPT;
    }

    public Date getNgayLap() {
        return ngayLap;
    }

    public String getMaNV() {
        return maNV;
    }

    public String getMaKH() {
        return maKH;
    }

    public String getMaHD() {
        return maHD;
    }

    public String getLyDo() {
        return lyDo;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public KhachHang getKhachHang() {
        return khachHang;
    }

    public List<ChiTietPhieuTra> getChiTietPhieuTra() {
        return chiTietPhieuTra;
    }

    public void setMaPT(String maPT) {
        this.maPT = maPT;
    }

    public void setNgayLap(Date ngayLap) {
        this.ngayLap = ngayLap;
    }

    public void setMaNV(String maNV) {
        this.maNV = maNV;
    }

    public void setMaKH(String maKH) {
        this.maKH = maKH;
    }

    public void setMaHD(String maHD) {
        this.maHD = maHD;
    }

    public void setLyDo(String lyDo) {
        this.lyDo = lyDo;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public void setKhachHang(KhachHang khachHang) {
        this.khachHang = khachHang;
    }

    public void setChiTietPhieuTra(List<ChiTietPhieuTra> ctList) {
        this.chiTietPhieuTra = ctList;
    }

    public void addChiTietPhieuTra(ChiTietPhieuTra ct) {
        this.chiTietPhieuTra.add(ct);
    }

    public double tinhTongTien() {
        double tong = 0;
        for (ChiTietPhieuTra ct : chiTietPhieuTra) {
            tong += ct.getSoLuong() * ct.getDonGia();
        }
        return tong;
    }
}