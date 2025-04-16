/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author Admin
 */
public class ChiTietPhieuNhap {
    private PhieuNhap phieuNhap;
    private Thuoc thuoc;
    private int soLuong;
    private double donGia;

    public ChiTietPhieuNhap(PhieuNhap phieuNhap, Thuoc thuoc, int soLuong, double donGia) {
        this.phieuNhap = phieuNhap;
        this.thuoc = thuoc;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public PhieuNhap getPhieuNhap() {
        return phieuNhap;
    }

    public Thuoc getThuoc() {
        return thuoc;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setPhieuNhap(PhieuNhap phieuNhap) {
        this.phieuNhap = phieuNhap;
    }

    public void setThuoc(Thuoc thuoc) {
        this.thuoc = thuoc;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    
          
}
