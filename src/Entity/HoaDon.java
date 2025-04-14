/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.util.Date;

public class HoaDon {
    private String id;
    private Date ngayLap;
    private String idNhanVien;
    private String idKhachHang;
    private double tongTien;

    public HoaDon(String id, Date ngayLap, String idNhanVien, String idKhachHang, double tongTien) {
        this.id = id;
        this.ngayLap = ngayLap;
        this.idNhanVien = idNhanVien;
        this.idKhachHang = idKhachHang;
        this.tongTien = tongTien;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public Date getNgayLap() { return ngayLap; }
    public void setNgayLap(Date ngayLap) { this.ngayLap = ngayLap; }

    public String getIdNhanVien() { return idNhanVien; }
    public void setIdNhanVien(String idNhanVien) { this.idNhanVien = idNhanVien; }

    public String getIdKhachHang() { return idKhachHang; }
    public void setIdKhachHang(String idKhachHang) { this.idKhachHang = idKhachHang; }

    public double getTongTien() { return tongTien; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }
}

