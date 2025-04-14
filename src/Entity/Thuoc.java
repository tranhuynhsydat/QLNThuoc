/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.util.Date;

public class Thuoc {

    private String id;
    private String tenThuoc;
    private byte[] hinhAnh;
    private String thanhPhan;
    private DanhMuc danhMuc;
    private DonViTinh donViTinh;
    private XuatXu xuatXu;
    private int soLuong;
    private double giaNhap;
    private double donGia;
    private Date hsd;

    public String getId() {
        return id;
    }

    public String getTenThuoc() {
        return tenThuoc;
    }

    public byte[] getHinhAnh() {
        return hinhAnh;
    }

    public String getThanhPhan() {
        return thanhPhan;
    }

    public DanhMuc getDanhMuc() {
        return danhMuc;
    }

    public DonViTinh getDonViTinh() {
        return donViTinh;
    }

    public XuatXu getXuatXu() {
        return xuatXu;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public double getGiaNhap() {
        return giaNhap;
    }

    public double getDonGia() {
        return donGia;
    }

    public Date getHsd() {
        return hsd;
    }


    public void setTenThuoc(String tenThuoc) {
        this.tenThuoc = tenThuoc;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public void setThanhPhan(String thanhPhan) {
        this.thanhPhan = thanhPhan;
    }

    public void setDanhMuc(DanhMuc danhMuc) {
        this.danhMuc = danhMuc;
    }

    public void setDonViTinh(DonViTinh donViTinh) {
        this.donViTinh = donViTinh;
    }

    public void setXuatXu(XuatXu xuatXu) {
        this.xuatXu = xuatXu;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public void setGiaNhap(double giaNhap) {
        this.giaNhap = giaNhap;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }

    public void setHsd(Date hsd) {
        this.hsd = hsd;
    }

    public Thuoc(String id, String tenThuoc, byte[] hinhAnh, String thanhPhan, DanhMuc danhMuc, DonViTinh donViTinh, XuatXu xuatXu, int soLuong, double giaNhap, double donGia, Date hsd) {
        this.id = id;
        this.tenThuoc = tenThuoc;
        this.hinhAnh = hinhAnh;
        this.thanhPhan = thanhPhan;
        this.danhMuc = danhMuc;
        this.donViTinh = donViTinh;
        this.xuatXu = xuatXu;
        this.soLuong = soLuong;
        this.giaNhap = giaNhap;
        this.donGia = donGia;
        this.hsd = hsd;
    }

    
}
