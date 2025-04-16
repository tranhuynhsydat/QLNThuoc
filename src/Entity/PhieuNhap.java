/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author Admin
 */
public class PhieuNhap {
    private String maPN;
    private NhanVien nhanVien;
    private NhaCungCap nhaCungCap;
    private String trangThai;
    private Date thoiGian;

    public PhieuNhap(String maPN, NhanVien nhanVien, NhaCungCap nhaCungCap, String trangThai, Date thoiGian) {
        this.maPN = maPN;
        this.nhanVien = nhanVien;
        this.nhaCungCap = nhaCungCap;
        this.trangThai = trangThai;
        this.thoiGian = thoiGian;
    }

    public String getMaPN() {
        return maPN;
    }

    public NhanVien getNhanVien() {
        return nhanVien;
    }

    public NhaCungCap getNhaCungCap() {
        return nhaCungCap;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public Date getThoiGian() {
        return thoiGian;
    }

    public void setMaPN(String maPN) {
        this.maPN = maPN;
    }

    public void setNhanVien(NhanVien nhanVien) {
        this.nhanVien = nhanVien;
    }

    public void setNhaCungCap(NhaCungCap nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public void setThoiGian(Date thoiGian) {
        this.thoiGian = thoiGian;
    }

   
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.maPN);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final PhieuNhap other = (PhieuNhap) obj;
        return Objects.equals(this.maPN, other.maPN);
    }

    @Override
    public String toString() {
        return "PhieuNhap{" + "maPN=" + maPN + ", nhanVien=" + nhanVien + ", nhaCungCap=" + nhaCungCap + ", trangThai=" + trangThai + ", thoiGian=" + thoiGian + '}';
    }
    
    
   
         
}
