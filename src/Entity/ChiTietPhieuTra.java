package Entity;

public class ChiTietPhieuTra {
    private String maPT;
    private String maThuoc;
    private int soLuong;
    private double donGia;

    public ChiTietPhieuTra() {}

    public ChiTietPhieuTra(String maPT, String maThuoc, int soLuong, double donGia) {
        this.maPT = maPT;
        this.maThuoc = maThuoc;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public String getMaPT() { return maPT; }
    public void setMaPT(String maPT) { this.maPT = maPT; }

    public String getMaThuoc() { return maThuoc; }
    public void setMaThuoc(String maThuoc) { this.maThuoc = maThuoc; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public double getDonGia() { return donGia; }
    public void setDonGia(double donGia) { this.donGia = donGia; }

    public double getThanhTien() {
        return soLuong * donGia;
    }

    @Override
    public String toString() {
        return "ChiTietPhieuTra{" +
                "maPT='" + maPT + '\'' +
                ", maThuoc='" + maThuoc + '\'' +
                ", soLuong=" + soLuong +
                ", donGia=" + donGia +
                '}';
    }
}
