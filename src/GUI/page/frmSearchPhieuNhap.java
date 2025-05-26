/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package GUI.page;

import DAO.ChiTietPhieuNhapDAO;
import DAO.NhaCungCapDAO;
import DAO.PhieuNhapDAO;
import Entity.ChiTietPhieuNhap;
import Entity.NhaCungCap;
import Entity.NhanVien;
import Entity.PhieuNhap;
import GUI.form.formChiTietPhieuNhap;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
 * @author Admin
 */
public class frmSearchPhieuNhap extends javax.swing.JPanel {
    private List<PhieuNhap> phieuNhapList = new ArrayList<>(); 
    private int startIndex;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private final DecimalFormat currencyFormat = new DecimalFormat("#,### VND");
    /**
     * Creates new form frmSearchPhieuNhap
     */
    public frmSearchPhieuNhap() {
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
                 startIndex += 13 ; // Tăng chỉ mục bắt đầu để tải dữ liệu tiếp theo
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
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                List<PhieuNhap> danhSachPhieuNhap = PhieuNhapDAO.getAllPhieuNhap(); // Lấy tất cả hóa đơn
                System.out.println("Dữ liệu nhận được từ DB: " + danhSachPhieuNhap.size() + " dòng");
                if (danhSachPhieuNhap == null || danhSachPhieuNhap.isEmpty()) {
                    System.out.println("Không có dữ liệu để tải.");
                } else {
                    SwingUtilities.invokeLater(() -> {
                        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                        model.setRowCount(0); // Xóa dữ liệu cũ trong bảng

                        int stt = 1;
                        // Thêm dữ liệu mới vào bảng
                        for (PhieuNhap pn : danhSachPhieuNhap) {
                            NhaCungCap ncc = pn.getNhaCungCap();
                            NhanVien nv = pn.getNhanVien();

                            Object[] rowData = {
                                    stt++,
                                    pn.getId(),
                                    ncc != null ? ncc.getTenNhaCungCap(): "N/A", // Sử dụng getHoTen() từ KhachHang
                                    ncc != null ? ncc.getSdt(): "N/A", // Sử dụng getSdt() từ KhachHang
                                    nv != null ? nv.getHoTen() : "N/A",
                                    dateFormat.format(pn.getNgayLap()),
                                    currencyFormat.format(pn.getTongTien())
                            };
                            model.addRow(rowData); // Thêm dòng vào bảng
                        }

                        model.fireTableDataChanged(); // Đảm bảo bảng được làm mới
                        jTable1.revalidate(); // Cập nhật lại bảng
                        jTable1.repaint(); // Vẽ lại bảng
                    });
                }
                return null;
            }
        };
        worker.execute();
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel11 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        jPanel31 = new javax.swing.JPanel();
        jPanel46 = new javax.swing.JPanel();
        jPanel47 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel48 = new javax.swing.JPanel();
        txtMaPN = new javax.swing.JTextField();
        jPanel32 = new javax.swing.JPanel();
        jPanel35 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel36 = new javax.swing.JPanel();
        txtTenNCC = new javax.swing.JTextField();
        jPanel37 = new javax.swing.JPanel();
        jPanel38 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel39 = new javax.swing.JPanel();
        txtTenNV = new javax.swing.JTextField();
        jPanel40 = new javax.swing.JPanel();
        jPanel41 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel42 = new javax.swing.JPanel();
        dateNgayLap = new com.toedter.calendar.JDateChooser();
        jPanel43 = new javax.swing.JPanel();
        jPanel44 = new javax.swing.JPanel();
        jPanel45 = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        btnTimKiem = new javax.swing.JButton();
        btnChiTiet = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel34 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setPreferredSize(new java.awt.Dimension(829, 624));
        setLayout(new java.awt.BorderLayout());

        jPanel8.setBackground(new java.awt.Color(255, 102, 102));
        jPanel8.setMaximumSize(new java.awt.Dimension(829, 350));
        jPanel8.setPreferredSize(new java.awt.Dimension(829, 250));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jPanel9.setBackground(new java.awt.Color(0, 120, 92));
        jPanel9.setMinimumSize(new java.awt.Dimension(829, 50));
        jPanel9.setPreferredSize(new java.awt.Dimension(829, 50));
        jPanel9.setLayout(new java.awt.BorderLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Nhập thông tin tìm kiếm");
        jPanel9.add(jLabel3, java.awt.BorderLayout.CENTER);

        jPanel8.add(jPanel9, java.awt.BorderLayout.PAGE_START);

        jPanel11.setMinimumSize(new java.awt.Dimension(829, 300));
        jPanel11.setLayout(new javax.swing.BoxLayout(jPanel11, javax.swing.BoxLayout.Y_AXIS));

        jPanel21.setMinimumSize(new java.awt.Dimension(829, 38));
        jPanel21.setLayout(new java.awt.BorderLayout());

        jPanel30.setMinimumSize(new java.awt.Dimension(300, 38));
        jPanel30.setPreferredSize(new java.awt.Dimension(500, 38));
        jPanel30.setLayout(new java.awt.BorderLayout());
        jPanel21.add(jPanel30, java.awt.BorderLayout.LINE_START);

        jPanel31.setMinimumSize(new java.awt.Dimension(669, 38));
        jPanel31.setPreferredSize(new java.awt.Dimension(669, 38));
        jPanel31.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 7));
        jPanel21.add(jPanel31, java.awt.BorderLayout.CENTER);

        jPanel11.add(jPanel21);

        jPanel46.setMinimumSize(new java.awt.Dimension(829, 38));
        jPanel46.setLayout(new java.awt.BorderLayout());

        jPanel47.setMinimumSize(new java.awt.Dimension(300, 38));
        jPanel47.setPreferredSize(new java.awt.Dimension(500, 38));
        jPanel47.setLayout(new java.awt.BorderLayout());

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Mã phiếu nhập:");
        jLabel11.setAlignmentX(20.0F);
        jLabel11.setAlignmentY(20.0F);
        jPanel47.add(jLabel11, java.awt.BorderLayout.CENTER);

        jPanel46.add(jPanel47, java.awt.BorderLayout.LINE_START);

        jPanel48.setMinimumSize(new java.awt.Dimension(669, 38));
        jPanel48.setPreferredSize(new java.awt.Dimension(669, 38));
        jPanel48.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 7));

        txtMaPN.setPreferredSize(new java.awt.Dimension(350, 32));
        jPanel48.add(txtMaPN);

        jPanel46.add(jPanel48, java.awt.BorderLayout.CENTER);

        jPanel11.add(jPanel46);

        jPanel32.setMinimumSize(new java.awt.Dimension(829, 38));
        jPanel32.setLayout(new java.awt.BorderLayout());

        jPanel35.setMinimumSize(new java.awt.Dimension(300, 38));
        jPanel35.setPreferredSize(new java.awt.Dimension(500, 38));
        jPanel35.setLayout(new java.awt.BorderLayout());

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Tên nhà cung cấp:");
        jLabel9.setAlignmentX(20.0F);
        jLabel9.setAlignmentY(20.0F);
        jPanel35.add(jLabel9, java.awt.BorderLayout.CENTER);

        jPanel32.add(jPanel35, java.awt.BorderLayout.LINE_START);

        jPanel36.setMinimumSize(new java.awt.Dimension(669, 38));
        jPanel36.setPreferredSize(new java.awt.Dimension(669, 38));
        jPanel36.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 7));

        txtTenNCC.setPreferredSize(new java.awt.Dimension(350, 32));
        jPanel36.add(txtTenNCC);

        jPanel32.add(jPanel36, java.awt.BorderLayout.CENTER);

        jPanel11.add(jPanel32);

        jPanel37.setMinimumSize(new java.awt.Dimension(829, 38));
        jPanel37.setLayout(new java.awt.BorderLayout());

        jPanel38.setMinimumSize(new java.awt.Dimension(300, 38));
        jPanel38.setPreferredSize(new java.awt.Dimension(500, 38));
        jPanel38.setLayout(new java.awt.BorderLayout());

        jLabel7.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel7.setText("Tên nhân viên:");
        jLabel7.setAlignmentX(20.0F);
        jLabel7.setAlignmentY(20.0F);
        jPanel38.add(jLabel7, java.awt.BorderLayout.CENTER);

        jPanel37.add(jPanel38, java.awt.BorderLayout.LINE_START);

        jPanel39.setMinimumSize(new java.awt.Dimension(669, 38));
        jPanel39.setPreferredSize(new java.awt.Dimension(669, 38));
        jPanel39.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 7));

        txtTenNV.setPreferredSize(new java.awt.Dimension(350, 32));
        jPanel39.add(txtTenNV);

        jPanel37.add(jPanel39, java.awt.BorderLayout.CENTER);

        jPanel11.add(jPanel37);

        jPanel40.setMinimumSize(new java.awt.Dimension(829, 38));
        jPanel40.setLayout(new java.awt.BorderLayout());

        jPanel41.setMinimumSize(new java.awt.Dimension(300, 38));
        jPanel41.setPreferredSize(new java.awt.Dimension(500, 38));
        jPanel41.setLayout(new java.awt.BorderLayout());

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Ngày nhập:");
        jLabel10.setAlignmentX(20.0F);
        jLabel10.setAlignmentY(20.0F);
        jPanel41.add(jLabel10, java.awt.BorderLayout.CENTER);

        jPanel40.add(jPanel41, java.awt.BorderLayout.LINE_START);

        jPanel42.setMinimumSize(new java.awt.Dimension(669, 38));
        jPanel42.setPreferredSize(new java.awt.Dimension(669, 38));
        jPanel42.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 7));

        dateNgayLap.setMinimumSize(new java.awt.Dimension(182, 22));
        dateNgayLap.setPreferredSize(new java.awt.Dimension(150, 30));
        jPanel42.add(dateNgayLap);

        jPanel40.add(jPanel42, java.awt.BorderLayout.CENTER);

        jPanel11.add(jPanel40);

        jPanel43.setMinimumSize(new java.awt.Dimension(829, 38));
        jPanel43.setLayout(new java.awt.BorderLayout());

        jPanel44.setMinimumSize(new java.awt.Dimension(300, 38));
        jPanel44.setPreferredSize(new java.awt.Dimension(500, 38));
        jPanel44.setLayout(new java.awt.BorderLayout());
        jPanel43.add(jPanel44, java.awt.BorderLayout.LINE_START);

        jPanel45.setMinimumSize(new java.awt.Dimension(669, 38));
        jPanel45.setPreferredSize(new java.awt.Dimension(669, 38));
        jPanel45.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 7));
        jPanel43.add(jPanel45, java.awt.BorderLayout.CENTER);

        jPanel11.add(jPanel43);

        jPanel8.add(jPanel11, java.awt.BorderLayout.CENTER);

        add(jPanel8, java.awt.BorderLayout.PAGE_START);

        jPanel13.setPreferredSize(new java.awt.Dimension(829, 55));
        jPanel13.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 100, 10));

        btnTimKiem.setBackground(new java.awt.Color(0, 120, 92));
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnTimKiem.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.setMaximumSize(new java.awt.Dimension(85, 35));
        btnTimKiem.setMinimumSize(new java.awt.Dimension(85, 35));
        btnTimKiem.setPreferredSize(new java.awt.Dimension(105, 35));
        btnTimKiem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimKiemActionPerformed(evt);
            }
        });
        jPanel13.add(btnTimKiem);

        btnChiTiet.setBackground(new java.awt.Color(0, 120, 92));
        btnChiTiet.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        btnChiTiet.setForeground(new java.awt.Color(255, 255, 255));
        btnChiTiet.setText("Chi tiết");
        btnChiTiet.setMaximumSize(new java.awt.Dimension(85, 35));
        btnChiTiet.setMinimumSize(new java.awt.Dimension(85, 35));
        btnChiTiet.setPreferredSize(new java.awt.Dimension(105, 35));
        btnChiTiet.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnChiTietActionPerformed(evt);
            }
        });
        jPanel13.add(btnChiTiet);

        add(jPanel13, java.awt.BorderLayout.PAGE_END);

        jPanel12.setPreferredSize(new java.awt.Dimension(829, 220));
        jPanel12.setLayout(new java.awt.BorderLayout());

        jPanel33.setBackground(new java.awt.Color(0, 120, 92));
        jPanel33.setPreferredSize(new java.awt.Dimension(829, 50));
        jPanel33.setLayout(new java.awt.BorderLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Danh sách hóa đơn");
        jPanel33.add(jLabel4, java.awt.BorderLayout.CENTER);

        jPanel12.add(jPanel33, java.awt.BorderLayout.PAGE_START);

        jPanel34.setLayout(new java.awt.BorderLayout());

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "STT", "Mã phiếu nhập", "Tên nhà cung cấp", "SĐT", "Tên nhân viên", "Ngày nhập", "Tổng hóa đơn"
            }
        ));
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.setShowHorizontalLines(true);
        jScrollPane1.setViewportView(jTable1);

        jPanel34.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel12.add(jPanel34, java.awt.BorderLayout.CENTER);

        add(jPanel12, java.awt.BorderLayout.CENTER);
    }// </editor-fold>//GEN-END:initComponents

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimKiemActionPerformed
        String maPN = txtMaPN.getText().trim();
    String tenNV = txtTenNV.getText().trim();
    String tenNCC = txtTenNCC.getText().trim();
    Date ngayLap = dateNgayLap.getDate();

    SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
        @Override
        protected Void doInBackground() throws Exception {
            List<PhieuNhap> danhSachPhieuNhap = PhieuNhapDAO.searchPhieuNhap(maPN, tenNV, tenNCC, ngayLap);

            SwingUtilities.invokeLater(() -> {
                DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
                model.setRowCount(0);

                if (danhSachPhieuNhap.isEmpty()) {
                    JOptionPane.showMessageDialog(frmSearchPhieuNhap.this, "Không tìm thấy phiếu nhập phù hợp!");
                } else {
                    int stt = 1;
                    for (PhieuNhap pn : danhSachPhieuNhap) {
                        NhaCungCap ncc = pn.getNhaCungCap();
                        NhanVien nv = pn.getNhanVien();

                        Object[] rowData = {
                            stt++,
                            pn.getId(),
                            ncc != null ? ncc.getTenNhaCungCap() : "N/A",
                            ncc != null ? ncc.getSdt() : "N/A",
                            nv != null ? nv.getHoTen() : "N/A",
                            dateFormat.format(pn.getNgayLap()),
                            currencyFormat.format(pn.getTongTien())
                        };
                        model.addRow(rowData);
                    }

                    model.fireTableDataChanged();
                    jTable1.revalidate();
                    jTable1.repaint();
                }
            });

            return null;
        }
    };

    worker.execute();
    }//GEN-LAST:event_btnTimKiemActionPerformed

    private void btnChiTietActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnChiTietActionPerformed
        // TODO add your handling code here:
        int selectedRow = jTable1.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu nhập!");
            return;
        }

        String maPN = jTable1.getValueAt(selectedRow, 1).toString(); // Cột 0 là mã phiếu nhập

        // Lấy danh sách chi tiết phiếu nhập từ DAO
        ChiTietPhieuNhapDAO ctpnDAO = new ChiTietPhieuNhapDAO();
        List<ChiTietPhieuNhap> listCTPN = ctpnDAO.getChiTietByPhieuNhapId(maPN);

        if (listCTPN == null || listCTPN.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Phiếu nhập không có chi tiết!");
            return;
        }

        // Mở form chi tiết
        formChiTietPhieuNhap form = new formChiTietPhieuNhap(null, true, listCTPN);
        form.setLocationRelativeTo(this);
        form.setVisible(true);

    }//GEN-LAST:event_btnChiTietActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChiTiet;
    private javax.swing.JButton btnTimKiem;
    private com.toedter.calendar.JDateChooser dateNgayLap;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel39;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField txtMaPN;
    private javax.swing.JTextField txtTenNCC;
    private javax.swing.JTextField txtTenNV;
    // End of variables declaration//GEN-END:variables
}
