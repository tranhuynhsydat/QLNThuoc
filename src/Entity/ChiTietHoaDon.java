/*
 * Entity/ChiTietHoaDon.java
 */
package Entity;

public class ChiTietHoaDon {
    private String idHoaDon;
    private String idThuoc;
    private int soLuong;
    private double donGia;
    private Thuoc thuoc;

    public ChiTietHoaDon() {
    }

    public ChiTietHoaDon(String idHoaDon, String idThuoc, int soLuong, double donGia) {
        this.idHoaDon = idHoaDon;
        this.idThuoc = idThuoc;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }
    
    public ChiTietHoaDon(String idHoaDon, Thuoc thuoc, int soLuong) {
        this.idHoaDon = idHoaDon;
        this.idThuoc = thuoc.getId();
        this.soLuong = soLuong;
        this.donGia = thuoc.getDonGia();
        this.thuoc = thuoc;
    }

    // Getters
    public String getIdHoaDon() { return idHoaDon; }
    public String getIdThuoc() { return idThuoc; }
    public int getSoLuong() { return soLuong; }
    public double getDonGia() { return donGia; }
    public Thuoc getThuoc() { return thuoc; }

    // Setters
    public void setIdHoaDon(String idHoaDon) { this.idHoaDon = idHoaDon; }
    public void setIdThuoc(String idThuoc) { this.idThuoc = idThuoc; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }
    public void setDonGia(double donGia) { this.donGia = donGia; }
    public void setThuoc(Thuoc thuoc) { this.thuoc = thuoc; }

    // Tính thành tiền
    public double getThanhTien() {
        return soLuong * donGia;
    }
    
    @Override
    public String toString() {
        return "ChiTietHoaDon{" + "idHoaDon=" + idHoaDon + ", idThuoc=" + idThuoc + ", soLuong=" + soLuong + ", donGia=" + donGia + '}';
    }
}