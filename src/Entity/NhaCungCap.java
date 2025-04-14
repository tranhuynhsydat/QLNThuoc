/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

public class NhaCungCap {
    private String id;
    private String tenNhaCungCap;
    private String diaChi;
    private String sdt;

    public String getId() {
        return id;
    }

    public String getTenNhaCungCap() {
        return tenNhaCungCap;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public String getSdt() {
        return sdt;
    }

    public void setTenNhaCungCap(String tenNhaCungCap) {
        this.tenNhaCungCap = tenNhaCungCap;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public NhaCungCap(String id, String tenNhaCungCap, String diaChi, String sdt) {
        this.id = id;
        this.tenNhaCungCap = tenNhaCungCap;
        this.diaChi = diaChi;
        this.sdt = sdt;
    }
    
    
}

