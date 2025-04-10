/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import ConnectDB.DatabaseConnection;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import Entity.NhanVien;
/**
 *
 * @author roxan
 */
public class NhanVienDAO {

    public static String generateMaNhanVien() {
        String prefix = "NV-";
        int maxNumber = 0;

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id FROM NhanVien WHERE id LIKE 'NV-%'")) {

            while (rs.next()) {
                String ma = rs.getString("id");
                if (ma != null && ma.startsWith(prefix)) {
                    try {
                        int num = Integer.parseInt(ma.substring(3));
                        if (num > maxNumber) {
                            maxNumber = num;
                        }
                    } catch (NumberFormatException e) {
                        // bỏ qua mã sai định dạng
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        int newNumber = maxNumber + 1;
        return prefix + String.format("%03d", newNumber);
    }

    public static boolean Them(NhanVien nv) {
        String sql = "INSERT INTO NhanVien (id, hoTen, sdt, gioiTinh, dtSinh, ngayVaoLam, cccd, chucVu) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nv.getId());
            ps.setString(2, nv.getHoTen());
            ps.setString(3, nv.getSdt());
            ps.setString(4, nv.getGioiTinh());
            ps.setDate(5, new java.sql.Date(nv.getDtSinh().getTime()));
            ps.setDate(6, new java.sql.Date(nv.getNgayVaoLam().getTime()));
            ps.setString(7, nv.getCccd());
            ps.setString(8, nv.getChucVu());

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
