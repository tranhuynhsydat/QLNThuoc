/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @author roxan
 */
class NhanVien {
	private String id;
    private String hoTen;
    private String sdt;
    private String gioiTinh;
    //Ngày tháng năm sinh
    private Date dtSinh;
    private Date ngayVaoLam;
	public NhanVien(String id, String hoTen, String sdt, String gioiTinh, Date dtSinh, Date ngayVaoLam) {
		super();
		this.id = id;
		this.hoTen = hoTen;
		this.sdt = sdt;
		this.gioiTinh = gioiTinh;
		this.dtSinh = dtSinh;
		this.ngayVaoLam = ngayVaoLam;
	}
	public NhanVien() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getHoTen() {
		return hoTen;
	}
	public void setHoTen(String hoTen) {
		this.hoTen = hoTen;
	}
	public String getSdt() {
		return sdt;
	}
	public void setSdt(String sdt) {
		this.sdt = sdt;
	}
	public String getGioiTinh() {
		return gioiTinh;
	}
	public void setGioiTinh(String gioiTinh) {
		this.gioiTinh = gioiTinh;
	}
	public Date getDtSinh() {
		return dtSinh;
	}
	public void setDtSinh(Date dtSinh) {
		this.dtSinh = dtSinh;
	}
	public Date getNgayVaoLam() {
		return ngayVaoLam;
	}
	public void setNgayVaoLam(Date ngayVaoLam) {
		this.ngayVaoLam = ngayVaoLam;
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		NhanVien other = (NhanVien) obj;
		return Objects.equals(id, other.id);
	}
    
}
