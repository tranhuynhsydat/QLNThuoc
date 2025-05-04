package Entity;

public class ChiTietPhieuDoi {
    private String maPD;
    private String maThuocCu;
    private int soLuongCu;
    private double donGiaCu;

    private String maThuocMoi;
    private int soLuongMoi;
    private double donGiaMoi;

    private double tongTien;

    // Getters
    public String getMaPD() { return maPD; }
    public String getMaThuocCu() { return maThuocCu; }
    public int getSoLuongCu() { return soLuongCu; }
    public double getDonGiaCu() { return donGiaCu; }
    public String getMaThuocMoi() { return maThuocMoi; }
    public int getSoLuongMoi() { return soLuongMoi; }
    public double getDonGiaMoi() { return donGiaMoi; }
    public double getTongTien() { return tongTien; }
    public double getThanhTien() { return tongTien; } // alias

    // Setters
    public void setMaPD(String maPD) { this.maPD = maPD; }
    public void setMaThuocCu(String maThuocCu) { this.maThuocCu = maThuocCu; }
    public void setSoLuongCu(int soLuongCu) { this.soLuongCu = soLuongCu; }
    public void setDonGiaCu(double donGiaCu) { this.donGiaCu = donGiaCu; }
    public void setMaThuocMoi(String maThuocMoi) { this.maThuocMoi = maThuocMoi; }
    public void setSoLuongMoi(int soLuongMoi) { this.soLuongMoi = soLuongMoi; }
    public void setDonGiaMoi(double donGiaMoi) { this.donGiaMoi = donGiaMoi; }
    public void setTongTien(double tongTien) { this.tongTien = tongTien; }

    // Aliases cho tương thích
    public void setIdHoaDonDoi(String maPD) { setMaPD(maPD); }
    public void setIdThuoc(String maThuocCu) { setMaThuocCu(maThuocCu); }
    public void setSoLuong(int soLuongCu) { setSoLuongCu(soLuongCu); }
    public void setDonGia(double donGiaCu) { setDonGiaCu(donGiaCu); }
    public void setThuoc(String tenThuoc) { } // giả lập (nếu bạn cần hiện text thì gán tạm)

    @Override
    public String toString() {
        return "ChiTietPhieuDoi{" +
                "maPD='" + maPD + '\'' +
                ", maThuocCu='" + maThuocCu + '\'' +
                ", soLuongCu=" + soLuongCu +
                ", donGiaCu=" + donGiaCu +
                ", maThuocMoi='" + maThuocMoi + '\'' +
                ", soLuongMoi=" + soLuongMoi +
                ", donGiaMoi=" + donGiaMoi +
                ", tongTien=" + tongTien +
                '}';
    }
}
