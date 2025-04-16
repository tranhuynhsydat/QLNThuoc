/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

import java.util.Objects;

/**
 *
 * @author roxan
  */
 public class TaiKhoan {
     private String id;
     private String username;
     private String password;
     private NhanVien nhanVien;
 
     public String getId() {
         return id;
     }
 
     public String getUsername() {
         return username;
     }
 
     public String getPassword() {
         return password;
     }
 
     public NhanVien getNhanVien() {
         return nhanVien;
     }
 
     public void setId(String id) {
         this.id = id;
     }
 
     public void setUsername(String username) {
         this.username = username;
     }
 
     public void setPassword(String password) {
         this.password = password;
     }
 
     public void setNhanVien(NhanVien nhanVien) {
         this.nhanVien = nhanVien;
     }
 
     public TaiKhoan(String id, String username, String password, NhanVien nhanVien) {
         this.id = id;
         this.username = username;
         this.password = password;
         this.nhanVien = nhanVien;
     }
 
 }