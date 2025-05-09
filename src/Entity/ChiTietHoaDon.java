package Entity;

public class ChiTietHoaDon {
    private String maHD;
    private String maThuoc;
    private String tenThuoc;
    private int soLuong;
    private double donGia;
    private double thanhTien;

    // Constructor mặc định
    public ChiTietHoaDon() {
    }

    // Constructor đầy đủ tham số
    public ChiTietHoaDon(String mHD, String maThuoc, String tenThuoc, int soLuong, double donGia,
            double thanhTien) {
        this.maHD = maHD;
        this.maThuoc = maThuoc;
        this.tenThuoc = tenThuoc;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.thanhTien = thanhTien;
    }

    // Getters và Setters

    public String getIdHoaDon() {
        return maHD;
    }

    public void setIdHoaDon(String maHD) {
        this.maHD = maHD;
    }

    public String getIdThuoc() {
        return maThuoc;
    }

    public void setIdThuoc(String maThuoc) {
        this.maThuoc = maThuoc;
    }

    public String getThuoc() {
        return tenThuoc;
    }

    public void setThuoc(String tenThuoc) {
        this.tenThuoc = tenThuoc;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
        // Tự động cập nhật thành tiền khi thay đổi số lượng
        if (this.donGia > 0) {
            this.thanhTien = this.soLuong * this.donGia;
        }
    }

    public double getDonGia() {
        return donGia;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
        // Tự động cập nhật thành tiền khi thay đổi đơn giá
        if (this.soLuong > 0) {
            this.thanhTien = this.soLuong * this.donGia;
        }
    }

    public double getThanhTien() {
        return thanhTien;
    }

    public void setThanhTien(double thanhTien) {
        this.thanhTien = thanhTien;
    }

    // Phương thức tính thành tiền
    public void tinhThanhTien() {
        this.thanhTien = this.soLuong * this.donGia;
    }

    @Override
    public String toString() {
        return "ChiTietHoaDon{" +
                ", hoaDonId=" + maHD +
                ", maThuoc='" + maThuoc + '\'' +
                ", tenThuoc='" + tenThuoc + '\'' +
                ", soLuong=" + soLuong +
                ", donGia=" + donGia +
                ", thanhTien=" + thanhTien +
                '}';
    }
}
