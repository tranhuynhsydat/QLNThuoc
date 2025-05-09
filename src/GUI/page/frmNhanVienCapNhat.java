/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI.page;

import DAO.NhanVienDAO;
import Entity.NhanVien;
import GUI.form.formThemNCC;
import GUI.form.formSuaNV;
import GUI.form.formThemNV;
import java.util.List;
import javax.swing.ButtonGroup;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author roxan
 */
public class frmNhanVienCapNhat extends javax.swing.JPanel {

    private int startIndex =0 ;

    /**
     * Creates new form NewJPanel
     */
    public frmNhanVienCapNhat() {
//        initComponents();
//        btnThem.addActionListener(evt -> openFormThemNV());
//        btnSua.addActionListener(evt -> openFormSuaNV());
//        loadTableData();
        initComponents();
        configureTable();
        startIndex = 0;
        loadDataToTable();
        // Thêm sự kiện cuộn bảng
        jScrollPane1.getVerticalScrollBar().addAdjustmentListener(e -> {
            JScrollBar vertical = jScrollPane1.getVerticalScrollBar();
            int max = vertical.getMaximum();
           int current = vertical.getValue();
            int visible = vertical.getVisibleAmount();

            // Kiểm tra nếu người dùng đã cuộn đến cuối bảng
            if (current + visible >= max) {
                startIndex += 13; // Tăng chỉ mục bắt đầu để tải dữ liệu tiếp theo
                loadDataToTable();  // Tải thêm dữ liệu
            }
        });
    
    }
    private void configureTable() {
        // Ngăn không cho phép người dùng chỉnh sửa bảng
        jTable1.setDefaultEditor(Object.class, null);  // Điều này vô hiệu hóa khả năng chỉnh sửa của bất kỳ ô nào trong bảng.

        // Căn giữa cho tất cả các cell trong bảng
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Căn giữa cho từng cột
        for (int i = 0; i < jTable1.getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Ngăn không cho phép chọn nhiều dòng
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
   }

    private void loadDataToTable() {
        // Lấy dữ liệu thuốc với batch tiếp theo (10 dòng)
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
           @Override
            protected Void doInBackground() throws Exception {
                // Lấy danh sách NCC từ cơ sở dữ liệu (10 dòng bắt đầu từ startIndex)
                List<NhanVien> nvList = NhanVienDAO.getNhanVienBatch(startIndex, 13);  // startIndex là chỉ mục bắt đầu
                SwingUtilities.invokeLater(() -> {
                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

                    // Chỉ thêm dữ liệu mới vào bảng, không xóa dữ liệu cũ
                    for (NhanVien nv : nvList) {
                        model.addRow(new Object[]{
                            nv.getId(),
                            nv.getHoTen(),
                            nv.getSdt(),
                            nv.getGioiTinh(),
                            nv.getDtSinh(),
                            nv.getNgayVaoLam(),
                            nv.getCccd(),
                            nv.getChucVu()
                        });
                    }
                });
               return null;
            }
        };
        worker.execute();
    }
//        private void openFormThemNV() {
//    JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
//    formThemNV dialog = new formThemNV(parentFrame, true);
//    dialog.setLocationRelativeTo(this); 
//    dialog.setVisible(false); 
//}
//
//private void openFormSuaNV() {
//    JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
//    formSuaNV dialog = new formSuaNV(parentFrame, true);
//    dialog.setLocationRelativeTo(this); 
//    dialog.setVisible(false); 
//}
//    private void loadTableData() {
//         // Lấy tất cả nhân viên từ cơ sở dữ liệu
//         List<NhanVien> danhSachNhanVien = NhanVienDAO.getAllNhanVien();
// 
//         // Tạo DefaultTableModel với các cột
//         String[] columnNames = {"Mã NV", "Họ Tên NV","SĐT","Giới tính","Ngày vào làm","Chức vụ" , "Tuổi"};
//         DefaultTableModel model = new DefaultTableModel(columnNames, 0);
// 
//         // Thêm từng nhân viên vào bảng
//          for (NhanVien nv : danhSachNhanVien) {
//                        Object[] rowData = {
//                            nv.getId(),
//                            nv.getHoTen(),
//                            nv.getSdt(),
//                            nv.getGioiTinh(),
//                            nv.getDtSinh(),
//                            nv.getNgayVaoLam(),
//                            nv.getCccd(),
//                            nv.getChucVu()
//             };
//             model.addRow(rowData);  // Thêm dòng vào model
//         }
//         jTable1.setDefaultEditor(Object.class, null);
// 
//         // Gán DefaultTableModel cho JTable
//         jTable1.setModel(model);  // jTable1 là JTable trên form của bạn
//         DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
//         centerRenderer.setHorizontalAlignment(JLabel.CENTER);
//         for (int i = 0; i < jTable1.getColumnCount(); i++) {
//             jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
//         }
//     }


    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tablePanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnPanel = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();

        setMinimumSize(new java.awt.Dimension(829, 624));
        setPreferredSize(new java.awt.Dimension(829, 624));
        setLayout(new java.awt.BorderLayout());

        tablePanel.setMinimumSize(new java.awt.Dimension(829, 452));
        tablePanel.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(0, 120, 92));
        jPanel1.setMinimumSize(new java.awt.Dimension(829, 50));
        jPanel1.setName(""); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(829, 50));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel2.setBackground(new java.awt.Color(69, 142, 168));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Thông tin nhân viên");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel2, java.awt.BorderLayout.CENTER);

        tablePanel.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã NV", "Họ tên", "SĐT", "Giới tính", "Ngày sinh", "Ngày vào làm", "Chức vụ", "CCCD"
            }
        ));
        jTable1.setPreferredSize(new java.awt.Dimension(600, 402));
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.setShowHorizontalLines(true);
        jScrollPane1.setViewportView(jTable1);

        tablePanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(tablePanel, java.awt.BorderLayout.CENTER);

        btnPanel.setBackground(new java.awt.Color(222, 222, 222));
        btnPanel.setPreferredSize(new java.awt.Dimension(655, 51));
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 100, 8);
        flowLayout1.setAlignOnBaseline(true);
        btnPanel.setLayout(flowLayout1);

        btnThem.setBackground(new java.awt.Color(0, 120, 92));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("Thêm");
        btnThem.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnThem.setMaximumSize(new java.awt.Dimension(85, 35));
        btnThem.setMinimumSize(new java.awt.Dimension(85, 35));
        btnThem.setPreferredSize(new java.awt.Dimension(105, 35));
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });
        btnPanel.add(btnThem);

        btnSua.setBackground(new java.awt.Color(0, 120, 92));
        btnSua.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnSua.setForeground(new java.awt.Color(255, 255, 255));
        btnSua.setText("Sửa");
        btnSua.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnSua.setMaximumSize(new java.awt.Dimension(85, 35));
        btnSua.setMinimumSize(new java.awt.Dimension(85, 35));
        btnSua.setPreferredSize(new java.awt.Dimension(105, 35));
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });
        btnPanel.add(btnSua);

        btnXoa.setBackground(new java.awt.Color(0, 120, 92));
        btnXoa.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnXoa.setForeground(new java.awt.Color(255, 255, 255));
        btnXoa.setText("Xoá");
        btnXoa.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        btnXoa.setMaximumSize(new java.awt.Dimension(85, 35));
        btnXoa.setMinimumSize(new java.awt.Dimension(85, 35));
        btnXoa.setPreferredSize(new java.awt.Dimension(105, 35));
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });
        btnPanel.add(btnXoa);

        add(btnPanel, java.awt.BorderLayout.PAGE_END);
    }// </editor-fold>//GEN-END:initComponents

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow != -1) {
            String maNV = jTable1.getValueAt(selectedRow, 0).toString();  // Lấy mã nhân viên từ cột đầu tiên

            // Mở form sửa nhân viên và truyền mã nhân viên vào constructor
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            formSuaNV dialog = new formSuaNV(parentFrame, true, maNV);  // Truyền mã nhân viên vào constructor
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
            loadDataToTable();  // Gọi lại phương thức để làm mới bảng
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên để sửa!");
        }
//    int selectedRow = jTable1.getSelectedRow();
//         if (selectedRow != -1) {
//             String maNV = jTable1.getValueAt(selectedRow, 0).toString();  // Lấy mã nhân viên từ cột đầu tiên
// 
//             // Mở form sửa nhân viên và truyền mã nhân viên vào constructor
//             JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
//             formSuaNV dialog = new formSuaNV(parentFrame, true, maNV);  // Truyền mã nhân viên vào constructor
//             dialog.setLocationRelativeTo(this);
//             dialog.setVisible(true);
//             loadTableData();
//         } else {
//             JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để sửa!");
//         }
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // Kiểm tra nếu có dòng được chọn trong JTable
        int selectedRow = jTable1.getSelectedRow();
         if (selectedRow != -1) {
             String maNV = jTable1.getValueAt(selectedRow, 0).toString();  // Lấy mã nhân viên từ cột đầu tiên
 
             // Hiển thị hộp thoại xác nhận xóa
             int response = JOptionPane.showConfirmDialog(this,
                     "Bạn có chắc chắn muốn xóa khách hàng này?",
                     "Xác nhận", JOptionPane.YES_NO_OPTION);
 
             // Nếu người dùng chọn Yes, thực hiện xóa
             if (response == JOptionPane.YES_OPTION) {
                 // Gọi hàm xóa nhân viên trong DAO
                 if (NhanVienDAO.xoa(maNV)) {
                     JOptionPane.showMessageDialog(this, "Xóa khách hàng thành công!");
                     loadDataToTable();  // Làm mới bảng sau khi xóa
                 } else {
                     JOptionPane.showMessageDialog(this, "Xóa khách hàng thất bại!");
                 }
             }
         } else {
             JOptionPane.showMessageDialog(this, "Vui lòng chọn khách hàng để xóa!");
         }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        // TODO add your handling code here:
//        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
//         formThemNV dialog = new formThemNV(parentFrame, true);  // Mở formThemNV
//         dialog.setLocationRelativeTo(this);
//         dialog.setVisible(true);
// 
//         // Sau khi đóng formThemNV, gọi lại phương thức để làm mới bảng
//         loadTableData();
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        formThemNV dialog = new formThemNV(parentFrame, true);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        startIndex = 0;
        loadDataToTable();
    }//GEN-LAST:event_btnThemActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnPanel;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel tablePanel;
    // End of variables declaration//GEN-END:variables
}
