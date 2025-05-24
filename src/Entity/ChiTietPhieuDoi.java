package Entity;

public class ChiTietPhieuDoi {
    private String maPD;
    private String maThuocMoi;
    private int soLuongMoi;
    private double donGiaMoi;

    public ChiTietPhieuDoi() {
    }

    public ChiTietPhieuDoi(String maPD, String maThuocMoi, int soLuongMoi, double donGiaMoi) {
        this.maPD = maPD;
        this.maThuocMoi = maThuocMoi;
        this.soLuongMoi = soLuongMoi;
        this.donGiaMoi = donGiaMoi;
    }

    public String getMaPD() {
        return maPD;
    }

    public void setMaPD(String maPD) {
        this.maPD = maPD;
    }

    public String getMaThuocMoi() {
        return maThuocMoi;
    }

    public void setMaThuocMoi(String maThuocMoi) {
        this.maThuocMoi = maThuocMoi;
    }

    public int getSoLuongMoi() {
        return soLuongMoi;
    }

    public void setSoLuongMoi(int soLuongMoi) {
        this.soLuongMoi = soLuongMoi;
    }

    public double getDonGiaMoi() {
        return donGiaMoi;
    }

    public void setDonGiaMoi(double donGiaMoi) {
        this.donGiaMoi = donGiaMoi;
    }

    public double getThanhTien() {
        return soLuongMoi * donGiaMoi;
    }

    @Override
    public String toString() {
        return "ChiTietPhieuDoi{" +
                "maPD='" + maPD + '\'' +
                ", maThuocMoi='" + maThuocMoi + '\'' +
                ", soLuongMoi=" + soLuongMoi +
                ", donGiaMoi=" + donGiaMoi +
                ", thanhTien=" + getThanhTien() +
                '}';
    }
}