/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;


/**
 *
 * @author Admin
 */
public class PhieuNhap {
    private String id;
    private Date ngayLap;
    private String idNhanVien;
    private String idNhaCungCap;
    private double tongTien;
    private NhanVien nhanVien;
    private NhaCungCap nhaCungCap;
    private List<ChiTietPhieuNhap> chiTietPhieuNhap;

    public PhieuNhap() {
        this.chiTietPhieuNhap = new ArrayList<>();
    }

    public PhieuNhap(String id, Date ngayLap, String idNhanVien, String idNhaCungCap, double tongTien) {
        this.id = id;
        this.ngayLap = ngayLap;
        this.idNhanVien = idNhanVien;
        this.idNhaCungCap = idNhaCungCap;
        this.tongTien = tongTien;
        this.chiTietPhieuNhap = new ArrayList<>();
    }
    
    public PhieuNhap(String id, Date ngayLap, String idNhanVien, String idNhaCungCap) {
        this.id = id;
        this.ngayLap = ngayLap;
        this.idNhanVien = idNhanVien;
        this.idNhaCungCap = idNhaCungCap;
        this.tongTien = 0;
        this.chiTietPhieuNhap = new ArrayList<>();
    }

    // Getters
    public String getId() { return id; }
    public Date getNgayLap() { return ngayLap; }
    public String getIdNhanVien() { return idNhanVien; }
    public String getIdNhaCungCap() { return idNhaCungCap; }
    public double getTongTien() { return tongTien; }
    public NhanVien getNhanVien() { return nhanVien; }
    public NhaCungCap getNhaCungCap() { return nhaCungCap; }
    public List<ChiTietPhieuNhap> getChiTietPhieuNhap() { return chiTietPhieuNhap; }

    // Setters
    public void setId(String id) { this.id = id; }
    public void setNgayLap(Date ngayLap) { this.ngayLap = ngayLap; }
    public void setIdNhanVien(String idNhanVien) { this.idNhanVien = idNhanVien; }
    public void setIdNhaCungCap(String idNhaCungCap) { this.idNhaCungCap = idNhaCungCap; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }
    public void setNhanVien(NhanVien nhanVien) { this.nhanVien = nhanVien; }
    public void setNhaCungCap(NhaCungCap nhaCungCap) { this.nhaCungCap = nhaCungCap; }
    public void setChiTietPhieuNhap(List<ChiTietPhieuNhap> chiTietHoaDon) { this.chiTietPhieuNhap = chiTietHoaDon; }

    // Thêm chi tiết hoá đơn
    public void addChiTietPhieuNhap(ChiTietPhieuNhap chiTiet) {
        this.chiTietPhieuNhap.add(chiTiet);
        this.tongTien += chiTiet.getThanhTien();
    }

    // Tính tổng tiền
    public void tinhTongTien() {
        double tong = 0;
        for (ChiTietPhieuNhap chiTiet : chiTietPhieuNhap) {
            tong += chiTiet.getThanhTien();
        }
        this.tongTien = tong;
    }
    
    @Override
    public String toString() {
        return "PhieuNhap{" + "id=" + id + ", ngayLap=" + ngayLap + ", nhanVien=" + idNhanVien + ", nhaCungCap=" + idNhaCungCap + ", tongTien=" + tongTien + '}';
    }
}
