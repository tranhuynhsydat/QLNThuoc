/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI.page;

import DAO.HoaDonTraDAO;
import Entity.HoaDonTra;
import DAO.NhanVienDAO;
import Entity.NhanVien;
import GUI.form.formThemNCC;
import GUI.form.formSuaNV;
import GUI.form.formThemNV;
import java.util.List;
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
public class frmHoaDonTraCapNhat extends javax.swing.JPanel {

    private int startIndex = 0;

    public frmHoaDonTraCapNhat() {
        initComponents();
        configureTable();
        loadDataToTable();
        jScrollPane1.getVerticalScrollBar().addAdjustmentListener(e -> {
            JScrollBar vertical = jScrollPane1.getVerticalScrollBar();
            int max = vertical.getMaximum();
            int current = vertical.getValue();
            int visible = vertical.getVisibleAmount();

            if (current + visible >= max) {
                startIndex += 10; // Increase the starting index to load more data
                loadDataToTable(); // Load more data
            }
        });
    }

    private void configureTable() {
        jTable1.setDefaultEditor(Object.class, null); // Disable editing in any cell

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < jTable1.getColumnCount(); i++) {
            jTable1.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION); // Allow single row selection
    }

    private void loadDataToTable() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                startIndex = 0;
                // Fetch data from HoaDonTraDAO (Return Invoice Data)
                List<HoaDonTra> danhSachHoaDonTra = HoaDonTraDAO.getHoaDonTraBatch(startIndex, 10);
                System.out.println("Dữ liệu nhận được từ DB: " + danhSachHoaDonTra.size() + " dòng");

                if (danhSachHoaDonTra == null || danhSachHoaDonTra.isEmpty()) {
                    System.out.println("Không có dữ liệu để tải.");
                } else {
                    SwingUtilities.invokeLater(() -> {
                        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                        model.setRowCount(0); // Clear previous data

                        int stt = 1; // Serial number counter
                        for (HoaDonTra hd : danhSachHoaDonTra) {
                            Object[] rowData = {
                                    stt++, // Serial number
                                    hd.getMaPT(),
                                    hd.getTenKhachHang(),
                                    hd.getSdt(),
                                    hd.getTenNhanVien(),
                                    hd.getNgayDoi(),
                                    hd.getLyDo(),
                                    hd.getTongTien()
                            };
                            model.addRow(rowData); // Add row to table
                        }

                        model.fireTableDataChanged(); // Update the table
                        jTable1.revalidate();
                        jTable1.repaint(); // Repaint the table
                    });
                }
                return null;
            }
        };
        worker.execute();
    }

    // Event handler for "Add" button
private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            // Tạo đối tượng frmHoaDonTraThem không có tham số
            frmHoaDonTraThem dialog = new frmHoaDonTraThem();

            // Nếu dialog này kế thừa từ JPanel thay vì JDialog, cần hiển thị nó trong một
            // cửa sổ mới
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            JFrame dialogFrame = new JFrame("Thêm hóa đơn");
            dialogFrame.setContentPane(dialog);

            // Thiết lập kích thước tối đa
            dialogFrame.setExtendedState(JFrame.MAXIMIZED_BOTH); // Mở ở chế độ toàn màn hình
            // HOẶC bạn có thể dùng một trong các cách sau

            // Cách 1: Sử dụng kích thước cụ thể
            // dialogFrame.setSize(1024, 768); // Đặt kích thước cố định

            // Cách 2: Sử dụng kích thước màn hình
            // Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            // dialogFrame.setSize(screenSize.width, screenSize.height);

            // Cách 3: Sử dụng một tỷ lệ nhất định của màn hình
            // Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            // dialogFrame.setSize(screenSize.width * 9/10, screenSize.height * 9/10);

            dialogFrame.setLocationRelativeTo(parentFrame);
            dialogFrame.setVisible(true);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                    "Không thể mở form thêm hóa đơn: " + ex.getMessage(),
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        // Sau khi đóng formThemHoaDon, gọi lại phương thức để làm mới bảng
        loadDataToTable();
    }// GEN-LAST:event_btnThemActionPerformed

    // Event handler for "Delete" button
    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow != -1) {
            String maPD = jTable1.getValueAt(selectedRow, 1).toString(); // Get the invoice ID from the first column

            // Confirm deletion
            int response = JOptionPane.showConfirmDialog(this,
                    "Bạn có chắc chắn muốn xóa hóa đơn trả này?", "Xác nhận", JOptionPane.YES_NO_OPTION);

            // If user confirms, perform the deletion
            if (response == JOptionPane.YES_OPTION) {
                if (HoaDonTraDAO.xoaHoaDonTra(maPD)) {
                    JOptionPane.showMessageDialog(this, "Xóa hóa đơn trả thành công!");
                    loadDataToTable(); // Refresh the table after deletion
                } else {
                    JOptionPane.showMessageDialog(this, "Xóa hóa đơn trả thất bại!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn trả để xóa!");
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        tablePanel = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        btnPanel = new javax.swing.JPanel();
        btnThem = new javax.swing.JButton();
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
        jLabel2.setText("Thông tin hóa đơn trả");
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel1.add(jLabel2, java.awt.BorderLayout.CENTER);

        tablePanel.add(jPanel1, java.awt.BorderLayout.PAGE_START);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {

                },
                new String[] {
                        "STT", "Mã phiếu trả", "Tên khách hàng", "SĐT", "Tên nhân viên", "Ngày đổi/trả", "Lý do",
                        "Tổng hóa đơn"
                }));
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel btnPanel;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JPanel tablePanel;
    // End of variables declaration//GEN-END:variables
}
