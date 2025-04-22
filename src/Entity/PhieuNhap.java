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
    private String ngayLap;
    private String idNhanVien;
    private String idNhaCungCap;
    private double tongTien;
    private NhanVien nhanVien;
    private NhaCungCap nhaCungCap;
    private boolean trangThai;
    private List<ChiTietPhieuNhap> chiTietPhieuNhap;

    public PhieuNhap(String id, String ngayLap, String idNhanVien, String idNhaCungCap, double tongTien, NhanVien nhanVien,NhaCungCap nhaCungCap, boolean trangThai, List<ChiTietPhieuNhap> chiTietPhieuNhap) {
        this.id = id;
        this.ngayLap = ngayLap;
        this.idNhanVien = idNhanVien;
        this.idNhaCungCap = idNhaCungCap;
        this.tongTien = tongTien;
        this.nhanVien = nhanVien;
        this.trangThai = trangThai;
        this.chiTietPhieuNhap = chiTietPhieuNhap;
    }

    public String getId() {
        return id;
    }

    public String getNgayLap() {
        return ngayLap;
    }

    public String getIdNhanVien() {
        return idNhanVien;
    }

    public String getIdNhaCungCap() {
        return idNhaCungCap;
    }

    public double getTongTien() {
        return tongTien;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }
    public NhaCungCap getNhaCungCap() {
        return nhaCungCap;
    }

    public boolean isTrangThai() {
        return trangThai;
    }

    public List<ChiTietPhieuNhap> getChiTietPhieuNhap() {
        return chiTietPhieuNhap;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNgayLap(String ngayLap) {
        this.ngayLap = ngayLap;
    }

    public void setIdNhanVien(String idNhanVien) {
        this.idNhanVien = idNhanVien;
    }

    public void setIdNhaCungCap(String idNhaCungCap) {
        this.idNhaCungCap = idNhaCungCap;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }
    public void setNhaCungCap(NhaCungCap nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public void setTrangThai(boolean trangThai) {
        this.trangThai = trangThai;
    }

    public void setChiTietPhieuNhap(List<ChiTietPhieuNhap> chiTietPhieuNhap) {
        this.chiTietPhieuNhap = chiTietPhieuNhap;
    }
    
    

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
        return "PhieuNhap{" + "id=" + id + ", ngayLap=" + ngayLap + ", nhanVien=" + idNhanVien + ", khachHang=" + idNhaCungCap + ", tongTien=" + tongTien + '}';
    }
}
