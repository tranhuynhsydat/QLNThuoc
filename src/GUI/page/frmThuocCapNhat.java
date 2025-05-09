/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI.page;

import DAO.ThuocDAO;
import Entity.Thuoc;
import GUI.form.formSuaThuoc;
import GUI.form.formThemThuoc;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollBar;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Admin
 */
public class frmThuocCapNhat extends javax.swing.JPanel {

    private int startIndex = 0;

    /**
     * Creates new form frmThuoc
     */
    public frmThuocCapNhat() {
        initComponents();
        configureTable();
        loadDataToTable();
        jScrollPane1.getVerticalScrollBar().addAdjustmentListener(e -> {
            JScrollBar vertical = jScrollPane1.getVerticalScrollBar();
            int max = vertical.getMaximum();
            int current = vertical.getValue();
            int visible = vertical.getVisibleAmount();

            // Kiểm tra nếu người dùng đã cuộn đến cuối bảng
            if (current + visible >= max) {
                startIndex += 13;  // Tăng chỉ mục bắt đầu để tải dữ liệu tiếp theo
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
        // Tạo một worker để tải dữ liệu trong nền
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                // Lấy danh sách thuốc từ cơ sở dữ liệu
                List<Thuoc> thuocList = ThuocDAO.getThuocBatch(startIndex, 13);  // startIndex là chỉ mục bắt đầu
                if (thuocList != null && !thuocList.isEmpty()) {
                    SwingUtilities.invokeLater(() -> {
                        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                        if (startIndex == 0) {
                            model.setRowCount(0);  // Xóa tất cả dữ liệu hiện tại trong bảng khi tải lại từ đầu
                        }

                        // Thêm dữ liệu mới vào bảng
                        for (Thuoc thuoc : thuocList) {
                            Object[] rowData = {
                                thuoc.getId(),
                                thuoc.getTenThuoc(),
                                thuoc.getThanhPhan(),
                                thuoc.getGiaNhap(),
                                thuoc.getDonGia(),
                                thuoc.getHsd(),
                                thuoc.getDanhMuc() != null ? thuoc.getDanhMuc().getTen() : null,
                                thuoc.getDonViTinh() != null ? thuoc.getDonViTinh().getTen() : null,
                                thuoc.getXuatXu() != null ? thuoc.getXuatXu().getTen() : null,
                                thuoc.getSoLuong()
                            };
                            model.addRow(rowData);  // Thêm dòng vào bảng
                        }

                        // Sắp xếp lại bảng sau khi dữ liệu được thêm vào
                        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>((DefaultTableModel) jTable1.getModel());
                        jTable1.setRowSorter(sorter);
                        sorter.setSortKeys(Arrays.asList(new RowSorter.SortKey(0, SortOrder.ASCENDING)));  // Sắp xếp theo cột đầu tiên (Mã thuốc)
                    });
                }
                return null;
            }
        };
        worker.execute();  // Chạy worker
    }

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

        setLayout(new java.awt.BorderLayout());

        tablePanel.setLayout(new java.awt.BorderLayout());

        jPanel1.setBackground(new java.awt.Color(0, 120, 92));
        jPanel1.setMinimumSize(new java.awt.Dimension(829, 50));
        jPanel1.setName(""); // NOI18N
        jPanel1.setPreferredSize(new java.awt.Dimension(829, 50));
        jPanel1.setLayout(new java.awt.BorderLayout());

        jLabel2.setBackground(new java.awt.Color(0, 153, 153));
        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Thông tin thuốc");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel2, java.awt.BorderLayout.CENTER);

        tablePanel.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã thuốc", "Tên thuốc", "Thành phần", "Giá nhập", "Giá bán", "HSD", "Danh mục", "Đơn vị tính", "Xuất xứ", "Số lượng"
            }
        ));
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.setShowHorizontalLines(true);
        jScrollPane1.setViewportView(jTable1);

        tablePanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        add(tablePanel, java.awt.BorderLayout.CENTER);

        btnPanel.setBackground(new java.awt.Color(222, 222, 222));
        btnPanel.setPreferredSize(new java.awt.Dimension(829, 50));
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 100, 8);
        flowLayout1.setAlignOnBaseline(true);
        btnPanel.setLayout(flowLayout1);

        btnThem.setBackground(new java.awt.Color(0, 120, 92));
        btnThem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnThem.setForeground(new java.awt.Color(255, 255, 255));
        btnThem.setText("Thêm");
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

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow != -1) {
            String maThuoc = jTable1.getValueAt(selectedRow, 0).toString();  // Lấy mã nhân viên từ cột đầu tiên

            // Hiển thị hộp thoại xác nhận xóa
            int response = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn xóa thuốc này?",
                    "Xác nhận", JOptionPane.YES_NO_OPTION);

            // Nếu người dùng chọn Yes, thực hiện xóa
            if (response == JOptionPane.YES_OPTION) {
                // Gọi hàm xóa nhân viên trong DAO
                if (ThuocDAO.xoa(maThuoc)) {
                    JOptionPane.showMessageDialog(this, "Xóa thuốc thành công!");
                    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                    model.setRowCount(0);
                    startIndex = 0;
                    loadDataToTable();  // Làm mới bảng sau khi xóa
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa thuốc thất bại!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn thuốc để xóa!");
        }
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        formThemThuoc dialog = new formThemThuoc(parentFrame, true);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        startIndex = 0;
        loadDataToTable();
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow != -1) {
            String maThuoc = jTable1.getValueAt(selectedRow, 0).toString();  // Lấy mã thuốc từ cột đầu tiên

            // Mở form sửa thuốc và truyền mã thuốc vào constructor
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            formSuaThuoc dialog = new formSuaThuoc(parentFrame, true, maThuoc);  // Truyền mã thuốc vào constructor
            dialog.setLocationRelativeTo(this);
            dialog.setVisible(true);
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);
            startIndex = 0;
            loadDataToTable();  // Gọi lại phương thức để làm mới bảng
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn thuốc để sửa!");
        }
    }//GEN-LAST:event_btnSuaActionPerformed


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
