package GUI.page;

import DAO.HoaDonDAO;
import DAO.PhieuTraDAO;
import Entity.ChiTietPhieuTra;
import Entity.HoaDon;
import Entity.KhachHang;
import Entity.NhanVien;
import Entity.PhieuTra;
import GUI.form.formChiTietPhieuTra;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.JTextField; // Added for JTextField

public class frmSearchPhieuTra extends javax.swing.JPanel {
    private List<PhieuTra> phieuTraList = new ArrayList<>(); // Initialize to avoid NullPointerException
    private int startIndex = 0;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
    private final DecimalFormat currencyFormat = new DecimalFormat("#,### VND");

    public frmSearchPhieuTra() {
        initComponents();
        loadDataToTable();
        configureTable();
    }

    private void configureTable() {
        // Prevent table editing
        jTable2.setDefaultEditor(Object.class, null);

        // Center align all cells
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);

        for (int i = 0; i < jTable2.getColumnCount(); i++) {
            jTable2.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        // Allow single row selection only
        jTable2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
    }

    private void loadDataToTable() {
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                phieuTraList = PhieuTraDAO.getAllPhieuTra(); // Populate phieuTraList

                SwingUtilities.invokeLater(() -> {
                    DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
                    model.setRowCount(0);

                    int stt = 1;
                    for (PhieuTra pt : phieuTraList) {
                        KhachHang kh = pt.getKhachHang();
                        NhanVien nv = pt.getNhanVien();
                        String maHD = pt.getMaHD();
                        String lyDo = pt.getLyDo();
                        Date ngayLap = pt.getNgayLap();
                        double tongTien = pt.tinhTongTien();

                        Object[] rowData = {
                                stt++,
                                pt.getMaPT(),
                                maHD != null ? maHD : "N/A",
                                kh != null ? kh.getHoTen() : "N/A",
                                kh != null ? kh.getSdt() : "N/A",
                                nv != null ? nv.getHoTen() : "N/A",
                                ngayLap != null ? dateFormat.format(ngayLap) : "N/A",
                                lyDo != null ? lyDo : "N/A",
                                currencyFormat.format(tongTien)
                        };
                        model.addRow(rowData);
                    }

                    model.fireTableDataChanged();
                    jTable2.revalidate();
                    jTable2.repaint();
                });
                return null;
            }
        };
        worker.execute();
    }

    private void btnTimKiemActionPerformed(java.awt.event.ActionEvent evt) {
        // Get input data
        String maPT = txtMaPT.getText().trim();
        String maHD = txtMaHD.getText().trim();
        String tenKH = txtTenKH.getText().trim();
        String sdt = txtSdtKH.getText().trim();
        Date ngayTra = dateNgayTra.getDate();

        // Validate input
        if (maPT.isEmpty() && maHD.isEmpty() && tenKH.isEmpty() && sdt.isEmpty() && ngayTra == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập ít nhất một tiêu chí tìm kiếm!", "Thông báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Perform asynchronous search
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                phieuTraList = PhieuTraDAO.searchPhieuTra(maPT, maHD, tenKH, sdt, ngayTra);

                SwingUtilities.invokeLater(() -> {
                    DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
                    model.setRowCount(0);

                    int stt = 1;
                    for (PhieuTra pt : phieuTraList) {
                        KhachHang kh = pt.getKhachHang();
                        NhanVien nv = pt.getNhanVien();
                        Object[] rowData = {
                                stt++,
                                pt.getMaPT(),
                                pt.getMaHD(),
                                kh != null ? kh.getHoTen() : "N/A",
                                kh != null ? kh.getSdt() : "N/A",
                                nv != null ? nv.getHoTen() : "N/A",
                                pt.getNgayLap() != null ? dateFormat.format(pt.getNgayLap()) : "N/A",
                                pt.getLyDo(),
                                currencyFormat.format(pt.tinhTongTien())
                        };
                        model.addRow(rowData);
                    }

                    model.fireTableDataChanged();
                    jTable2.revalidate();
                    jTable2.repaint();

                    if (phieuTraList.isEmpty()) {
                        JOptionPane.showMessageDialog(frmSearchPhieuTra.this, "Không tìm thấy phiếu trả nào phù hợp!",
                                "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                    }
                });

                return null;
            }
        };
        worker.execute();
    }

    private void btnChiTietActionPerformed(java.awt.event.ActionEvent evt) {
        int selectedRow = jTable2.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một phiếu trả để xem chi tiết!", "Thông Báo",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        PhieuTra selectedPhieuTra = phieuTraList.get(selectedRow);
        List<ChiTietPhieuTra> chiTietList = new ArrayList<>(); // Replace with actual DAO call

        JFrame frame = new JFrame("Chi Tiết Phiếu Trả");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        formChiTietPhieuTra detailForm = new formChiTietPhieuTra(selectedPhieuTra, chiTietList);
        frame.add(detailForm);
        frame.setResizable(false);
        frame.setSize(450, 600);
        frame.setLocationRelativeTo(this);
        frame.setVisible(true);
    }

    private void btnPDFActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO: Implement PDF export functionality
        JOptionPane.showMessageDialog(this, "Chức năng xuất PDF chưa được triển khai!", "Thông báo",
                JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // <editor-fold defaultstate="collapsed" desc="Generated
    // Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        jPanel13 = new javax.swing.JPanel();
        btnTimKiem = new javax.swing.JButton();
        btnChiTiet = new javax.swing.JButton();
        btnPDF = new javax.swing.JButton();
        jPanel12 = new javax.swing.JPanel();
        jPanel33 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel34 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jPanel36 = new javax.swing.JPanel();
        jPanel37 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel38 = new javax.swing.JPanel();
        txtMaPT = new javax.swing.JTextField(); // Changed to JTextField
        jPanel21 = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        txtMaHD = new javax.swing.JTextField(); // Changed to JTextField
        jPanel14 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        txtTenKH = new javax.swing.JTextField(); // Changed to JTextField
        jPanel16 = new javax.swing.JPanel();
        jPanel32 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        txtSdtKH = new javax.swing.JTextField(); // Changed to JTextField
        jPanel17 = new javax.swing.JPanel();
        jPanel22 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jPanel23 = new javax.swing.JPanel();
        dateNgayTra = new com.toedter.calendar.JDateChooser();
        jPanel18 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jPanel20 = new javax.swing.JPanel();

        setLayout(new java.awt.BorderLayout());

        jPanel13.setPreferredSize(new java.awt.Dimension(829, 55));
        jPanel13.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 100, 10));

        btnTimKiem.setBackground(new java.awt.Color(0, 120, 92));
        btnTimKiem.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnTimKiem.setForeground(new java.awt.Color(255, 255, 255));
        btnTimKiem.setText("Tìm kiếm");
        btnTimKiem.setPreferredSize(new java.awt.Dimension(105, 35));
        btnTimKiem.addActionListener(evt -> btnTimKiemActionPerformed(evt));
        jPanel13.add(btnTimKiem);

        btnChiTiet.setBackground(new java.awt.Color(0, 120, 92));
        btnChiTiet.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnChiTiet.setForeground(new java.awt.Color(255, 255, 255));
        btnChiTiet.setText("Chi tiết");
        btnChiTiet.setPreferredSize(new java.awt.Dimension(105, 35));
        btnChiTiet.addActionListener(evt -> btnChiTietActionPerformed(evt));
        jPanel13.add(btnChiTiet);

        btnPDF.setBackground(new java.awt.Color(0, 120, 92));
        btnPDF.setFont(new java.awt.Font("Segoe UI", 1, 14));
        btnPDF.setForeground(new java.awt.Color(255, 255, 255));
        btnPDF.setText("Xuất PDF");
        btnPDF.setPreferredSize(new java.awt.Dimension(105, 35));
        btnPDF.addActionListener(evt -> btnPDFActionPerformed(evt)); // Added ActionListener
        jPanel13.add(btnPDF);

        add(jPanel13, java.awt.BorderLayout.PAGE_END);

        jPanel12.setPreferredSize(new java.awt.Dimension(829, 220));
        jPanel12.setLayout(new java.awt.BorderLayout());

        jPanel33.setBackground(new java.awt.Color(0, 120, 92));
        jPanel33.setPreferredSize(new java.awt.Dimension(829, 50));
        jPanel33.setLayout(new java.awt.BorderLayout());

        jLabel4.setFont(new java.awt.Font("Segoe UI", 1, 24));
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Danh sách phiếu trả");
        jPanel33.add(jLabel4, java.awt.BorderLayout.CENTER);

        jPanel12.add(jPanel33, java.awt.BorderLayout.PAGE_START);

        jPanel34.setLayout(new java.awt.BorderLayout());

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
                new Object[][] {
                        { null, null, null, null, null, null, null, null, null },
                        { null, null, null, null, null, null, null, null, null },
                        { null, null, null, null, null, null, null, null, null },
                        { null, null, null, null, null, null, null, null, null }
                },
                new String[] {
                        "STT", "Mã phiếu trả", "Mã hóa đơn", "Tên khách hàng", "SĐT", "Tên nhân viên", "Ngày trả",
                        "Lý do", "Tổng phiếu trả"
                }));
        jTable2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable2.setShowHorizontalLines(true);
        jScrollPane2.setViewportView(jTable2);

        jPanel34.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel12.add(jPanel34, java.awt.BorderLayout.CENTER);

        add(jPanel12, java.awt.BorderLayout.CENTER);

        jPanel8.setBackground(new java.awt.Color(255, 102, 102));
        jPanel8.setPreferredSize(new java.awt.Dimension(829, 250));
        jPanel8.setLayout(new java.awt.BorderLayout());

        jPanel9.setBackground(new java.awt.Color(0, 120, 92));
        jPanel9.setPreferredSize(new java.awt.Dimension(829, 50));
        jPanel9.setLayout(new java.awt.BorderLayout());

        jLabel3.setFont(new java.awt.Font("Segoe UI", 1, 24));
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("Nhập thông tin tìm kiếm");
        jPanel9.add(jLabel3, java.awt.BorderLayout.CENTER);

        jPanel8.add(jPanel9, java.awt.BorderLayout.PAGE_START);

        jPanel10.setLayout(new javax.swing.BoxLayout(jPanel10, javax.swing.BoxLayout.Y_AXIS));

        jPanel36.setMinimumSize(new java.awt.Dimension(829, 38));
        jPanel36.setLayout(new java.awt.BorderLayout());

        jPanel37.setPreferredSize(new java.awt.Dimension(500, 38));
        jPanel37.setLayout(new java.awt.BorderLayout());

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Mã phiếu trả:");
        jPanel37.add(jLabel11, java.awt.BorderLayout.CENTER);

        jPanel36.add(jPanel37, java.awt.BorderLayout.LINE_START);

        jPanel38.setPreferredSize(new java.awt.Dimension(669, 38));
        jPanel38.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 7));

        txtMaPT.setPreferredSize(new java.awt.Dimension(350, 30));
        jPanel38.add(txtMaPT);

        jPanel36.add(jPanel38, java.awt.BorderLayout.CENTER);

        jPanel10.add(jPanel36);

        jPanel21.setMinimumSize(new java.awt.Dimension(829, 38));
        jPanel21.setLayout(new java.awt.BorderLayout());

        jPanel30.setPreferredSize(new java.awt.Dimension(500, 38));
        jPanel30.setLayout(new java.awt.BorderLayout());

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Mã hóa đơn:");
        jPanel30.add(jLabel9, java.awt.BorderLayout.CENTER);

        jPanel21.add(jPanel30, java.awt.BorderLayout.LINE_START);

        jPanel31.setPreferredSize(new java.awt.Dimension(669, 38));
        jPanel31.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 7));

        txtMaHD.setPreferredSize(new java.awt.Dimension(350, 30));
        jPanel31.add(txtMaHD);

        jPanel21.add(jPanel31, java.awt.BorderLayout.CENTER);

        jPanel10.add(jPanel21);

        jPanel14.setMinimumSize(new java.awt.Dimension(829, 38));
        jPanel14.setLayout(new java.awt.BorderLayout());

        jPanel26.setPreferredSize(new java.awt.Dimension(500, 38));
        jPanel26.setLayout(new java.awt.BorderLayout());

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Tên khách hàng:");
        jPanel26.add(jLabel8, java.awt.BorderLayout.CENTER);

        jPanel14.add(jPanel26, java.awt.BorderLayout.LINE_START);

        jPanel27.setPreferredSize(new java.awt.Dimension(669, 38));
        jPanel27.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 7));

        txtTenKH.setPreferredSize(new java.awt.Dimension(350, 30));
        jPanel27.add(txtTenKH);

        jPanel14.add(jPanel27, java.awt.BorderLayout.CENTER);

        jPanel10.add(jPanel14);

        jPanel16.setMinimumSize(new java.awt.Dimension(829, 38));
        jPanel16.setLayout(new java.awt.BorderLayout());

        jPanel32.setPreferredSize(new java.awt.Dimension(500, 38));
        jPanel32.setLayout(new java.awt.BorderLayout());

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Số điện thoại:");
        jPanel32.add(jLabel10, java.awt.BorderLayout.CENTER);

        jPanel16.add(jPanel32, java.awt.BorderLayout.LINE_START);

        jPanel35.setPreferredSize(new java.awt.Dimension(669, 38));
        jPanel35.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 7));

        txtSdtKH.setPreferredSize(new java.awt.Dimension(350, 30));
        jPanel35.add(txtSdtKH);

        jPanel16.add(jPanel35, java.awt.BorderLayout.CENTER);

        jPanel10.add(jPanel16);

        jPanel17.setMinimumSize(new java.awt.Dimension(829, 38));
        jPanel17.setLayout(new java.awt.BorderLayout());

        jPanel22.setPreferredSize(new java.awt.Dimension(500, 38));
        jPanel22.setLayout(new java.awt.BorderLayout());

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Ngày mua:");
        jPanel22.add(jLabel6, java.awt.BorderLayout.CENTER);

        jPanel17.add(jPanel22, java.awt.BorderLayout.LINE_START);

        jPanel23.setPreferredSize(new java.awt.Dimension(669, 38));
        jPanel23.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 7));

        dateNgayTra.setPreferredSize(new java.awt.Dimension(150, 30));
        jPanel23.add(dateNgayTra);

        jPanel17.add(jPanel23, java.awt.BorderLayout.CENTER);

        jPanel10.add(jPanel17);

        jPanel18.setMinimumSize(new java.awt.Dimension(829, 38));
        jPanel18.setLayout(new java.awt.BorderLayout());

        jPanel19.setPreferredSize(new java.awt.Dimension(500, 38));
        jPanel19.setLayout(new java.awt.BorderLayout());
        jPanel18.add(jPanel19, java.awt.BorderLayout.LINE_START);

        jPanel20.setPreferredSize(new java.awt.Dimension(669, 38));
        jPanel20.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 7));
        jPanel18.add(jPanel20, java.awt.BorderLayout.CENTER);

        jPanel10.add(jPanel18);

        jPanel8.add(jPanel10, java.awt.BorderLayout.CENTER);

        add(jPanel8, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChiTiet;
    private javax.swing.JButton btnPDF;
    private javax.swing.JButton btnTimKiem;
    private com.toedter.calendar.JDateChooser dateNgayTra;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
    private javax.swing.JPanel jPanel32;
    private javax.swing.JPanel jPanel33;
    private javax.swing.JPanel jPanel34;
    private javax.swing.JPanel jPanel35;
    private javax.swing.JPanel jPanel36;
    private javax.swing.JPanel jPanel37;
    private javax.swing.JPanel jPanel38;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField txtMaHD; // Changed to JTextField
    private javax.swing.JTextField txtMaPT; // Changed to JTextField
    private javax.swing.JTextField txtSdtKH; // Changed to JTextField
    private javax.swing.JTextField txtTenKH; // Changed to JTextField
    // End of variables declaration//GEN-END:variables
}
