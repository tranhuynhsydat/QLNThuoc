/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI.page.ThongKe;

/**
 *
 * @author roxan
 */
public class frmThongKe extends javax.swing.JPanel {

    /**
     * Creates new form ThongKe
     */
    public frmThongKe() {
        initComponents();
        initLayout();
    }
    private void initLayout() {

        tabPane.addTab("Thống kê theo ngày", new frmThongKeDoanhThuTungNgayTrongThangPage());
        tabPane.addTab("Thống kê theo tháng", new frmThongKeDoanhThuTheoThangPage());
        tabPane.addTab("Thống kê theo năm", new frmThongKeDoanhThuTheoNamPage());

        this.add(tabPane);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tabPane = new javax.swing.JTabbedPane();

        setBackground(new java.awt.Color(230, 245, 245));
        setMinimumSize(new java.awt.Dimension(829, 624));
        setLayout(new java.awt.BorderLayout());
        add(tabPane, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTabbedPane tabPane;
    // End of variables declaration//GEN-END:variables
}
