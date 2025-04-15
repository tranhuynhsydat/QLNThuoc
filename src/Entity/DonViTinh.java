/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Entity;

/**
 *
 * @author roxan
 */
public class DonViTinh {
    private String id;
    private String ten;

    public String getId() {
        return id;
    }

    public String getTen() {
        return ten;
    }

    public void setId(String id) {
        this.id = id;
    }

    public DonViTinh(String id, String ten) {
        this.id = id;
        this.ten = ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public DonViTinh(String id) {
        this.id = id;
    }
    
    
}
