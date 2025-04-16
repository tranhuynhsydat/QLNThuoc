/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.util.Date;

/**
 *
 * @author roxan
 */
public class ThongKe {

    private Date thoiGian;
    private double doanhThu;
    private double chiPhi;

    public ThongKe() {
    }

    public ThongKe(Date thoiGian, double doanhThu, double chiPhi) {
        this.thoiGian = thoiGian;
        this.doanhThu = doanhThu;
        this.chiPhi = chiPhi;
    }

    public Date getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(Date thoiGian) {
        this.thoiGian = thoiGian;
    }

    public double getDoanhThu() {
        return doanhThu;
    }

    public void setDoanhThu(double doanhThu) {
        this.doanhThu = doanhThu;
    }

    public double getChiPhi() {
        return chiPhi;
    }

    public void setChiPhi(double chiPhi) {
        this.chiPhi = chiPhi;
    }

    @Override
    public String toString() {
        return "ThongKeDoanhThu{" + "thoiGian=" + thoiGian + ", doanhThu=" + doanhThu + ", chiPhi=" + chiPhi + '}';
    }

    public double getLoiNhuan() {
        return this.doanhThu - this.chiPhi;
    }
}
