package GUI.page;

import DAO.ChiTietPhieuTraDAO;
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
                List<PhieuTra> danhSachHoaDonDoi = PhieuTraDAO.getAllPhieuTra(); // Lấy tất cả phiếu đổi
                System.out.println("Dữ liệu nhận được từ DB: "
                        + (danhSachHoaDonDoi != null ? danhSachHoaDonDoi.size() : 0) + " dòng");

                if (danhSachHoaDonDoi == null || danhSachHoaDonDoi.isEmpty()) {
                    System.out.println("Không có dữ liệu để tải.");
                    return null;
                }

                SwingUtilities.invokeLater(() -> {
                    DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
                    model.setRowCount(0); // Xóa dữ liệu cũ trong bảng

                    int stt = 1;
                    for (PhieuTra pd : danhSachHoaDonDoi) {
                        String maPD = pd.getMaPT();
                        String maHD = pd.getMaHD() != null ? pd.getMaHD() : "N/A";
                        KhachHang kh = pd.getKhachHang();
                        String tenKH = (kh != null) ? kh.getHoTen() : "N/A";
                        String sdtKH = (kh != null) ? kh.getSdt() : "N/A";
                        NhanVien nv = pd.getNhanVien();
                        String tenNV = (nv != null) ? nv.getHoTen() : "N/A";
                        String ngayLapStr = (pd.getNgayLap() != null) ? dateFormat.format(pd.getNgayLap()) : "N/A";
                        String lyDo = pd.getLyDo() != null ? pd.getLyDo() : "N/A";
                        double tongTienHoaDon = pd.getTongTienHoaDon();
                        double tongTienPhieuDoi = 0;
                        double chenhLech = tongTienPhieuDoi - tongTienHoaDon;

                        Object[] rowData = {
                            stt++,
                            maPD,
                            maHD,
                            tenKH,
                            sdtKH,
                            tenNV,
                            ngayLapStr,
                            lyDo,
                            String.format("%,.0f VND", tongTienHoaDon),
                            String.format("%,.0f VND", tongTienPhieuDoi),
                            (chenhLech > 0) ? "Khách trả thêm: " + String.format("%,.0f VND", chenhLech)
                            : (chenhLech < 0)
                            ? "Hoàn lại khách: " + String.format("%,.0f VND", Math.abs(chenhLech))
                            : "Không chênh lệch"
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
        // Lấy dữ liệu từ các trường nhập liệu
        String maPD = txtMaPT.getText().trim();
        String maHD = txtMaHD.getText().trim();
        String tenKH = txtTenKH.getText().trim();
        String sdtKH = txtSdtKH.getText().trim();
        Date ngayTra = dateNgayTra.getDate();

        // // Kiểm tra dữ liệu đầu vào
        // if (maPD.isEmpty() && maHD.isEmpty() && tenKH.isEmpty() && sdtKH.isEmpty() &&
        // ngayTra == null) {
        // JOptionPane.showMessageDialog(this, "Vui lòng nhập ít nhất một tiêu chí tìm
        // kiếm!", "Thông báo",
        // JOptionPane.WARNING_MESSAGE);
        // return;
        // }
        // Thực hiện tìm kiếm bất đồng bộ
        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                List<PhieuTra> danhSachPhieuDoi = PhieuTraDAO.searchPhieuTra(maPD, maHD, tenKH, sdtKH, ngayTra);

                SwingUtilities.invokeLater(() -> {
                    DefaultTableModel model = (DefaultTableModel) jTable2.getModel();
                    model.setRowCount(0); // Xóa dữ liệu cũ

                    int stt = 1;
                    for (PhieuTra pd : danhSachPhieuDoi) {
                        String maPDResult = pd.getMaPT();
                        String maHDResult = pd.getMaHD() != null ? pd.getMaHD() : "N/A";
                        String tenKhachHang = pd.getKhachHang() != null ? pd.getKhachHang().getHoTen() : "N/A";
                        String sdtKhachHang = pd.getKhachHang() != null ? pd.getKhachHang().getSdt() : "N/A";
                        String tenNhanVien = pd.getNhanVien() != null ? pd.getNhanVien().getHoTen() : "N/A";
                        String ngayLapStr = pd.getNgayLap() != null ? dateFormat.format(pd.getNgayLap()) : "N/A";
                        String lyDo = pd.getLyDo() != null ? pd.getLyDo() : "N/A";
                        double tongTienHoaDon = pd.getTongTienHoaDon();
                        double tongTienPhieuDoi = 0;
                        double chenhLech = tongTienPhieuDoi - tongTienHoaDon;

                        Object[] rowData = {
                            stt++,
                            maPDResult,
                            maHDResult,
                            tenKhachHang,
                            sdtKhachHang,
                            tenNhanVien,
                            ngayLapStr,
                            lyDo,
                            currencyFormat.format(tongTienHoaDon), // Đồng nhất định dạng với PhieuTra
                            currencyFormat.format(tongTienPhieuDoi),
                            (chenhLech > 0) ? "Khách trả thêm: " + currencyFormat.format(chenhLech)
                            : (chenhLech < 0)
                            ? "Hoàn lại khách: " + currencyFormat.format(Math.abs(chenhLech))
                            : "Không chênh lệch"
                        };
                        model.addRow(rowData);
                    }

                    model.fireTableDataChanged();
                    jTable2.revalidate();
                    jTable2.repaint();

                    // Thông báo nếu không tìm thấy kết quả
                    if (danhSachPhieuDoi.isEmpty()) {
                        JOptionPane.showMessageDialog(frmSearchPhieuTra.this, "Không tìm thấy phiếu đổi nào phù hợp!",
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
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn!");
            return;
        }

        String maHD = jTable2.getValueAt(selectedRow, 1).toString(); // Cột 0 là mã phiếu nhập

        ChiTietPhieuTraDAO ctpdDAO = new ChiTietPhieuTraDAO();
        List<ChiTietPhieuTra> listCTPD = ctpdDAO.getChiTietByHoaDoiId(maHD);

        if (listCTPD == null || listCTPD.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Hóa đơn không có chi tiết!");
            return;
        }

        // Mở form chi tiết
        formChiTietPhieuTra form = new formChiTietPhieuTra(null, true, listCTPD);
        form.setLocationRelativeTo(this);
        form.setVisible(true);
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
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel13 = new javax.swing.JPanel();
        btnTimKiem = new javax.swing.JButton();
        btnChiTiet = new javax.swing.JButton();
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
        txtMaPT = new java.awt.TextField();
        jPanel21 = new javax.swing.JPanel();
        jPanel30 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jPanel31 = new javax.swing.JPanel();
        txtMaHD = new java.awt.TextField();
        jPanel14 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        txtTenKH = new java.awt.TextField();
        jPanel16 = new javax.swing.JPanel();
        jPanel32 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel35 = new javax.swing.JPanel();
        txtSdtKH = new java.awt.TextField();
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
        jLabel4.setText("Danh sách phiếu trả");
        jPanel33.add(jLabel4, java.awt.BorderLayout.CENTER);

        jPanel12.add(jPanel33, java.awt.BorderLayout.PAGE_START);

        jPanel34.setLayout(new java.awt.BorderLayout());

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "STT", "Mã phiếu trả", "Mã hóa đơn", "Tên khách hàng", "SĐT", "Tên nhân viên", "Ngày trả", "Lý do", "Tổng phiếu trả"
            }
        ));
        jTable2.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable2.setShowHorizontalLines(true);
        jScrollPane2.setViewportView(jTable2);

        jPanel34.add(jScrollPane2, java.awt.BorderLayout.CENTER);

        jPanel12.add(jPanel34, java.awt.BorderLayout.CENTER);

        add(jPanel12, java.awt.BorderLayout.CENTER);

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

        jPanel10.setMinimumSize(new java.awt.Dimension(829, 300));
        jPanel10.setLayout(new javax.swing.BoxLayout(jPanel10, javax.swing.BoxLayout.Y_AXIS));

        jPanel36.setMinimumSize(new java.awt.Dimension(829, 38));
        jPanel36.setLayout(new java.awt.BorderLayout());

        jPanel37.setMinimumSize(new java.awt.Dimension(300, 38));
        jPanel37.setPreferredSize(new java.awt.Dimension(500, 38));
        jPanel37.setLayout(new java.awt.BorderLayout());

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("Mã phiếu trả:");
        jLabel11.setAlignmentX(20.0F);
        jLabel11.setAlignmentY(20.0F);
        jPanel37.add(jLabel11, java.awt.BorderLayout.CENTER);

        jPanel36.add(jPanel37, java.awt.BorderLayout.LINE_START);

        jPanel38.setMinimumSize(new java.awt.Dimension(669, 38));
        jPanel38.setPreferredSize(new java.awt.Dimension(669, 38));
        jPanel38.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 7));

        txtMaPT.setMaximumSize(new java.awt.Dimension(350, 32));
        txtMaPT.setMinimumSize(new java.awt.Dimension(350, 32));
        txtMaPT.setPreferredSize(new java.awt.Dimension(350, 30));
        jPanel38.add(txtMaPT);

        jPanel36.add(jPanel38, java.awt.BorderLayout.CENTER);

        jPanel10.add(jPanel36);

        jPanel21.setMinimumSize(new java.awt.Dimension(829, 38));
        jPanel21.setLayout(new java.awt.BorderLayout());

        jPanel30.setMinimumSize(new java.awt.Dimension(300, 38));
        jPanel30.setPreferredSize(new java.awt.Dimension(500, 38));
        jPanel30.setLayout(new java.awt.BorderLayout());

        jLabel9.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel9.setText("Mã hóa đơn:");
        jLabel9.setAlignmentX(20.0F);
        jLabel9.setAlignmentY(20.0F);
        jPanel30.add(jLabel9, java.awt.BorderLayout.CENTER);

        jPanel21.add(jPanel30, java.awt.BorderLayout.LINE_START);

        jPanel31.setMinimumSize(new java.awt.Dimension(669, 38));
        jPanel31.setPreferredSize(new java.awt.Dimension(669, 38));
        jPanel31.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 7));

        txtMaHD.setMaximumSize(new java.awt.Dimension(350, 32));
        txtMaHD.setMinimumSize(new java.awt.Dimension(350, 32));
        txtMaHD.setPreferredSize(new java.awt.Dimension(350, 30));
        jPanel31.add(txtMaHD);

        jPanel21.add(jPanel31, java.awt.BorderLayout.CENTER);

        jPanel10.add(jPanel21);

        jPanel14.setMinimumSize(new java.awt.Dimension(829, 38));
        jPanel14.setLayout(new java.awt.BorderLayout());

        jPanel26.setMinimumSize(new java.awt.Dimension(300, 38));
        jPanel26.setPreferredSize(new java.awt.Dimension(500, 38));
        jPanel26.setLayout(new java.awt.BorderLayout());

        jLabel8.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("Tên khách hàng:");
        jLabel8.setAlignmentX(20.0F);
        jLabel8.setAlignmentY(20.0F);
        jPanel26.add(jLabel8, java.awt.BorderLayout.CENTER);

        jPanel14.add(jPanel26, java.awt.BorderLayout.LINE_START);

        jPanel27.setMinimumSize(new java.awt.Dimension(669, 38));
        jPanel27.setPreferredSize(new java.awt.Dimension(669, 38));
        jPanel27.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 7));

        txtTenKH.setMaximumSize(new java.awt.Dimension(350, 32));
        txtTenKH.setMinimumSize(new java.awt.Dimension(350, 32));
        txtTenKH.setPreferredSize(new java.awt.Dimension(350, 30));
        jPanel27.add(txtTenKH);

        jPanel14.add(jPanel27, java.awt.BorderLayout.CENTER);

        jPanel10.add(jPanel14);

        jPanel16.setMinimumSize(new java.awt.Dimension(829, 38));
        jPanel16.setLayout(new java.awt.BorderLayout());

        jPanel32.setMinimumSize(new java.awt.Dimension(300, 38));
        jPanel32.setPreferredSize(new java.awt.Dimension(500, 38));
        jPanel32.setLayout(new java.awt.BorderLayout());

        jLabel10.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel10.setText("Số điện thoại:");
        jLabel10.setAlignmentX(20.0F);
        jLabel10.setAlignmentY(20.0F);
        jPanel32.add(jLabel10, java.awt.BorderLayout.CENTER);

        jPanel16.add(jPanel32, java.awt.BorderLayout.LINE_START);

        jPanel35.setMinimumSize(new java.awt.Dimension(669, 38));
        jPanel35.setPreferredSize(new java.awt.Dimension(669, 38));
        jPanel35.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 7));

        txtSdtKH.setMaximumSize(new java.awt.Dimension(350, 32));
        txtSdtKH.setMinimumSize(new java.awt.Dimension(350, 32));
        txtSdtKH.setPreferredSize(new java.awt.Dimension(350, 30));
        jPanel35.add(txtSdtKH);

        jPanel16.add(jPanel35, java.awt.BorderLayout.CENTER);

        jPanel10.add(jPanel16);

        jPanel17.setMinimumSize(new java.awt.Dimension(829, 38));
        jPanel17.setLayout(new java.awt.BorderLayout());

        jPanel22.setMinimumSize(new java.awt.Dimension(300, 38));
        jPanel22.setPreferredSize(new java.awt.Dimension(500, 38));
        jPanel22.setLayout(new java.awt.BorderLayout());

        jLabel6.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Ngày mua:");
        jLabel6.setAlignmentX(20.0F);
        jLabel6.setAlignmentY(20.0F);
        jPanel22.add(jLabel6, java.awt.BorderLayout.CENTER);

        jPanel17.add(jPanel22, java.awt.BorderLayout.LINE_START);

        jPanel23.setMinimumSize(new java.awt.Dimension(669, 38));
        jPanel23.setPreferredSize(new java.awt.Dimension(669, 38));
        jPanel23.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 7));

        dateNgayTra.setMinimumSize(new java.awt.Dimension(182, 22));
        dateNgayTra.setPreferredSize(new java.awt.Dimension(150, 30));
        jPanel23.add(dateNgayTra);

        jPanel17.add(jPanel23, java.awt.BorderLayout.CENTER);

        jPanel10.add(jPanel17);

        jPanel18.setMinimumSize(new java.awt.Dimension(829, 38));
        jPanel18.setLayout(new java.awt.BorderLayout());

        jPanel19.setMinimumSize(new java.awt.Dimension(300, 38));
        jPanel19.setPreferredSize(new java.awt.Dimension(500, 38));
        jPanel19.setLayout(new java.awt.BorderLayout());
        jPanel18.add(jPanel19, java.awt.BorderLayout.LINE_START);

        jPanel20.setMinimumSize(new java.awt.Dimension(669, 38));
        jPanel20.setPreferredSize(new java.awt.Dimension(669, 38));
        jPanel20.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 5, 7));
        jPanel18.add(jPanel20, java.awt.BorderLayout.CENTER);

        jPanel10.add(jPanel18);

        jPanel8.add(jPanel10, java.awt.BorderLayout.CENTER);

        add(jPanel8, java.awt.BorderLayout.PAGE_START);
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnChiTiet;
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
    private java.awt.TextField txtMaHD;
    private java.awt.TextField txtMaPT;
    private java.awt.TextField txtSdtKH;
    private java.awt.TextField txtTenKH;
    // End of variables declaration//GEN-END:variables
}
