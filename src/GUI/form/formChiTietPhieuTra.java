/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JDialog.java to edit this template
 */
package GUI.form;

import DAO.KhachHangDAO;
import DAO.NhanVienDAO;
import DAO.PhieuTraDAO;
import DAO.ThuocDAO;
import Entity.ChiTietPhieuTra;
import Entity.KhachHang;
import Entity.NhanVien;
import Entity.PhieuTra;
import Entity.Thuoc;
import Utils.Formatter;
import com.formdev.flatlaf.extras.FlatSVGIcon;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Admin
 */
public class formChiTietPhieuTra extends javax.swing.JDialog {
    private List<ChiTietPhieuTra> listCTPT;
    private DefaultTableModel modal;
    /**
     * Creates new form formChiTietPhieuTra
     */
    public formChiTietPhieuTra(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
    }
    public formChiTietPhieuTra(java.awt.Frame parent, boolean modal, List<ChiTietPhieuTra> ctpt) {
        super(parent, modal);
        this.listCTPT = ctpt;
        initComponents();
        fillInput();
        fillTable();
        configureTable();
        addTableSelectionListener();
    }
    private void fillInput() {
        if (!listCTPT.isEmpty()) {
            String maPT = listCTPT.get(0).getMaPT();
            txtMaPD.setText(maPT);
            PhieuTra phieuDoi = PhieuTraDAO.getHoaDonByMaPD(maPT);
            if (phieuDoi != null) {
                String maHD = phieuDoi.getMaHD();
                txtMaHD1.setText(maHD);
                // Lấy thông tin khách hàng
                KhachHang kh = KhachHangDAO.getKhachHangByMaKH(phieuDoi.getMaKH());
                if (kh != null) {
                    txtTenKH.setText(kh.getHoTen());
                } else {
                    txtTenKH.setText("Không tìm thấy KH");
                }

                // Lấy thông tin nhân viên
                NhanVien nv = NhanVienDAO.getNhanVienByMaNV(phieuDoi.getMaNV());
                if (nv != null) {
                    txtTenNV.setText(nv.getHoTen());
                } else {
                    txtTenNV.setText("Không tìm thấy NV");
                }

                txtLyDo.setText(phieuDoi.getLyDo());
            } else {
                txtTenKH.setText("Không tìm thấy phiếu");
                txtTenNV.setText("");
                txtLyDo.setText("");
            }
        }
    }

    private void fillTable() {
        modal = new DefaultTableModel();
        String[] header = new String[]{"STT", "Mã thuốc", "Tên thuốc", "Số lượng", "Đơn giá", "Thành tiền"};
        modal.setColumnIdentifiers(header);
        table.setModel(modal);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
        table.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        table.getColumnModel().getColumn(0).setPreferredWidth(30);
        table.getColumnModel().getColumn(1).setPreferredWidth(200);

        loadTableCTHD(listCTPT);
    }

    public void loadTableCTHD(List<ChiTietPhieuTra> list) {
        modal.setRowCount(0);
        listCTPT = list;
        int stt = 1;
        double sum = 0;

        for (ChiTietPhieuTra e : listCTPT) {
            sum += e.getThanhTien();
            Thuoc thuoc = ThuocDAO.getThuocByMaThuoc(e.getMaThuoc());
            String tenThuoc = thuoc != null ? thuoc.getTenThuoc() : "Không tìm thấy";
            modal.addRow(new Object[]{
                String.valueOf(stt),
                e.getMaThuoc(),
                tenThuoc,
                e.getSoLuong(),
                Formatter.FormatVND(e.getDonGia()),
                Formatter.FormatVND(e.getThanhTien())
            });
            stt++;
        }
        txtTong.setText(Formatter.FormatVND(sum));
    }

    private void configureTable() {
        // Ngăn không cho phép người dùng chỉnh sửa bảng
        table.setDefaultEditor(Object.class, null);  // Điều này vô hiệu hóa khả năng chỉnh sửa của bất kỳ ô nào trong bảng.

        // Căn giữa cho tất cả các cell trong bảng
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        // Căn giữa cho từng cột
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Ngăn không cho phép chọn nhiều dòng
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    }

    private void addTableSelectionListener() {
        // Thêm listener xử lý sự kiện khi chọn dòng trong bảng
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) { // Chỉ xử lý khi sự kiện chọn đã hoàn tất
                    int selectedRow = table.getSelectedRow();
                    if (selectedRow != -1) {
                        // Lấy mã thuốc từ dòng đã chọn
                        String maThuoc = table.getValueAt(selectedRow, 1) != null
                                ? table.getValueAt(selectedRow, 1).toString() : "";

                        // Tìm thông tin thuốc theo mã thuốc
                        Thuoc thuoc = ThuocDAO.getThuocByMaThuoc(maThuoc);

                        if (thuoc != null) {

                            // Hiển thị ảnh thuốc
                            byte[] anhThuoc = thuoc.getHinhAnh();
                            if (anhThuoc != null && anhThuoc.length > 0) {
                                ImageIcon icon = new ImageIcon(anhThuoc);
                                txtHinhAnh.setIcon(icon);  // Cập nhật ảnh vào lblAnh
                            } else {
                                // Nếu không có ảnh, hiển thị ảnh mặc định
                                txtHinhAnh.setIcon(new FlatSVGIcon("./icon/image.svg"));
                            }
                        }
                    }
                }
            }
        });
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel15 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        hoaDonPanel = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txtMaPD = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        txtMaHD1 = new javax.swing.JTextField();
        jPanel17 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        txtTenKH = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        txtTenNV = new javax.swing.JTextField();
        jPanel19 = new javax.swing.JPanel();
        imagePanel = new javax.swing.JPanel();
        txtHinhAnh = new javax.swing.JLabel();
        txtLyDo = new javax.swing.JTextField();
        tableItemPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        table = new javax.swing.JTable();
        jPanel20 = new javax.swing.JPanel();
        jPanel21 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        txtTong = new javax.swing.JTextField();
        jPanel22 = new javax.swing.JPanel();
        lblThuoc = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        btnHuy = new javax.swing.JButton();
        btnPrint = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(1400, 672));

        jPanel15.setBackground(new java.awt.Color(0, 120, 92));
        jPanel15.setMinimumSize(new java.awt.Dimension(100, 60));
        jPanel15.setPreferredSize(new java.awt.Dimension(500, 50));
        jPanel15.setLayout(new java.awt.BorderLayout());

        jLabel8.setFont(new java.awt.Font("Roboto Medium", 0, 18)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("CHI TIẾT HÓA ĐON");
        jLabel8.setPreferredSize(new java.awt.Dimension(149, 40));
        jPanel15.add(jLabel8, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel15, java.awt.BorderLayout.NORTH);

        jPanel8.setBackground(new java.awt.Color(255, 255, 255));
        jPanel8.setLayout(new java.awt.BorderLayout());

        hoaDonPanel.setBackground(new java.awt.Color(255, 255, 255));
        hoaDonPanel.setPreferredSize(new java.awt.Dimension(1400, 80));
        hoaDonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 20, 16));

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));
        jPanel9.setPreferredSize(new java.awt.Dimension(240, 40));
        jPanel9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

        jLabel4.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel4.setText("Mã phiếu đổi");
        jLabel4.setPreferredSize(new java.awt.Dimension(120, 40));
        jPanel9.add(jLabel4);

        txtMaPD.setEditable(false);
        txtMaPD.setFont(new java.awt.Font("Roboto Mono", 1, 14)); // NOI18N
        txtMaPD.setText("Z2NX8CN1A");
        txtMaPD.setFocusable(false);
        txtMaPD.setPreferredSize(new java.awt.Dimension(100, 40));
        jPanel9.add(txtMaPD);

        hoaDonPanel.add(jPanel9);

        jPanel10.setBackground(new java.awt.Color(255, 255, 255));
        jPanel10.setPreferredSize(new java.awt.Dimension(240, 40));
        jPanel10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

        jLabel6.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel6.setText("Mã hoá đơn đổi");
        jLabel6.setPreferredSize(new java.awt.Dimension(120, 40));
        jPanel10.add(jLabel6);

        txtMaHD1.setEditable(false);
        txtMaHD1.setFont(new java.awt.Font("Roboto Mono", 1, 14)); // NOI18N
        txtMaHD1.setText("Z2NX8CN1A");
        txtMaHD1.setFocusable(false);
        txtMaHD1.setPreferredSize(new java.awt.Dimension(100, 40));
        jPanel10.add(txtMaHD1);

        hoaDonPanel.add(jPanel10);

        jPanel17.setBackground(new java.awt.Color(255, 255, 255));
        jPanel17.setPreferredSize(new java.awt.Dimension(370, 40));
        jPanel17.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

        jLabel5.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel5.setText("Tên khách hàng");
        jLabel5.setPreferredSize(new java.awt.Dimension(100, 40));
        jPanel17.add(jLabel5);

        txtTenKH.setEditable(false);
        txtTenKH.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtTenKH.setText("Nguyễn Văn A");
        txtTenKH.setFocusable(false);
        txtTenKH.setPreferredSize(new java.awt.Dimension(250, 40));
        jPanel17.add(txtTenKH);

        hoaDonPanel.add(jPanel17);

        jPanel18.setBackground(new java.awt.Color(255, 255, 255));
        jPanel18.setMinimumSize(new java.awt.Dimension(140, 25));
        jPanel18.setPreferredSize(new java.awt.Dimension(320, 40));
        jPanel18.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

        jLabel7.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        jLabel7.setText("Tên nhân viên");
        jLabel7.setPreferredSize(new java.awt.Dimension(100, 40));
        jPanel18.add(jLabel7);

        txtTenNV.setEditable(false);
        txtTenNV.setFont(new java.awt.Font("Roboto", 0, 14)); // NOI18N
        txtTenNV.setText("Vũ Nương");
        txtTenNV.setFocusable(false);
        txtTenNV.setPreferredSize(new java.awt.Dimension(200, 40));
        jPanel18.add(txtTenNV);

        hoaDonPanel.add(jPanel18);

        jPanel8.add(hoaDonPanel, java.awt.BorderLayout.PAGE_START);

        jPanel19.setBackground(new java.awt.Color(255, 255, 255));
        jPanel19.setPreferredSize(new java.awt.Dimension(400, 100));
        jPanel19.setLayout(new java.awt.BorderLayout());

        imagePanel.setBackground(new java.awt.Color(255, 255, 255));
        imagePanel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(237, 237, 237), 2, true));
        imagePanel.setPreferredSize(new java.awt.Dimension(300, 300));
        imagePanel.setLayout(new java.awt.BorderLayout());

        txtHinhAnh.setBackground(new java.awt.Color(255, 255, 255));
        txtHinhAnh.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        txtHinhAnh.setIcon(new FlatSVGIcon("./icon/image.svg"));
        txtHinhAnh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        txtHinhAnh.setPreferredSize(new java.awt.Dimension(200, 100));
        imagePanel.add(txtHinhAnh, java.awt.BorderLayout.CENTER);

        jPanel19.add(imagePanel, java.awt.BorderLayout.CENTER);

        txtLyDo.setPreferredSize(new java.awt.Dimension(70, 60));
        jPanel19.add(txtLyDo, java.awt.BorderLayout.SOUTH);

        jPanel8.add(jPanel19, java.awt.BorderLayout.WEST);

        tableItemPanel.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(240, 240, 240), 1, true));

        table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã thuốc", "Tên thuốc", "Số lượng", "Đơn giá", "Thành tiền"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        table.setFocusable(false);
        table.setRowHeight(40);
        table.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        table.setShowHorizontalLines(true);
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(table);

        tableItemPanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        jPanel20.setBackground(new java.awt.Color(255, 255, 255));
        jPanel20.setPreferredSize(new java.awt.Dimension(800, 60));
        jPanel20.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT));

        jPanel21.setBackground(new java.awt.Color(255, 255, 255));
        jPanel21.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 0));

        jLabel9.setFont(new java.awt.Font("Roboto", 1, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 51, 0));
        jLabel9.setText("Tổng hóa đơn:");
        jLabel9.setPreferredSize(new java.awt.Dimension(120, 40));
        jPanel21.add(jLabel9);

        txtTong.setEditable(false);
        txtTong.setFont(new java.awt.Font("Roboto Mono Medium", 0, 14)); // NOI18N
        txtTong.setForeground(new java.awt.Color(255, 51, 0));
        txtTong.setText("1000000");
        txtTong.setFocusable(false);
        txtTong.setPreferredSize(new java.awt.Dimension(200, 40));
        jPanel21.add(txtTong);

        jPanel20.add(jPanel21);

        tableItemPanel.add(jPanel20, java.awt.BorderLayout.PAGE_END);

        jPanel22.setBackground(new java.awt.Color(0, 120, 92));
        jPanel22.setMinimumSize(new java.awt.Dimension(100, 60));
        jPanel22.setPreferredSize(new java.awt.Dimension(500, 30));
        jPanel22.setLayout(new java.awt.BorderLayout());

        lblThuoc.setFont(new java.awt.Font("Roboto Medium", 0, 14)); // NOI18N
        lblThuoc.setForeground(new java.awt.Color(255, 255, 255));
        lblThuoc.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblThuoc.setText("Thông tin thuốc");
        jPanel22.add(lblThuoc, java.awt.BorderLayout.CENTER);

        tableItemPanel.add(jPanel22, java.awt.BorderLayout.NORTH);

        jPanel8.add(tableItemPanel, java.awt.BorderLayout.CENTER);

        getContentPane().add(jPanel8, java.awt.BorderLayout.CENTER);

        jPanel23.setBackground(new java.awt.Color(255, 255, 255));
        jPanel23.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 8, 5));

        btnHuy.setBackground(new java.awt.Color(255, 102, 102));
        btnHuy.setFont(new java.awt.Font("Roboto Mono Medium", 0, 16)); // NOI18N
        btnHuy.setForeground(new java.awt.Color(255, 255, 255));
        btnHuy.setText("HỦY BỎ");
        btnHuy.setBorderPainted(false);
        btnHuy.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnHuy.setFocusPainted(false);
        btnHuy.setFocusable(false);
        btnHuy.setPreferredSize(new java.awt.Dimension(200, 40));
        btnHuy.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnHuyActionPerformed(evt);
            }
        });
        jPanel23.add(btnHuy);

        btnPrint.setBackground(new java.awt.Color(0, 120, 92));
        btnPrint.setFont(new java.awt.Font("Roboto Mono Medium", 0, 16)); // NOI18N
        btnPrint.setForeground(new java.awt.Color(255, 255, 255));
        btnPrint.setText("In hóa đơn");
        btnPrint.setBorderPainted(false);
        btnPrint.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPrint.setFocusPainted(false);
        btnPrint.setFocusable(false);
        btnPrint.setPreferredSize(new java.awt.Dimension(200, 40));
        btnPrint.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPrintActionPerformed(evt);
            }
        });
        jPanel23.add(btnPrint);

        getContentPane().add(jPanel23, java.awt.BorderLayout.PAGE_END);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tableMouseClicked

    }//GEN-LAST:event_tableMouseClicked

    private void btnHuyActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnHuyActionPerformed
        this.dispose();
    }//GEN-LAST:event_btnHuyActionPerformed

    private void btnPrintActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPrintActionPerformed
//        String maPD = listCTPD.get(0).getMaPD();
//        PhieuDoi pd = PhieuDoiDAO.getHoaDonByMaPD(maPD);
//        new WritePDF().printPhieuDoi(pd, listCTPD);
    }//GEN-LAST:event_btnPrintActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(formChiTietPhieuTra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(formChiTietPhieuTra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(formChiTietPhieuTra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(formChiTietPhieuTra.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                formChiTietPhieuTra dialog = new formChiTietPhieuTra(new javax.swing.JFrame(), true);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnHuy;
    private javax.swing.JButton btnPrint;
    private javax.swing.JPanel hoaDonPanel;
    private javax.swing.JPanel imagePanel;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblThuoc;
    private javax.swing.JTable table;
    private javax.swing.JPanel tableItemPanel;
    private javax.swing.JLabel txtHinhAnh;
    private javax.swing.JTextField txtLyDo;
    private javax.swing.JTextField txtMaHD1;
    private javax.swing.JTextField txtMaPD;
    private javax.swing.JTextField txtTenKH;
    private javax.swing.JTextField txtTenNV;
    private javax.swing.JTextField txtTong;
    // End of variables declaration//GEN-END:variables
}
