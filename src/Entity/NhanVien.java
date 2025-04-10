/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author roxan
 */
public class NhanVien {
    private String id;
    private String hoTen;
    private String sdt;
    private String gioiTinh;
    private Date dtSinh;
    private Date ngayVaoLam;
    private String cccd;
    private String chucVu; 

    public NhanVien(String id, String hoTen, String sdt, String gioiTinh, Date dtSinh, Date ngayVaoLam, String cccd, String chucVu) {
        this.id = id;
        this.hoTen = hoTen;
        this.sdt = sdt;
        this.gioiTinh = gioiTinh;
        this.dtSinh = dtSinh;
        this.ngayVaoLam = ngayVaoLam;
        this.cccd = cccd;
        this.chucVu = chucVu;
    }

    // Getter và Setter cho các trường
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public Date getDtSinh() {
        return dtSinh;
    }

    public void setDtSinh(Date dtSinh) {
        this.dtSinh = dtSinh;
    }

    public Date getNgayVaoLam() {
        return ngayVaoLam;
    }

    public void setNgayVaoLam(Date ngayVaoLam) {
        this.ngayVaoLam = ngayVaoLam;
    }

    public String getCccd() {
        return cccd;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getChucVu() {
        return chucVu;
    }

    public void setChucVu(String chucVu) {
        this.chucVu = chucVu;
    }
}

